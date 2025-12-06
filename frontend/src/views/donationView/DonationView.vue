<script setup>
import DonationCard from '../donationView/DonationCard.vue'
</script>

<template>
  <div class="donation-container">
    <h1>Support Our Mission</h1>
    <p>Every contribution counts for disaster victims.</p>

    <DonationCard />
  </div>
</template>

<style scoped>
.donation-container {
  min-height: 80vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #f8f9fa;
  padding: 20px;
  text-align: center;
}

h1 {
  color: #2c3e50;
  margin-bottom: 10px;
}
</style>