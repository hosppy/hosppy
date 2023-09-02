<script>
  import { user } from '$lib/utils/store';
  import { onMount } from 'svelte';

  /**
   * @type {any}
   */

  onMount(async () => {
    let res = await fetch('/api/authenticate');
    if (res.status === 200) {
      user.set(await res.json());
    }
    if (res.status === 403) {
      user.set(null);
    }
  });
</script>

<div class="flex items-center">
  {#if $user}
    <div>
      <button
        class="flex border-none b-white m-0 p-0 rounded-full text-sm case-normal"
        type="button"
      >
        <img
          class="w-8 h-8 rounded-full opacity-100 v-mid"
          src="https://api.iconify.design/subway:admin.svg"
          alt="avatar"
        />
      </button>
    </div>
  {:else}
    <a class="nav-link" href="/login">登录</a>
    <a class="inline-flex items-center" href="/register">
      <button class="btn">注册</button>
    </a>
  {/if}
</div>
