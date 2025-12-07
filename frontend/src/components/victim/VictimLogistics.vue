<script setup>
import { ref, reactive, defineEmits } from 'vue'

const emit = defineEmits(['submit', 'back'])

// State: 'select' or 'form'
const step = ref('select')
const selectedCategory = ref(null)

// Form data
const formData = reactive({
  // Housing
  peopleCount: 1,
  duration: '1-night',
  location: '',
  useGPS: false,

  // Transport
  passengerCount: 1,
  destination: '',
  timing: 'now',
  specificTime: '',

  // Supplies
  itemType: 'food',
  urgency: 'normal'
})

const categories = [
  {
    id: 'housing',
    title: 'Housing Needed',
    description: 'I need a place to stay',
    icon: '🏠',
    color: '#3b82f6'
  },
  {
    id: 'transport',
    title: 'Transport Needed',
    description: 'I need transportation',
    icon: '🚗',
    color: '#8b5cf6'
  },
  {
    id: 'supplies',
    title: 'Supplies Needed',
    description: 'I need food, water, or clothes',
    icon: '📦',
    color: '#f59e0b'
  }
]

const durationOptions = [
  { value: '1-night', label: '1 Night' },
  { value: '2-3-days', label: '2-3 Days' },
  { value: '1-week', label: '1 Week' },
  { value: 'longer', label: 'Longer' }
]

const supplyTypes = [
  { value: 'food', label: 'Food' },
  { value: 'water', label: 'Water' },
  { value: 'clothes', label: 'Clothes' },
  { value: 'medicine', label: 'Medicine' },
  { value: 'blankets', label: 'Blankets' },
  { value: 'other', label: 'Other' }
]

function selectCategory(categoryId) {
  selectedCategory.value = categoryId
  step.value = 'form'
}

function goBackToSelect() {
  step.value = 'select'
  selectedCategory.value = null
}

function useCurrentGPS() {
  formData.useGPS = true
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        formData.location = `GPS: ${position.coords.latitude.toFixed(4)}, ${position.coords.longitude.toFixed(4)}`
      },
      () => {
        formData.location = 'GPS unavailable'
      }
    )
  }
}

function submitRequest() {
  const payload = {
    category: selectedCategory.value,
    timestamp: new Date().toISOString()
  }

  if (selectedCategory.value === 'housing') {
    payload.peopleCount = formData.peopleCount
    payload.duration = formData.duration
    payload.location = formData.location
  } else if (selectedCategory.value === 'transport') {
    payload.passengerCount = formData.passengerCount
    payload.destination = formData.destination
    payload.timing = formData.timing
    if (formData.timing === 'later') {
      payload.specificTime = formData.specificTime
    }
  } else if (selectedCategory.value === 'supplies') {
    payload.itemType = formData.itemType
    payload.urgency = formData.urgency
  }

  console.log('📦 Logistics Request Payload:', payload)

  // Emit to parent
  emit('submit', payload)
}

function handleBack() {
  emit('back')
}

function getCategoryTitle() {
  return categories.find(c => c.id === selectedCategory.value)?.title || ''
}

function getCategoryIcon() {
  return categories.find(c => c.id === selectedCategory.value)?.icon || ''
}
</script>

