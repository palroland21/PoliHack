<template>
  <div class="page-container">

    <div class="hero-section">
      <div class="logo-box">
        <img src="/ResQ.png" alt="ResQ Logo" class="logo-img">
      </div>
      <h1>About ResQ</h1>
      <p class="subtitle">Empowering communities to respond faster in times of crisis.</p>
    </div>

    <div class="mission-grid">
      <div class="info-card">
        <div class="icon-circle red">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M20.84 4.61a5.5 5.5 0 0 0-7.78 0L12 5.67l-1.06-1.06a5.5 5.5 0 0 0-7.78 7.78l1.06 1.06L12 21.23l7.78-7.78 1.06-1.06a5.5 5.5 0 0 0 0-7.78z"></path></svg>
        </div>
        <h3>For Victims</h3>
        <p>Immediate access to medical help, resources, and shelter when every second counts.</p>
      </div>

      <div class="info-card">
        <div class="icon-circle green">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"></path></svg>
        </div>
        <h3>For Rescuers</h3>
        <p>A coordinated platform for volunteers, doctors, and companies to offer targeted aid.</p>
      </div>

      <div class="info-card">
        <div class="icon-circle blue">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="10"></circle><line x1="2" y1="12" x2="22" y2="12"></line><path d="M12 2a15.3 15.3 0 0 1 4 10 15.3 15.3 0 0 1-4 10 15.3 15.3 0 0 1-4-10 15.3 15.3 0 0 1 4-10z"></path></svg>
        </div>
        <h3>Community First</h3>
        <p>Connecting local heroes with neighbors in need, bridging the gap before official services arrive.</p>
      </div>
    </div>

    <div class="story-section">
      <h2>Our Story</h2>
      <p>
        ResQ started with a simple idea: <strong>what if help was just around the corner?</strong>
        In emergency situations, ambulance response times can be critical.
        By mobilizing retired doctors, medical students, and willing volunteers nearby,
        we can save lives while official emergency services are on their way.
      </p>
    </div>

    <div class="cta-section">
      <button @click="$router.push('/register')" class="cta-btn">Join the Community</button>
    </div>

  </div>
</template>

<style scoped>
.page-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 60px 20px;
  font-family: 'Segoe UI', sans-serif;
  color: #333;
}

.hero-section {
  text-align: center;
  margin-bottom: 60px;
}
.logo-box {
  width: 80px; height: 80px;
  background: #f8f9fa; border-radius: 50%;
  display: flex; align-items: center; justify-content: center;
  margin: 0 auto 20px;
}
.logo-img { height: 50px; width: auto; }
h1 { font-size: 2.5rem; margin-bottom: 10px; }
.subtitle { font-size: 1.2rem; color: #666; max-width: 600px; margin: 0 auto; }

.mission-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 30px;
  margin-bottom: 60px;
}

.info-card {
  background: white;
  padding: 30px;
  border-radius: 16px;
  border: 1px solid #eee;
  text-align: center;
  transition: transform 0.2s, box-shadow 0.2s;
}
.info-card:hover { transform: translateY(-5px); box-shadow: 0 10px 20px rgba(0,0,0,0.05); }

.icon-circle {
  width: 60px; height: 60px;
  border-radius: 50%;
  display: flex; align-items: center; justify-content: center;
  margin: 0 auto 20px;
}
.icon-circle.red { background: #ffebee; color: #dc3545; }
.icon-circle.green { background: #e8f5e9; color: #198754; }
.icon-circle.blue { background: #e3f2fd; color: #0d6efd; }

.info-card h3 { margin-bottom: 10px; font-size: 1.2rem; }
.info-card p { color: #666; line-height: 1.5; font-size: 0.95rem; }

.story-section {
  background: #f8f9fa;
  padding: 40px;
  border-radius: 16px;
  text-align: center;
  margin-bottom: 40px;
}
.story-section h2 { margin-bottom: 20px; }
.story-section p { max-width: 700px; margin: 0 auto; line-height: 1.6; color: #555; font-size: 1.05rem; }

.cta-section { text-align: center; }
.cta-btn {
  background: #198754; color: white;
  padding: 12px 30px; border: none; border-radius: 8px;
  font-size: 1.1rem; font-weight: 600; cursor: pointer;
  transition: background 0.2s;
}
.cta-btn:hover { background: #157347; }
</style>