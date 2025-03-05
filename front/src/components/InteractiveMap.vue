<template>
  <div class="map-container">
    <div id="map" ref="mapRef"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, onUnmounted } from 'vue'
import Leaflet from 'leaflet'

export default defineComponent({
  name: 'InteractiveMap',
  setup() {
    const mapRef = ref<HTMLElement | null>(null)
    let map: Leaflet.Map | null = null

    onMounted(() => {
      if (mapRef.value) {
        // Initialiser la carte
        map = Leaflet.map(mapRef.value).setView([35, 10], 3)

        // Ajouter le fond de carte
        Leaflet.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution:
            '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
        }).addTo(map)
      }
    })

    onUnmounted(() => {
      // Nettoyer la carte lors de la destruction du composant
      if (map) {
        map.remove()
        map = null
      }
    })

    return {
      mapRef,
    }
  },
})
</script>

<style scoped>
.map-container {
  width: 100%;
  height: 100%;
}

#map {
  width: 100%;
  height: 100vh;
}
</style>
