<template>
  <div class="map-container">
    <div id="map" ref="mapRef"></div>
  </div>
</template>

<script lang="ts">
import { defineComponent, onMounted, ref, onUnmounted } from 'vue'
import L from 'leaflet'

export default defineComponent({
  name: 'ClickableMap',
  emits: ['country-selected'],
  setup(props, { emit }) {
    const mapRef = ref<HTMLElement | null>(null)
    let map: L.Map | null = null
    let geojsonLayer: L.GeoJSON | null = null
    let selectedCountry: L.Layer | null = null

    const _randomColor = () => {
      return '#' + (0x1000000 + Math.random() * 0xffffff).toString(16).substr(1, 6)
    }

    onMounted(async () => {
      if (mapRef.value) {
        map = L.map(mapRef.value).setView([35, 10], 3)

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
          attribution:
            '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
        }).addTo(map)

        try {
          const response = await fetch(
            'https://raw.githubusercontent.com/datasets/geo-countries/master/data/countries.geojson',
          )
          const data = await response.json()

          const defaultStyle = {
            fillColor: '#f8f9fa',
            weight: 1,
            opacity: 0.5,
            color: '#ced4da',
            fillOpacity: 0.2,
          }

          geojsonLayer = L.geoJSON(data, {
            style: defaultStyle,
            onEachFeature: (feature, layer) => {
              layer.on({
                mouseover: (e) => {
                  const layer = e.target

                  if (selectedCountry !== layer) {
                    const randomFillColor = _randomColor()

                    layer.feature.properties._randomColor = randomFillColor
                    const hoverStyle = {
                      fillColor: randomFillColor,
                      weight: 1,
                      color: randomFillColor,
                      fillOpacity: 0.3,
                    }

                    layer.setStyle(hoverStyle)
                  }

                  if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
                    layer.bringToFront()
                  }
                },

                mouseout: (e) => {
                  const layer = e.target
                  if (selectedCountry !== layer && geojsonLayer) {
                    geojsonLayer.resetStyle(layer)
                  }
                },

                click: (e) => {
                  const layer = e.target
                  const properties = feature.properties

                  if (selectedCountry === layer) {
                    if (geojsonLayer) {
                      geojsonLayer.resetStyle(layer)
                    }
                    selectedCountry = null
                    emit('country-selected', null)
                    return
                  }

                  if (selectedCountry && geojsonLayer) {
                    geojsonLayer.resetStyle(selectedCountry)
                  }

                  const countryColor = properties._randomColor || _randomColor()

                  const selectedStyle = {
                    fillColor: countryColor,
                    weight: 3,
                    color: countryColor,
                    fillOpacity: 0.5,
                    opacity: 0.5,
                  }

                  layer.setStyle(selectedStyle)
                  selectedCountry = layer

                  if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
                    layer.bringToFront()
                  }

                  if (map && layer.getBounds) {
                    map.fitBounds(layer.getBounds())
                  }

                  emit('country-selected', properties.ADMIN)
                },
              })
            },
          }).addTo(map)
        } catch (error) {
          console.error('Erreur lors du chargement des données GeoJSON:', error)
        }
      }
    })

    onUnmounted(() => {
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
