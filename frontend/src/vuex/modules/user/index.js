import api from '@/api/user'
import * as types from './mutation-types.js'
import { orderBy, findIndex, find } from 'lodash'

const user = {
	namespaced: true,
	state: {
		users: [],
		errorMessage: ''
	},
    getters: {
		// エラーメッセージを返す
		error: (state) => state.error,
		// ユーザーリストを引数の項目でソートして返す
		orderList: (state) => (field) =>
			orderBy(state.users, field, 'asc'),
		// ユーザーのempNoから配列インデックスを返す
		findIndexById: (state) => (id) =>
			findIndex(state.users, o => o.empNo === empNo),
		// ユーザーのempNoからユーザー情報を返す
		findUserById: (state) => (empNo) =>
			find(state.users, o => o.empNo === empNo)
	},
    mutations: {
		// 全ユーザー情報をセット
		[types.SET_USERS] (state, payload) {
		  state.users = payload
		},
//	    // ユーザー情報に追加
//	    [types.ADD_USER] (state, payload) {
//	      state.users.push(payload)
//	    },
//	    // ユーザー情報を更新
//	    [types.UPDATE_USER] (state, payload) {
//	      const idx = findIndex(state.users, o => o.empNo === payload.empNo)
//	      Vue.set(state.users, idx, payload)
//	    }
	},
    actions: {
		// 全ユーザーを読み込む
		load({ commit }) {
		  return api.getUsers().then(userList => {
			commit(types.SET_USERS, userList)
		  })
		},
		// ユーザー情報を保存
	    save({ commit }, { user, editFlg }) {
	      // editFlgがtrueなら編集
	      const type = editFlg ? api.putUser : api.postUser
	      return type(user.empNo, user).then(entry => {
	        // サーバー側で成功したらフロント側のデータを更新
	    	commit('types.SET_USERS', entry)
//	        if (editFlg) {
//	          commit('types.UPDATE_USER', entry)
//	        } else {
//	          commit('types.ADD_USER', entry)
//	        }
	      }).catch(error => {
	        // サーバー側で失敗したらエラーをセット
	        // commit('setError', error)
	    	alert(error)
	      })
	    }
	}
}
export default user