<template>
  <div class="container" align="center">
    <!--<div th:if="${errorMessage}">
      <p th:text="${errorMessage}" class="err"></p>
      <a href="/admin/user" class="btn btn-default btn-sm active">もう一度読み込む</a>
    </div>-->
    <div id="resultTable">
      tamura
      <div v-show="users.length > 0" class="table-responsive col-sm-offset-3 col-sm-6" align="center">
        <form action="/admin/delete" method="post" id="userDeleteForm">
          <table class="table table-bordered table table-striped">
            <tr>
              <th class="col-sm-1"></th><th class="col-sm-3">社員番号</th><th class="col-sm-3">名前</th><th class="col-sm-3">権限</th><th class="col-sm-1"></th>
            </tr>
            <tr v-for="user in orderList('empNo')">
              <td><input type="checkbox" class="form-control input-sm" name="empNo" v-model="user.empNo" /></td>
              <td>{{user.empNo}}</td>
              <td>{{user.name}}</td>
              <td v-show="user.auth == 'ROLE_USER'">ユーザ</td>
              <td v-show="user.auth == 'ROLE_ADMIN'">管理者</td>
              <td><button type="button" class="btn btn-default btn-sm active select">選択</button></td>
            </tr>
          </table>
        </form>
        <button type="button" class="btn btn-default btn-sm active" id="register">新規登録</button>
        <button type="button" class="btn btn-default btn-sm active" id="delete">削除</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'admin',
  data () {
    return {
      // loading: true
    }
  },
  computed: {
    ...mapGetters('user', [
      'orderList'
    ])
  },
  created () {
    // 作成時にユーザーリストを取得
    this.$store.dispatch('user/load').then(() => {
      // this.loading = false
      console.log('取得完了')
    }).catch(e => {
      console.log(e)
    })
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}

th, td {
  font-size: 16px
}
</style>
