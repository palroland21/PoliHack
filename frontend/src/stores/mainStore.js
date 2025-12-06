import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useMainStore = defineStore('main', () => {
    // Verificam la start daca exista token
    const isLoggedIn = ref(!!localStorage.getItem('token'));
    const username = ref(localStorage.getItem('username') || '');

    // Actiune Login
    function login(user) {
        isLoggedIn.value = true;
        username.value = user;
    }

    // Actiune Logout
    function logout() {
        isLoggedIn.value = false;
        username.value = '';
        // (Optional) Putem sterge si aici din localStorage pentru siguranta dubla
        localStorage.removeItem('token');
        localStorage.removeItem('userType');
        localStorage.removeItem('username');
    }

    return { isLoggedIn, username, login, logout };
});