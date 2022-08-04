<script setup lang="ts">
import { ref } from 'vue'
import { create } from '@/api/account'

const email = ref('')
const errMsg = ref('')
const registered = ref(false)
const disabled = ref(false)

const handleSubmit = () => {
  errMsg.value = ''
  disabled.value = true
  create(email.value)
    .then(() => {
      registered.value = true
      disabled.value = false
    })
    .catch(error => {
      errMsg.value = error.response.data.message
      disabled.value = false
    })
}
</script>
<template>
  <div
    class="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8"
  >
    <div class="max-w-md w-full space-y-8">
      <div>
        <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
          注册账号
        </h2>
      </div>
      <div v-if="registered" class="mt-8 space-y-6">
        <p class="text-gray-500 text-base leading-relaxed">
          注册成功！激活链接已发送到邮箱
          <span class="text-indigo-500"> {{ email }} </span>，请注意查收！
        </p>
        <div>
          <router-link
            to="/login"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            已有账号？立即登录
          </router-link>
        </div>
      </div>
      <form v-else class="mt-8 space-y-6" @submit.prevent="handleSubmit()">
        <div class="rounded-md shadow-sm -space-y-px">
          <div>
            <label for="email-address" class="sr-only">邮箱地址</label>
            <input
              v-model="email"
              type="email"
              autocomplete="email"
              required
              class="appearance-none rounded-md relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="邮箱"
            />
          </div>
        </div>
        <span
          v-show="errMsg != ''"
          class="flex items-center font-normal tracking-wide text-red-500 text-xs mt-1 ml-1"
        >
          {{ errMsg }}
        </span>

        <div>
          <button
            type="submit"
            :disabled="disabled"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            注册
          </button>
        </div>
        <div>
          <router-link
            to="/login"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            已有账号？立即登录
          </router-link>
        </div>
      </form>
    </div>
  </div>
</template>
