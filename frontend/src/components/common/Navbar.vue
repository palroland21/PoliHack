<template>
  <nav class="navbar">
    <div class="navbar-container">
      <!-- Logo and brand -->
      <div class="navbar-brand">
        <img src="/ResQ.png" alt="ResQ Logo" class="logo-img">
      </div>

      <!-- Navigation menu -->
      <ul class="nav-menu">
        <li class="nav-item">
          <router-link to="/" class="nav-link" @click="handleNavClick">
            Acasă
          </router-link>
        </li>
        <li class="nav-item">
          <a href="#about" class="nav-link" @click="handleNavClick">
            Despre</a>
        </li>
        <li class="nav-item">
          <a href="#contact" class="nav-link" @click="handleNavClick">
            Contact
          </a>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  name: 'Navbar',
  methods: {
    handleNavClick() {
      // Navigation link clicked
    }
  }
}
</script>

<style scoped>
/* Navbar styling */
.navbar {
  background-color: #f5f5f5;
  padding: 0;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.08);
  position: sticky;
  top: 0;
  z-index: 1000;
}

.navbar-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
}

/* Logo styling */
.navbar-brand {
  display: flex;
  align-items: center;
  margin-right: 40px;
}

.logo-img {
  width: 50px;
  height: 50px;
  object-fit: contain;
  display: block;
}


/* Navigation menu */
.nav-menu {
  display: flex;
  list-style: none;
  gap: 50px;
  margin: 0;
  padding: 0;
}

.nav-item {
  margin: 0;
}

.nav-link {
  color: #999;
  text-decoration: none;
  font-weight: 500;
  font-size: 15px;
  position: relative;
  padding: 5px 0;
  transition: color 0.3s ease;
}

.nav-link::after {
  content: '';
  position: absolute;
  bottom: -5px;
  left: 0;
  width: 0;
  height: 2px;
  background-color: #8b5a3c;
  transition: width 0.3s ease;
}

.nav-link:hover {
  color: #333;
}

.nav-link:hover::after {
  width: 100%;
}
</style>
