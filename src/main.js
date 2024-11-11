import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

// axios.interceptors.request.user(
//   // axios 提供拦截
//   config => {
//     if (localStorage.getItem('token')) {
//       console.log('config')
//       config.headers.Authorization = window.sessionStorage.getItem('token')
//     }
//     return config
//   }
// )

Vue.config.productionTip = false
Vue.use(ElementUI);
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')

