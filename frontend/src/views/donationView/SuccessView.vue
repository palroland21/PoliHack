<template>
  <div class="success-page">
    <div class="card">
      <div style="font-size: 50px;">🎉</div>
      <h1>Thank you for your donation!</h1>
      <p>Payment processed successfully. Your help is invaluable.</p>
      <router-link to="/" class="home-btn">Back to Home</router-link>
    </div>
  </div>
</template>

<style scoped>
.success-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #e9ecef;
}
.card {
  background: white;
  padding: 40px;
  border-radius: 15px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}
.home-btn {
  display: inline-block;
  margin-top: 20px;
  padding: 10px 20px;
  background: #00b09b;
  color: white;
  text-decoration: none;
  border-radius: 5px;
  font-weight: bold;
}
</style>