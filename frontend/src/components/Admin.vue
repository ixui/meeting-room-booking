<template>
  <div class="container" align="center" id="admin">
    <!--<div th:if="${errorMessage}">
      <p th:text="${errorMessage}" class="err"></p>
      <a href="/admin/user" class="btn btn-default btn-sm active">もう一度読み込む</a>
    </div>-->
    <div id="resultTable">
      <div v-show="users != null" class="table-responsive col-sm-offset-3 col-sm-6" align="center">
        <form action="/admin/delete" method="post" id="userDeleteForm">
          <el-table ref="multipleTable" :data="users" border style="width: 50%">
            <el-table-column type="selection" width="50">
            </el-table-column>
            <el-table-column sortable prop="empNo" label="社員番号">
            </el-table-column>
            <el-table-column prop="name" label="名前">
            </el-table-column>
            <el-table-column prop="auth" label="権限">
            </el-table-column>
            <el-table-column label="">
              <template scope="scope">
                <el-button size="small" @click="handleEdit(scope.$index, scope.row)">Edit</el-button>
                <el-button size="small" type="danger" @click="handleDelete(scope.$index, scope.row)">Delete</el-button>
              </template>
            </el-table-column>
          </el-table>
        </form>
        <button type="button" class="btn btn-default btn-sm active" id="register">新規登録</button>
        <button type="button" class="btn btn-default btn-sm active" id="delete">削除</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex'

export default {
  name: 'admin',
  data () {
    return {
      // loading: true
    }
  },
  computed: {
    ...mapState('user', [
      'users'
    ])
  },
  created () {
    // 作成時にユーザーリストを取得
    this.$store.dispatch('user/load').then(() => {
      // this.loading = false
    }).catch(e => {
      console.log(e)
    })
  }
}
</script>

<style scoped>

a {
  color: #42b983;
}

table {
	min-width: 650px;
}

th, td {
  font-size: 16px
}

th:nth-child(1),
td:nth-child(1) {
	min-width: 50px;
	width: 10%;
}
th:nth-child(2),
td:nth-child(2),
th:nth-child(3),
td:nth-child(3),
th:nth-child(4),
td:nth-child(4) {
	min-width: 120px;
	width: 20%;
}
th:nth-child(5),
td:nth-child(5) {
	min-width: 200px;
	width: 30%;
}
</style>
