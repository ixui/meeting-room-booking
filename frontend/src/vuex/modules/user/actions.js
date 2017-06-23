export const actions = {
    // 全ユーザーを読み込む
    load({ commit }) {
      return api.getUsers().then(userList => {
        commit([types.SET_USERS], userList)
      })
    }
}