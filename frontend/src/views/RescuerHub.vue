<template>
  <div class="page-container">

    <div class="top-nav">
      <button v-if="activeTab" class="nav-btn" @click="activeTab = null">
        <span class="arrow">←</span> Back to selection
      </button>
      <button v-else class="nav-btn" @click="goHome">
        <span class="arrow">←</span> Home
      </button>
    </div>

    <div class="content-wrapper">

      <div v-if="!activeTab" class="selection-view">
        <h1 class="main-title">What can you offer?</h1>
        <p class="subtitle">Select the type of aid you can provide</p>

        <div class="cards-grid">

          <div class="card transport-card" @click="activeTab = 'transport'">
            <div class="icon-circle transport-bg">
              <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-3.1c-.5-.9-.8-1.4-1.3-1.4H9c-.5 0-.8.5-1.3 1.4C6.8 8.6 5.5 10 5.5 10s-2.7.6-4.5 1.1C.2 11.3 0 12.1 0 13v3c0 .6.4 1 1 1h2"/><circle cx="7" cy="17" r="2"/><circle cx="17" cy="17" r="2"/><path d="M14 6h2a2 2 0 0 1 2 2v2H5v-2a2 2 0 0 1 2-2h2"/></svg>
            </div>
            <div class="card-text">
              <h3>Transport</h3>
              <p>Transport people or goods</p>
            </div>
          </div>

          <div class="card housing-card" @click="activeTab = 'housing'">
            <div class="icon-circle housing-bg">
              <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
            </div>
            <div class="card-text">
              <h3>Housing</h3>
              <p>Offer temporary shelter</p>
            </div>
          </div>

          <div class="card resources-card" @click="activeTab = 'resources'">
            <div class="icon-circle resources-bg">
              <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path><polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline><line x1="12" y1="22.08" x2="12" y2="12"></line></svg>
            </div>
            <div class="card-text">
              <h3>Resources</h3>
              <p>Water, food, clothing, etc.</p>
            </div>
          </div>

          <div class="card medical-card" @click="activeTab = 'medical'">
            <div class="icon-circle medical-bg">
              <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z"/><path d="M12 5 9.04 7.96a2.17 2.17 0 0 0 0 3.08c.86.86 2.26.86 3.12 0l2.8-2.8"/><path d="m16.5 3-1.43 1.43c-.86.86-.86 2.26 0 3.12l1.43 1.43"/></svg>
            </div>
            <div class="card-text">
              <h3>Medical Assistance</h3>
              <p>First aid and medical support</p>
            </div>
          </div>

        </div>
      </div>

      <div v-else class="active-module">
        <component :is="activeComponent" />
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';

import TransportModule from '../components/rescuer/TransportModule.vue';
import HousingModule from '../components/rescuer/HousingModule.vue';
import ResourcesModule from '../components/rescuer/ResourcesModule.vue';
import MedicalModule from '../components/rescuer/MedicalModule.vue';

const router = useRouter();
const activeTab = ref(null);

const goHome = () => {
  router.push('/');
};

const activeComponent = computed(() => {
  switch (activeTab.value) {
    case 'transport': return TransportModule;
    case 'housing': return HousingModule;
    case 'resources': return ResourcesModule;
    case 'medical': return MedicalModule;
    default: return null;
  }
});
</script>

<style scoped>

.page-container {
  min-height: 100vh;
  background-color: #ffffff;
  font-family: 'Segoe UI', sans-serif;
  padding: 20px;
}


.top-nav {
  display: flex;
  justify-content: flex-start;
  margin-bottom: 20px;
}

.nav-btn {
  background: none;
  border: none;
  font-size: 1rem;
  color: #333;
  cursor: pointer;
  display: flex;
  align-items: center;
  padding: 10px 0;
  font-weight: 500;
}

.nav-btn:hover {
  text-decoration: underline;
}

.arrow {
  margin-right: 8px;
  font-size: 1.2rem;
}

.content-wrapper {
  max-width: 600px;
  margin: 0 auto;
}

.selection-view {
  text-align: center;
  margin-top: 20px;
}

.main-title {
  font-size: 2rem;
  font-weight: 800;
  color: #000;
  margin-bottom: 10px;
}

.subtitle {
  color: #666;
  font-size: 1rem;
  margin-bottom: 40px;
}

.cards-grid {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.card {
  display: flex;
  align-items: center;
  padding: 20px;
  background-color: white;
  border: 1px solid #e0e0e0;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.2s ease;
  text-align: left;
}

.card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.08);
  border-color: #ccc;
}


.icon-circle {
  width: 50px;
  height: 50px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  flex-shrink: 0;
}

.card-text h3 {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 700;
  color: #2c3e50;
}

.card-text p {
  margin: 4px 0 0;
  font-size: 0.9rem;
  color: #7f8c8d;
}

.transport-bg { background-color: #e8f5e9; color: #198754; }
.transport-card:hover { border-color: #198754; background-color: #f8fff9; }
.transport-card h3 { color: #198754; }

.housing-bg { background-color: #e3f2fd; color: #0d6efd; }
.housing-card:hover { border-color: #0d6efd; background-color: #f0f8ff; }
.housing-card h3 { color: #0d6efd; }

.resources-bg { background-color: #fff8e1; color: #f57f17; }
.resources-card:hover { border-color: #f57f17; background-color: #fffdf5; }
.resources-card h3 { color: #f57f17; }

.medical-bg { background-color: #ffebee; color: #d32f2f; }
.medical-card:hover { border-color: #d32f2f; background-color: #fff5f5; }
.medical-card h3 { color: #d32f2f; }
</style>