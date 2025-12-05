import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import VictimFlow from '../views/VictimFlow.vue'
import RescuerHub from '../views/RescuerHub.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView
  },
  {
    path: '/victim',
    name: 'VictimFlow',
    component: VictimFlow
  },
  {
    path: '/rescuer',
    name: 'RescuerHub',
    component: RescuerHub
  }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router
