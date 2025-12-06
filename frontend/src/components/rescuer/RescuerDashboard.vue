<template>
  <div class="dashboard-container">

    <div class="dashboard-header">
      <div class="welcome-text">
        <h1>Rescuer Dashboard</h1>
        <p>Welcome back, <strong>{{ store.user || 'Volunteer' }}</strong>!</p>
      </div>
      <button @click="$router.push('/rescuer')" class="add-resource-btn">
        + Add New Resource
      </button>
    </div>

    <div class="tabs-wrapper">
      <button
          class="tab-btn"
          :class="{ active: activeTab === 'transport' }"
          @click="activeTab = 'transport'"
      >
        <span class="icon">🚗</span> Transport
      </button>

      <button
          class="tab-btn"
          :class="{ active: activeTab === 'housing' }"
          @click="activeTab = 'housing'"
      >
        <span class="icon">🏠</span> Housing
      </button>

      <button
          class="tab-btn"
          :class="{ active: activeTab === 'resources' }"
          @click="activeTab = 'resources'"
      >
        <span class="icon">📦</span> Resources
      </button>
    </div>

    <div v-if="isLoading" class="loading-state">
      <div class="spinner"></div>
      <p>Loading {{ activeTab }}...</p>
    </div>

    <div v-else-if="currentList.length === 0" class="empty-state">
      <p>You have no <strong>{{ activeTab }}</strong> offers active.</p>
    </div>

    <div v-else class="offers-grid">
      <div v-for="offer in currentList" :key="offer.id" class="offer-card">

        <div class="card-icon" :class="activeTab">
          <svg v-if="activeTab === 'transport'" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-3.1c-.5-.9-.8-1.4-1.3-1.4H9c-.5 0-.8.5-1.3 1.4C6.8 8.6 5.5 10 5.5 10s-2.7.6-4.5 1.1C.2 11.3 0 12.1 0 13v3c0 .6.4 1 1 1h2"/><circle cx="7" cy="17" r="2"/><circle cx="17" cy="17" r="2"/><path d="M14 6h2a2 2 0 0 1 2 2v2H5v-2a2 2 0 0 1 2-2h2"/></svg>
          <svg v-if="activeTab === 'housing'" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>
          <svg v-if="activeTab === 'resources'" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path><polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline><line x1="12" y1="22.08" x2="12" y2="12"></line></svg>
        </div>

        <div class="card-content">
          <div class="card-header">
            <h3>{{ offer.title }}</h3>
          </div>
          <p class="description">{{ offer.details }}</p>
          <p class="date">Status: <span class="status-active">{{ offer.date }}</span></p>
        </div>

        <button class="delete-btn" @click="deleteOffer(offer.id)" title="Delete">
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path></svg>
        </button>

      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useMainStore } from '@/stores/mainStore';

const store = useMainStore();
const activeTab = ref('transport');
const currentList = ref([]);
const isLoading = ref(false);

// Configurare Base URL (Presupunem port 8080 pentru Spring Boot)
// IMPORTANT: Controller-ul tau e mapat pe "/rescuer", deci url-ul de baza e acesta:
const BASE_URL = 'http://localhost:9090/rescuer';

