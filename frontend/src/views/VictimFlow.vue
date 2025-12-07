<template>
  <div class="victim-flow">
    <button class="back-btn" @click="goBack" v-if="step < 4">
      <span class="arrow">←</span> Back
    </button>

    <LocationStep
        v-if="step === 1"
        @update:location="location => confirmedLocation = location"
        @update:coordinates="coords => confirmedCoordinates = coords"
        @next="nextStep"
    />

    <NeedsSelector
        v-if="step === 2"
        @update:selectedNeeds="needs => selectedNeeds = needs"
        @next="nextStep"
        @back="prevStep"
    />

    <TriageForm
        v-if="step === 3"
        @update:triageResult="result => triageResult = result"
        @submit="showResult"
        @back="prevStep"
    />

    <div v-if="step === 4" class="result-container">
      <div class="success-animation">
        <div class="checkmark">✓</div>
      </div>
      <h1 class="result-title">Help is on the way!</h1>
      <p class="result-subtitle">Your request has been submitted successfully</p>

      <div class="priority-card" v-if="triageResult" :class="'priority-' + triageResult.level">
        <div class="priority-header">
          <span class="priority-badge">Priority {{ triageResult.level }}</span>
          <span class="priority-label">{{ triageResult.levelText }}</span>
        </div>
        <div class="priority-message">
          <span v-if="triageResult.level === 1">🚨 Emergency services have been notified</span>
          <span v-else-if="triageResult.level === 2">⚠️ A responder will contact you soon</span>
          <span v-else>✅ Your request is in queue</span>
        </div>
      </div>

      <div class="summary-section">
        <h3 class="summary-title">📋 Request Summary</h3>

        <div class="summary-item">
          <div class="summary-icon">📍</div>
          <div class="summary-content">
            <span class="summary-label">Location</span>
            <span class="summary-value">{{ confirmedLocation || 'Not provided' }}</span>
          </div>
        </div>

        <div class="summary-item">
          <div class="summary-icon">🆘</div>
          <div class="summary-content">
            <span class="summary-label">Type of Help</span>
            <span class="summary-value">{{ getNeedsText() }}</span>
          </div>
        </div>

        <div class="summary-item">
          <div class="summary-icon">🏥</div>
          <div class="summary-content">
            <span class="summary-label">Triage Level</span>
            <span class="summary-value" :class="'level-' + (triageResult ? triageResult.level : 3)">
              Level {{ triageResult ? triageResult.level : 'N/A' }}
            </span>
          </div>
        </div>
      </div>

      <div class="next-steps">
        <h4>What happens next?</h4>
        <ul>
          <li>Stay at your reported location if possible</li>
          <li>Keep your phone accessible</li>
          <li>A rescuer will contact you shortly</li>
        </ul>
      </div>

      <button class="btn btn-home" @click="goHome">
        Return to Home
      </button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import LocationStep from '@/components/victim/LocationStep.vue'
import NeedsSelector from '@/components/victim/NeedsSelector.vue'
import TriageForm from '@/components/victim/TriageForm.vue'

export default {
  name: 'VictimFlow',
  components: {
    LocationStep,
    NeedsSelector,
    TriageForm
  },
  setup() {
    const router = useRouter()
    const step = ref(1)
    const confirmedLocation = ref(null)
    const confirmedCoordinates = ref(null)
    const selectedNeeds = ref([])
    const triageResult = ref(null)

    function nextStep() {
      step.value++
    }

    function prevStep() {
      step.value--
    }

    function showResult() {
      step.value = 4
      console.log('Victim Flow Data:', {
        location: confirmedLocation.value,
        coordinates: confirmedCoordinates.value,
        needs: selectedNeeds.value,
        triageResult: triageResult.value
      })
    }

    function goBack() {
      if (step.value === 1) {
        router.push('/')
      } else {
        step.value--
      }
    }

    function goHome() {
      router.push('/')
    }

    function getNeedsText() {
      const needsMap = {
        1: 'Medical Assistance',
        2: 'Resources & Logistics'
      }
      if (selectedNeeds.value.length === 0) return 'Not specified'
      return selectedNeeds.value.map(id => needsMap[id] || id).join(', ')
    }

    return {
      step,
      nextStep,
      prevStep,
      goBack,
      goHome,
      getNeedsText,
      confirmedLocation,
      confirmedCoordinates,
      selectedNeeds,
      triageResult,
      showResult
    }
  }
}
</script>

