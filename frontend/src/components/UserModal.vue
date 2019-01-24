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
  	<el-dialog title="title" width="30%" :visible="dialogFormVisible" :before-close="handleClose('form')">
	  <el-form :model="form" :rules="rules" ref="form">
	    <el-form-item label="社員番号" :label-width="formLabelWidth" :visible="empNo==null" prop="empNo">
	      <el-input v-model="form.empNo" auto-complete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="氏名" :label-width="formLabelWidth" prop="name">
	      <el-input v-model="form.name" auto-complete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="パスワード" :label-width="formLabelWidth" :visible="empNo==null" prop="pass">
	      <el-input v-model="form.pass" auto-complete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="パスワード(確認)" :label-width="formLabelWidth" :visible="empNo==null" prop="confirmPass">
	      <el-input v-model="form.confirmPass" auto-complete="off"></el-input>
	    </el-form-item>
	    <el-form-item label="権限" :label-width="formLabelWidth" prop="auth">
	      <el-select v-model="form.auth" placeholder="Please select">
	        <el-option label="一般ユーザ" value="ROLE_USER"></el-option>
	        <el-option label="管理者" value="ROLE_ADMIN"></el-option>
	      </el-select>
	    </el-form-item>
	  </el-form>
	  <span slot="footer" class="dialog-footer">
	    <el-button @click="handleClose('form')">Cancel</el-button>
	    <el-button type="primary" @click="onSaveUser('form')">Register</el-button>
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
      // triggerはblurを指定するとフォーカスが外れたタイミング
      // changeを指定するとリアルタイムでvalidationが行われる
      rules: {
    	  empNo: [
            { required: true, message: '入力してください。', trigger: 'blur' }
          ],
    	  name: [
            { required: true, message: '入力してください。', trigger: 'blur' }
          ],
    	  pass: [
            { required: true, message: '入力してください。', trigger: 'blur' },
            { min: 8, message: '8文字以上で入力してください。', trigger: 'blur' },
          ],
    	  confirmPass: [
            { required: true, message: '入力してください。', trigger: 'blur' },
            { min: 3, message: '8文字以上で入力してください。', trigger: 'blur' },
          ],
    	  auth: [
            { required: true, message: '入力してください。', trigger: 'blur' }
          ]
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
    onSaveUser(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$store.dispatch('user/save', { user: this.form, editFlg: this.empNo!=null }).then(() => {
            // 結果にエラーが無ければウィンドウを閉じる
            // エラーがあればメッセージとして表示
            if (!this.error) this.$emit('close')
          })
        }
      })
    },
    // ×ボタン、ダイアログ外を押下した場合の処理
    handleClose(formName) {
      // formの値を空にする
      this.$refs[formName].resetFields();
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