import axios from 'axios'

export interface CountryData {
  name: string
}

const API_BASE_URL = 'https://your-backend-api.com'

export const CountryService = {
  /**
   * Envoi le nom du pays et recupère l'anecdote générée
   * @param name Nom du pays en anglais
   * @returns Promise contenant l'anecdote générée et la réponse
   */
  async getAnecdote(name: string): Promise<String> {
    // try {
    //   const response = await axios.get(`${API_BASE_URL}/countries/${isoCode}`)
    //   return response.data
    // } catch (error) {
    //   console.error('Erreur lors de la récupération des données du pays:', error)
    //   throw error
    // }
  },
}

export default CountryService
