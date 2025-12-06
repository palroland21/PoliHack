<script setup>
import { ref, computed, onMounted } from 'vue';
import axios from 'axios';
import { useMainStore } from '@/stores/mainStore'; // Asigură-te că importi store-ul

const store = useMainStore();

// --- STATE ---
const customAmount = ref('');
const selectedCurrency = ref('RON');
const loading = ref(false);
const activeTab = ref('donation'); // 'donation' sau 'subscription'

const currencies = ['RON', 'EUR', 'USD'];
const presetAmounts = [20, 50, 100, 200];

// --- LOGICA PENTRU AFISARE TABS ---
// Verificam daca userul este Victima (Client)
const isVictim = computed(() => {
  // Verificam in store sau localStorage
  const type = store.userType || localStorage.getItem('userType');
  return type === 'client' || type === 'victim';
});

// --- FUNCȚIA PENTRU DONAȚIE (ONE-TIME) ---
const initiateDonation = async () => {
  const amountToPay = customAmount.value;

  if (!amountToPay || amountToPay <= 0) {
    alert("Please enter a valid amount!");
    return;
  }

  loading.value = true;

  try {
    const response = await axios.post('http://localhost:9090/payment/create-checkout-session', {
      amount: amountToPay,
      currency: selectedCurrency.value,
      type: 'donation' // Backend-ul poate folosi asta pentru a distinge
    });

    if (response.data && response.data.checkoutUrl) {
      window.location.href = response.data.checkoutUrl;
    }
  } catch (error) {
    console.error("Error processing donation:", error);
    alert("Payment failed.");
  } finally {
    loading.value = false;
  }
};

// --- FUNCȚIA PENTRU SUBSCRIPȚIE (RECURRING) ---
const initiateSubscription = async () => {
  loading.value = true;

  try {
    // Trimitem cerere către endpoint-ul de subscripție
    // Backend-ul trebuie să creeze o sesiune Stripe cu mode='subscription' și un priceId fix
    const response = await axios.post('http://localhost:9090/payment/create-subscription-session', {
      currency: 'USD',
      price: 10 // Prețul fix de 10$
    });

    if (response.data && response.data.checkoutUrl) {
      window.location.href = response.data.checkoutUrl;
    }
  } catch (error) {
    console.error("Error processing subscription:", error);
    alert("Subscription failed. Check console.");
  } finally {
    loading.value = false;
  }
};
</script>

<template>
  <div class="donation-card">
    <div class="card-header">
      <h3 v-if="activeTab === 'donation'">☕ Support the Project</h3>
      <h3 v-else>⭐ Become a Member</h3>
      <p>Every contribution counts for disaster victims.</p>
    </div>

    <div class="card-body">

      <div v-if="isVictim" class="tabs-container">
        <button
            class="tab-btn"
            :class="{ active: activeTab === 'donation' }"
            @click="activeTab = 'donation'"
        >
          Donation
        </button>
        <button
            class="tab-btn"
            :class="{ active: activeTab === 'subscription' }"
            @click="activeTab = 'subscription'"
        >
          Pay Subscription
        </button>
      </div>

      <div v-if="activeTab === 'donation'" class="fade-in">
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

      <div v-else class="subscription-content fade-in">
        <div class="sub-icon">🚀</div>
        <h4>Monthly Premium Support</h4>
        <p class="sub-desc">Get access to premium resources and help us maintain the platform.</p>

        <div class="price-box">
          <span class="amount">10$</span>
          <span class="period">/ month</span>
        </div>

        <ul class="benefits-list">
          <li>✅ Priority Support</li>
          <li>✅ Recurring contribution</li>
          <li>✅ Cancel anytime</li>
        </ul>

        <button
            class="donate-btn sub-btn"
            @click="initiateSubscription"
            :disabled="loading"
        >
          <span v-if="!loading">Subscribe for $10/mo</span>
          <span v-else>Processing...</span>
        </button>
      </div>

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
  background: linear-gradient(135deg, #00b09b, #96c93d);
  color: white;
  padding: 1.5rem;
  text-align: center;
  transition: background 0.3s ease;
}

