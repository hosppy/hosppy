/**
 * @param {string} route
 * @param {RequestInit | undefined} options
 */
export default function request(route, options) {
  let endpoint = '/api' + route;
  return fetch(endpoint, {
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include',
    ...options
  });
}
