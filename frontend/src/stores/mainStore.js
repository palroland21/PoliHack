import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useMainStore = defineStore('main', () => {
    // 1. INITIALIZARE: Citim din localStorage folosind cheile EXACTE din Login.vue
    // Login.vue salveaza ID-ul in cheia 'loggedUserId', deci il citim de acolo.
    const token = ref(localStorage.getItem('token') || '');
    const userId = ref(localStorage.getItem('loggedUserId') || null);
    const username = ref(localStorage.getItem('username') || '');

    // isLoggedIn e true daca avem un token valid
    const isLoggedIn = ref(!!token.value);

    // 2. ACTIUNE LOGIN
    // Login.vue apeleaza: store.login(loginForm.username)
    // Deoarece Login.vue a salvat deja datele in localStorage inainte sa apeleze asta,
    // noi doar actualizam state-ul reactiv al Pinia citind din nou valorile.
    function login(user) {
        isLoggedIn.value = true;
        username.value = user;

        // CRUCIAL: Actualizam ref-urile citind ce tocmai a salvat Login.vue
        // Astfel, Dashboard-ul va avea acces imediat la token si userId
        token.value = localStorage.getItem('token');
        userId.value = localStorage.getItem('loggedUserId');
    }

    // 3. ACTIUNE LOGOUT
    function logout() {
        isLoggedIn.value = false;
        username.value = '';
        token.value = '';
        userId.value = null;

        // Stergem cheile folosite in Login.vue
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('userType');
        localStorage.removeItem('loggedUserId');
    }

    return {
        isLoggedIn,
        username,
        userId,   // Dashboard-ul va folosi: store.userId
        token,    // Dashboard-ul va folosi: store.token
        login,
        logout
    };
});