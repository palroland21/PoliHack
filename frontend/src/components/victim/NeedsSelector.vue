<script setup>
import { ref, defineEmits } from 'vue'

const emit = defineEmits(['update:selectedNeeds', 'next', 'back'])

const selectedNeeds = ref([])

const needs = [
  {
    id: 1,
    label: 'Medical Assistance',
    description: 'I need a doctor or ambulance',
    icon: '🩺',
    color: '#d74141'
  },
  {
    id: 2,
    label: 'Resources & Logistics',
    description: 'Water, food, transport, shelter',
    icon: '📦',
    color: '#e8a838'
  }
]

function toggleNeed(id) {
  const index = selectedNeeds.value.indexOf(id)
  if (index > -1) {
    selectedNeeds.value.splice(index, 1)
  } else {
    selectedNeeds.value.push(id)
  }
  emit('update:selectedNeeds', selectedNeeds.value)
}

function handleContinue() {
  emit('next')
}
</script>

<template>
  <div class="step-container">

    <div class="step-header">
      <h1 class="step-title">Step 2: What Do You Need?</h1>
      <p class="step-description">Select the category of help you need</p>
    </div>

    <div class="needs-list">
      <button
        v-for="need in needs"
        :key="need.id"
        class="need-card"
        :class="{ active: selectedNeeds.includes(need.id) }"
        @click="toggleNeed(need.id)"
      >
        <div class="icon-box" :style="{ backgroundColor: need.color + '20' }">
          <span class="need-icon">{{ need.icon }}</span>
        </div>
        <div class="text-content">
          <h3 class="need-label" :style="{ color: need.color }">{{ need.label }}</h3>
          <p class="need-description">{{ need.description }}</p>
        </div>
      </button>
    </div>

    <button class="btn btn-continue" @click="handleContinue" :disabled="selectedNeeds.length === 0">
      Continue <span class="arrow-right">→</span>
    </button>
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


.step-header {
  text-align: center;
  margin-bottom: 40px;
}

.step-title {
  font-size: 32px;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 12px;
}

.step-description {
  font-size: 16px;
  color: #666;
}

.needs-list {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-bottom: 40px;
}

.need-card {
  width: 100%;
  padding: 20px 24px;
  border: 2px solid #f0e0e0;
  border-radius: 16px;
  background: linear-gradient(135deg, rgba(215, 65, 65, 0.05) 0%, rgba(255, 255, 255, 1) 100%);
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  gap: 20px;
  text-align: left;
}

.need-card:hover {
  border-color: #d74141;
  transform: translateY(-2px);
  box-shadow: 0 8px 20px rgba(215, 65, 65, 0.1);
}

.need-card.active {
  border-color: #d74141;
  background: linear-gradient(135deg, rgba(215, 65, 65, 0.1) 0%, rgba(215, 65, 65, 0.05) 100%);
  box-shadow: 0 0 0 3px rgba(215, 65, 65, 0.1);
}

.icon-box {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.need-icon {
  font-size: 28px;
}

.text-content {
  flex: 1;
}

.need-label {
  font-size: 18px;
  font-weight: 700;
  margin: 0 0 4px 0;
}

.need-description {
  font-size: 14px;
  color: #666;
  margin: 0;
}

.btn-continue {
  width: 100%;
  background: linear-gradient(135deg, #d74141 0%, #c56a6a 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 18px 24px;
  font-size: 17px;
  font-weight: 600;
  border: none;
  border-radius: 14px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-continue:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn-continue:hover:not(:disabled) {
  transform: translateY(-3px);
  box-shadow: 0 12px 24px rgba(215, 65, 65, 0.25);
}

.arrow-right {
  font-size: 20px;
  transition: transform 0.3s ease;
}

.btn-continue:hover:not(:disabled) .arrow-right {
  transform: translateX(4px);
}

@media (max-width: 768px) {
  .step-title {
    font-size: 26px;
  }

  .need-card {
    padding: 16px 20px;
  }

  .icon-box {
    width: 48px;
    height: 48px;
  }

  .need-icon {
    font-size: 24px;
  }

  .need-label {
    font-size: 16px;
  }
}
</style>
