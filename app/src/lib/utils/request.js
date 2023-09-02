/**
 * @param {string} route
 * @param {RequestInit | undefined} options
 */
export default function request(route, options) {
  let endpoint = process.env.BASE_URL + route;
  return fetch(endpoint, {
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include',
    ...options
  });
}
