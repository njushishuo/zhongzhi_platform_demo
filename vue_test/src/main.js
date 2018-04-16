import Vue from 'vue'
import App from './App'
import router from './router'
import VueCookie from 'vue-cookie'
import util from './router/utils'
import Axios from 'axios'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'


Axios.defaults.baseURL = util.hostPort + '/'
console.log(Axios.defaults.baseURL)
console.log(util.hostPort)

Vue.config.productionTip = false
Vue.use(VueCookie)
Vue.use(Vuetify)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
})
