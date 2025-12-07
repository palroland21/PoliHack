<template>
  <div class="resources-container">
    <div class="header-center">
      <div class="icon-circle-large">
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path><polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline><line x1="12" y1="22.08" x2="12" y2="12"></line></svg>
      </div>
      <h2>Resources Offer</h2>
      <p>What resources can you provide?</p>
    </div>

    <form @submit.prevent="submitOffer" class="form-content">
      <div class="form-group">
        <label>Resource Types (select all that apply)</label>
        <div class="grid-3">

          <div
              class="selection-card"
              :class="{ active: form.selectedResources.includes('water') }"
              @click="toggleResource('water')"
          >
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 2.69l5.66 5.66a8 8 0 1 1-11.31 0z"></path></svg>
            </div>
            <span class="card-title">Water</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.selectedResources.includes('food') }"
              @click="toggleResource('food')"
          >
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 2v7c0 1.1.9 2 2 2h4a2 2 0 0 0 2-2V2"/><path d="M7 2v20"/><path d="M21 15V2v0a5 5 0 0 0-5 5v6c0 1.1.9 2 2 2h3Zm0 0v7"/></svg>
            </div>
            <span class="card-title">Food</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.selectedResources.includes('clothes') }"
              @click="toggleResource('clothes')"
          >
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20.38 3.46L16 2a4 4 0 0 1-8 0L3.62 3.46a2 2 0 0 0-1.34 2.23l.58 3.47a1 1 0 0 0 .99.84H6v10c0 1.1.9 2 2 2h8a2 2 0 0 0 2-2V10h2.15a1 1 0 0 0 .99-.84l.58-3.47a2 2 0 0 0-1.34-2.23z"/></svg>
            </div>
            <span class="card-title">Clothes</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.selectedResources.includes('meds') }"
              @click="toggleResource('meds')"
          >
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M10.5 20.5 19 12a4.95 4.95 0 1 0-7-7l-8.5 8.5a4.95 4.95 0 0 0 7 7Z"/><path d="m8.5 8.5 7 7"/></svg>
            </div>
            <span class="card-title">Meds</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.selectedResources.includes('kids') }"
              @click="toggleResource('kids')"
          >
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"/><path d="M8 14s1.5 2 4 2 4-2 4-2"/><line x1="9" y1="9" x2="9.01" y2="9"/><line x1="15" y1="9" x2="15.01" y2="9"/></svg>
            </div>
            <span class="card-title">For Kids</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.selectedResources.includes('other') }"
              @click="toggleResource('other')"
          >
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 8V4H8"/><rect width="16" height="12" x="4" y="8" rx="2"/><path d="M2 14h2"/><path d="M20 14h2"/><path d="M15 13a3 3 0 1 0-6 0"/></svg>
            </div>
            <span class="card-title">Other</span>
          </div>

        </div>
      </div>

      <div class="form-group">
        <label>Estimated Quantity</label>
        <input
            type="text"
            v-model="form.quantity"
            placeholder="ex: 50 bottles of water, 20 food packs"
            class="input-field"
            required
        />
      </div>

      <div class="form-group">
        <label>Pickup Location</label>
        <input
            type="text"
            v-model="form.location"
            placeholder="ex: Main Street no. 10, Downtown"
            class="input-field"
            required
        />
      </div>

      <div class="form-group">
        <label>Additional Details</label>
        <textarea
            v-model="form.details"
            placeholder="Other relevant information about the resources..."
            class="input-field textarea-field"
        ></textarea>
      </div>

      <button
          type="submit"
          class="submit-btn"
          :disabled="isLoading"
      >
        <span v-if="!isLoading">Register Offer →</span>
        <span v-else>Sending...</span>
      </button>

      <div class="message-container">
        <p v-if="successMessage" class="success-text">
          ✅ {{ successMessage }}
        </p>

        <p v-if="errorMessage" class="error-text">
          ⚠️ {{ errorMessage }}
        </p>
      </div>

    </form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import axios from 'axios';

const isLoading = ref(false);
const errorMessage = ref('');
const successMessage = ref('');


const form = reactive({
  selectedResources: [],
  quantity: '',
  location: '',
  details: ''
});

const toggleResource = (type) => {
  if (form.selectedResources.includes(type)) {
    form.selectedResources = form.selectedResources.filter(item => item !== type);
  } else {
    form.selectedResources.push(type);
  }
};

