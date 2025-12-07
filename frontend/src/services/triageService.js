// src/services/triageService.js

// Environment variable or fallback URL
const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:9090/api';
const DEFAULT_TIMEOUT_MS = 15000; // 15 seconds timeout

export const triageService = {
  /**
   * Sends symptoms description to the backend for analysis.
   * Handles timeouts and parses JSON error responses.
   * * @param {string} description - Free text description of symptoms
   * @returns {Promise<Object>} - Backend response (priority, summary, advice)
   */
  async analyzeSymptoms(description) {
    // AbortController to handle request timeouts (so the UI doesn't freeze)
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), DEFAULT_TIMEOUT_MS);

    try {
      const url = `${API_BASE}/triage/analyze`;
      const payload = { description };

      console.debug(`[triageService] 📡 Sending: ${url}`, payload);

      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify(payload),
        signal: controller.signal // Link the timeout signal here
      });

      // Clear the timeout if response is received
      clearTimeout(timeoutId);

      // Handle non-2xx responses
      if (!response.ok) {
        // Try to parse the error as JSON (Spring Boot usually sends JSON errors)
        let errorInfo;
        try {
          errorInfo = await response.json();
        } catch (e) {
          // Fallback to plain text if not JSON
          errorInfo = await response.text();
        }

        const errorMessage = typeof errorInfo === 'object'
            ? JSON.stringify(errorInfo)
            : errorInfo;

        throw new Error(`Server Error (${response.status}): ${errorMessage}`);
      }

      // Return successful data
      const data = await response.json();
      console.debug('[triageService] ✅ Response:', data);
      return data;

    } catch (error) {
      if (error.name === 'AbortError') {
        console.error('[triageService] ⏱️ Request timed out.');
        throw new Error('Server timed out. Please try again later.');
      }

      console.error('[triageService] ❌ Error:', error);
      throw error;
    } finally {
      // Safety cleanup
      clearTimeout(timeoutId);
    }
  }
};

export default triageService;