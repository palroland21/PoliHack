<template>
  <div class="transport-container">

    <div class="header-center">
      <div class="icon-circle-large">
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-3.1c-.5-.9-.8-1.4-1.3-1.4H9c-.5 0-.8.5-1.3 1.4C6.8 8.6 5.5 10 5.5 10s-2.7.6-4.5 1.1C.2 11.3 0 12.1 0 13v3c0 .6.4 1 1 1h2"/><circle cx="7" cy="17" r="2"/><circle cx="17" cy="17" r="2"/><path d="M14 6h2a2 2 0 0 1 2 2v2H5v-2a2 2 0 0 1 2-2h2"/></svg>
      </div>
      <h2>Transport Offer</h2>
      <p>Complete the details about the transport you can provide</p>
    </div>

    <form @submit.prevent="submitOffer" class="form-content">

      <div class="form-group">
        <label>Vehicle Type</label>
        <div class="grid-3">
          <div
              class="selection-card"
              :class="{ active: form.vehicleType === 'car' }"
              @click="form.vehicleType = 'car'"
          >
            <span class="card-title">Car</span>
            <span class="card-subtitle">1-4 people</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.vehicleType === 'minibus' }"
              @click="form.vehicleType = 'minibus'"
          >
            <span class="card-title">Van/Minibus</span>
            <span class="card-subtitle">5-8 people</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.vehicleType === 'truck' }"
              @click="form.vehicleType = 'truck'"
          >
            <span class="card-title">Truck</span>
            <span class="card-subtitle">Cargo/Goods</span>
          </div>
        </div>
      </div>

      <div class="form-group">
        <label>Capacity / Available Seats</label>
        <input
            type="text"
            v-model="form.capacity"
            placeholder="ex: 4 people"
            class="input-field"
        />
      </div>

      <div class="form-group">
        <label>Possible Destinations</label>
        <input
            type="text"
            v-model="form.destinations"
            placeholder="ex: Hospital, Train Station, Aid Center"
            class="input-field"
        />
      </div>

      <div class="form-group">
        <label>Availability</label>
        <div class="grid-3">
          <div
              class="selection-card center-content"
              :class="{ active: form.availability === 'flexible' }"
              @click="form.availability = 'flexible'"
          >
            <span class="dot green"></span>
            <span class="card-title">Flexible</span>
          </div>

          <div
              class="selection-card center-content"
              :class="{ active: form.availability === 'soon' }"
              @click="form.availability = 'soon'"
          >
            <span class="dot yellow"></span>
            <span class="card-title">Soon</span>
          </div>

          <div
              class="selection-card center-content"
              :class="{ active: form.availability === 'immediate' }"
              @click="form.availability = 'immediate'"
          >
            <span class="dot red"></span>
            <span class="card-title">Immediate</span>
          </div>
        </div>
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

// form data
const form = reactive({
  vehicleType: 'car',
  capacity: '',
  destinations: '',
  availability: 'soon'
});

// functie ajutatoare pentru a prelua ID-ul din local storage
const getLoggedUserId = () => {
  const userId = localStorage.getItem('loggedUserId');

  if (!userId) {
    console.warn("DEBUG AUTH: 'loggedUserId' key is missing in Local Storage. Login is required.");
    return 0;
  }

  const numericId = Number(userId);

  if (isNaN(numericId) || numericId <= 0) {
    console.error(`DEBUG AUTH: 'loggedUserId' found but is invalid/zero: ${userId}`);
    return 0; // Invalid ID
  }

  return numericId;
};


const submitOffer = async () => {
  errorMessage.value = '';
  successMessage.value = '';

  const loggedUserId = getLoggedUserId();

  if (loggedUserId === 0) {
    errorMessage.value = "User not authenticated. Please log in before submitting an offer.";
    return;
  }

  if (!form.capacity || !form.destinations) {
    errorMessage.value = "Please complete capacity and destinations.";
    return;
  }

  isLoading.value = true;

  const requestPayload = {
    vehicleType: form.vehicleType.toUpperCase(),
    capacity: form.capacity,
    destinations: form.destinations,
    availabilityStatus: form.availability.toUpperCase(),
    rescuerId: loggedUserId
  };

  try {

    const response = await axios.post('http://localhost:9090/transport/add', requestPayload);

    console.log("Success:", response.data);

    successMessage.value = "Offer successfully registered!";

    form.capacity = '';
    form.destinations = '';
    form.vehicleType = 'car';
    form.availability = 'soon';

  } catch (error) {
    console.error("Technical error details:", error);
    errorMessage.value = "Something went wrong. Please try again later.";
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>

.transport-container {
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
  background-color: #e8f5e9;
  color: #198754;
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
  gap: 10px;
}

.input-field {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  background-color: #f9f9f9;
  box-sizing: border-box;
}
.input-field:focus {
  outline: none;
  border-color: #198754;
  background-color: #fff;
}

.selection-card {
  border: 1px solid #eee;
  background-color: #fff;
  border-radius: 10px;
  padding: 15px 10px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  transition: all 0.2s;
  text-align: center;
}

.selection-card:hover {
  border-color: #198754;
}

.selection-card.active {
  background-color: #e8f5e9;
  border-color: #198754;
  color: #198754;
}

.card-title {
  font-weight: 700;
  font-size: 0.95rem;
  display: block;
}

.card-subtitle {
  font-size: 0.75rem;
  color: #888;
  margin-top: 4px;
}
.active .card-subtitle {
  color: #198754;
}

.center-content {
  flex-direction: row;
  gap: 8px;
}
.dot {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  display: inline-block;
}
.dot.green { background-color: #28a745; }
.dot.yellow { background-color: #ffc107; }
.dot.red { background-color: #dc3545; }


.submit-btn {
  margin-top: 10px;
  width: 100%;
  padding: 15px;
  background-color: #81c784;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  transition: background 0.3s;
}

.submit-btn:hover:not(:disabled) {
  background-color: #66bb6a;
}

.submit-btn:disabled {
  background-color: #a5d6a7;
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
    grid-template-columns: 1fr;
  }
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>