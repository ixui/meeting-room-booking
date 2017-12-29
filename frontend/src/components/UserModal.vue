<template>
  <!-- <transition name="modal">
    <div class="modal" v-if="empNo!=null" @click.self="$emit('close')">
      <div class="body">
        <h1>{{ title }}</h1>
        <button type="submit" style="display:none"></button>
        <transition name="modal">
          <div v-if="error" class="error" @click.self="$store.commit('item/resetError')">{{ error }}</div>
        </transition>
        <dl>
          <dt>社員番号</dt>
          <dd>
            <input v-model="form.empNo">
          </dd>
          <dt>氏名</dt>
          <dd>
            <input v-model="form.name">
          </dd>
          <dt>パスワード</dt>
          <dd>
            <input v-model="form.password">
          </dd>
          <dt>パスワード(確認)</dt>
          <dd>
            <input v-model="form.passwordConf">
          </dd>
          <dt>権限</dt>
          <dd>
            <input v-model="form.auth" size="3">
          </dd>
        </dl>
        <footer>
          <div class="left">
            <button type="button" @click="$emit('close')">閉じる</button>
          </div>
          <div class="right">
            <button type="button" @click="onSaveMember">保存する</button>
          </div>
        </footer>
      </div>
    </div>
  </transition> -->
  	<el-dialog title="title" width="30%" :visible="dialogFormVisible" :before-close="handleClose">
	  <el-form :model="form">
	    <el-form-item label="社員番号" :label-width="formLabelWidth" :visible="empNo==null">
	      <el-input v-model="form.empNo" auto-complete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="氏名" :label-width="formLabelWidth">
	      <el-input v-model="form.name" auto-complete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="パスワード" :label-width="formLabelWidth" :visible="empNo==null">
	      <el-input v-model="form.pass" auto-complete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="パスワード(確認)" :label-width="formLabelWidth" :visible="empNo==null">
	      <el-input v-model="form.confirmPass" auto-complete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="権限" :label-width="formLabelWidth">
	      <el-select v-model="form.auth" placeholder="Please select">
	        <el-option label="一般ユーザ" value="ROLE_USER"></el-option>
	        <el-option label="管理者" value="ROLE_ADMIN"></el-option>
	      </el-select>
	    </el-form-item>
	  </el-form>
	  <span slot="footer" class="dialog-footer">
	    <el-button @click="$emit('close')">Cancel</el-button>
	    <el-button type="primary" @click="onSaveUser">Register</el-button>
	  </span>
	</el-dialog>
</template>

<script>
import { mapGetters } from 'vuex'
import cloneDeep from 'lodash/cloneDeep'
export default {
  name: 'user-modal',
  // 型を指定して親からデータを受け取る→['user']指定せずにこのように書くことも可
  props: {
	empNo: String,
	dialogFormVisible: Boolean
  },
  data() {
    return {
      form: {
    	  empNo: '',
    	  name: '',
    	  pass: '',
    	  confirmPass: '',
    	  auth: ''
      },
      addFlg: false
    }
  },
  computed: {
	title() {
	  return this.empNo != null ? '編集' : '追加'
	},
	...mapGetters('user', ['findUserById', 'error'])
  },
  methods: {
    // 保存ボタン＆サブミットで内部データをストアに送る
    onSaveUser() {
      this.$store.dispatch('user/save', { user: this.form, editFlg: this.empNo!=null }).then(() => {
        // 結果にエラーが無ければウィンドウを閉じる
        // エラーがあればメッセージとして表示
        if (!this.error) this.$emit('close')
      })
    },
    // ×ボタン、ダイアログ外を押下した場合の処理
    handleClose() {
      this.$emit('close')
    }
  },
  created() {
	if (this.empNo != null) {
	  this.form = cloneDeep(this.findUserById(this.empNo))
	}
  }
}
</script>