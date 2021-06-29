import axios from 'axios'
import Vue from 'vue'
import router from './router'

axios.defaults.baseURL = 'http://47.106.126.149:9001/'
// axios.defaults.headers.common['Authorization'] = window.localStorage.getItem('token')
axios.defaults.withCredentials = true
Vue.prototype.$http = axios
axios.interceptors.request.use(
  config => {
    console.log(config)
    if (!(config.data && config.data.param)) {
      if (localStorage.getItem('token')) {
        config.headers.myAuthorization = localStorage.getItem('token')
      }
    }
    return config
  },
  error => {
    if (error && error.status) {
      switch (error.response.status) {
        case 403:
          router.push('/E_403')
          break
        case 404:
          router.push('/E_404')
          break
        case 500:
          router.push('/E_500')
          break
      }
    } else { return Promise.reject(error) }
  })
export default axios
