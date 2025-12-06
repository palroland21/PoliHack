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

      <h1>Rescuer Login</h1>
      <p class="subtitle">Log in to manage your resources</p>

      <form @submit.prevent="handleLogin" class="login-form">

        <div class="form-group">
          <label>Username</label>
          <input
              type="text"
              v-model="username"
              placeholder="Enter your username"
              class="input-field"
          />
        </div>

        <div class="form-group">
          <label>Password</label>
          <input
              type="password"
              v-model="password"
              placeholder="Enter your password"
              class="input-field"
          />
        </div>

        <button type="submit" class="login-btn">
          → Login
        </button>

      </form>

      <div class="card-footer">
        <p>No account? <router-link to="/register" class="link">Register here</router-link></p>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const username = ref('');
const password = ref('');

const handleLogin = () => {
  // Aici vei pune logica de Firebase Login
  console.log("Logging in...", username.value);
  // Simulam succes si mergem la Dashboard
  router.push('/dashboard');
};
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f8f9fa;
  font-family: 'Segoe UI', sans-serif;
  padding: 20px;
  position: relative; /* Necesar pentru pozitionarea butonului back */
}

/* STIL BUTON BACK (Stanga Sus) */
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
  text-align: center;
  margin-top: 20px;
}

/* LOGO STYLES */
.logo-header {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.logo-img {
  height: 80px; /* Dimensiune logo */
  width: auto;
  object-fit: contain;
}

h1 {
  margin: 0 0 10px 0;
  color: #333;
  font-size: 1.8rem;
}

.subtitle {
  color: #666;
  margin-bottom: 30px;
}

.form-group {
  text-align: left;
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

.login-btn {
  width: 100%;
  padding: 14px;
  background-color: #198754; /* Verdele ResQ */
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