<script>
  import { onMount } from 'svelte';

  export let label = 'Dropdown';
  /**
   * @type {string[]}
   */
  export let items = [];

  let isOpen = false;

  function toggleDropdown() {
    isOpen = !isOpen;
  }

  function handleClickOutside(event) {
    if (!event.target.closest('.dropdown-menu')) {
      isOpen = false;
    }
  }

  onMount(() => {
    document.addEventListener('click', handleClickOutside);

    return () => {
      document.removeEventListener('click', handleClickOutside);
    };
  });
</script>

<div class="relative nav-link">
  <div class="inline-block">
    <div
      class="inline-flex items-center w-full"
      on:click|stopPropagation={toggleDropdown}
      on:keyup={toggleDropdown}
    >
      {label}
      <span class="i-uim-angle-down" />
    </div>
    {#if isOpen}
      <div class="absolute z-20 dropdown-menu">
        {#each items as item}
          <a href="#" class="dropdown-item">{item}</a>
        {/each}
      </div>
    {/if}
  </div>
</div>
