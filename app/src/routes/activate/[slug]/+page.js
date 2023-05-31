import request from '$lib/utils/request';
import { redirect } from '@sveltejs/kit';

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
  const res = await request('/accounts/activate/' + params.slug, { method: 'GET' });
  if (res.ok) {
    throw redirect(307, '/login');
  } else {
    throw redirect(307, '/login?failed');
  }
}
