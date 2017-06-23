export const getters = {
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
}