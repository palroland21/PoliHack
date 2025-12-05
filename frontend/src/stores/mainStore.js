import { defineStore } from 'pinia'

export const useMainStore = defineStore('main', {
  state: () => ({
    userRole: null, // 'victim' or 'rescuer'
    location: null
  }),
  actions: {
    setUserRole(role) {
      this.userRole = role
    },
    setLocation(location) {
      this.location = location
    }
  }
})
