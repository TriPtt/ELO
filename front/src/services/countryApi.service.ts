import axios from 'axios'

const apiClient = axios.create({
  baseURL: 'https://restcountries.com/v3.1',
  headers: {
    'Content-Type': 'application/json',
  },
})

export const countryApiService = {
  getCountryData(name: String) {
    return apiClient.get(`/name/${name}`)
  },
  // postCountryData(payload) {
  //   return axios.post('/api/data', payload)
  // },
}
