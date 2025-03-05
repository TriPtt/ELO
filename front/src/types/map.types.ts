export interface CountryProperties {
  name: string
  iso_a2: string
  iso_a3: string
  continent?: string
  region?: string
  [key: string]: any
}

export interface CountryFeature {
  type: 'Feature'
  properties: CountryProperties
  geometry: {
    type: string
    coordinates: number[][][] | number[][][][]
  }
}

export interface CountriesGeoJSON {
  type: 'FeatureCollection'
  features: CountryFeature[]
}
