import Vuex from 'vuex'
import Vue from 'vue'
import user from './modules/user/index'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    user
  }
})

export default store