import axios from 'axios'

const BASE_URL = '/admin/user/';

// 成功の処理
const apiSuccess = (response) => {
  return demoTimer().then(() => {
    if (response.data.status === true) {
      return response.data.userList
    } else {
      return Promise.reject(response.data)
    }
  })
}

// 失敗の処理
const apiError = (error) => Promise.reject(error.message || 'ERROR')

export default {
  getUsers: () =>
    axios.get('/admin/user/').then(apiSuccess).catch(apiError)
  // postUser: (id, item) =>
  //   axios.post('/admin/user/', { item: item }).then(apiSuccess).catch(apiError),
  // getUser: (id) =>
  //   axios.get(`/admin/user/${id}`, { id: id }).then(apiSuccess).catch(apiError),
  // putUser: (id, item) =>
  //   axios.put(`/admin/user/${id}`, { item: item }).then(apiSuccess).catch(apiError),
  // deleteUser: (id) =>
  //   axios.delete(`/admin/user/${id}`, { id: id }).then(apiSuccess).catch(apiError)
}