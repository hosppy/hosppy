import request from '$lib/utils/request';
import { error, redirect } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export function load({ params }) {
  request('/accounts/activate/' + params.slug, { method: 'GET' })
    .then((res) => {
      if (res.ok) {
        throw redirect(307, '/login');
      }
      throw error(404, 'Not Found');
    })
    .catch((err) => {
      console.log(err);
    });
  return {
    title: 'Hello world!',
    content: 'Welcome to our blog. Lorem ipsum dolor sit amet...',
    slug: params.slug
  };
}
