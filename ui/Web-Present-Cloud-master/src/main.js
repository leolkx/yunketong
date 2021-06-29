// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// 导入字体图标
import './assets/element-ui-font/iconfont.css'
// 导入全局样式表
import './assets/css/global.css'
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import http from './http'

Vue.prototype.$http = http
Vue.use(ElementUI)

// axios.defaults.baseURL = 'http://172.20.86.137:8081/'
// // axios.defaults.headers.common['Authorization'] = window.localStorage.getItem('token')
// axios.defaults.withCredentials = true
// Vue.prototype.$http = axios
// axios.interceptors.request.use(
//   config => {
//     console.log(config)
//     if (!(config.data && config.data.param)) {
//       if (localStorage.getItem('token')) {
//         config.headers.myAuthorization = localStorage.getItem('token')
//       }
//     }
//     return config
//   },
//   error => {
//     if (error && error.status) {
//       switch (error.response.status) {
//         case 403:
//           router.push('/E_403')
//           break
//         case 404:
//           router.push('/E_404')
//           break
//         case 500:
//           router.push('/E_500')
//           break
//       }
//     } else { return Promise.reject(error) }
//   })
// Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
