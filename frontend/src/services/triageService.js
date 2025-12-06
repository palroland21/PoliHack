const API_URL = 'http://localhost:9092/api/triage';

export const triageService = {
  async analyzeSymptoms(description, age) {
    try {
      const response = await fetch(`${API_URL}/analyze`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          description: description,
          age: parseInt(age)
        })
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      return await response.json();
    } catch (error) {
      console.error('Triage API error:', error);
      throw error;
    }
  }
};

export default triageService;

