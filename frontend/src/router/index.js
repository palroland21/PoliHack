import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import VictimFlow from '../views/VictimFlow.vue'
import RescuerHub from '../views/RescuerHub.vue'

const routes = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/victim',
        name: 'victim',
        component: VictimFlow
    },
    {
        path: '/rescuer',
        name: 'rescuer',
        component: RescuerHub
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router