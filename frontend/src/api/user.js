import axios from 'axios'

const BASE_URL = '/admin/user/';

//// 成功の処理
//const apiSuccess = (response) => {
//      return response.userList
//}

// 失敗の処理
const apiError = (error) => Promise.reject(error.message || 'ERROR')

export default {
  getUsers: () =>
    axios.get('/admin/user').then((res) => {return res.data}),

  getUser: (empNo) =>
    axios.get(`/admin/user/${empNo}`, empNo).then((res) => { return res.data }),

  postUser: (empNo, user) =>
    axios.post('/admin/user', user).then((res) => { return res.data }).catch(apiError),

  putUser: (empNo, user) =>
    axios.put(`/admin/user/${empNo}`, user).then((res) => { return res.data }).catch(apiError),

  deleteUser: (empNoList) =>
    axios.delete('/admin/user', empNoList).then((res) => { return res.data }).catch(apiError)
}