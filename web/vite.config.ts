import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import WindiCSS from 'vite-plugin-windicss'
import pluginRewriteAll from 'vite-plugin-rewrite-all'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src'), 
    },
  },
  plugins: [vue(), WindiCSS(), pluginRewriteAll()],
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
