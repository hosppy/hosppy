/**
 * @param {string} route
 * @param {RequestInit | undefined} options
 */
export default function request(route, options) {
  return fetch(`${import.meta.env.VITE_API_BASE_URL}/api` + route, {
    headers: {
      'Content-Type': 'application/json'
    },
    ...options
  });
}
