<template>
  <div class="register-container">

    <router-link to="/" class="back-btn-absolute">
      <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><line x1="19" y1="12" x2="5" y2="12"></line><polyline points="12 19 5 12 12 5"></polyline></svg>
      Back
    </router-link>

    <div class="register-card">

      <div class="logo-header">
        <img src="/ResQ.png" alt="ResQ Logo" class="logo-img">
      </div>

      <h1>Create Account</h1>
      <p class="subtitle">Join ResQ to offer help</p>

      <div class="tabs-container">
        <button
            class="tab-btn"
            :class="{ active: activeTab === 'individual' }"
            @click="activeTab = 'individual'"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
          Individual
        </button>
        <button
            class="tab-btn"
            :class="{ active: activeTab === 'company' }"
            @click="activeTab = 'company'"
        >
          <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><rect x="2" y="7" width="20" height="14" rx="2" ry="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>
          Company
        </button>
      </div>

      <form @submit.prevent="handleRegister" class="register-form">

        <div v-if="activeTab === 'individual'" class="form-section">
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
            <label>Personal ID (SSN/CNP)</label>
            <input type="text" v-model="form.personalId" placeholder="Personal Numeric Code" class="input-field" />
          </div>

          <div class="form-group">
            <label>Phone</label>
            <input type="tel" v-model="form.phone" placeholder="Phone number" class="input-field" />
          </div>
        </div>

        <div v-if="activeTab === 'company'" class="form-section">
          <div class="form-group">
            <label>Company Name</label>
            <input type="text" v-model="form.companyName" placeholder="Company Name LLC" class="input-field" />
          </div>

          <div class="row-2">
            <div class="form-group">
              <label>Tax ID (CUI)</label>
              <input type="text" v-model="form.cui" placeholder="Registration Code" class="input-field" />
            </div>
            <div class="form-group">
              <label>Reg. Number</label>
              <input type="text" v-model="form.regNo" placeholder="J00/000/0000" class="input-field" />
            </div>
          </div>

          <div class="form-group">
            <label>HQ Address</label>
            <input type="text" v-model="form.address" placeholder="Full headquarters address" class="input-field" />
          </div>

          <div class="form-group">
            <label>Contact Person</label>
            <input type="text" v-model="form.contactPerson" placeholder="Full Name" class="input-field" />
          </div>

          <div class="row-2">
            <div class="form-group">
              <label>Phone</label>
              <input type="tel" v-model="form.phone" placeholder="Contact phone" class="input-field" />
            </div>
            <div class="form-group">
              <label>Company Email</label>
              <input type="email" v-model="form.email" placeholder="contact@company.com" class="input-field" />
            </div>
          </div>

          <div class="form-group">
            <label>Username</label>
            <input type="text" v-model="form.username" placeholder="Choose a username" class="input-field" />
          </div>
        </div>

        <div v-if="activeTab === 'individual'" class="form-group">
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
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><line x1="12" y1="8" x2="12" y2="12"></line><line x1="12" y1="16" x2="12.01" y2="16"></line></svg>
          {{ errorMessage }}
        </div>

        <button type="submit" class="submit-btn">
          Create Account
        </button>

      </form>

      <div class="card-footer">
        <p>Already have an account? <router-link to="/login" class="link">Login here</router-link></p>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const activeTab = ref('individual');
const errorMessage = ref('');

const form = reactive({
  firstName: '',
  lastName: '',
  username: '',
  personalId: '',
  phone: '',
  email: '',
  password: '',
  confirmPassword: '',
  companyName: '',
  cui: '',
  regNo: '',
  address: '',
  contactPerson: ''
});

onMounted(() => {
  if (localStorage.getItem('token')) {
    console.log("User logged in, redirecting from Register to Home.");
    router.push('/');
  }
});

const handleRegister = async () => {
  errorMessage.value = '';


  // frontend validations - check for empty common fields
  if (!form.username || !form.password || !form.email || !form.phone) {
    errorMessage.value = "Please fill in all required fields (Username, Email, Phone, Password).";
    return;
  }

  //pass match
  if (form.password !== form.confirmPassword) {
    errorMessage.value = "Passwords do not match!";
    return;
  }

  let url = '';
  let payload = {};

  if (activeTab.value === 'individual') {
    if (!form.firstName || !form.lastName) {
      errorMessage.value = "First Name and Last Name are required.";
      return;
    }
    if (!form.personalId || form.personalId.length !== 13 || isNaN(form.personalId)) {
      errorMessage.value = "Personal ID (CNP) must be exactly 13 numeric digits.";
      return;
    }

    url = 'http://localhost:9090/auth/register';
    payload = {
      firstName: form.firstName,
      lastName: form.lastName,
      username: form.username,
      pid: form.personalId,
      telephone: form.phone,
      email: form.email,
      password: form.password,
      assistanceTypeList: []
    };

  } else {
    if (!form.companyName || !form.cui || !form.address) {
      errorMessage.value = "Company Name, CUI and Address are required.";
      return;
    }

    url = 'http://localhost:9090/auth/company/register';
    payload = {
      companyName: form.companyName,
      cui: form.cui,
      registrationNumber: form.regNo,
      headquartersAddress: form.address,
      contactPerson: form.contactPerson,
      phoneNumber: form.phone,
      email: form.email,
      username: form.username,
      password: form.password
    };
  }

  try {
    //backend request
    const response = await fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    });

    if (response.ok) {
      const data = await response.json();
      console.log(`${activeTab.value} registration successful:`, data);
      router.push('/login');
    } else {
      // Show Backend Error
      const errorText = await response.text();
      errorMessage.value = errorText || 'Registration failed. Please try again.';
      console.error('Registration failed:', errorText);
    }
  } catch (error) {
    console.error('Network error:', error);
    errorMessage.value = 'Connection error. Check if the server is running on port 9090.';
  }
};
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #f8f9fa;
  padding: 40px 20px;
  font-family: 'Segoe UI', sans-serif;
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

.register-card {
  background: white;
  padding: 40px;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.06);
  width: 100%;
  max-width: 500px;
  margin-top: 20px;
}

.logo-header {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.logo-img {
  height: 60px;
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

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  font-weight: 600;
  margin-bottom: 6px;
  color: #333;
  font-size: 0.9rem;
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

.row-2 {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 15px;
}

/* ERROR MESSAGE STYLING */
.error-alert {
  background-color: #ffeaea;
  color: #d63301;
  padding: 12px;
  border-radius: 8px;
  margin-top: 20px;
  margin-bottom: 10px;
  border: 1px solid #ffcccc;
  font-size: 0.9rem;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
  justify-content: center;
}

.submit-btn {
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

.submit-btn:hover {
  background-color: #157347;
}

.card-footer {
  text-align: center;
  margin-top: 20px;
  font-size: 0.9rem;
  color: #666;
}

.link {
  color: #198754;
  font-weight: 600;
  text-decoration: none;
}
</style>