<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import Index from './index.vue'
export default {
  name: 'App',
  components: {
    Index
  },
  data() {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }


}


</script>
