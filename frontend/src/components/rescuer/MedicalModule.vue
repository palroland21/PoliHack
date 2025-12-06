<template>
  <div class="medical-module-container">

    <div class="header-section">
      <h2>🚑 Medical Assistance</h2>
      <p class="subtitle">Emergency response unit for qualified personnel.</p>
    </div>

    <div v-if="!isVerified" class="verification-wrapper">
      <div class="verification-card">
        <div class="icon-area">🩺</div>
        <h3>Medical Verification Required</h3>
        <p>To access patient data and medical emergencies, please verify your credentials with the National Registry.</p>

        <form @submit.prevent="handleVerification">

          <div class="form-group">
            <label>Full Name</label>
            <input
                v-model="form.fullName"
                type="text"
                placeholder="Dr. Nume Prenume"
                required
            />
          </div>

          <div class="form-group">
            <label>CUIM (Cod Unic)</label>
            <input
                v-model="form.cuim"
                type="text"
                placeholder="Ex: 123456"
                required
            />
          </div>

          <div class="form-group">
            <label>Specialization</label>
            <select v-model="form.specialization">
              <option>General Medicine</option>
              <option>Emergency (UPU)</option>
              <option>Surgery</option>
              <option>Paramedic</option>
            </select>
          </div>

          <button type="submit" class="verify-btn" :disabled="isLoading">
            {{ isLoading ? 'Checking Registry...' : 'Verify Identity' }}
          </button>

        </form>
      </div>
    </div>

    <div v-else class="medical-dashboard">

      <div class="status-banner">
        ✅ <strong>Identity Verified:</strong> You are active as: <em>{{ form.specialization }}</em>.
      </div>

      <div class="requests-list">
        <h3>Urgent Cases Nearby</h3>

        <div class="empty-state">
          <p>Scanning area for medical emergencies...</p>
          <div class="loader"></div>
        </div>

      </div>

    </div>

  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useMainStore } from '@/stores/mainStore';

const store = useMainStore();

// Starea locala: Daca e validat sau nu
const isVerified = ref(false);
const isLoading = ref(false);

// Datele formularului
const form = reactive({
  fullName: '',
  cuim: '',
  specialization: 'General Medicine'
});

// Verificam la incarcare daca userul a fost deja validat in trecut
// (Poti salva asta in localStorage sau in store ca sa nu ceara de fiecare data)
onMounted(() => {
  const savedStatus = localStorage.getItem('isMedicalVerified');
  if (savedStatus === 'true') {
    isVerified.value = true;
  }
});

// Functia care trimite datele la Backend (Java)
const handleVerification = async () => {
  if (!store.userId) {
    alert("Error: You are not logged in!");
    return;
  }

  isLoading.value = true;

  // Endpoint-ul creat in Java (Controller-ul tau)
  // URL-ul: http://localhost:8080/medical/verify/{rescuerId}
  const url = `http://localhost:9090/medical/verify/${store.userId}`;

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        // Daca ai securitate pe endpoint, decomenteaza linia de mai jos:
        // 'Authorization': `Bearer ${store.token}`
      },
      body: JSON.stringify(form)
    });

    if (response.ok) {
      // SUCES: Backend-ul a validat (simulat) medicul
      setTimeout(() => {
        isVerified.value = true;
        isLoading.value = false;

        // Salvam local ca sa tinem minte sesiunea asta
        localStorage.setItem('isMedicalVerified', 'true');
      }, 1000); // Mic delay pentru efect vizual
    } else {
      alert("Verification Failed: CUIM not found or invalid.");
      isLoading.value = false;
    }

  } catch (error) {
    console.error("Connection error:", error);
    alert("Server error. Check console.");
    isLoading.value = false;
  }
};
</script>

<style scoped>
/* Container Principal */
.medical-module-container {
  max-width: 800px;
  margin: 30px auto;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  padding: 0 20px;
}

.header-section { text-align: center; margin-bottom: 30px; }
.header-section h2 { color: #dc3545; margin: 0; font-size: 2rem; } /* Rosu Medical */
.subtitle { color: #666; margin-top: 5px; }

/* CARD VERIFICARE */
.verification-wrapper { display: flex; justify-content: center; }

.verification-card {
  background: white;
  width: 100%;
  max-width: 500px;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 8px 30px rgba(0,0,0,0.08);
  border: 1px solid #f0f0f0;
  text-align: left;
}

.icon-area { font-size: 3rem; text-align: center; margin-bottom: 10px; }
.verification-card h3 { text-align: center; margin-top: 0; color: #333; }
.verification-card p { text-align: center; color: #777; font-size: 0.95rem; margin-bottom: 25px; }

.form-group { margin-bottom: 15px; }
.form-group label { display: block; font-weight: 600; margin-bottom: 5px; color: #444; }
.form-group input, .form-group select {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  box-sizing: border-box; /* IMPORTANT pt padding */
  transition: border 0.2s;
}
.form-group input:focus { border-color: #dc3545; outline: none; }

.verify-btn {
  width: 100%;
  padding: 14px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  margin-top: 10px;
  transition: background 0.2s;
}
.verify-btn:hover { background-color: #b02a37; }
.verify-btn:disabled { background-color: #e6aeb3; cursor: wait; }

/* DASHBOARD DUPA VALIDARE */
.status-banner {
  background-color: #d1e7dd;
  color: #0f5132;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 30px;
  border: 1px solid #badbcc;
  text-align: center;
}

.requests-list {
  background: #fff;
  padding: 30px;
  border-radius: 12px;
  min-height: 200px;
  border: 1px solid #eee;
  text-align: center;
}

/* Loader pt efect */
.loader {
  border: 4px solid #f3f3f3;
  border-top: 4px solid #dc3545;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  animation: spin 1s linear infinite;
  margin: 20px auto;
}
@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
</style>