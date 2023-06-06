import { sveltekit } from '@sveltejs/kit/vite';
import UnoCss from 'unocss/vite';
import {
  presetIcons,
  presetTypography,
  presetUno,
  transformerCompileClass,
  transformerDirectives,
  transformerVariantGroup
} from 'unocss';

/** @type {import('vite').UserConfig} */
const config = {
  plugins: [
    UnoCss({
      presets: [presetUno(), presetTypography(), presetIcons({ scale: 2.0 })],
      transformers: [transformerDirectives(), transformerVariantGroup(), transformerCompileClass()]
    }),
    sveltekit()
  ],
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
};

export default config;
