import Vue from 'vue'
import Router from 'vue-router'
import ElementUI from 'element-ui'
import locale from 'element-ui/lib/locale/lang/ja'
import 'element-ui/lib/theme-default/index.css'
// elementuiにasync-validatorが含まれているためそちらを使用する
//import VeeValidate, { Validator } from 'vee-validate'
//import ja from 'vee-validate/dist/locale/ja'
import Admin from '../components/Admin'

Vue.use(ElementUI, {locale})
//Vue.use(VeeValidate, {locale: 'ja'})
Vue.use(Router)

export default new Router({
  mode: 'history',
  base: '/admin/index/',
  routes: [
    {
      path: '/',
      name: 'Admin',
      component: Admin
    }
  ]
})
