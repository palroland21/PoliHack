<template>
  <div class="register-container">
    <router-link to="/client/login" class="back-btn-absolute">
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="12" x2="5" y2="12"></line><polyline points="12 19 5 12 12 5"></polyline></svg>
      Back to Login
    </router-link>

    <div class="register-card">
      <div class="logo-header">
        <img src="/ResQ.png" alt="ResQ Logo" class="logo-img">
      </div>

      <h1>Join as Beneficiary</h1>
      <p class="subtitle">Create an account to ask for help</p>

      <form @submit.prevent="handleRegister" class="register-form">

        <div class="row-2">
          <div class="form-group">
            <label>First Name</label>
            <input type="text" v-model="form.firstName" placeholder="First Name" class="input-field" />
          </div>
          <div class="form-group">
            <label>Last Name</label>
            <input type="text" v-model="form.lastName" placeholder="Last Name" class="input-field" />
          </div>
        </div>

        <div class="form-group">
          <label>Username</label>
          <input type="text" v-model="form.username" placeholder="Choose a username" class="input-field" />
        </div>

        <div class="form-group">
          <label>Phone</label>
          <input type="tel" v-model="form.phone" placeholder="Phone number" class="input-field" />
        </div>

        <div class="form-group">
          <label>Personal ID (Optional/Req)</label>
          <input type="text" v-model="form.pid" placeholder="CNP" class="input-field" />
        </div>

        <div class="form-group">
          <label>Email</label>
          <input type="email" v-model="form.email" placeholder="email@example.com" class="input-field" />
        </div>

        <div class="row-2">
          <div class="form-group">
            <label>Password</label>
            <input type="password" v-model="form.password" placeholder="Password" class="input-field" />
          </div>
          <div class="form-group">
            <label>Confirm</label>
            <input type="password" v-model="form.confirmPassword" placeholder="Confirm" class="input-field" />
          </div>
        </div>

        <div v-if="errorMessage" class="error-alert">
          {{ errorMessage }}
        </div>

        <button type="submit" class="submit-btn">
          Register
        </button>
      </form>

      <div class="card-footer">
        <p>Already have an account? <router-link to="/client/login" class="link">Login here</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const errorMessage = ref('');

const form = reactive({
  firstName: '',
  lastName: '',
  username: '',
  phone: '',
  email: '',
  pid: '',
  password: '',
  confirmPassword: ''
});

const handleRegister = async () => {
  errorMessage.value = '';

  if (form.password !== form.confirmPassword) {
    errorMessage.value = "Passwords do not match!";
    return;
  }

  const url = 'http://localhost:9090/auth/client/register';

  const payload = {
    firstName: form.firstName,
    lastName: form.lastName,
    username: form.username,
    telephone: form.phone,
    email: form.email,
    password: form.password,
    cnp: form.pid
  };

  try {
    const response = await fetch(url, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });

    if (response.ok) {
      router.push('/client/login');
    } else {
      const errorText = await response.text();
      errorMessage.value = errorText || 'Registration failed.';
    }
  } catch (error) {
    errorMessage.value = 'Connection error.';
  }
};
</script>

<style scoped>
.submit-btn {
  background-color: #dc3545;
  width: 100%; padding: 14px; color: white; border: none; border-radius: 8px; font-size: 1.1rem; font-weight: 600; cursor: pointer; margin-top: 10px; transition: background 0.3s;
}
.submit-btn:hover { background-color: #bb2d3b; }
.link { color: #dc3545; font-weight: 600; text-decoration: none; }
.input-field:focus { outline: none; border-color: #dc3545; background-color: white; }

.register-container { min-height: 100vh; display: flex; align-items: center; justify-content: center; background-color: #f8f9fa; padding: 40px 20px; font-family: 'Segoe UI', sans-serif; position: relative; }
.back-btn-absolute { position: absolute; top: 20px; left: 20px; display: flex; align-items: center; gap: 8px; font-size: 1rem; color: #555; text-decoration: none; font-weight: 600; z-index: 10; }
.register-card { background: white; padding: 40px; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.06); width: 100%; max-width: 500px; margin-top: 20px; }
.logo-header { display: flex; justify-content: center; margin-bottom: 20px; }
.logo-img { height: 60px; width: auto; object-fit: contain; }
h1 { text-align: center; margin: 0 0 10px 0; color: #333; font-size: 1.8rem; }
.subtitle { text-align: center; color: #666; margin-bottom: 30px; }
.form-group { margin-bottom: 15px; }
label { display: block; font-weight: 600; margin-bottom: 6px; color: #333; font-size: 0.9rem; }
.input-field { width: 100%; padding: 12px 15px; border: 1px solid #ddd; border-radius: 8px; font-size: 1rem; background-color: #f9f9f9; box-sizing: border-box; }
.row-2 { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
.error-alert { background-color: #ffeaea; color: #d63301; padding: 12px; border-radius: 8px; margin-top: 20px; border: 1px solid #ffcccc; font-size: 0.9rem; text-align: center;}
.card-footer { text-align: center; margin-top: 20px; font-size: 0.9rem; color: #666; }
</style>