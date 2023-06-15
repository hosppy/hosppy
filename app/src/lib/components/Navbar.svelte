<script>
  import Login from './Login.svelte';
</script>

<div class="min-h-full px-8 bg-white dark:bg-gray-800 shadow">
  <div class="relative flex justify-between">
    <div class="absolute inset-x-0 bottom-0 h-px bg-slate-900/5" />
    <div class="flex h-12 ws-nowrap">
      <a class="nav-link text-base" href="/">毫升派</a>
      <div class="flex">
        <a class="nav-link" href="/">首页</a>
        <a class="nav-link" href="#">题库</a>
        <a class="nav-link" href="#">专栏</a>
        <a class="nav-link" href="#">求职</a>
      </div>
    </div>
    <Login />
  </div>
</div>

<style>
</style>
