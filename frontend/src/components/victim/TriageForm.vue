<script setup>
import { ref, computed, defineEmits } from 'vue'
import triageService from '@/services/triageService'

const emit = defineEmits(['update:triageResult', 'submit', 'back'])

const symptoms = ref('')
const isAnalyzing = ref(false)
const triageResult = ref(null)
const errorMessage = ref('')

// Helper to map the AI priority (1, 2, 3) to UI details
const getSeverityDetails = (priority, category, advice, summary) => {
  let color, levelClass

  switch (priority) {
    case 1:
      color = '#dc2626' // Red
      levelClass = 'critical'
      break
    case 2:
      color = '#f59e0b' // Amber
      levelClass = 'medium'
      break
    case 3:
      color = '#22c55e' // Green
      levelClass = 'low'
      break
    default:
      color = '#6b7280'
      levelClass = 'general'
  }

  return {
    level: priority,
    levelText: category || (priority === 1 ? 'URGENT' : priority === 2 ? 'MODERATE' : 'MILD'),
    color,
    levelClass,
    advice: advice || '',
    summary: summary || ''
  }
}

// Analyze symptoms via AI Backend
async function analyzeSymptoms() {
  if (!symptoms.value.trim()) {
    return
  }

  isAnalyzing.value = true
  errorMessage.value = ''
  triageResult.value = null

  try {
    // Call the Spring Boot AI Controller via triageService
    const response = await triageService.analyzeSymptoms(symptoms.value, 0)

    // Map the response to UI details
    triageResult.value = getSeverityDetails(
      response.priority,
      response.category,
      response.advice,
      response.summary
    )

  } catch (error) {
    console.error('AI Triage failed:', error)

    // User-friendly error message
    if (error.message && error.message.includes('403')) {
      errorMessage.value = "AI service temporarily unavailable. Using fallback assessment."
    } else {
      errorMessage.value = "Could not reach AI service. Using fallback assessment."
    }

    // Fallback: Use keyword-based local triage
    triageResult.value = performLocalTriage(symptoms.value)
  } finally {
    isAnalyzing.value = false
  }
}

// Submit final result to parent
async function submitTriage() {
  if (!triageResult.value) {
    return
  }

  // Emit success to parent to move to next step
  emit('update:triageResult', triageResult.value)
  emit('submit')
}

function handleBack() {
  emit('back')
}

// Computed property to check if symptoms are entered
const hasSymptoms = computed(() => symptoms.value.trim().length > 0)
</script>