<template>
  <div class="logistics-container">
    <!-- Back to VictimFlow button -->
    <button class="back-main-btn" @click="handleBack" v-if="step === 'select'">
      ← Back
    </button>

    <!-- STATE 1: Category Selection -->
    <div v-if="step === 'select'" class="category-selection">
      <div class="header">
        <h1 class="title">What do you need?</h1>
        <p class="subtitle">Select the type of help you require</p>
      </div>

      <div class="categories-grid">
        <button
          v-for="category in categories"
          :key="category.id"
          class="category-card"
          :style="{ '--category-color': category.color }"
          @click="selectCategory(category.id)"
        >
          <div class="category-icon">{{ category.icon }}</div>
          <h3 class="category-title">{{ category.title }}</h3>
          <p class="category-desc">{{ category.description }}</p>
        </button>
      </div>
    </div>

    <!-- STATE 2: Request Form -->
    <div v-if="step === 'form'" class="request-form">
      <button class="back-btn" @click="goBackToSelect">
        ← Back to categories
      </button>

      <div class="form-header">
        <span class="form-icon">{{ getCategoryIcon() }}</span>
        <h2 class="form-title">{{ getCategoryTitle() }}</h2>
      </div>

      <!-- HOUSING FORM -->
      <div v-if="selectedCategory === 'housing'" class="form-content">
        <div class="form-group">
          <label class="form-label">How many people?</label>
          <div class="number-input-wrapper">
            <button class="number-btn" @click="formData.peopleCount = Math.max(1, formData.peopleCount - 1)">−</button>
            <input type="number" v-model.number="formData.peopleCount" min="1" class="number-input" />
            <button class="number-btn" @click="formData.peopleCount++">+</button>
          </div>
        </div>

        <div class="form-group">
          <label class="form-label">Duration needed</label>
          <div class="duration-options">
            <button
              v-for="option in durationOptions"
              :key="option.value"
              class="duration-btn"
              :class="{ active: formData.duration === option.value }"
              @click="formData.duration = option.value"
            >
              {{ option.label }}
            </button>
          </div>
        </div>

        <div class="form-group">
          <label class="form-label">Your location</label>
          <div class="location-input-wrapper">
            <input
              type="text"
              v-model="formData.location"
              placeholder="Enter address or use GPS"
              class="text-input"
            />
            <button class="gps-btn" @click="useCurrentGPS">
              📍 GPS
            </button>
          </div>
        </div>
      </div>

      <!-- TRANSPORT FORM -->
      <div v-if="selectedCategory === 'transport'" class="form-content">
        <div class="form-group">
          <label class="form-label">How many passengers?</label>
          <div class="number-input-wrapper">
            <button class="number-btn" @click="formData.passengerCount = Math.max(1, formData.passengerCount - 1)">−</button>
            <input type="number" v-model.number="formData.passengerCount" min="1" class="number-input" />
            <button class="number-btn" @click="formData.passengerCount++">+</button>
          </div>
        </div>

        <div class="form-group">
          <label class="form-label">Destination</label>
          <input
            type="text"
            v-model="formData.destination"
            placeholder="Where do you need to go?"
            class="text-input full-width"
          />
        </div>

        <div class="form-group">
          <label class="form-label">When do you need transport?</label>
          <div class="timing-options">
            <button
              class="timing-btn"
              :class="{ active: formData.timing === 'now' }"
              @click="formData.timing = 'now'"
            >
              🚨 Now (Urgent)
            </button>
            <button
              class="timing-btn"
              :class="{ active: formData.timing === 'later' }"
              @click="formData.timing = 'later'"
            >
              🕐 Later
            </button>
          </div>
          <input
            v-if="formData.timing === 'later'"
            type="datetime-local"
            v-model="formData.specificTime"
            class="text-input full-width mt-3"
          />
        </div>
      </div>

      <!-- SUPPLIES FORM -->
      <div v-if="selectedCategory === 'supplies'" class="form-content">
        <div class="form-group">
          <label class="form-label">What do you need?</label>
          <div class="supply-options">
            <button
              v-for="supply in supplyTypes"
              :key="supply.value"
              class="supply-btn"
              :class="{ active: formData.itemType === supply.value }"
              @click="formData.itemType = supply.value"
            >
              {{ supply.label }}
            </button>
          </div>
        </div>

        <div class="form-group">
          <label class="form-label">How urgent is this?</label>
          <div class="urgency-options">
            <button
              class="urgency-btn critical"
              :class="{ active: formData.urgency === 'critical' }"
              @click="formData.urgency = 'critical'"
            >
              🔴 Critical
            </button>
            <button
              class="urgency-btn high"
              :class="{ active: formData.urgency === 'high' }"
              @click="formData.urgency = 'high'"
            >
              🟠 High
            </button>
            <button
              class="urgency-btn normal"
              :class="{ active: formData.urgency === 'normal' }"
              @click="formData.urgency = 'normal'"
            >
              🟢 Normal
            </button>
          </div>
        </div>
      </div>

      <!-- SUBMIT BUTTON -->
      <button class="submit-btn" @click="submitRequest">
        Request Help →
      </button>
    </div>
  </div>
</template>

<style scoped>
.logistics-container {
  max-width: 600px;
  width: 100%;
  padding: 20px;
  margin: 0 auto;
}

.back-main-btn {
  background: none;
  border: none;
  font-size: 15px;
  color: #666;
  cursor: pointer;
  padding: 8px 0;
  display: flex;
  align-items: center;
  gap: 8px;
  font-weight: 500;
  margin-bottom: 20px;
}

.back-main-btn:hover {
  color: #333;
}

/* Category Selection */
.category-selection {
  text-align: center;
}

.header {
  margin-bottom: 32px;
}

.title {
  font-size: 28px;
  font-weight: 700;
  color: #1a1a1a;
  margin: 0 0 8px 0;
}

.subtitle {
  font-size: 15px;
  color: #666;
  margin: 0;
}

.categories-grid {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.category-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 28px 20px;
  background: white;
  border: 2px solid #e5e7eb;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  text-align: center;
}