<style scoped>
.victim-flow {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f5f5 0%, #fafafa 100%);
  padding: 40px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', sans-serif;
}

.back-btn {
  align-self: flex-start;
  max-width: 700px;
  width: 100%;
  background: none;
  border: none;
  font-size: 15px;
  color: #666;
  cursor: pointer;
  padding: 12px 0;
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 30px;
  font-weight: 500;
  transition: all 0.3s ease;
}

.back-btn:hover {
  color: #333;
  gap: 12px;
}

.back-btn .arrow {
  font-size: 20px;
}

.result-container {
  max-width: 600px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 20px;
}

.success-animation {
  margin-bottom: 24px;
}

.checkmark {
  width: 80px;
  height: 80px;
  background: linear-gradient(135deg, #22c55e 0%, #16a34a 100%);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40px;
  color: white;
  animation: scaleIn 0.5s ease;
}

@keyframes scaleIn {
  0% { transform: scale(0); }
  50% { transform: scale(1.2); }
  100% { transform: scale(1); }
}

.result-title {
  font-size: 28px;
  font-weight: 700;
  color: #1a1a1a;
  margin: 0 0 8px 0;
}

.result-subtitle {
  font-size: 15px;
  color: #666;
  margin: 0 0 30px 0;
}

/* Priority Card */
.priority-card {
  width: 100%;
  border-radius: 16px;
  overflow: hidden;
  margin-bottom: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.priority-card.priority-1 {
  background: linear-gradient(135deg, #dc2626 0%, #b91c1c 100%);
}

.priority-card.priority-2 {
  background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
}

.priority-card.priority-3 {
  background: linear-gradient(135deg, #22c55e 0%, #16a34a 100%);
}

.priority-header {
  padding: 20px;
  color: white;
  display: flex;
  align-items: center;
  gap: 16px;
}

.priority-badge {
  background: rgba(255, 255, 255, 0.25);
  padding: 6px 14px;
  border-radius: 20px;
  font-weight: 700;
  font-size: 14px;
}

.priority-label {
  font-size: 18px;
  font-weight: 600;
  text-transform: uppercase;
}

.priority-message {
  padding: 16px 20px;
  background: rgba(255, 255, 255, 0.15);
  color: white;
  font-size: 15px;
  font-weight: 500;
}

.summary-section {
  width: 100%;
  background: white;
  border-radius: 16px;
  padding: 24px;
  margin-bottom: 24px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  text-align: left;
}

.summary-title {
  font-size: 16px;
  font-weight: 700;
  color: #374151;
  margin: 0 0 20px 0;
}

.summary-item {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 14px 0;
  border-bottom: 1px solid #f0f0f0;
}

.summary-item:last-child {
  border-bottom: none;
  padding-bottom: 0;
}

.summary-icon {
  font-size: 24px;
  width: 40px;
  height: 40px;
  background: #f3f4f6;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.summary-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.summary-label {
  font-size: 12px;
  font-weight: 600;
  color: #9ca3af;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.summary-value {
  font-size: 15px;
  color: #1f2937;
  font-weight: 500;
}

.level-1 { color: #dc2626; }
.level-2 { color: #f59e0b; }
.level-3 { color: #22c55e; }

.next-steps {
  width: 100%;
  background: #f0fdf4;
  border: 1px solid #bbf7d0;
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 24px;
  text-align: left;
}

.next-steps h4 {
  margin: 0 0 12px 0;
  font-size: 14px;
  font-weight: 600;
  color: #166534;
}

.next-steps ul {
  margin: 0;
  padding-left: 20px;
}

.next-steps li {
  font-size: 14px;
  color: #15803d;
  margin-bottom: 8px;
  line-height: 1.5;
}

.next-steps li:last-child {
  margin-bottom: 0;
}

.btn-home {
  width: 100%;
  padding: 16px 24px;
  background: linear-gradient(135deg, #d74141 0%, #c53030 100%);
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-home:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(215, 65, 65, 0.3);
}

@media (max-width: 768px) {
  .victim-flow {
    padding: 30px 16px;
  }

  .result-title {
    font-size: 24px;
  }

  .priority-label {
    font-size: 14px;
  }

  .summary-section {
    padding: 16px;
  }
}
</style>