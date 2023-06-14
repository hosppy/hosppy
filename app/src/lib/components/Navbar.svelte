<script>
  import Dropdown from './Dropdown.svelte';
  import Login from './Login.svelte';
</script>

<div class="min-h-full px-8 bg-white dark:bg-gray-800 shadow">
  <div class="flex justify-between">
    <div class="flex h-12 ws-nowrap">
      <a class="nav-link text-base" href="/">毫升派</a>
      <div class="flex">
        <a class="nav-link" href="/">首页</a>
        <a class="nav-link" href="#">题库</a>
        <a class="nav-link" href="#">专栏</a>
        <a class="nav-link" href="#">求职</a>
        <Dropdown />
      </div>
    </div>
    <Login />
  </div>
</div>
