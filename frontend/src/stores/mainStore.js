// src/stores/mainStore.js
import { defineStore } from 'pinia'

export const useMainStore = defineStore('main', {
    state: () => ({
        // Date existente
        userRole: null, // 'victim' sau 'rescuer'
        location: null,

        // Date NOI pentru Autentificare
        isLoggedIn: false,
        user: null // Aici vom stoca username-ul sau datele userului
    }),

    actions: {
        // Actiuni existente
        setUserRole(role) {
            this.userRole = role
        },
        setLocation(location) {
            this.location = location
        },

        // Actiuni NOI pentru Login/Logout
        login(username) {
            this.isLoggedIn = true;
            this.user = username;
        },

        logout() {
            this.isLoggedIn = false;
            this.user = null;
            // Optional: Putem reseta si rolul la delogare
            // this.userRole = null;
        }
    }
})