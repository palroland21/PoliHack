// Update: use configurable API base and send payload { description, lang }; add debug logging for easier testing
const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:9090/api';

export const triageService = {
  /**
   * Send symptoms text to backend triage endpoint.
   * @param {string} description - free text symptoms (EN/RO)
   * @param {number} lang - optional language hint (0 default)
   */
  async analyzeSymptoms(description, lang = 0) {
    try {
      const url = `${API_BASE}/triage/analyze`;
      const payload = { description, lang };
      // Debug logging so you can see in the browser console what is sent
      console.debug('[triageService] POST', url, payload);

      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      });

      if (!response.ok) {
        const text = await response.text().catch(() => '');
        throw new Error(`HTTP error ${response.status}: ${text}`);
      }

      return await response.json();
    } catch (error) {
      console.error('Triage API error:', error);
      // Re-throw a friendly error for UI handling
      throw new Error('Failed to contact triage backend. See console for details.');
    }
  }
};

export default triageService;
