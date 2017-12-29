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
            <el-table-column type="selection" min-width="50">
            </el-table-column>
            <el-table-column sortable prop="empNo" label="社員番号" min-width="120">
            </el-table-column>
            <el-table-column prop="name" label="名前" min-width="120">
            </el-table-column>
            <el-table-column prop="auth" label="権限" min-width="120">
            </el-table-column>
            <el-table-column label="" min-width="150">
              <template scope="scope">
                <!-- <el-button size="small" @click="edit(scope.$index, scope.row)">編集</el-button> -->
                <el-button size="small" @click="edit(scope.$index, scope.row)">編集</el-button>
              </template>
            </el-table-column>
          </el-table>
        </form>
        <el-button size="small" @click="createUser()">新規登録</el-button>
        <el-button size="small" @click="deleteUser()">削除</el-button>
      </div>
    </div>
    <user-modal :empNo="empNo" :dialogFormVisible="dialogFormVisible" @close="dialogFormVisible=false" v-if="dialogFormVisible"></user-modal>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import UserModal from './UserModal'

export default {
  name: 'admin',
  data () {
    return {
      // loading: true,
      empNo: null,
      dialogFormVisible: false,
    }
  },
  computed: {
    ...mapState('user', [
      'users'
    ])
  },
  methods: {
	// 編集ダイアログを表示する
	// 引数として選択したユーザーのデータを受け取る
	// ダイアログにempNoを渡すためにローカルの変数に値を入れておく
    edit(index, row) {
      this.dialogFormVisible=true;
      this.empNo=row.empNo;
    },
    createUser() {
      this.dialogFormVisible=true;
    },
    deleteUser(index, row) {
      console.log(index, row);
    }
  },
  created () {
    // 作成時にユーザーリストを取得
    this.$store.dispatch('user/load').then(() => {
      // this.loading = false
    }).catch(e => {
      console.log(e)
    })
  },
  components: { UserModal }
}
</script>

<style lang="less" scoped>

a {
  color: #42b983;
}

table {
  min-width: 650px;
}

th, td {
  font-size: 16px
}

#resultTable {
	.el-table {
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
			min-width: 150px;
			width: 30%;
		}
	}
}
</style>
