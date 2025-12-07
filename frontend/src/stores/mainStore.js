import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useMainStore = defineStore('main', () => {
    // init: citim din localStorage folosind cheile exacte din Login.vue
    const token = ref(localStorage.getItem('token') || '');
    const userId = ref(localStorage.getItem('loggedUserId') || null);
    const username = ref(localStorage.getItem('username') || '');

    // isLoggedIn e true daca avem un token valid
    const isLoggedIn = ref(!!token.value);

    // actiune login
    // Login.vue apeleaza: store.login(loginForm.username)
    function login(user) {
        isLoggedIn.value = true;
        username.value = user;

        token.value = localStorage.getItem('token');
        userId.value = localStorage.getItem('loggedUserId');
    }

    //actiune logout
    function logout() {
        isLoggedIn.value = false;
        username.value = '';
        token.value = '';
        userId.value = null;

        // stergem cheile folosite in Login.vue
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('userType');
        localStorage.removeItem('loggedUserId');
    }

    return {
        isLoggedIn,
        username,
        userId,
        token,
        login,
        logout
    };
});