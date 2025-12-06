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

    <div class="stats-container">
      <div class="stat-card">
        <h3>{{ myOffers.length }}</h3>
        <p>Active Resources</p>
      </div>
      <div class="stat-card">
        <h3>{{ countType('transport') }}</h3>
        <p>Transport</p>
      </div>
      <div class="stat-card">
        <h3>{{ countType('housing') }}</h3>
        <p>Housing</p>
      </div>
    </div>

    <div class="resources-section">
      <div class="section-title">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="heart-icon"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path></svg>
        <h2>Your Active Resources</h2>
      </div>
      <p class="section-subtitle">Here you can view and manage all the help you are offering</p>

      <div v-if="myOffers.length === 0" class="empty-state">
        <p>You haven't added any resources yet.</p>
      </div>

      <div v-else class="offers-list">

        <div v-for="offer in myOffers" :key="offer.id" class="offer-card">

          <div class="card-icon" :class="offer.type">
            <svg v-if="offer.type === 'transport'" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 17h2c.6 0 1-.4 1-1v-3c0-.9-.7-1.7-1.5-1.9C18.7 10.6 16 10 16 10s-1.3-1.4-2.2-3.1c-.5-.9-.8-1.4-1.3-1.4H9c-.5 0-.8.5-1.3 1.4C6.8 8.6 5.5 10 5.5 10s-2.7.6-4.5 1.1C.2 11.3 0 12.1 0 13v3c0 .6.4 1 1 1h2"/><circle cx="7" cy="17" r="2"/><circle cx="17" cy="17" r="2"/><path d="M14 6h2a2 2 0 0 1 2 2v2H5v-2a2 2 0 0 1 2-2h2"/></svg>

            <svg v-if="offer.type === 'housing'" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path><polyline points="9 22 9 12 15 12 15 22"></polyline></svg>

            <svg v-if="offer.type === 'resources'" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"></path><polyline points="3.27 6.96 12 12.01 20.73 6.96"></polyline><line x1="12" y1="22.08" x2="12" y2="12"></line></svg>

            <svg v-if="offer.type === 'medical'" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M19 14c1.49-1.46 3-3.21 3-5.5A5.5 5.5 0 0 0 16.5 3c-1.76 0-3 .5-4.5 2-1.5-1.5-2.74-2-4.5-2A5.5 5.5 0 0 0 2 8.5c0 2.3 1.5 4.05 3 5.5l7 7Z"/><path d="M12 5 9.04 7.96a2.17 2.17 0 0 0 0 3.08c.86.86 2.26.86 3.12 0l2.8-2.8"/><path d="m16.5 3-1.43 1.43c-.86.86-.86 2.26 0 3.12l1.43 1.43"/></svg>
          </div>

          <div class="card-content">
            <div class="card-header">
              <h3>{{ offer.title }}</h3>
              <span class="badge" :class="offer.type">{{ capitalize(offer.type) }}</span>
            </div>
            <p class="description">{{ offer.details }}</p>
            <p class="date">Added on: {{ offer.date }}</p>
          </div>

          <button class="delete-icon-btn" @click="deleteOffer(offer.id)" title="Remove offer">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path><line x1="10" y1="11" x2="10" y2="17"></line><line x1="14" y1="11" x2="14" y2="17"></line></svg>
          </button>

        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useMainStore } from '@/stores/mainStore';

const store = useMainStore();

// Mock Data - In realitate le iei din Firebase
// Am folosit structura din imaginea ta
const myOffers = ref([
  {
    id: 1,
    type: 'transport',
    title: 'Passenger Transport',
    details: 'Available for urgent medical transport - Bucharest',
    date: '15.01.2024'
  },
  {
    id: 2,
    type: 'housing',
    title: 'Temporary Shelter',
    details: '2 places available - Central Area Apartment',
    date: '14.01.2024'
  },
  {
    id: 3,
    type: 'resources',
    title: 'Water and Food',
    details: 'Bottled water, canned food, blankets',
    date: '13.01.2024'
  }
]);

const capitalize = (s) => s.charAt(0).toUpperCase() + s.slice(1);

