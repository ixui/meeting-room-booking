import Vue from 'vue'
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
		findMemberById: (state) => (empNo) =>
			find(state.users, o => o.empNo === empNo)
	},
    mutations: {
		// 全ユーザー情報をセット
		[types.SET_USERS] (state, userList) {
			state.users = userList
		}
	},
    actions: {
		// 全ユーザーを読み込む
		load({ commit }) {
			return api.getUsers().then(userList => {
				commit(types.SET_USERS, userList)
			})
		}
	}
}
export default user