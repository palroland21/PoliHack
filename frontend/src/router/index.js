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

import VictimLogin from "@/components/victim/VictimLogin.vue";
import VictimRegister from "@/components/victim/VictimRegister.vue";
import ResourcesSelection from "@/views/ResourcesSelection.vue";

const routes = [
    {
        path: '/',
        name: 'Home',
        component: HomeView
    },
    // rute pt VICTIME (CLIENT) ---
    {
        path: '/client/login',
        name: 'VictimLogin',
        component: VictimLogin
    },
    {
        path: '/client/register',
        name: 'VictimRegister',
        component: VictimRegister
    },
    {
        path: '/client',
        name: 'VictimFlow',
        component: VictimFlow,
        beforeEnter: (to, from, next) => {
            const token = localStorage.getItem('token');
            const userType = localStorage.getItem('userType');

            if (token && (userType === 'client' || userType === 'victim')) {
                next();
            } else {
                next('/client/login');
            }
        }
    },
    // restul rutelor
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
    },
    {
        path: '/select-resources',
        name: 'ResourcesSelection',
        component: ResourcesSelection
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
})

export default router