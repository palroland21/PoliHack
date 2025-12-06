<script setup>
import { ref, defineEmits } from 'vue'
import LeafletMap from '@/components/common/LeafletMap.vue'

const emit = defineEmits(['update:location', 'update:coordinates', 'next'])

const mapRef = ref(null)
const confirmedLocation = ref(null)
const confirmedCoordinates = ref(null)
const locationError = ref(null)
const gpsLoading = ref(false)

function onLocationSelected(locationData) {
  confirmedCoordinates.value = {
    lat: locationData.lat,
    lon: locationData.lon
  }
  confirmedLocation.value = locationData.address
  locationError.value = null

  emit('update:location', confirmedLocation.value)
  emit('update:coordinates', confirmedCoordinates.value)
}

async function getGPS() {
  gpsLoading.value = true
  locationError.value = null

  if (!navigator.geolocation) {
    locationError.value = 'Geolocation is not supported by your browser'
    gpsLoading.value = false
    return
  }

  navigator.geolocation.getCurrentPosition(
    async (position) => {
      const lat = position.coords.latitude
      const lon = position.coords.longitude
      const accuracy = position.coords.accuracy

      console.log('GPS Position:', { lat, lon, accuracy: accuracy + ' meters' })

      confirmedCoordinates.value = { lat, lon }

      // Set marker on map with small delay to ensure map is ready
      setTimeout(() => {
        if (mapRef.value) {
          mapRef.value.setLocationFromGPS(lat, lon)
        }
      }, 100)

      try {
        const response = await fetch(
          `https://nominatim.openstreetmap.org/reverse?format=json&lat=${lat}&lon=${lon}&zoom=18&addressdetails=1&accept-language=en`
        )
        const data = await response.json()

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
        confirmedLocation.value = address
      } catch (error) {
        confirmedLocation.value = `GPS: ${lat}, ${lon}`
      }

      emit('update:location', confirmedLocation.value)
      emit('update:coordinates', confirmedCoordinates.value)
      gpsLoading.value = false
    },
    (error) => {
      let message = 'Could not access your location'
      if (error.code === error.PERMISSION_DENIED) {
        message = 'Location permission denied. Please enable location access.'
      } else if (error.code === error.POSITION_UNAVAILABLE) {
        message = 'Location information is unavailable.'
      } else if (error.code === error.TIMEOUT) {
        message = 'Location request timeout.'
      }
      locationError.value = message
      gpsLoading.value = false
    },
    { enableHighAccuracy: true, timeout: 10000, maximumAge: 0 }
  )
}

function handleContinue() {
  emit('next')
}
</script>

<template>
  <div class="step-container">
    <div class="step-header">
      <div class="step-icon">📍</div>
      <h1 class="step-title">Step 2: Your Location</h1>
      <p class="step-description">Use GPS or click on the map to set your location</p>
    </div>

    <div class="location-options">
      <button class="btn btn-gps" @click="getGPS" :disabled="gpsLoading">
        <span class="gps-icon">📍</span>
        <span>{{ gpsLoading ? 'Getting location...' : 'Use My GPS Location' }}</span>
      </button>

      <div class="divider-container">
        <span class="divider">OR</span>
      </div>

      <LeafletMap
        ref="mapRef"
        :marker-lat="confirmedCoordinates?.lat"
        :marker-lon="confirmedCoordinates?.lon"
        @location-selected="onLocationSelected"
      />

      <div v-if="locationError" class="error-message">
        ⚠️ {{ locationError }}
      </div>

      <div v-if="confirmedLocation" class="location-display">
        <p class="location-confirmed">✓ Location confirmed</p>
        <p class="location-text">{{ confirmedLocation }}</p>
        <div v-if="confirmedCoordinates" class="coordinates-info">
          <p class="coords-label">Coordinates:</p>
          <p class="coords-value">Lat: {{ confirmedCoordinates.lat }} | Lon: {{ confirmedCoordinates.lon }}</p>
        </div>
      </div>

      <button class="btn btn-continue" @click="handleContinue" :disabled="!confirmedLocation">
        Continue <span class="arrow-right">→</span>
      </button>
    </div>
  </div>
</template>

<style scoped>
.step-container {
  max-width: 700px;
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.step-header {
  margin-bottom: 40px;
}

.step-icon {
  font-size: 60px;
  margin-bottom: 20px;
  opacity: 0.9;
  display: inline-block;
}

.step-title {
  font-size: 32px;
  font-weight: 700;
  color: #1a1a1a;
  margin-bottom: 12px;
}

.step-description {
  font-size: 16px;
  color: #666;
  line-height: 1.6;
}

.location-options {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-bottom: 40px;
}

.divider-container {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 16px;
  margin: 4px 0;
}

.divider-container::before,
.divider-container::after {
  content: '';
  flex: 1;
  height: 1px;
  background-color: #e0e0e0;
}

.divider {
  color: #999;
  font-size: 13px;
  font-weight: 500;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.btn {
  padding: 16px 24px;
  border: none;
  border-radius: 12px;
  font-weight: 600;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-family: inherit;
}

.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn-gps {
  background-color: transparent;
  border: 2px solid #d74141;
  color: #d74141;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 16px 24px;
  font-size: 16px;
}

.btn-gps:hover:not(:disabled) {
  background-color: rgba(215, 65, 65, 0.05);
  transform: translateY(-2px);
  box-shadow: 0 6px 12px rgba(215, 65, 65, 0.15);
}

.gps-icon {
  font-size: 20px;
}

.error-message {
  background-color: #ffe6e6;
  border: 1.5px solid #d74141;
  border-radius: 10px;
  padding: 12px 16px;
  color: #d74141;
  font-size: 14px;
  font-weight: 500;
  text-align: center;
}

.location-display {
  background: linear-gradient(135deg, rgba(215, 65, 65, 0.08) 0%, rgba(215, 65, 65, 0.03) 100%);
  border: 1.5px solid #d74141;
  border-radius: 10px;
  padding: 14px 16px;
  text-align: center;
}

.location-confirmed {
  font-size: 14px;
  font-weight: 600;
  color: #d74141;
  margin: 0 0 6px 0;
}

.location-text {
  font-size: 14px;
  color: #333;
  margin: 0;
  word-break: break-word;
}

.coordinates-info {
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px solid rgba(215, 65, 65, 0.2);
}

.coords-label {
  font-size: 11px;
  font-weight: 600;
  color: #d74141;
  margin: 0 0 4px 0;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.coords-value {
  font-size: 12px;
  color: #666;
  margin: 0;
  font-family: 'Monaco', 'Courier New', monospace;
}

.btn-continue {
  width: 100%;
  background: linear-gradient(135deg, #d74141 0%, #c56a6a 100%);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 16px 24px;
  font-size: 16px;
}

.btn-continue:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 10px 20px rgba(215, 65, 65, 0.25);
}

.arrow-right {
  font-size: 18px;
  transition: transform 0.3s ease;
}

.btn-continue:hover:not(:disabled) .arrow-right {
  transform: translateX(4px);
}

@media (max-width: 768px) {
  .step-container {
    max-width: 100%;
  }

  .step-title {
    font-size: 26px;
  }

  .step-description {
    font-size: 14px;
  }
}
</style>