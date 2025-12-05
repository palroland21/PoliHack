<template>
  <div>
    <h2>Flux Victima</h2>
    <!-- Step 1: Location -->
    <div v-if="step === 1">
      <h3>Pasul 1: Alege Locația</h3>
      <!-- Location selection UI -->
      <button @click="nextStep">Pasul Următor</button>
    </div>

    <!-- Step 2: Needs -->
    <div v-if="step === 2">
      <h3>Pasul 2: Alege Nevoile</h3>
      <!-- Needs selection UI -->
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'VictimFlow',
  setup() {
    const step = ref(1)

    function nextStep() {
      step.value++
    }

    return {
      step,
      nextStep
    }
  }
}
</script>
