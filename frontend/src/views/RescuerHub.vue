<template>
  <div>
    <h2>Panou Salvator</h2>
    <!-- Rescuer modules will be displayed here -->
    <TransportModule />
    <HousingModule />
    <ResourcesModule />
  </div>
</template>

<script>
import TransportModule from '@/components/rescuer/TransportModule.vue'
import HousingModule from '@/components/rescuer/HousingModule.vue'
import ResourcesModule from '@/components/rescuer/ResourcesModule.vue'

export default {
  name: 'RescuerHub',
  components: {
    TransportModule,
    HousingModule,
    ResourcesModule
  }
}
</script>
