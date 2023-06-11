<script>
  import Navbar from '$lib/components/Navbar.svelte';
  import 'virtual:uno.css';
</script>

<Navbar />
<slot />

<style>
  :global(body) {
    margin: 0;
    background-color: #e5e7eb;
  }
</style>
