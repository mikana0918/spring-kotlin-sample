import { resolve } from 'path'
const envPath = `.env.${process.env.ENV || 'local'}`
require('dotenv').config({ path: envPath })

export default {
  // Disable server-side rendering: https://go.nuxtjs.dev/ssr-mode
  ssr: false,

  // Target: https://go.nuxtjs.dev/config-target
  target: 'static',

  alias: {
    '@': resolve(__dirname, './'),
  },

  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: 'frontend',
    htmlAttrs: {
      lang: 'en',
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '' },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    // https://go.nuxtjs.dev/typescript
    '@nuxt/typescript-build',
    // https://go.nuxtjs.dev/tailwindcss
    '@nuxtjs/tailwindcss',
    '@nuxtjs/proxy',
    [
      '@nuxtjs/vuetify',
      {
        /* module options */
      },
    ],
    [
      '@nuxtjs/dotenv',
      {
        filename: envPath,
      },
    ],
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
  ],

  // Axios module configuration: https://go.nuxtjs.dev/config-axios
  axios: {
    baseURL: process.env.API_ENDPOINT,
    credentials: true,
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {},

  // @nuxtjs/proxyの設定
  proxy: {
    '/api': {
      target: process.env.API_ENDPOINT, // [TODO] デプロイする場合は環境変数で管理するなり
      changeOrigin: true,
      secure: false,
    },
  },
}
