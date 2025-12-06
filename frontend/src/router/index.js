import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import VictimFlow from '../views/VictimFlow.vue'
import RescuerHub from '../views/RescuerHub.vue'
import LoginView from "@/views/LoginView.vue";
import RegisterView from "@/views/RegisterView.vue";
import DonationView from "../views/donationView/DonationView.vue";
import SuccessView from "../views/donationView/SuccessView.vue";
import RescuerDashboard from "@/components/rescuer/RescuerDashboard.vue";
import AboutView from "@/views/AboutView.vue";
import ContactView from "@/views/ContactView.vue";

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
    },
    {
        path: '/login',
        name: 'LoginView',
        component: LoginView
    },
    {
        path: '/register',
        name: 'RegisterView',
        component: RegisterView
    },
    {
        path: '/donate',
        name: 'DonationView',
        component: DonationView
    },
    {
        path: '/success',
        name: 'SuccessView',
        component: SuccessView
    },
    {
        path: '/dashboard',
        name: 'RescuerDashboard',
        component: RescuerDashboard
    },
    {
        path: '/about',
        name: 'AboutView',
        component: AboutView
    },
    {
        path: '/contact',
        name: 'ContactView',
        component: ContactView
    }

]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router
