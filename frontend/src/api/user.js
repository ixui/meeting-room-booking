import axios from 'axios'

const BASE_URL = '/admin/user/';

//// 成功の処理
//const apiSuccess = (response) => {
//      return response.userList
//}
//
// 失敗の処理
const apiError = (error) => Promise.reject(error.message || 'ERROR')

//// postでパラメーターを送信できるように変換する
//let params = new URLSearchParams();
//params.append('text', 'テストだよー');

export default {
  getUsers: () =>
    axios.get('/admin/user').then((res) => {return res.data}),

  getUser: (empNo) =>
    axios.get(`/admin/user/${empNo}`, { empNo: empNo }).then((res) => { return res.data }),

  postUser: (empNo, user) =>
    axios.post('/admin/user', new URLSearchParams().append('user', user)).then((res) => { return res.data }).catch(apiError),

  putUser: (empNo, user) =>
    axios.put(`/admin/user/${empNo}`, { user: user }).then((res) => { return res.data }).catch(apiError),

  deleteUser: (empNo) =>
//    demox.delete(`/admin/user/${empNo}`, { empNo: empNo }).then().catch(apiError)
    axios.delete(`/admin/user/${empNo}`, { empNo: empNo }).then((res) => { return res.data }).catch(apiError)
}