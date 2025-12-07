<template>
  <div class="selection-container">

    <div class="header">
       <button @click="goBack" class="back-btn">
        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="12" x2="5" y2="12"></line><polyline points="12 19 5 12 12 5"></polyline></svg>
        Back
      </button>
      <div class="title-group">
        <h1>Available Assistance</h1>
        <p>Select the specific items you need from the lists below.</p>
      </div>
    </div>

    <div class="tabs-wrapper">
      <div class="tabs">
        <button class="tab-btn" :class="{ active: activeTab === 'resources' }" @click="activeTab = 'resources'">
          <span>📦</span> Resources
        </button>
        <button class="tab-btn" :class="{ active: activeTab === 'transport' }" @click="activeTab = 'transport'">
          <span>🚗</span> Transport
        </button>
        <button class="tab-btn" :class="{ active: activeTab === 'housing' }" @click="activeTab = 'housing'">
          <span>🏠</span> Housing
        </button>
      </div>
    </div>

    <div class="list-container">

      <div v-if="isLoading" class="state-message">
        <div class="spinner"></div>
        <p>Searching for available items...</p>
      </div>

      <div v-else-if="currentList.length === 0" class="state-message empty">
        <div class="empty-icon">📂</div>
        <p>No available items found in this category.</p>
      </div>

      <div v-else class="items-grid">
        <div v-for="item in currentList" :key="item.id" class="item-card">

          <div class="card-top">
            <div class="card-header">
              <h3>{{ getItemTitle(item) }}</h3>
              <span class="status-badge" :class="item.status === 'FREE' ? 'free' : 'busy'">
                {{ item.status === 'FREE' ? 'FREE' : 'TAKEN' }}
              </span>
            </div>

            <div class="card-details">
              <div class="detail-row">
                <span class="detail-icon">ℹ️</span>
                <p>{{ getItemDetails(item) }}</p>
              </div>
            </div>
          </div>

          <button
              class="request-btn"
              :class="{ 'btn-disabled': item.status !== 'FREE' }"
              :disabled="item.status !== 'FREE'"
              @click="requestItem(item.id)"
          >
            {{ item.status === 'FREE' ? 'Request This' : 'Unavailable' }}
          </button>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();

const activeTab = ref('resources');
const resourcesList = ref([]);
const transportList = ref([]);
const housingList = ref([]);
const isLoading = ref(false);

const API_URL = 'http://localhost:9090';

//fetch Data
const fetchData = async () => {
  isLoading.value = true;
  try {
    const [resData, transData, houseData] = await Promise.all([
      fetch(`${API_URL}/public/resources`).then(r => r.json()),
      fetch(`${API_URL}/public/transport`).then(r => r.json()),
      fetch(`${API_URL}/public/housing`).then(r => r.json())
    ]);

    resourcesList.value = resData;
    transportList.value = transData;
    housingList.value = houseData;
  } catch (error) {
    console.error("Error fetching data:", error);
  } finally {
    isLoading.value = false;
  }
};

// const currentStep = ref(1);
onMounted(() => {
  fetchData();
});

const currentList = computed(() => {
  if (activeTab.value === 'resources') return resourcesList.value;
  if (activeTab.value === 'transport') return transportList.value;
  if (activeTab.value === 'housing') return housingList.value;
  return [];
});

const getItemTitle = (item) => {
  if (activeTab.value === 'resources') return "Resource Pack";
  if (activeTab.value === 'transport') return item.vehicleType;
  if (activeTab.value === 'housing') return item.housingType;
};

const getItemDetails = (item) => {
  if (activeTab.value === 'resources') return `Qty: ${item.quantity} | ${item.pickupLocation}`;
  if (activeTab.value === 'transport') return `${item.capacity} seats | To: ${item.destinations}`;
  if (activeTab.value === 'housing') return `Cap: ${item.capacity} pers | ${item.address}`;
};