<template>
  <div class="step-container">
    <div class="step-header">
      <div class="step-icon">🤖</div>
      <h1 class="step-title">Step 3: AI Triage Assistant</h1>
      <p class="step-description">
        Describe your situation in English or Romanian. Our AI will assess the urgency.
      </p>
    </div>

    <div class="form-section">
      <div class="form-group">
        <label for="symptoms">Describe your symptoms:</label>
        <textarea
            id="symptoms"
            v-model="symptoms"
            class="symptoms-input"
            placeholder="E.g., I cannot breathe, chest pain, bleeding leg... / Am dureri de cap și febră mare..."
            rows="5"
            @input="triageResult = null"
            :disabled="isAnalyzing"
        ></textarea>
      </div>


      <div v-if="errorMessage" class="error-banner">
        ❌ {{ errorMessage }}
      </div>

      <button
          class="btn btn-analyze"
          @click="analyzeSymptoms"
          :disabled="!hasSymptoms || isAnalyzing"
      >
        <span v-if="isAnalyzing" class="spinner">↻</span>
        {{ isAnalyzing ? 'Consulting AI...' : '🔍 Analyze Severity' }}
      </button>

      <transition name="fade">
        <div v-if="triageResult" class="triage-result" :style="{ borderColor: triageResult.color }">
          <div class="result-header" :style="{ backgroundColor: triageResult.color }">
            <span class="priority-number">{{ triageResult.level }}</span>
            <span class="level-text">{{ triageResult.levelText }}</span>
          </div>

          <div class="result-body">
            <div v-if="triageResult.summary" class="result-section">
              <h4>📋 Summary</h4>
              <p>{{ triageResult.summary }}</p>
            </div>

            <div v-if="triageResult.advice" class="result-section">
              <h4>💡 Advice</h4>
              <p>{{ triageResult.advice }}</p>
            </div>

            <div class="urgency-info">
              <span v-if="triageResult.level === 1">🚨 CALL AMBULANCE IMMEDIATELY!</span>
              <span v-else-if="triageResult.level === 2">⚠️ See a doctor within 24 hours</span>
              <span v-else>✅ Home treatment or family doctor</span>
            </div>
          </div>
        </div>
      </transition>
    </div>

    <div class="button-group">
      <button class="btn btn-secondary" @click="handleBack" :disabled="isAnalyzing">
        ← Back
      </button>
      <button
          class="btn btn-continue"
          @click="submitTriage"
          :disabled="!triageResult || isAnalyzing"
      >
        Confirm & Submit <span class="arrow-right">→</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.step-container {
  max-width: 700px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.step-header { text-align: center; margin-bottom: 30px; }
.step-icon { font-size: 60px; margin-bottom: 20px; }
.step-title { font-size: 28px; font-weight: 700; color: #1a1a1a; margin-bottom: 12px; }
.step-description { font-size: 15px; color: #666; line-height: 1.5; }

.form-section { width: 100%; margin-bottom: 30px; }

.symptoms-input {
  width: 100%;
  padding: 16px;
  border: 2px solid #ddd;
  border-radius: 12px;
  font-size: 16px;
  font-family: inherit;
  resize: vertical;
  min-height: 120px;
  transition: all 0.3s ease;
  box-sizing: border-box;
}

.symptoms-input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.1);
}

.error-banner {
  background-color: #fee2e2;
  color: #b91c1c;
  padding: 10px;
  border-radius: 8px;
  margin-top: 10px;
  font-size: 14px;
  text-align: center;
}

.btn {
  padding: 14px 24px;
  border: none;
  border-radius: 12px;
  font-weight: 600;
  font-size: 15px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn:disabled { opacity: 0.6; cursor: not-allowed; }

.btn-analyze {
  width: 100%;
  margin-top: 16px;
  background: linear-gradient(135deg, #6366f1 0%, #4f46e5 100%); /* AI Purple theme */
  color: white;
}

.btn-analyze:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(79, 70, 229, 0.3);
}

/* Result Box Styles */
.triage-result {
  margin-top: 24px;
  border: 2px solid;
  border-radius: 12px;
  overflow: hidden;
  background: white;
  box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
}

.result-header {
  padding: 16px 20px;
  color: white;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.level-badge {
  background: rgba(0, 0, 0, 0.2);
  padding: 4px 12px;
  border-radius: 20px;
  font-weight: 700;
  font-size: 14px;
}

.result-body { padding: 20px; text-align: center; }
.ai-desc { font-size: 18px; font-weight: 500; color: #333; margin-bottom: 10px; }
.disclaimer { font-size: 12px; color: #999; font-style: italic; }

/* Navigation Buttons */
.button-group { width: 100%; display: flex; gap: 16px; }

.btn-secondary {
  flex: 0.4;
  background: #f0f0f0;
  color: #333;
  border: 1.5px solid #ddd;
}

.btn-continue {
  flex: 1;
  background: linear-gradient(135deg, #10b981 0%, #059669 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.btn-continue:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(16, 185, 129, 0.3);
}

.spinner {
  display: inline-block;
  animation: spin 1s linear infinite;
  margin-right: 5px;
}

@keyframes spin { 100% { transform: rotate(360deg); } }
.fade-enter-active, .fade-leave-active { transition: opacity 0.5s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }

/* Form groups */
.form-group {
  margin-bottom: 16px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 600;
  color: #374151;
  font-size: 14px;
}


/* Priority number in header */
.priority-number {
  width: 50px;
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255,255,255,0.3);
  border-radius: 50%;
  font-size: 24px;
  font-weight: bold;
}

.level-text {
  font-size: 18px;
  font-weight: 700;
  text-transform: uppercase;
}

/* Result sections */
.result-section {
  background: #f9fafb;
  padding: 16px;
  border-radius: 8px;
  margin-bottom: 12px;
  text-align: left;
}

.result-section h4 {
  margin: 0 0 8px 0;
  font-size: 14px;
  color: #6b7280;
}

.result-section p {
  margin: 0;
  font-size: 15px;
  color: #1f2937;
  line-height: 1.5;
}

.urgency-info {
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  padding: 12px;
  background: #f3f4f6;
  border-radius: 8px;
  margin-top: 8px;
}
</style>