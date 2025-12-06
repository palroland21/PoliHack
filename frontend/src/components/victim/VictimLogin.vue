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

      <h1>Need Assistance?</h1>
      <p class="subtitle">Login to request help immediately</p>

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
          Login to Get Help
        </button>
      </form>

      <div class="card-footer">
        <p>Don't have an account? <router-link to="/client/register" class="link">Register here</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const errorMessage = ref('');

const loginForm = reactive({
  username: '',
  password: ''
});

const submitLogin = async () => {
  errorMessage.value = '';

  const loginUrl = 'http://localhost:9090/auth/client/login';

  try {
    const response = await fetch(loginUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(loginForm)
    });

    if (response.ok) {
      const data = await response.json();

      const userId = data.id || data.userId;
      if (userId) {
        localStorage.setItem('loggedUserId', userId);
      }

      localStorage.setItem('token', data.token);
      localStorage.setItem('userType', 'client');
      localStorage.setItem('username', loginForm.username);

      window.location.href = '/client';

    } else {
      errorMessage.value = 'Invalid username or password.';
    }
  } catch (error) {
    console.error('Login error:', error);
    errorMessage.value = 'Server connection failed.';
  }
};
</script>

<style scoped>
.login-btn {
  background-color: #dc3545;
  width: 100%;
  padding: 14px;
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
  background-color: #bb2d3b;
}

.link {
  color: #dc3545;
  font-weight: 600;
  text-decoration: none;
}

.login-container { min-height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #f8f9fa; font-family: 'Segoe UI', sans-serif; padding: 20px; position: relative; }
.back-btn-absolute { position: absolute; top: 20px; left: 20px; display: flex; align-items: center; gap: 8px; font-size: 1rem; color: #555; text-decoration: none; font-weight: 600; z-index: 10; }
.login-card { background: white; padding: 40px; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.06); width: 100%; max-width: 450px; margin-top: 20px; }
.logo-header { display: flex; justify-content: center; margin-bottom: 20px; }
.logo-img { height: 80px; width: auto; object-fit: contain; }
h1 { text-align: center; margin: 0 0 10px 0; color: #333; font-size: 1.8rem; }
.subtitle { text-align: center; color: #666; margin-bottom: 30px; }
.form-group { margin-bottom: 20px; }
label { display: block; font-weight: 600; margin-bottom: 8px; color: #333; }
.input-field { width: 100%; padding: 12px 15px; border: 1px solid #ddd; border-radius: 8px; font-size: 1rem; background-color: #f9f9f9; box-sizing: border-box; }
.input-field:focus { outline: none; border-color: #dc3545; background-color: white; }
.error-alert { background-color: #ffeaea; color: #d63301; padding: 10px; border-radius: 6px; margin-bottom: 20px; text-align: center; border: 1px solid #ffcccc; }
.card-footer { text-align: center; margin-top: 25px; font-size: 0.95rem; color: #666; }
</style>