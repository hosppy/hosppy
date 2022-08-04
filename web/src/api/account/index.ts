import http from '@/api'

export function create(email: string) {
  return http.post('/accounts', {
    email,
  })
}

export function verifyToken(token: string) {
  return http.get('/accounts/activate/' + token)
}

export function activate(token: string, password: string) {
  return http.post('/accounts/activate/' + token, { password })
}
