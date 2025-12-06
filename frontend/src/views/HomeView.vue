<template>
  <div class="home-container">
    <div class="card-wrapper">

      <div class="header-section">
        <div class="logo-circle">
          <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
          </svg>
        </div>
        <h1 class="app-title">Emergency Aid</h1>
        <p class="app-subtitle">Emergency situation coordination platform</p>
      </div>

      <div class="divider">
        <span>Who are you?</span>
      </div>

      <div class="action-buttons">

        <button class="action-card victim-card" @click="handleVictimClick">
          <div class="icon-box">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path>
            </svg>
          </div>
          <div class="text-content">
            <h3>I NEED HELP</h3>
            <p>I am a victim and I need assistance</p>
          </div>
        </button>

        <button class="action-card rescuer-card" @click="handleRescuerClick">
          <div class="icon-box">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path>
            </svg>
          </div>
          <div class="text-content">
            <h3>I WANT TO HELP</h3>
            <p>I can offer help and resources</p>
          </div>
        </button>

      </div>

      <p class="footer-text">Choose the option that suits you to continue</p>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useMainStore } from '@/stores/mainStore';

const router = useRouter();
const store = useMainStore();

// --- LOGICA PENTRU VICTIMĂ ---
const handleVictimClick = () => {
  const token = localStorage.getItem('token');
  const userType = localStorage.getItem('userType');

  // SCENARIUL 2: Daca e logat -> Harta (/client)
  if (token && (userType === 'client' || userType === 'victim')) {
    router.push('/client');
  }
  // Altfel -> Login (/client/login)
  else {
    router.push('/client/login');
  }
};

// --- LOGICA PENTRU SALVATOR (Rescuer) ---
const handleRescuerClick = () => {
  if (store.isLoggedIn) {
    router.push('/rescuer');
  } else {
    router.push({ path: '/login', query: { redirect: '/rescuer' } });
  }
};
</script>

<style scoped>
/* CSS-ul rămâne neschimbat */
.home-container {
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f8f9fa;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  padding: 20px;
}

.card-wrapper {
  max-width: 500px;
  width: 100%;
  text-align: center;
}

.logo-circle {
  width: 60px;
  height: 60px;
  background-color: #eee;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 20px;
  color: #333;
}

.app-title {
  font-size: 2rem;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 10px;
}

.app-subtitle {
  color: #666;
  font-size: 1rem;
  margin-bottom: 30px;
}

.divider {
  margin: 30px 0;
  font-weight: 600;
  color: #333;
  font-size: 1.1rem;
}

.action-buttons {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.action-card {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 20px;
  border: none;
  border-radius: 12px;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
  text-align: left;
  box-shadow: 0 4px 6px rgba(0,0,0,0.1);
}

.action-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 15px rgba(0,0,0,0.15);
}

.icon-box {
  margin-right: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.text-content h3 {
  margin: 0;
  font-size: 1.1rem;
  font-weight: 800;
  letter-spacing: 0.5px;
}

.text-content p {
  margin: 5px 0 0;
  font-size: 0.9rem;
  opacity: 0.9;
}

.victim-card {
  background-color: #dc3545;
  color: white;
}

.rescuer-card {
  background-color: #198754;
  color: white;
}

.footer-text {
  margin-top: 40px;
  color: #888;
  font-size: 0.85rem;
}
</style>