const getLoggedUserId = () => {
  const userId = localStorage.getItem('loggedUserId');
  return (userId && !isNaN(Number(userId))) ? Number(userId) : 0;
};

const submitOffer = async () => {
  errorMessage.value = '';
  successMessage.value = '';

  const loggedUserId = getLoggedUserId();
  const token = localStorage.getItem('token');

  if (loggedUserId === 0 || !token) {
    errorMessage.value = "User not authenticated. Please log in before submitting an offer.";
    return;
  }

  //validari
  if (form.selectedResources.length === 0 || !form.quantity || !form.location) {
    errorMessage.value = "Please select at least one resource type and complete Quantity and Location fields.";
    return;
  }

  isLoading.value = true;

  //prepare object for backend
  //trimitem array de stringuri
  const requestPayload = {
    resourceTypes: form.selectedResources.map(resource => {
      if (resource === 'kids') return 'FOR_KIDS';
      if (resource === 'meds') return 'MEDS';
      if (resource === 'clothes') return 'CLOTHES';
      return resource.toUpperCase().replace(/\s/g, '_');
    }),
    quantity: form.quantity,
    pickupLocation: form.location,
    additionalDetails: form.details,
    rescuerId: loggedUserId
  };

  try {
    const response = await axios.post('http://localhost:9090/resources/add', requestPayload, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    });

    console.log("Resource Offer Success:", response.data);

    successMessage.value = "Resource offer successfully registered!";

    form.selectedResources = [];
    form.quantity = '';
    form.location = '';
    form.details = '';

  } catch (error) {
    console.error("Technical error details:", error);
    if (error.response && error.response.status === 403) {
      errorMessage.value = "Access denied. Please log in again.";
    } else {
      errorMessage.value = "Something went wrong. Check if the server is running or if data format is correct.";
    }
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>


.resources-container {
  padding: 10px;
  animation: fadeIn 0.3s ease-in-out;
  max-width: 600px;
  margin: 0 auto;
}

.header-center {
  text-align: center;
  margin-bottom: 30px;
}

.icon-circle-large {
  width: 60px;
  height: 60px;
  background-color: #fff3cd;
  color: #ffc107;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 15px;
}

.header-center h2 {
  margin: 0;
  font-size: 1.5rem;
  color: #333;
}

.header-center p {
  color: #666;
  font-size: 0.9rem;
  margin-top: 5px;
}

.form-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-group label {
  display: block;
  font-weight: 600;
  margin-bottom: 10px;
  color: #333;
  font-size: 0.95rem;
}

.grid-3 {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
}

.selection-card {
  border: 1px solid #eee;
  background-color: #fff;
  border-radius: 12px;
  padding: 15px 5px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  text-align: center;
  height: 80px;
}

.selection-card:hover {
  border-color: #ffc107;
  background-color: #fffbf0;
}

.selection-card.active {
  background-color: #fff3cd;
  border-color: #ffc107;
  color: #d39e00;
}

.card-icon {
  margin-bottom: 6px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.card-icon svg {
  width: 24px;
  height: 24px;
}

.card-title {
  font-weight: 600;
  font-size: 0.85rem;
}

.input-field {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  background-color: #f9f9f9;
  box-sizing: border-box;
  font-family: inherit;
}
.textarea-field {
  resize: vertical;
  min-height: 80px;
}

.input-field:focus {
  outline: none;
  border-color: #ffc107;
  background-color: #fff;
}

.submit-btn {
  margin-top: 10px;
  width: 100%;
  padding: 15px;
  background-color: #ffc107;
  color: #333;
  border: none;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.3s;
}

.submit-btn:hover:not(:disabled) {
  background-color: #ffad33;
}

.submit-btn:disabled {
  background-color: #fce3a2;
  cursor: not-allowed;
  opacity: 0.8;
}

.message-container {
  text-align: center;
  margin-top: 10px;
  min-height: 24px;
}

.error-text {
  color: #dc3545;
  font-size: 0.95rem;
  font-weight: 500;
  margin: 0;
}

.success-text {
  color: #198754;
  font-size: 1rem;
  font-weight: 600;
  margin: 0;
  animation: fadeIn 0.5s;
}

@media (max-width: 500px) {
  .grid-3 {
    grid-template-columns: 1fr 1fr;
  }
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>