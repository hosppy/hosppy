<script>
  let email = '';
  let password = '';

  async function handleSubmit() {
    let res = await fetch('/api/authentication', {
      method: 'POST',
      body: JSON.stringify({
        username: email,
        password: password
      }),
      headers: {
        'Content-Type': 'application/json'
      },
      credentials: 'include'
    });
    if (res.status === 200) {
      window.location.href = '/';
    }
  }
</script>

<div class="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
  <div class="max-w-md w-full space-y-8">
    <div>
      <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">登录账号</h2>
    </div>
    <form class="mt-8 space-y-6" autocomplete="on">
      <div class="rounded-md shadow-sm -space-y-px">
        <div>
          <label for="email-address" class="sr-only">邮箱地址</label>
          <input
            id="email-address"
            name="email"
            type="email"
            autocomplete="email"
            required
            class="input rounded-t-md"
            placeholder="邮箱"
            bind:value={email}
          />
        </div>
        <div>
          <label for="password" class="sr-only">密码</label>
          <input
            id="password"
            name="password"
            type="password"
            autocomplete="current-password"
            maxlength="16"
            required
            class="input rounded-b-md"
            placeholder="密码"
            bind:value={password}
          />
        </div>
      </div>

      <div class="flex items-center justify-between">
        <div class="flex items-center">
          <input id="remember-me" name="remember-me" type="checkbox" class="input-checkbox" />
          <label for="remember-me" class="ml-2 block text-sm text-gray-900"> 记住我 </label>
        </div>

        <div class="text-sm">
          <a href="/forgot-password" class="font-medium text-indigo-600 hover:text-indigo-500">
            忘记密码?
          </a>
        </div>
      </div>

      <div>
        <button type="submit" class="w-full btn" on:click|preventDefault={handleSubmit}>
          登录
        </button>
      </div>
      <div>
        <a href="/register" class="no-underline">
          <button class="w-full btn"> 注册 </button>
        </a>
      </div>
    </form>
  </div>
</div>