const fetchCategoryData = async (category) => {
  // Verificam token-ul
  if (!store.token) return;

  // IMPORTANT: Avem nevoie de ID-ul userului logat pentru URL
  // Asigura-te ca in store (la login) ai salvat si userId-ul, nu doar token-ul!
  const userId = store.userId;

  if (!userId) {
    console.error("User ID not found in store! Cannot fetch data.");
    return;
  }

  isLoading.value = true;
  currentList.value = [];

  try {
    let endpoint = '';

    // AICI E MODIFICAREA PRINCIPALA: Adaugam userId la finalul URL-ului
    // Ex: http://localhost:8080/rescuer/transport/5
    if (category === 'transport') endpoint = `${BASE_URL}/transport/${userId}`;
    else if (category === 'housing') endpoint = `${BASE_URL}/housing/${userId}`;
    else if (category === 'resources') endpoint = `${BASE_URL}/resources/${userId}`;

    const response = await fetch(endpoint, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${store.token}`
      }
    });

    if (response.ok) {
      currentList.value = await response.json();
    } else if (response.status === 401) {
      store.logout();
    }
  } catch (error) {
    console.error("Fetch error:", error);
  } finally {
    isLoading.value = false;
  }
};

const deleteOffer = async (id) => {
  if(confirm("Are you sure you want to delete this?")) {
    try {
      const type = activeTab.value; // 'transport', 'housing' sau 'resources'

      // Endpoint-ul de stergere din Controller:
      // @DeleteMapping("/offer/{type}/{id}")
      // URL: http://localhost:8080/rescuer/offer/transport/12
      await fetch(`${BASE_URL}/offer/${type}/${id}`, {
        method: 'DELETE',
        headers: { 'Authorization': `Bearer ${store.token}` }
      });

      // Eliminam din lista vizuala (fara refresh la pagina)
      currentList.value = currentList.value.filter(o => o.id !== id);

    } catch (error) { console.error(error); }
  }
};

// WATCHER: Cand schimbam tab-ul, incarcam automat datele noi
watch(activeTab, (newTab) => {
  fetchCategoryData(newTab);
});

// Initial load
onMounted(() => {
  // Verificam daca userul e logat inainte sa cerem date
  if (store.token && store.userId) {
    fetchCategoryData(activeTab.value);
  }
});
</script>

<style scoped>
/* Acelasi stil ca inainte, nu necesita modificari */
.dashboard-container { max-width: 900px; margin: 40px auto; padding: 0 20px; font-family: 'Segoe UI', sans-serif; }
.dashboard-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
.welcome-text h1 { margin: 0; font-size: 2rem; color: #333; }
.welcome-text p { margin: 5px 0 0; color: #666; }
.add-resource-btn { background-color: #198754; color: white; border: none; padding: 10px 20px; border-radius: 8px; font-weight: 600; cursor: pointer; transition: background 0.2s; }
.add-resource-btn:hover { background-color: #157347; }
.tabs-wrapper { display: flex; gap: 15px; margin-bottom: 30px; border-bottom: 2px solid #f0f0f0; padding-bottom: 10px; }
.tab-btn { background: transparent; border: none; padding: 10px 20px; font-size: 1rem; font-weight: 600; color: #666; cursor: pointer; border-radius: 8px; display: flex; align-items: center; gap: 8px; transition: all 0.2s; }
.tab-btn:hover { background-color: #f8f9fa; color: #333; }
.tab-btn.active { background-color: #e8f5e9; color: #198754; }
.loading-state, .empty-state { text-align: center; padding: 60px; background: #f9f9f9; border-radius: 12px; color: #888; }
.offers-grid { display: flex; flex-direction: column; gap: 15px; }
.offer-card { display: flex; align-items: center; padding: 20px; background: white; border: 1px solid #eee; border-radius: 12px; box-shadow: 0 2px 8px rgba(0,0,0,0.03); transition: transform 0.2s; }
.offer-card:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.08); }
.card-icon { width: 50px; height: 50px; border-radius: 10px; display: flex; align-items: center; justify-content: center; margin-right: 20px; flex-shrink: 0; }
.card-icon.transport { background: #e8f5e9; color: #198754; }
.card-icon.housing { background: #e3f2fd; color: #0d6efd; }
.card-icon.resources { background: #fff3cd; color: #ffc107; }
.card-content { flex: 1; }
.card-header h3 { margin: 0 0 5px 0; font-size: 1.1rem; color: #333; }
.description { margin: 0 0 5px 0; color: #555; font-size: 0.95rem; }
.date { font-size: 0.85rem; color: #999; }
.status-active { color: #198754; font-weight: 600; }
.delete-btn { background: transparent; border: none; color: #dc3545; padding: 8px; border-radius: 6px; cursor: pointer; opacity: 0.7; transition: all 0.2s; }
.delete-btn:hover { opacity: 1; background-color: #ffebee; }
.spinner { width: 30px; height: 30px; border: 3px solid #f3f3f3; border-top: 3px solid #198754; border-radius: 50%; animation: spin 1s linear infinite; margin: 0 auto 10px; }
@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
</style>