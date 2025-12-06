<script setup>
import { ref, defineEmits } from 'vue'

const emit = defineEmits(['update:medicalInfo', 'submit', 'back'])

const medicalInfo = ref('')

function handleSubmit() {
  emit('update:medicalInfo', medicalInfo.value)
  emit('submit')
}

function handleBack() {
  emit('back')
}
</script>

<template>
  <div class="step-container">
    <div class="step-header">
      <div class="step-icon">🏥</div>
      <h1 class="step-title">Step 3: Health Information</h1>
      <p class="step-description">Tell us about any medical conditions or health concerns</p>
    </div>

    <div class="form-group">
      <textarea
        class="address-input"
        placeholder="Any medical conditions, allergies, or important health information..."
        v-model="medicalInfo"
        rows="6"
      ></textarea>
    </div>

    <div class="button-group">
      <button class="btn btn-secondary" @click="handleBack">← Back</button>
      <button class="btn btn-continue" @click="handleSubmit">
        Submit <span class="arrow-right">→</span>
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
  text-align: center;
}

.step-header {
  margin-bottom: 50px;
}

.step-icon {
  font-size: 70px;
  margin-bottom: 25px;
  opacity: 0.9;
  display: inline-block;
  filter: drop-shadow(0 4px 8px rgba(0, 0, 0, 0.05));
}

.step-title {
  font-size: 36px;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 16px;
  letter-spacing: -0.5px;
}

.step-description {
  font-size: 17px;
  color: #666;
  line-height: 1.6;
}

.form-group {
  width: 100%;
  margin-bottom: 50px;
}

.address-input {
  width: 100%;
  padding: 16px 18px;
  border: 1.5px solid #ddd;
  border-radius: 10px;
  font-size: 15px;
  color: #333;
  background-color: white;
  transition: all 0.3s ease;
  font-family: inherit;
  min-width: 0;
  resize: vertical;
  min-height: 120px;
  box-sizing: border-box;
}

.address-input:focus {
  outline: none;
  background-color: white;
  border-color: #d74141;
  box-shadow: 0 0 0 4px rgba(215, 65, 65, 0.08);
}

.address-input::placeholder {
  color: #aaa;
}

.button-group {
  width: 100%;
  display: flex;
  gap: 16px;
  align-items: center;
  justify-content: center;
}

.button-group .btn {
  flex: 1;
}

.button-group .btn-secondary {
  flex: 0.5;
}

.btn {
  padding: 16px 24px;
  border: none;
  border-radius: 14px;
  font-weight: 600;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-family: inherit;
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn-secondary {
  background-color: #f0f0f0;
  color: #333;
  border: 1.5px solid #ddd;
}

.btn-secondary:hover:not(:disabled) {
  background-color: #e5e5e5;
  transform: translateY(-3px);
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
}

.btn-continue:hover:not(:disabled) {
  transform: translateY(-3px);
  box-shadow: 0 12px 24px rgba(215, 65, 65, 0.25);
}

.btn-continue:active:not(:disabled) {
  transform: translateY(-1px);
}

.arrow-right {
  font-size: 20px;
  transition: transform 0.3s ease;
}

.btn-continue:hover:not(:disabled) .arrow-right {
  transform: translateX(4px);
}

@media (max-width: 768px) {
  .step-container {
    max-width: 100%;
  }

  .step-title {
    font-size: 28px;
  }

  .step-description {
    font-size: 15px;
  }

  .button-group {
    flex-direction: column-reverse;
  }

  .button-group .btn {
    width: 100%;
  }
}
</style>