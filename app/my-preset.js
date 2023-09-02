/** @type {import('unocss').Preset} */
export default function myPreset() {
  return {
    name: 'my-preset',
    shortcuts: {
      btn: 'flex justify-center py-2 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 cursor-pointer',
      input:
        'appearance-none relative block w-full box-border px-3 py-2 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 focus:z-10 sm:text-sm',
      'input-checkbox': 'h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded',
      'nav-item': 'items-center',
      'nav-link':
        'px-4 inline-flex items-center rounded-0 border-solid border-0 border-transparent border-b-2 hover:border-gray-300 text-dark dark:text-white text-sm font-medium dark:hover:bg-gray-700 decoration-none',
      'dropdown-menu':
        'right-0 w-56 mt-2 origin-top-right bg-white rounded-md shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none m-0 p-0',
      'dropdown-item': 'block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 decoration-none',
      'dropdown-divider': 'border-gray-100 m-y-0 h-0 border-1 border-solid op-100 overflow-hidden'
    }
  };
}
