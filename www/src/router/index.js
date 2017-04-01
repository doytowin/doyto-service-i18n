import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Group from '@/modules/Group'
import I18n from '@/modules/I18n'
import I18nLocale from '@/modules/I18nLocale'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/group',
      name: 'Group',
      component: Group
    },
    {
      path: '/i18n/:group',
      name: 'I18n',
      component: I18n
    },
    {
      path: '/i18n/:group/:locale',
      name: 'I18nLocale',
      component: I18nLocale
    },
    {
      path: '/',
      name: 'Hello',
      component: Hello
    }
  ]
})
