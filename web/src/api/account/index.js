import http from '@/api'

export function createAccount(email) {
  return http.post('/accounts', {
    email,
  })
}
