<template>
  <div class="map-wrapper">
    <div id="map" ref="mapContainer"></div>
    <p class="map-hint">Click on the map to set your location</p>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch, defineProps, defineEmits } from 'vue'
import L from 'leaflet'
import 'leaflet/dist/leaflet.css'

const props = defineProps({
  initialLat: {
    type: Number,
    default: 46.77
  },
  initialLon: {
    type: Number,
    default: 23.59
  },
  initialZoom: {
    type: Number,
    default: 15
  },
  markerLat: {
    type: Number,
    default: null
  },
  markerLon: {
    type: Number,
    default: null
  }
})

const emit = defineEmits(['location-selected'])

const mapContainer = ref(null)
let map = null
let marker = null

// Fix Leaflet default icon issue
delete L.Icon.Default.prototype._getIconUrl
L.Icon.Default.mergeOptions({
  iconRetinaUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-icon-2x.png',
  iconUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-icon.png',
  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-shadow.png'
})

function setMarker(lat, lon) {
  if (marker) {
    marker.setLatLng([lat, lon])
  } else {
    marker = L.marker([lat, lon]).addTo(map)
  }
  map.setView([lat, lon], map.getZoom())
}

function onMapClick(e) {
  const lat = e.latlng.lat
  const lon = e.latlng.lng

  setMarker(lat, lon)

  // Reverse geocode to get address with zoom level for more precision
  fetch(`https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lon}&zoom=18&addressdetails=1&accept-language=en`)
    .then(res => res.json())
    .then(data => {
      let address = ''
      if (data.address) {
        const a = data.address
        const parts = []
        if (a.house_number) parts.push(a.house_number)
        if (a.road) parts.push(a.road)
        if (a.neighbourhood) parts.push(a.neighbourhood)
        if (a.suburb) parts.push(a.suburb)
        if (a.city || a.town || a.village) parts.push(a.city || a.town || a.village)
        if (a.county) parts.push(a.county)
        address = parts.join(', ') || data.display_name
      } else {
        address = data.display_name || `${lat}, ${lon}`
      }
      emit('location-selected', {
        lat: lat,
        lon: lon,
        address
      })
    })
    .catch(() => {
      emit('location-selected', {
        lat: lat,
        lon: lon,
        address: `${lat}, ${lon}`
      })
    })
}

// Expose method to set marker from parent
function setLocationFromGPS(lat, lon) {
  if (map) {
    // Invalidate size first in case map container size changed
    map.invalidateSize()

    // Remove existing marker if any
    if (marker) {
      map.removeLayer(marker)
      marker = null
    }

    // Create new marker at exact position
    marker = L.marker([lat, lon]).addTo(map)

    // Center map on marker with higher zoom for precision
    map.setView([lat, lon], 18)
  }
}

defineExpose({ setLocationFromGPS })

onMounted(() => {
  map = L.map(mapContainer.value).setView([props.initialLat, props.initialLon], props.initialZoom)

  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
  }).addTo(map)

  map.on('click', onMapClick)

  // If initial marker position provided
  if (props.markerLat && props.markerLon) {
    setMarker(props.markerLat, props.markerLon)
  }
})

onUnmounted(() => {
  if (map) {
    map.remove()
  }
})

// Watch for external marker updates
watch(() => [props.markerLat, props.markerLon], ([lat, lon]) => {
  if (lat && lon && map) {
    setMarker(lat, lon)
  }
})
</script>

<style scoped>
.map-wrapper {
  width: 100%;
  border-radius: 12px;
  overflow: hidden;
  border: 2px solid #ddd;
}

#map {
  height: 300px;
  width: 100%;
  z-index: 1;
}

.map-hint {
  background-color: #f5f5f5;
  margin: 0;
  padding: 10px;
  font-size: 13px;
  color: #666;
  text-align: center;
}
</style>
