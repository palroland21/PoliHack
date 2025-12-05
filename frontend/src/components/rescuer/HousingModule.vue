<template>
  <div class="housing-container">

    <div class="header-center">
      <div class="icon-circle-large">
        <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
      </div>
      <h2>Housing Offer</h2>
      <p>Complete the details about the shelter you can provide</p>
    </div>

    <form @submit.prevent="submitOffer" class="form-content">

      <div class="form-group">
        <label>Space Type</label>
        <div class="grid-3">

          <div
              class="selection-card"
              :class="{ active: form.spaceType === 'room' }"
              @click="form.spaceType = 'room'"
          >
            <div class="card-icon">🛏️</div>
            <span class="card-title">Room</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.spaceType === 'apartment' }"
              @click="form.spaceType = 'apartment'"
          >
            <div class="card-icon">🏢</div>
            <span class="card-title">Apartment</span>
          </div>

          <div
              class="selection-card"
              :class="{ active: form.spaceType === 'house' }"
              @click="form.spaceType = 'house'"
          >
            <div class="card-icon">🏠</div>
            <span class="card-title">House</span>
          </div>

        </div>
      </div>

      <div class="form-group">
        <label>Person Capacity</label>
        <input
            type="text"
            v-model="form.capacity"
            placeholder="ex: 2-4 people"
            class="input-field"
        />
      </div>

      <div class="form-group">
        <label>Area / Address</label>
        <input
            type="text"
            v-model="form.address"
            placeholder="ex: Downtown, Main Street"
            class="input-field"
        />
      </div>

      <div class="form-group">
        <label>Available Duration</label>
        <input
            type="text"
            v-model="form.duration"
            placeholder="ex: 1-2 weeks"
            class="input-field"
        />
      </div>

      <div class="form-group">
        <label>Amenities</label>
        <div class="tags-container">
          <button
              type="button"
              v-for="amenity in amenitiesList"
              :key="amenity"
              class="amenity-tag"
              :class="{ selected: form.amenities.includes(amenity) }"
              @click="toggleAmenity(amenity)"
          >
            {{ amenity }}
          </button>
        </div>
      </div>

      <div class="form-group">
        <label>Additional Notes</label>
        <textarea
            v-model="form.notes"
            placeholder="Other relevant details..."
            class="input-field textarea-field"
        ></textarea>
      </div>

      <button type="submit" class="submit-btn">
        Register Offer →
      </button>

    </form>
  </div>
</template>

<script setup>
import { reactive } from 'vue';

const amenitiesList = [
  "WiFi",
  "Kitchen",
  "Private Bath",
  "Heating",
  "Pets Allowed",
  "Parking",
  "Bed Linens"
];


const form = reactive({
  spaceType: 'room',
  capacity: '',
  address: '',
  duration: '',
  amenities: [],
  notes: ''
});

const toggleAmenity = (amenity) => {
  if (form.amenities.includes(amenity)) {
    form.amenities = form.amenities.filter(item => item !== amenity);
  } else {
    form.amenities.push(amenity);
  }
};

const submitOffer = () => {
  console.log("Submitting Housing Offer:", form);
  alert("Housing offer registered! (Check console)");
};
</script>

<style scoped>
.housing-container {
  padding: 10px;
  animation: fadeIn 0.3s ease-in-out;
}


.header-center {
  text-align: center;
  margin-bottom: 30px;
}

.icon-circle-large {
  width: 60px;
  height: 60px;
  background-color: #e3f2fd;
  color: #0d6efd;
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
  border-color: #0d6efd;
  background-color: #fff;
}

.grid-3 {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
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
}

.card-icon {
  font-size: 1.5rem;
  margin-bottom: 8px;
}

.selection-card:hover {
  border-color: #0d6efd;
  background-color: #f8fbff;
}

.selection-card.active {
  background-color: #e3f2fd;
  border-color: #0d6efd;
  color: #0d6efd;
}

.card-title {
  font-weight: 600;
  font-size: 0.9rem;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.amenity-tag {
  padding: 8px 16px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 20px;
  cursor: pointer;
  font-size: 0.9rem;
  color: #555;
  transition: all 0.2s;
}

.amenity-tag:hover {
  background-color: #f1f1f1;
}

.amenity-tag.selected {
  background-color: #0d6efd;
  color: white;
  border-color: #0d6efd;
}

/* Submit Button */
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

.submit-btn:hover {
  background-color: #66bb6a;
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