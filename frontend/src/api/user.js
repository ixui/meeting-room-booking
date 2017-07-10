import axios from 'axios'

const BASE_URL = '/admin/user/';

//// 成功の処理
//const apiSuccess = (response) => {
//      return response.userList
//}
//
//// 失敗の処理
//const apiError = (error) => Promise.reject(error.message || 'ERROR')

export default {
  getUsers: () =>
    axios.get('/admin/user').then((res) => {return res.data})
}