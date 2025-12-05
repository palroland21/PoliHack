<template>
  <div class="home">
    <h1>Sunt...</h1>
    <button @click="selectRole('victim')">Victimă</button>
    <button @click="selectRole('rescuer')">Salvator</button>
  </div>
</template>

<script>
import { useMainStore } from '@/stores/mainStore'
import { useRouter } from 'vue-router'

export default {
  name: 'HomeView',
  setup() {
    const store = useMainStore()
    const router = useRouter()

    function selectRole(role) {
      store.setUserRole(role)
      if (role === 'victim') {
        router.push('/victim')
      } else {
        router.push('/rescuer')
      }
    }

    return {
      selectRole
    }
  }
}
</script>