const requestItem = async (id) => {
  if(!confirm("Are you sure you want to request this item?")) return;
  const category = activeTab.value;

  try {
    const response = await fetch(`${API_URL}/public/reserve/${category}/${id}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' }
    });

    if (response.ok) {
      let targetList = [];
      if (category === 'resources') targetList = resourcesList.value;
      else if (category === 'transport') targetList = transportList.value;
      else if (category === 'housing') targetList = housingList.value;

      const item = targetList.find(i => i.id === id);
      if (item) item.status = 'TAKEN';

      alert("Request successful! The item has been reserved for you.");
    } else {
      const errorText = await response.text();
      alert("Error: " + errorText);
    }
  } catch (error) {
    console.error("Request failed", error);
    alert("Network error. Please try again.");
  }
};

const goBack = () => {
  router.push("/client");
  // router.push('/step-2');
};
</script>

<style scoped>
.selection-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 40px 20px;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color: #333;
}

.header {
  position: relative;
  text-align: center;
  margin-bottom: 50px;
}

.back-btn {
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  align-items: center;
  gap: 8px;
  background: transparent;
  border: none;
  font-size: 1rem;
  font-weight: 600;
  color: #666;
  cursor: pointer;
  padding: 8px 12px;
  border-radius: 8px;
  transition: all 0.2s;
}

.back-btn:hover {
  background-color: #f0f0f0;
  color: #333;
}

.title-group h1 {
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0 0 10px 0;
  color: #2c3e50;
}

.title-group p {
  font-size: 1.1rem;
  color: #666;
  margin: 0;
}

.tabs-wrapper {
  display: flex;
  justify-content: center;
  margin-bottom: 40px;
}

.tabs {
  background-color: #f8f9fa;
  padding: 5px;
  border-radius: 50px;
  display: inline-flex;
  gap: 5px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.03);
}

.tab-btn {
  padding: 12px 30px;
  border: none;
  background: transparent;
  border-radius: 40px;
  font-size: 1rem;
  font-weight: 600;
  color: #666;
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  display: flex;
  align-items: center;
  gap: 8px;
}

.tab-btn:hover {
  color: #333;
}

.tab-btn.active {
  background-color: #e8a838;
  color: white;
  box-shadow: 0 4px 15px rgba(232, 168, 56, 0.3);
}

/* GRID & CARDS */
.items-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 25px;
}

.item-card {
  background: white;
  border-radius: 16px;
  padding: 24px;
  border: 1px solid #eaeaea;
  box-shadow: 0 10px 30px rgba(0,0,0,0.04);
  transition: transform 0.2s, box-shadow 0.2s;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}

.item-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 15px 35px rgba(0,0,0,0.08);
  border-color: #e8a838;
}

.card-top {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 15px;
}

.card-header h3 {
  margin: 0;
  font-size: 1.25rem;
  color: #2c3e50;
  font-weight: 700;
  line-height: 1.3;
}

.status-badge {
  padding: 6px 12px;
  border-radius: 20px;
  font-size: 0.75rem;
  font-weight: 800;
  letter-spacing: 0.5px;
  text-transform: uppercase;
}

.status-badge.free {
  background-color: #d1e7dd;
  color: #0f5132;
}

.status-badge.busy {
  background-color: #f8d7da;
  color: #842029;
}

.detail-row {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  margin-top: 10px;
  background-color: #f9f9f9;
  padding: 10px;
  border-radius: 8px;
}

.detail-icon {
  font-size: 1.2rem;
}

.detail-row p {
  margin: 0;
  font-size: 0.95rem;
  color: #555;
  line-height: 1.5;
}

/* BUTTONS */
.request-btn {
  width: 100%;
  padding: 14px;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  background-color: #198754;
  color: white;
  transition: background-color 0.2s, transform 0.1s;
}

.request-btn:hover:not(:disabled) {
  background-color: #157347;
  transform: scale(1.02);
}

.request-btn:disabled, .btn-disabled {
  background-color: #e9ecef;
  color: #adb5bd;
  cursor: not-allowed;
  transform: none;
}

.state-message {
  text-align: center;
  padding: 60px;
  color: #888;
}

.empty-icon {
  font-size: 3rem;
  margin-bottom: 10px;
  opacity: 0.5;
}

.spinner {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #e8a838;
  border-radius: 50%;
  width: 40px;
  height: 40px;
  animation: spin 1s linear infinite;
  margin: 0 auto 15px;
}

@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
</style>