.card-header h3 { margin: 0; font-size: 1.4rem; }
.card-header p { margin: 5px 0 0; opacity: 0.9; font-size: 0.9rem; }

.card-body { padding: 1.5rem; }

/* --- STILURI PENTRU TABS (Format Individual/Company) --- */
.tabs-container {
  display: flex;
  background-color: #f1f3f5;
  padding: 5px;
  border-radius: 12px;
  margin-bottom: 25px;
}

.tab-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border: none;
  background: transparent;
  color: #666;
  font-weight: 600;
  cursor: pointer;
  border-radius: 8px;
  transition: all 0.3s ease;
  font-size: 0.95rem;
}

.tab-btn.active {
  background-color: white;
  color: #333;
  box-shadow: 0 2px 5px rgba(0,0,0,0.05);
}

/* --- Currency Selector --- */
.currency-selector { margin-bottom: 1rem; text-align: center; }
.currency-selector label { display: block; font-size: 0.85rem; color: #666; margin-bottom: 5px; }
.currency-options {
  display: flex; justify-content: center; gap: 5px; background: #f1f3f5; padding: 4px; border-radius: 8px; width: fit-content; margin: 0 auto;
}
.currency-options button {
  border: none; background: transparent; padding: 6px 12px; border-radius: 6px; cursor: pointer; font-weight: 600; color: #495057; transition: all 0.2s;
}
.currency-options button.active { background: white; color: #00b09b; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }

/* --- Presets & Inputs --- */
.preset-buttons { display: grid; grid-template-columns: 1fr 1fr; gap: 10px; margin-bottom: 1rem; }
.preset-btn {
  background: #fff; border: 1px solid #dee2e6; padding: 10px; border-radius: 8px; cursor: pointer; font-weight: 600; color: #343a40; transition: 0.2s;
}
.preset-btn:hover { background: #f8f9fa; border-color: #adb5bd; }
.divider { text-align: center; font-size: 0.8rem; color: #adb5bd; margin: 1rem 0; }
.custom-input-group { position: relative; margin-bottom: 1.5rem; }
.custom-input-group input { width: 100%; padding: 12px; border: 2px solid #e9ecef; border-radius: 8px; font-size: 1rem; outline: none; box-sizing: border-box; }
.custom-input-group input:focus { border-color: #00b09b; }
.currency-label { position: absolute; right: 15px; top: 50%; transform: translateY(-50%); color: #6c757d; font-weight: bold; }

/* --- Main Button --- */
.donate-btn {
  width: 100%; padding: 14px; background: #00b09b; color: white; border: none; border-radius: 8px; font-size: 1.1rem; font-weight: bold; cursor: pointer; transition: background 0.3s;
}
.donate-btn:hover:not(:disabled) { background: #009688; }
.donate-btn:disabled { background: #aab2bd; cursor: not-allowed; }

/* --- STILURI SPECIFICE PENTRU SUBSCRIPTION --- */
.subscription-content {
  text-align: center;
  padding: 10px 0;
}
.sub-icon { font-size: 3rem; margin-bottom: 10px; }
.sub-desc { color: #666; font-size: 0.9rem; margin-bottom: 20px; }
.price-box {
  background: #f8f9fa;
  padding: 15px;
  border-radius: 12px;
  margin-bottom: 20px;
  border: 1px dashed #00b09b;
}
.amount { font-size: 2rem; font-weight: 800; color: #333; }
.period { font-size: 1rem; color: #777; font-weight: 500; }

.benefits-list {
  list-style: none; padding: 0; margin-bottom: 25px; text-align: left; display: inline-block;
}
.benefits-list li { margin-bottom: 8px; font-size: 0.95rem; color: #555; }

.sub-btn {
  background: #4a90e2; /* Culoare diferită pt subscription */
}
.sub-btn:hover:not(:disabled) { background: #357abd; }

/* Animatie simpla de fade */
.fade-in {
  animation: fadeIn 0.3s ease-in-out;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(5px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>