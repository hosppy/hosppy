/**
 * @param {string} route
 * @param {RequestInit | undefined} options
 */
export default function request(route, options) {
  return fetch(`/api` + route, {
    headers: {
      'Content-Type': 'application/json'
    },
    ...options
  });
}
