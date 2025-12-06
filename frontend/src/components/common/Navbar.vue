<template>
  <nav class="navbar">
    <div class="navbar-container">

      <router-link to="/" class="navbar-brand">
        <div class="logo">
          <img src="/ResQ.png" alt="ResQ Logo" class="logo-icon">
          <span class="logo-text">ResQ</span>
        </div>
      </router-link>

      <ul class="nav-menu">
        <li class="nav-item">
          <router-link to="/" class="nav-link">Home</router-link>
        </li>
        <li class="nav-item">
          <a href="#" class="nav-link">About</a>
        </li>
        <li class="nav-item">
          <a href="#" class="nav-link">Contact</a>
        </li>
      </ul>

      <div class="navbar-actions">

        <router-link v-if="!store.isLoggedIn" to="/login" class="auth-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"/><polyline points="10 17 15 12 10 7"/><line x1="15" y1="12" x2="3" y2="12"/></svg>
          Sign In
        </router-link>

        <div v-else class="logged-in-actions">

          <router-link to="/dashboard" class="dashboard-btn">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="7" height="7"></rect><rect x="14" y="3" width="7" height="7"></rect><rect x="14" y="14" width="7" height="7"></rect><rect x="3" y="14" width="7" height="7"></rect></svg>
            Dashboard
          </router-link>

          <button @click="handleLogout" class="logout-btn">
            <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path><polyline points="16 17 21 12 16 7"></polyline><line x1="21" y1="12" x2="9" y2="12"></line></svg>
            Log Out
          </button>

        </div>

      </div>

    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useMainStore } from '@/stores/mainStore';

const store = useMainStore();
const router = useRouter();

const handleLogout = () => {
  // 1. Apelam actiunea de logout din Pinia
  store.logout();

  // 2. Redirectionam catre Home (sau Login)
  router.push('/');
};
</script>

<style scoped>
.navbar {
  background-color: #f8f9fa;
  padding: 0;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
  position: sticky;
  top: 0;
  z-index: 1000;
  font-family: 'Segoe UI', sans-serif;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 80px;
}

.navbar-brand { text-decoration: none; }

.logo { display: flex; align-items: center; gap: 10px; }
.logo-icon { height: 40px; width: auto; object-fit: contain; }
.logo-text { color: #333; font-weight: 700; font-size: 24px; }

.nav-menu { display: flex; list-style: none; gap: 40px; margin: 0; padding: 0; }
@media (max-width: 768px) { .nav-menu { display: none; } }

.nav-link {
  color: #6c757d; text-decoration: none; font-weight: 500; font-size: 16px; transition: color 0.3s ease;
}
.nav-link:hover { color: #198754; }

/* Wrapper pentru actiunile cand esti logat */
.logged-in-actions {
  display: flex;
  align-items: center;
  gap: 15px;
}

/* SIGN IN BUTTON (Verde) */
.auth-button {
  display: flex; align-items: center; gap: 8px;
  background-color: #198754; color: white;
  padding: 10px 24px; border-radius: 8px;
  text-decoration: none; font-weight: 600; font-size: 15px;
  transition: background-color 0.2s, transform 0.1s;
  box-shadow: 0 4px 6px rgba(25, 135, 84, 0.2);
}
.auth-button:hover { background-color: #157347; transform: translateY(-1px); }
.auth-button:active { transform: translateY(0); }

/* DASHBOARD BUTTON (Gri Inchis) */
.dashboard-btn {
  display: flex; align-items: center; gap: 8px;
  background-color: #343a40; color: white;
  padding: 8px 16px; border-radius: 8px;
  text-decoration: none; font-weight: 600; font-size: 14px;
  transition: background-color 0.2s;
}
.dashboard-btn:hover { background-color: #23272b; }

/* LOGOUT BUTTON (Rosu Outline / Minimal) */
.logout-btn {
  display: flex; align-items: center; gap: 8px;
  background-color: transparent;
  color: #dc3545; /* Rosu */
  border: 1px solid #dc3545;
  padding: 8px 16px; border-radius: 8px;
  font-weight: 600; font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}
.logout-btn:hover {
  background-color: #dc3545;
  color: white;
}
</style>