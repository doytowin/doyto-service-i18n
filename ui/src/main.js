// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
/* eslint-disable import/first */

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueResource from 'vue-resource'
// axios跨域
import axios from 'axios'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css'
import './assets/scss/global.scss'

import Util from './components/Util'
import Cons from './components/Cons'
import Crud from './components/Crud'
import DwPage from './partial/DwPage.vue'
/** vue-i18n **/
import VueI18n from 'vue-i18n'

Vue.config.productionTip = false

Vue.use(VueResource)
Vue.http.interceptors.push(function (request, next) { // 拦截器
// 跨域携带cookie
  request.credentials = true
  next()
})

window.axios = axios
axios.defaults.withCredentials = true

Vue.use(ElementUI)

window.Util = Util
window.Cons = Cons
window.Crud = Crud

Vue.component(DwPage.name, DwPage)

Vue.use(VueI18n)
const i18n = new VueI18n({})

Vue.directive('t', {
  bind (el, binding, vnode) {
    el.originText = el.innerText.trim()
    if (vnode.context.$root.lang) {
      el.innerHTML = vnode.context.$t(el.originText)
    }
  },
  componentUpdated (el, binding, vnode) {
    if (vnode.context.$root.lang) {
      el.innerHTML = vnode.context.$t(el.originText)
    }
  }
})
Vue.component('t', {
  render: function () {
    let key = this.$slots.default ? this.$slots.default[0].text : ' ';
    return this.$root._v(this.$root.lang ? this.$t(key) : key);
  }
})
/** vue-i18n **/

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  data () {
    return {
      isCollapse: false,
      lang: undefined,
      languages: [],
      system: {
        name: '多语言管理系统'
      },
      handleError: err => {
        let rsp = err.response;
        Util.alert(rsp.status + ': ' + rsp.statusText)
        this.$emit('loaded')
      }
    }
  },
  beforeCreate() {
    let vm = this
    window.addEventListener('resize', function () {
      vm.$emit('resize')
    })
    Util.alert = function (msg) {
      vm.$message({
        message: msg,
        type: 'error'
      })
    }

    Util.handleFailure = function (data) {
      if (data && !data.success) {
        if (data.code === 1001) {
          if (!location.hash.startsWith('#/?redirect=')) {
            vm.$router.replace('/?redirect=' + encodeURIComponent(location.hash.substring(1)))
          }
        } else {
          Util.alert(data.message || '服务访问出错')
        }
      } else {
        location.href = Cons.host('#/?redirect=' + encodeURIComponent(location.hash.substring(1)))
      }
    }
  },
  i18n,
  created () {
    this.switchLocale(localStorage.lang || 'zh_CN')

    axios.get(Cons.openApi('i18n/i18n/locale')).then(
      response => {
        let json = response.data
        if (json.success) {
          this.languages = json.data
        }
      }
    )
  },
  methods: {
    switchLocale (lang) {
      this.$emit('loading')
      axios.get(Cons.openApi('i18n/i18n/' + lang + '.json')).then(res => {
          i18n.locale = lang
          i18n.setLocaleMessage(lang, res.data.data)
          this.lang = localStorage.lang = lang
          this.$emit('loaded')
        },
        this.handleError
      )
    }
  },
  router,
  store,
  render: h => h(App)
}).$mount('#app')
