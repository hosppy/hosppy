<script>
  import Toast from '$lib/components/Toast.svelte';
  import request from '$lib/utils/request';

  let registered = false;
  let email = '';
  let password = '';

  function handleSubmit() {
    request('/accounts', {
      method: 'POST',
      body: JSON.stringify({ email })
    }).then((res) => {
      if (res.ok) {
        registered = true;
      } else {
        res.json().then((ret) => {
          alert(ret.message);
        });
      }
    });
  }
</script>

<div class="min-h-full flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
  <div class="max-w-md w-full space-y-8">
    <div>
      <h2 class="mt-6 text-center text-3xl font-extrabold text-gray-900">注册账号</h2>
    </div>
    {#if registered}
      <div class="mt-8 space-y-6">
        <p class="text-gray-500 text-base leading-relaxed">
          注册成功！激活链接已发送到邮箱
          <span class="text-indigo-500" />，请注意查收！
        </p>
        <div>
          <a
            href="/login"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            已有账号？立即登录
          </a>
        </div>
      </div>
    {:else}
      <form on:submit|preventDefault={handleSubmit} class="mt-8 space-y-6">
        <div class="rounded-md shadow-sm -space-y-px">
          <div>
            <label for="email-address" class="sr-only">邮箱地址</label>
            <input
              bind:value={email}
              type="email"
              autocomplete="email"
              required
              class="appearance-none rounded-t-md block w-full box-border px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="邮箱"
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
              class="appearance-none relative block w-full box-border px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 rounded-b-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm"
              placeholder="密码"
            />
          </div>
        </div>
        <span class="flex items-center font-normal tracking-wide text-red-500 text-xs mt-1 ml-1">
          <!-- {{ errMsg }} -->
        </span>

        <div>
          <button
            type="submit"
            class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
          >
            注册
          </button>
        </div>
        <div>
          <a href="/login" class="active:no-underline">
            <button
              class="group relative w-full flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
            >
              已有账号？立即登录
            </button>
          </a>
        </div>
      </form>
    {/if}
  </div>
</div>
