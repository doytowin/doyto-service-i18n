// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
/* eslint-disable import/first */
import Vue from 'vue'
import App from './App.vue'
import router from './router'

import VueResource from 'vue-resource'
Vue.use(VueResource)
Vue.http.interceptors.push(function (request, next) { // 拦截器
// 跨域携带cookie
  request.credentials = true
  next()
})

// axios跨域
import axios from 'axios'
window.axios = axios
axios.defaults.withCredentials = true

import ElementUI from 'element-ui'
Vue.use(ElementUI)

import 'element-ui/lib/theme-chalk/index.css'
import 'element-ui/lib/theme-chalk/display.css'
import './assets/scss/global.scss'

import Util from './components/Util'
window.Util = Util
import Cons from './components/Cons'
window.Cons = Cons
import Crud from './components/Crud'
window.Crud = Crud

import DwPage from './partial/DwPage.vue'
Vue.component(DwPage.name, DwPage)

/** vue-i18n **/
import VueI18n from 'vue-i18n'
Vue.use(VueI18n)
const i18n = new VueI18n({})

// Vue.directive('t', {})
Vue.component('t', {
  render: function (createElement) {
    return this.$slots.default[0]
  }
})
Vue.directive('t', {
  bind (el, binding, vnode) {
    el.originText = el.innerText.trim()
    // console.log(vnode.context)
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
    // console.log(this)
    if (!this.$root.lang) {
      return this.$slots.default[0]
    }
    return this.$root._v(this.$t(this.$slots.default[0].text))
  }
})
/** vue-i18n **/

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  data () {
    return {
      lang: undefined,
      languages: [],
      system: {
        name: '多语言管理系统'
      }
    }
  },
  created() {
    let vm = this
    window.addEventListener('resize', function () {
      vm.$emit('resize')
      console.log('$emit:resize')
    })
    Util.alert = function (o) {
      vm.$message({
        message: o,
        type: 'error'
      })
    }

    Util.handleFailure = function (data) {
      // const data = response.data
      if (data && !data.success) {
        if (data.code === 1) {
          vm.$router.replace('/?redirect=' + encodeURIComponent(location.hash.substring(1)))
        } else {
          Util.alert(data.info || '服务访问出错')
        }
      } else {
        // Util.alert('服务访问出错')
        location.href = Cons.url + '#/?redirect=' + encodeURIComponent(location.hash.substring(1))
      }
    }
  },
  i18n,
  mounted () {
    this.switchLang(localStorage.lang || 'zh_CN')

    axios.get(Cons.openapi + 'i18n/i18n/locale').then(
      response => {
        let json = response.data
        if (json.success) {
          this.languages = json.data
        }
      }
    )
  },
  methods: {
    switchLang (lang) {
      this.$emit('loading')
      axios.get(Cons.openapi + 'i18n/i18n/' + lang + '.json').then(res => {
        i18n.locale = lang
        i18n.setLocaleMessage(lang, res.data.data)
        this.lang = localStorage.lang = lang
        this.$emit('loaded')
      }, res => {
        alert('服务访问出错')
        this.$emit('loaded')
      })
    }
  },
  router,
  template: '<App/>',
  components: { App }
})
