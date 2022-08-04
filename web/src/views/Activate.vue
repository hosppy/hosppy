<script setup lang="ts">
import { activate, verifyToken } from '@/api/account'
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { useField, useForm } from 'vee-validate'
import * as yup from 'yup'

const verified = ref(false)
const errMsg = ref('')
const activated = ref(false)

const route = useRoute()

onMounted(() => {
  verifyToken(route.params.token as string)
    .then(() => {
      verified.value = true
    })
    .catch(err => {
      verified.value = false
      errMsg.value = err.response.data.message
    })
})

const { handleSubmit } = useForm()
const { value: password, errors } = useField<string>(
  'password',
  yup.string().required('请输入密码').min(6, '密码最少6位').max(16)
)

const onSubmit = handleSubmit(() => {
  activate(route.params.token as string, password.value)
    .then(() => {
      activated.value = true
    })
    .catch(err => {
      // TODO need message box
      alert(err.response.data.message)
    })
})
</script>

<template>
  <div
    class="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8"
  >
    <div class="max-w-md w-full space-y-8">
      <div>
        <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">
          激活账号
        </h2>
      </div>
      <form v-if="verified" class="mt-8 space-y-6" @submit="onSubmit">
        <div class="rounded-md shadow-sm -space-y-px">
          <div>
            <label for="password" class="sr-only">密码</label>
            <input
              id="password"
              v-model="password"
              name="password"
              type="password"
              autocomplete="current-password"
              maxlength="16"
              required
              class="appearance-none relative block w-full px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="密码"
            />
            <span
              class="flex items-center font-normal tracking-wide text-red-500 text-xs mt-1 ml-1"
              >{{ errors[0] }}</span
            >
          </div>
        </div>

        <div>
          <button
            type="submit"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            激活
          </button>
        </div>
      </form>
      <div v-else>{{ errMsg }}</div>
    </div>
  </div>
</template>