const countType = (type) => {
  return myOffers.value.filter(o => o.type === type).length;
};

const deleteOffer = (id) => {
  if(confirm("Are you sure you want to remove this resource?")) {
    myOffers.value = myOffers.value.filter(o => o.id !== id);
  }
};
</script>

<style scoped>
.dashboard-container {
  max-width: 900px;
  margin: 40px auto;
  padding: 0 20px;
  font-family: 'Segoe UI', sans-serif;
}

/* HEADER */
.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.welcome-text h1 { margin: 0; font-size: 2rem; color: #333; }
.welcome-text p { margin: 5px 0 0; color: #666; }

.add-resource-btn {
  background-color: #198754;
  color: white;
  border: none;
  padding: 12px 20px;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  font-size: 0.95rem;
  transition: background 0.2s;
}
.add-resource-btn:hover { background-color: #157347; }

/* STATS */
.stats-container {
  display: flex;
  gap: 20px;
  margin-bottom: 40px;
}

.stat-card {
  flex: 1;
  background: white;
  padding: 20px;
  border-radius: 12px;
  border: 1px solid #eee;
  text-align: center;
  box-shadow: 0 2px 5px rgba(0,0,0,0.03);
}

.stat-card h3 { margin: 0; font-size: 1.8rem; color: #198754; }
.stat-card p { margin: 5px 0 0; color: #666; font-size: 0.9rem; }

/* RESOURCES LIST */
.resources-section {
  background: white;
  border: 1px solid #e0e0e0;
  border-radius: 16px;
  padding: 30px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.03);
}

.section-title {
  display: flex;
  align-items: center;
  gap: 10px;
  color: #333;
}
.heart-icon { color: #198754; }
.section-title h2 { margin: 0; font-size: 1.3rem; }
.section-subtitle { margin: 5px 0 25px 30px; color: #888; font-size: 0.9rem; }

.empty-state {
  text-align: center;
  padding: 40px;
  color: #888;
  background: #f9f9f9;
  border-radius: 12px;
}

/* OFFER CARD STYLE (Replica din imaginea ta) */
.offers-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.offer-card {
  display: flex;
  align-items: center;
  padding: 20px;
  border: 1px solid #e0e0e0;
  border-radius: 12px;
  background: white;
  transition: box-shadow 0.2s;
}

.offer-card:hover {
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
}

/* Icons */
.card-icon {
  width: 50px;
  height: 50px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  flex-shrink: 0;
}
.card-icon.transport { background: #e8f5e9; color: #198754; } /* Verde deschis */
.card-icon.housing { background: #e3f2fd; color: #0d6efd; }   /* Albastru deschis */
.card-icon.resources { background: #fff3cd; color: #ffc107; } /* Galben deschis */
.card-icon.medical { background: #ffebee; color: #dc3545; }   /* Rosu deschis */

/* Content */
.card-content { flex: 1; }

.card-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 5px;
}

.card-header h3 { margin: 0; font-size: 1.1rem; color: #333; }

.badge {
  font-size: 0.75rem;
  padding: 3px 8px;
  border-radius: 12px;
  font-weight: 600;
  text-transform: uppercase;
}
.badge.transport { background: #e8f5e9; color: #198754; }
.badge.housing { background: #e3f2fd; color: #0d6efd; }
.badge.resources { background: #fff3cd; color: #ffc107; }
.badge.medical { background: #ffebee; color: #dc3545; }

.description { margin: 5px 0; color: #555; font-size: 0.95rem; }
.date { margin: 0; color: #999; font-size: 0.8rem; }

/* Delete Button */
.delete-icon-btn {
  background: transparent;
  border: none;
  color: #dc3545; /* Rosu pt cosul de gunoi */
  cursor: pointer;
  padding: 8px;
  border-radius: 8px;
  transition: background 0.2s;
}
.delete-icon-btn:hover {
  background: #ffebee;
}

@media (max-width: 600px) {
  .dashboard-header { flex-direction: column; align-items: flex-start; gap: 15px; }
  .stats-container { flex-direction: column; }
}
</style>