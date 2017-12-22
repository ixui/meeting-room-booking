import axios from 'axios'

const BASE_URL = '/admin/user/';

//// 成功の処理
//const apiSuccess = (response) => {
//      return response.userList
//}
//
// 失敗の処理
const apiError = (error) => Promise.reject(error.message || 'ERROR')

export default {
  getUsers: () =>
    axios.get('/admin/user').then((res) => {return res.data}),

  getUser: (empNo) =>
    axios.get(`/admin/user/${empNo}`, { empNo: empNo }).then((res) => { return res.data }),

  postUser: (empNo, user) =>
    demox.post('/admin/user', { user: user }).then((res) => { return res.data }).catch(apiError),

  putUser: (empNo, user) =>
    demox.put(`/admin/user/${empNo}`, { user: user }).then((res) => { return res.data }).catch(apiError),

  deleteUser: (empNo) =>
    demox.delete(`/admin/user/${empNo}`, { empNo: empNo }).then().catch(apiError)
}