.category-card:hover {
  border-color: #3b82f6;
  transform: translateY(-2px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.category-icon {
  font-size: 48px;
  margin-bottom: 12px;
}

.category-title {
  font-size: 20px;
  font-weight: 700;
  color: #1a1a1a;
  margin: 0 0 6px 0;
}

.category-desc {
  font-size: 14px;
  color: #666;
  margin: 0;
}

/* Form Styles */
.request-form {
  animation: slideIn 0.3s ease;
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateX(20px);
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
}

.back-btn {
  background: none;
  border: none;
  font-size: 14px;
  color: #666;
  cursor: pointer;
  padding: 8px 0;
  margin-bottom: 20px;
  display: flex;
  align-items: center;
  gap: 6px;
}

.back-btn:hover {
  color: #333;
}

.form-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 32px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e5e7eb;
}

.form-icon {
  font-size: 40px;
}

.form-title {
  font-size: 24px;
  font-weight: 700;
  color: #1a1a1a;
  margin: 0;
}

.form-content {
  display: flex;
  flex-direction: column;
  gap: 28px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.form-label {
  font-size: 16px;
  font-weight: 600;
  color: #374151;
}

/* Number Input */
.number-input-wrapper {
  display: flex;
  align-items: center;
  gap: 0;
  max-width: 180px;
}

.number-btn {
  width: 48px;
  height: 48px;
  border: 2px solid #e5e7eb;
  background: #f9fafb;
  font-size: 24px;
  font-weight: 600;
  color: #374151;
  cursor: pointer;
  transition: all 0.2s;
}

.number-btn:first-child {
  border-radius: 12px 0 0 12px;
}

.number-btn:last-child {
  border-radius: 0 12px 12px 0;
}

.number-btn:hover {
  background: #e5e7eb;
}

.number-input {
  width: 80px;
  height: 48px;
  border: 2px solid #e5e7eb;
  border-left: none;
  border-right: none;
  text-align: center;
  font-size: 20px;
  font-weight: 600;
  color: #1a1a1a;
}

.number-input:focus {
  outline: none;
}

/* Duration Options */
.duration-options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.duration-btn {
  padding: 14px 16px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  background: white;
  font-size: 15px;
  font-weight: 500;
  color: #374151;
  cursor: pointer;
  transition: all 0.2s;
}

.duration-btn:hover {
  border-color: #3b82f6;
}

.duration-btn.active {
  border-color: #3b82f6;
  background: #eff6ff;
  color: #1d4ed8;
}

/* Text Input */
.text-input {
  padding: 14px 16px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  font-size: 16px;
  color: #1a1a1a;
  transition: all 0.2s;
}

.text-input:focus {
  outline: none;
  border-color: #3b82f6;
  box-shadow: 0 0 0 4px rgba(59, 130, 246, 0.1);
}

.text-input.full-width {
  width: 100%;
  box-sizing: border-box;
}

.text-input::placeholder {
  color: #9ca3af;
}

/* Location Input */
.location-input-wrapper {
  display: flex;
  gap: 10px;
}

.location-input-wrapper .text-input {
  flex: 1;
}

.gps-btn {
  padding: 14px 20px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  background: #f9fafb;
  font-size: 14px;
  font-weight: 600;
  color: #374151;
  cursor: pointer;
  transition: all 0.2s;
  white-space: nowrap;
}

.gps-btn:hover {
  background: #e5e7eb;
}

/* Timing Options */
.timing-options {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.timing-btn {
  padding: 16px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  background: white;
  font-size: 15px;
  font-weight: 500;
  color: #374151;
  cursor: pointer;
  transition: all 0.2s;
}

.timing-btn:hover {
  border-color: #8b5cf6;
}

.timing-btn.active {
  border-color: #8b5cf6;
  background: #f5f3ff;
  color: #6d28d9;
}

/* Supply Options */
.supply-options {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
}

.supply-btn {
  padding: 14px 12px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  background: white;
  font-size: 14px;
  font-weight: 500;
  color: #374151;
  cursor: pointer;
  transition: all 0.2s;
}

.supply-btn:hover {
  border-color: #f59e0b;
}

.supply-btn.active {
  border-color: #f59e0b;
  background: #fffbeb;
  color: #b45309;
}

/* Urgency Options */
.urgency-options {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
}

.urgency-btn {
  padding: 16px 12px;
  border: 2px solid #e5e7eb;
  border-radius: 12px;
  background: white;
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s;
}

.urgency-btn.critical {
  color: #dc2626;
}

.urgency-btn.critical:hover,
.urgency-btn.critical.active {
  border-color: #dc2626;
  background: #fef2f2;
}

.urgency-btn.high {
  color: #f59e0b;
}

.urgency-btn.high:hover,
.urgency-btn.high.active {
  border-color: #f59e0b;
  background: #fffbeb;
}

.urgency-btn.normal {
  color: #22c55e;
}

.urgency-btn.normal:hover,
.urgency-btn.normal.active {
  border-color: #22c55e;
  background: #f0fdf4;
}

/* Submit Button */
.submit-btn {
  width: 100%;
  margin-top: 32px;
  padding: 18px 24px;
  background: linear-gradient(135deg, #d74141 0%, #c53030 100%);
  color: white;
  border: none;
  border-radius: 14px;
  font-size: 18px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.3s ease;
}

.submit-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 25px rgba(215, 65, 65, 0.3);
}

.submit-btn:active {
  transform: translateY(0);
}

.mt-3 {
  margin-top: 12px;
}

/* Responsive */
@media (max-width: 480px) {
  .logistics-container {
    padding: 16px;
  }

  .title {
    font-size: 24px;
  }

  .category-card {
    padding: 20px 16px;
  }

  .category-icon {
    font-size: 40px;
  }

  .category-title {
    font-size: 18px;
  }

  .duration-options,
  .timing-options {
    grid-template-columns: 1fr;
  }

  .supply-options,
  .urgency-options {
    grid-template-columns: repeat(2, 1fr);
  }

  .location-input-wrapper {
    flex-direction: column;
  }

  .gps-btn {
    width: 100%;
  }
}
</style>

