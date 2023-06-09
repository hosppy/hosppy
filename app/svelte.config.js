import adapter from '@sveltejs/adapter-node';
import { windi } from 'svelte-windicss-preprocess';

/** @type {import('@sveltejs/kit').Config} */
const config = {
  preprocess: [windi({})],
  kit: {
    adapter: adapter()
  }
};

export default config;
