import request from '$lib/utils/request';

/** @type {import('./$types').PageServerLoad} */
export async function load({ params }) {
  const res = await request(`/accounts/activate/${params.slug}`, {
    method: 'GET'
  });
  if (res.ok) {
    return {
      title: 'Activate Account Succeed'
    };
  } else {
    return res.json().then((data) => {
      return {
        title: 'Activate Account Failed'
      };
    });
  }
}
