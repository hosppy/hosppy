<script>
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
          <a href="/login" class="btn"> 已有账号？立即登录 </a>
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
              class="input rounded-t-md"
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
              class="input rounded-b-md"
              placeholder="密码"
            />
          </div>
        </div>
        <span class="flex items-center font-normal tracking-wide text-red-500 text-xs mt-1 ml-1">
          <!-- {{ errMsg }} -->
        </span>

        <div>
          <button type="submit" class="w-full btn"> 注册 </button>
        </div>
        <div>
          <a href="/login" class="no-underline">
            <button class="w-full btn"> 已有账号？立即登录 </button>
          </a>
        </div>
      </form>
    {/if}
  </div>
</div>
