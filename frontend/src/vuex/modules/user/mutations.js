import * as types from './mutation-types.js'

export const mutations = {
    // 全ユーザー情報をセット
    [types.SET_USERS] (state, userList) {
        state.users = userList
    }
}