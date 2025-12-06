<template>
  <div class="victim-flow">
    <!-- Back button -->
    <button class="back-btn" @click="goBack">
      <span class="arrow">←</span> Back
    </button>

    <!-- Step 1: Location -->
    <LocationStep
      v-if="step === 1"
      @update:location="location => confirmedLocation = location"
      @update:coordinates="coords => confirmedCoordinates = coords"
      @next="nextStep"
    />

    <!-- Step 2: Needs -->
    <NeedsSelector
      v-if="step === 2"
      @update:selectedNeeds="needs => selectedNeeds = needs"
      @next="nextStep"
      @back="prevStep"
    />

    <!-- Step 3: Medical Info -->
    <MedicForm
      v-if="step === 3"
      @update:medicalInfo="info => medicalInfo = info"
      @submit="submitFlow"
      @back="prevStep"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import LocationStep from '@/components/victim/LocationStep.vue'
import NeedsSelector from '@/components/victim/NeedsSelector.vue'
import MedicForm from '@/components/victim/MedicForm.vue'

export default {
  name: 'VictimFlow',
  components: {
    LocationStep,
    NeedsSelector,
    MedicForm
  },
  setup() {
    const router = useRouter()
    const step = ref(1)
    const confirmedLocation = ref(null)
    const confirmedCoordinates = ref(null)
    const selectedNeeds = ref([])
    const medicalInfo = ref('')

    function nextStep() {
      step.value++
    }

    function prevStep() {
      step.value--
    }

    function submitFlow() {
      const data = {
        location: confirmedLocation.value,
        coordinates: confirmedCoordinates.value,
        needs: selectedNeeds.value,
        medicalInfo: medicalInfo.value
      }
      console.log('Victim Flow Data:', data)
      // TODO: Send data to backend
      alert('Your information has been submitted. Help is on the way!')
      router.push('/')
    }

    function goBack() {
      router.push('/')
    }

    return {
      step,
      nextStep,
      prevStep,
      goBack,
      confirmedLocation,
      confirmedCoordinates,
      selectedNeeds,
      medicalInfo,
      submitFlow
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

@media (max-width: 768px) {
  .victim-flow {
    padding: 30px 16px;
  }
}
</style>
