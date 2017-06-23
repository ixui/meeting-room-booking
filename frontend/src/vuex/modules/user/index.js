import Vue from 'vue'
import api from '@/api/user'
import actions from './actions'
import mutations from './mutations'
import getters from './getters'

const user = {
    namespaced: true,
    state: {
        users: [],
        errorMessage: ''
    },
    getters,
    mutations,
    actions
}
export default user