import { sveltekit } from '@sveltejs/kit/vite';
import dotenv from 'dotenv';
import dotenvExpand from 'dotenv-expand';
import {
  presetIcons,
  presetTypography,
  presetUno,
  transformerCompileClass,
  transformerDirectives,
  transformerVariantGroup
} from 'unocss';
import UnoCss from 'unocss/vite';
import { defineConfig } from 'vite';
import myPreset from './my-preset';

/** @type {import('vite').UserConfig} */
const config = {
  plugins: [
    UnoCss({
      presets: [presetUno(), myPreset(), presetTypography(), presetIcons({ scale: 2.0 })],
      transformers: [transformerDirectives(), transformerVariantGroup(), transformerCompileClass()]
    }),
    sveltekit()
  ],
  server: {
    proxy: {
      '/api': {
        target: 'http://127.0.0.1:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
};

export default ({ mode }) => {
  let myEnv = dotenv.config();
  dotenvExpand.expand(myEnv);
  return defineConfig(config);
};
