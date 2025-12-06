<template>
  <div class="login-container">

    <router-link to="/" class="back-btn-absolute">
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="12" x2="5" y2="12"></line><polyline points="12 19 5 12 12 5"></polyline></svg>
      Back
    </router-link>

    <div class="login-card">

      <div class="logo-header">
        <img src="/ResQ.png" alt="ResQ Logo" class="logo-img">
      </div>

      <h1>Welcome Back</h1>
      <p class="subtitle">Login to manage your resources</p>

      <div class="tabs-container">
        <button
            class="tab-btn"
            :class="{ active: activeTab === 'individual' }"
            @click="setActiveTab('individual')"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
          Individual
        </button>
        <button
            class="tab-btn"
            :class="{ active: activeTab === 'company' }"
            @click="setActiveTab('company')"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="2" y="7" width="20" height="14" rx="2" ry="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>
          Company
        </button>
      </div>

      <form @submit.prevent="submitLogin" class="login-form">

        <div v-if="errorMessage" class="error-alert">
          {{ errorMessage }}
        </div>

        <div class="form-group">
          <label>Username</label>
          <input
              type="text"
              v-model="loginForm.username"
              placeholder="Enter your username"
              class="input-field"
              required
          />
        </div>

        <div class="form-group">
          <label>Password</label>
          <input
              type="password"
              v-model="loginForm.password"
              placeholder="Enter your password"
              class="input-field"
              required
          />
        </div>

        <button type="submit" class="login-btn">
          Login as {{ activeTab === 'individual' ? 'Individual' : 'Company' }}
        </button>

      </form>

      <div class="card-footer">
        <p>No account? <router-link to="/register" class="link">Register here</router-link></p>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useMainStore } from '@/stores/mainStore'; // Am schimbat în 'useMainStore' bazat pe fișierul inițial

const router = useRouter();
const route = useRoute();
const store = useMainStore(); // Folosim store-ul principal

const activeTab = ref('individual');
const errorMessage = ref('');

// O singură structură reactivă pentru datele din formular
const loginForm = reactive({
  username: '',
  password: ''
});

onMounted(() => {
  const token = localStorage.getItem('token');
  if (token) {
    console.log("User already logged in. Redirecting...");
    router.push('/');
  }
});

const setActiveTab = (tab) => {
  activeTab.value = tab;
  loginForm.username = ''; // Resetăm câmpurile la schimbarea tab-ului
  loginForm.password = '';
  errorMessage.value = '';
};


const submitLogin = async () => {
  errorMessage.value = '';

  const loginUrl = activeTab.value === 'individual'
      ? 'http://localhost:9090/auth/login'
      : 'http://localhost:9090/auth/company/login';

  try {
    const response = await fetch(loginUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(loginForm)
    });

    if (response.ok) {
      const data = await response.json();

      // ==========================================================
      // VERIFICARE: Acum că backend-ul returnează ID-ul în câmpul 'id'
      // ==========================================================
      const userId = data.id || data.userId;

      if (userId) {
        localStorage.setItem('loggedUserId', userId); // SALVAREA ID-ului (Fix-ul principal)
        console.log(`User ID ${userId} saved.`);
      } else {
        console.warn("User ID not found in the server response data. Check backend LoginResponse DTO.");
      }

      // Salvarea celorlalte date necesare
      localStorage.setItem('token', data.token);
      localStorage.setItem('userType', activeTab.value);
      localStorage.setItem('username', loginForm.username);

      // Actualizarea stării în Pinia (am presupus că folosești store.login din fisierul vechi)
      if(store.login) {
        store.login(loginForm.username);
      }

      const redirectPath = route.query.redirect || '/dashboard';
      router.push(redirectPath);

    } else {
      errorMessage.value = 'Invalid username or password.';
    }
  } catch (error) {
    console.error('Login error:', error);
    errorMessage.value = 'Server connection failed. Please try again later.';
  }
};
</script>

<style scoped>
/* Stilurile rămân neschimbate */
/* ... */

.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f8f9fa;
  font-family: 'Segoe UI', sans-serif;
  padding: 20px;
  position: relative;
}

.back-btn-absolute {
  position: absolute;
  top: 20px;
  left: 20px;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 1rem;
  color: #555;
  text-decoration: none;
  font-weight: 600;
  transition: color 0.2s;
  z-index: 10;
}

.back-btn-absolute:hover {
  color: #1a1a1a;
}

.login-card {
  background: white;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.06);
  width: 100%;
  max-width: 450px;
  margin-top: 20px;
}

.logo-header {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.logo-img {
  height: 80px;
  width: auto;
  object-fit: contain;
}

h1 {
  text-align: center;
  margin: 0 0 10px 0;
  color: #333;
  font-size: 1.8rem;
}

.subtitle {
  text-align: center;
  color: #666;
  margin-bottom: 30px;
}

/* TABS STYLING (Identic cu Register) */
.tabs-container {
  display: flex;
  background-color: #f1f3f5;
  padding: 5px;
  border-radius: 12px;
  margin-bottom: 30px;
}

.tab-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 10px;
  border: none;
  background: transparent;
  color: #666;
  font-weight: 600;
  cursor: pointer;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.tab-btn.active {
  background-color: white;
  color: #333;
  box-shadow: 0 2px 5px rgba(0,0,0,0.05);
}

/* FORM STYLES */
.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  font-weight: 600;
  margin-bottom: 8px;
  color: #333;
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
  background-color: white;
}

/* ERROR MESSAGE */
.error-alert {
  background-color: #ffeaea;
  color: #d63301;
  padding: 10px;
  border-radius: 6px;
  margin-bottom: 20px;
  text-align: center;
  font-size: 0.9rem;
  border: 1px solid #ffcccc;
}

.login-btn {
  width: 100%;
  padding: 14px;
  background-color: #198754;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: 600;
  cursor: pointer;
  margin-top: 10px;
  transition: background 0.3s;
}

.login-btn:hover {
  background-color: #157347;
}

.card-footer {
  text-align: center;
  margin-top: 25px;
  font-size: 0.95rem;
  color: #666;
}

.link {
  color: #198754;
  font-weight: 600;
  text-decoration: none;
}
</style>