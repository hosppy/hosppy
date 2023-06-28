import { persisted } from 'svelte-local-storage-store';
// const storedUser = localStorage.getItem('user');
export const user = persisted('user', null);
