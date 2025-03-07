<template>
  <div class="main-container">
    <div class="title-container">
      <h1>Commencez par sélectionner un pays</h1>
    </div>

    <InteractiveMap @country-selected="handleCountrySelection" />

    <div v-if="selectedCountry" class="text-box-container p-10 left-80">
      <div class="pb-7">
        <div class="flex justify-between">
          <h3 class="text-blue-700 font-sans text-5xl font-black">{{ selectedCountry }}</h3>
          <span class="text-5xl" v-if="countryApiData && countryApiData[0]">{{
            countryApiData[0].flag
          }}</span>
        </div>
        <h4 class="italic text-gray-800" v-if="countryApiData && countryApiData[0]">
          {{ Object.values(countryApiData[0].name.nativeName)[0].common }}
        </h4>
      </div>

      <transition-group name="fade" tag="div" class="text-container">
        <p
          v-for="(word, index) in textWords"
          :key="word + index"
          class="word text-gray-900"
          :style="{ transitionDelay: `${index * 150}ms` }"
        >
          {{ word }}
        </p>
      </transition-group>

      <div class="pt-8 flex justify-center">
        <button
          @click="validation"
          class="rounded-md font-extrabold bg-green-600 py-2 px-7 border border-transparent text-center text-xl text-white transition-all shadow-md hover:shadow-lg focus:bg-green-700 focus:shadow-none active:bg-green-700 hover:bg-green-700 active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none ml-2"
          type="button"
        >
          VRAI
        </button>
        <ConfettiExplosion v-if="visible" :particleCount="110" :force="0.8" :duration="'2000'" />

        <button
          class="rounded-md font-extrabold bg-red-600 py-2 px-7 border border-transparent text-center text-xl text-white transition-all shadow-md hover:shadow-lg focus:bg-red-700 focus:shadow-none active:bg-red-700 hover:bg-red-700 active:shadow-none disabled:pointer-events-none disabled:opacity-50 disabled:shadow-none ml-2"
          type="button"
        >
          FAUX
        </button>
      </div>
      <!-- <div v-if="loading" class="loading">Chargement des données...</div> -->
      <div v-if="aiApiError" class="error">
        {{ aiApiError }}
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { computed, defineComponent, inject, nextTick, onMounted, ref } from 'vue'
import InteractiveMap from './InteractiveMap.vue'
import ConfettiExplosion from 'vue-confetti-explosion'
import tada from '../assets/Tada.mp3'
import type CountryData from '../types/countryData.types'

export default defineComponent({
  name: 'Main',
  components: {
    InteractiveMap,
    ConfettiExplosion,
  },
  setup() {
    const selectedCountry = ref<any>(null)
    const aiApiLoading = ref<boolean>(false)
    const aiApiError = ref<string>('')
    const visible = ref(false)
    const countryApiService = inject('countryApiService')
    const countryApiLoading = ref<boolean>(false)
    const countryApiError = ref<string>('')
    const countryApiData = ref<any>(null)
    const audio = new Audio(tada)
    audio.preload = 'auto'
    const showText = ref(false)
    const text = 'Vue 3 permet de créer des animations fluides et élégantes.'
    const textWords = computed(() => text.split(' '))

    // const typewriterText =
    //   'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut odio viverra, ornare augue vitae, varius est. Suspendisse semper purus eu euismod vulputate. Quisque pulvinar urna non fringilla mollis. Ut aliquam dui lacus, ut volutpat ipsum laoreet eu. Quisque aliquet lorem at enim placerat porta. Ut pharetra aliquam elit, congue faucibus erat tempor ut. Aliquam vehicula sem lacus, et dignissim nunc congue eu.'

    onMounted(() => {})

    const explode = async () => {
      visible.value = false
      await nextTick()
      visible.value = true
    }

    const validation = async () => {
      // TODO condition
      if (true) {
        explode()
        audio.currentTime = 0
        audio.play()
      } else {
      }
    }

    const handleCountrySelection = async (countryProps: any) => {
      selectedCountry.value = countryProps

      // Réinitialiser les états précédents
      aiApiError.value = ''

      // Si un pays est sélectionné, faire une requête API
      if (countryProps) {
        aiApiLoading.value = true
        fetchCountryData(countryProps)
      }
    }

    const fetchCountryData = async (countryName: String) => {
      countryApiLoading.value = true

      try {
        const res = await countryApiService.getCountryData(countryName)
        countryApiData.value = res.data
        console.log(countryApiData.value)
        return countryApiData.value
      } catch (err: any) {
        countryApiError.value = err.message
        console.error(err)
      } finally {
        countryApiLoading.value = false
      }
    }

    return {
      selectedCountry,
      aiApiLoading,
      aiApiError,
      handleCountrySelection,
      visible,
      explode,
      validation,
      countryApiService,
      countryApiLoading,
      countryApiError,
      countryApiData,
      showText,
      text,
      textWords,
    }
  },
})
</script>

<style scoped>
.main-container {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-flow: column;
  background-color: #aad2de;
  height: 100vh;
  overflow-y: hidden;
  max-width: 1920px;
}

.text-container {
  margin: 20px 0;
  min-height: 40px;
  font-size: 1.2em;
  display: flex;
  flex-wrap: wrap;
}

.word {
  margin-right: 8px;
  margin-bottom: 8px;
}

.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

.text-box-container {
  position: absolute;
  margin-bottom: 100px;
  z-index: 999;
  width: 25%;
  max-width: 500px;

  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}

.loading {
}

.error {
}
</style>
