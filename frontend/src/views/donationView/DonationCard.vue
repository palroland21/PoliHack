<script setup>
import { ref } from 'vue';
import axios from 'axios';

// Variabile reactive (State)
const customAmount = ref(''); // Aici se salvează suma (și din input, și din butoane)
const selectedCurrency = ref('RON');
const loading = ref(false);

const currencies = ['RON', 'EUR', 'USD'];
const presetAmounts = [20, 50, 100, 200];

// Funcția care trimite plata către Backend
const initiateDonation = async () => {
  // Verificăm valoarea din customAmount
  const amountToPay = customAmount.value;

  if (!amountToPay || amountToPay <= 0) {
    alert("Please enter a valid amount!");
    return;
  }

  loading.value = true;


  try {
    // Trimitem la backend
    const response = await axios.post('http://localhost:9090/payment/create-checkout-session', {
      amount: amountToPay,
      currency: selectedCurrency.value
    });

    if (response.data && response.data.checkoutUrl) {
      window.location.href = response.data.checkoutUrl;
    }
  } catch (error) {
    console.error("Error processing payment:", error);
    alert("Something went wrong. Please check the console.");
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="donation-card">
    <div class="card-header">
      <h3>☕ Support the Project</h3>
      <p>Choose the amount and currency</p>
    </div>

    <div class="card-body">

      <div class="currency-selector">
        <label>Currency:</label>
        <div class="currency-options">
          <button
              v-for="curr in currencies"
              :key="curr"
              :class="{ active: selectedCurrency === curr }"
              @click="selectedCurrency = curr"
          >
            {{ curr }}
          </button>
        </div>
      </div>

      <div class="preset-buttons">
        <button
            v-for="amount in presetAmounts"
            :key="amount"
            class="preset-btn"
            @click="customAmount = amount"
            :disabled="loading"
        >
          {{ amount }} {{ selectedCurrency }}
        </button>
      </div>

      <div class="divider">or a custom amount</div>

      <div class="custom-input-group">
        <input
            type="number"
            v-model="customAmount"
            placeholder="Ex: 500"
            min="1"
        />
        <span class="currency-label">{{ selectedCurrency }}</span>
      </div>

      <button
          class="donate-btn"
          @click="initiateDonation"
          :disabled="loading || !customAmount"
      >
        <span v-if="!loading">Donate {{ customAmount ? customAmount + ' ' + selectedCurrency : '' }} ❤️</span>
        <span v-else>Processing...</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.donation-card {
  background: white;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.08);
  max-width: 400px;
  margin: 2rem auto;
  font-family: 'Segoe UI', sans-serif;
  border: 1px solid #f0f0f0;
  overflow: hidden;
}

.card-header {
  background: linear-gradient(135deg, #00b09b, #96c93d); /* Fresh Green */
  color: white;
  padding: 1.5rem;
  text-align: center;
}

.card-header h3 { margin: 0; font-size: 1.4rem; }
.card-header p { margin: 5px 0 0; opacity: 0.9; font-size: 0.9rem; }

.card-body { padding: 1.5rem; }

/* Currency Selector Styles */
.currency-selector {
  margin-bottom: 1rem;
  text-align: center;
}
.currency-selector label {
  display: block;
  font-size: 0.85rem;
  color: #666;
  margin-bottom: 5px;
}
.currency-options {
  display: flex;
  justify-content: center;
  gap: 5px;
  background: #f1f3f5;
  padding: 4px;
  border-radius: 8px;
  width: fit-content;
  margin: 0 auto;
}
.currency-options button {
  border: none;
  background: transparent;
  padding: 6px 12px;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 600;
  color: #495057;
  transition: all 0.2s;
}
.currency-options button.active {
  background: white;
  color: #00b09b;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.preset-buttons {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
  margin-bottom: 1rem;
}

.preset-btn {
  background: #fff;
  border: 1px solid #dee2e6;
  padding: 10px;
  border-radius: 8px;
  cursor: pointer;
  font-weight: 600;
  color: #343a40;
  transition: 0.2s;
}
.preset-btn:hover {
  background: #f8f9fa;
  border-color: #adb5bd;
}

.divider {
  text-align: center;
  font-size: 0.8rem;
  color: #adb5bd;
  margin: 1rem 0;
}

.custom-input-group {
  position: relative;
  margin-bottom: 1.5rem;
}
.custom-input-group input {
  width: 100%;
  padding: 12px;
  border: 2px solid #e9ecef;
  border-radius: 8px;
  font-size: 1rem;
  outline: none;
}
.custom-input-group input:focus {
  border-color: #00b09b;
}
.currency-label {
  position: absolute;
  right: 15px;
  top: 50%;
  transform: translateY(-50%);
  color: #6c757d;
  font-weight: bold;
}

.donate-btn {
  width: 100%;
  padding: 14px;
  background: #00b09b;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background 0.3s;
}
.donate-btn:hover:not(:disabled) {
  background: #009688;
}
.donate-btn:disabled {
  background: #aab2bd;
  cursor: not-allowed;
}
</style>