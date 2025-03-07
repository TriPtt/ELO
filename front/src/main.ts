import './assets/main.css'
import 'leaflet/dist/leaflet.css'
import './index.css'
import { countryApiService } from './services/countryApi.service'

import { createApp } from 'vue'
import App from './App.vue'

const app = createApp(App)
app.provide('countryApiService', countryApiService)
app.mount('#app')
