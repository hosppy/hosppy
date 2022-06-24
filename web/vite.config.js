import { defineConfig } from 'vite'
import { join } from 'path'
import vue from '@vitejs/plugin-vue'
import WindiCSS from 'vite-plugin-windicss'

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': join(__dirname, 'src'),
    },
  },
  plugins: [vue(), WindiCSS()],
  server: {
    port: 4000,
    proxy: {
      '/api': {
        target: 'http://localhost',
        changeOrigin: true,
      },
    },
  },
})
