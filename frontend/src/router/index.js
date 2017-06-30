import Vue from 'vue'
import Router from 'vue-router'
import Admin from '@/components/Admin'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Admin',
      component: Admin
    }
  ]
})
