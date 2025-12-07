<template>
  <div class="page-container">

    <div class="header-center">
      <h1>Contact Us</h1>
      <p class="subtitle">Have questions or want to partner with us?</p>
    </div>

    <div class="contact-layout">

      <div class="info-side">
        <div class="info-item">
          <div class="icon-box">📧</div>
          <div>
            <h3>Email</h3>
            <p>support@resq-app.com</p>
            <p>partners@resq-app.com</p>
          </div>
        </div>

        <div class="info-item">
          <div class="icon-box">📞</div>
          <div>
            <h3>Phone</h3>
            <p>+40 700 123 456</p>
            <p>Mon-Fri, 9am - 6pm</p>
          </div>
        </div>

        <div class="info-item">
          <div class="icon-box">📍</div>
          <div>
            <h3>Headquarters</h3>
            <p>Bucharest, Romania</p>
            <p>Innovation Center, Floor 3</p>
          </div>
        </div>
      </div>

      <div class="form-side">
        <form @submit.prevent="sendMessage">
          <div class="form-group">
            <label>Your Name</label>
            <input type="text" placeholder="John Doe" class="input-field" required />
          </div>

          <div class="form-group">
            <label>Email Address</label>
            <input type="email" placeholder="john@example.com" class="input-field" required />
          </div>

          <div class="form-group">
            <label>Message</label>
            <textarea placeholder="How can we help you?" class="input-field textarea" required></textarea>
          </div>

          <button type="submit" class="submit-btn">Send Message</button>
        </form>
      </div>

    </div>

  </div>
</template>

<script setup>
const sendMessage = () => {
  alert("Message sent! We will get back to you soon.");
};
</script>

<style scoped>
.page-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 60px 20px;
  font-family: 'Segoe UI', sans-serif;
}

.header-center {
  text-align: center;
  margin-bottom: 50px;
}
h1 { font-size: 2.5rem; margin-bottom: 10px; color: #333; }
.subtitle { color: #666; font-size: 1.1rem; }

.contact-layout {
  display: grid;
  grid-template-columns: 1fr 1.5fr;
  gap: 50px;
  background: white;
  padding: 40px;
  border-radius: 20px;
  box-shadow: 0 4px 20px rgba(0,0,0,0.05);
}

.info-side {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.info-item {
  display: flex;
  gap: 15px;
}

.icon-box {
  width: 40px; height: 40px;
  background: #f0fdf4;
  border-radius: 10px;
  display: flex; align-items: center; justify-content: center;
  font-size: 1.2rem;
}

.info-item h3 { margin: 0 0 5px 0; font-size: 1.1rem; color: #333; }
.info-item p { margin: 0; color: #666; font-size: 0.95rem; }

.form-group { margin-bottom: 20px; }
label { display: block; font-weight: 600; margin-bottom: 8px; color: #333; }
.input-field {
  width: 100%; padding: 12px;
  border: 1px solid #ddd; border-radius: 8px;
  font-size: 1rem; background: #f9f9f9;
  font-family: inherit;
}
.textarea { min-height: 120px; resize: vertical; }
.input-field:focus { outline: none; border-color: #198754; background: white; }

.submit-btn {
  width: 100%; padding: 14px;
  background: #198754; color: white;
  border: none; border-radius: 8px;
  font-weight: 600; cursor: pointer;
  transition: background 0.2s;
}
.submit-btn:hover { background: #157347; }

@media (max-width: 768px) {
  .contact-layout { grid-template-columns: 1fr; gap: 30px; padding: 20px; }
  .info-side { flex-direction: row; flex-wrap: wrap; justify-content: space-between; }
}
</style>