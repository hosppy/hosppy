import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/404',
    name: 'NotFound',
    component: () => import('@/views/404.vue'),
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login.vue'),
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('@/views/Register.vue'),
  },
  {
    path: '/activate/:token',
    name: 'activate',
    component: () => import('@/views/Activate.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
