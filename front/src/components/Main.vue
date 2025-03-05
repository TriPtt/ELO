<template>
  <div class="map-container">
    <h2>Sélectionnez un pays</h2>

    <InteractiveMap @country-selected="handleCountrySelection" />

    <div v-if="selectedCountry" class="country-details">
      <h3>Pays sélectionné: {{ selectedCountry.value }}</h3>
      <div v-if="loading" class="loading">Chargement des données...</div>
      <div v-if="error" class="error">
        {{ error }}
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent, ref } from 'vue'
import InteractiveMap from './InteractiveMap.vue'

export default defineComponent({
  name: 'Main',
  components: {
    InteractiveMap,
  },
  setup() {
    const selectedCountry = ref<any>(null)
    const loading = ref<boolean>(false)
    const error = ref<string>('')

    const handleCountrySelection = async (countryProps: any) => {
      selectedCountry.value = countryProps
      console.log(selectedCountry.value)

      // Réinitialiser les états précédents
      error.value = ''

      // Si un pays est sélectionné, faire une requête API
      if (countryProps) {
        loading.value = true
      }
    }

    return {
      selectedCountry,
      loading,
      error,
      handleCountrySelection,
    }
  },
})
</script>

<style scoped>
.map-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

.country-details {
  margin-top: 25px;
  padding: 15px;
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

h3 {
  margin-top: 0;
  color: #1864ab;
}

.country-api-data {
  margin-top: 15px;
  padding: 10px;
  background-color: #e9ecef;
  border-radius: 4px;
}

.loading {
  margin-top: 15px;
  font-style: italic;
  color: #666;
}

.error {
  margin-top: 15px;
  padding: 10px;
  color: #721c24;
  background-color: #f8d7da;
  border-radius: 4px;
}
</style>
