<template>
  <div style="height: 100vh;overflow: hidden;display: flex;
  align-items: center;justify-content: center;background-color: #4bd192">
    <div style="display: flex;background-color: white;width: 50%;border-radius: 30px;overflow: hidden">
      <div style="flex: 1">
        <img src="@/assets/img/image.png" style="width: 100%">
      </div>
      <div style="flex: 1;display: flex; align-items: center;justify-content: center">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="loginRef">
          <div style="font-size: 20px;text-align: center; font-weight: bold;margin-bottom: 20px"> 欢迎登录后台管理系统</div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex">
              <el-input prefix-icon="el-icon-circle-check" style="flex: 1" v-model="user.code"></el-input>
              <div style="flex: 1; height: 36px">
                <valid-code @update:value="getCode" />
              </div>
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
          </el-form-item>
          <div style="display: flex">
            <div style="flex: 1">没有账号？请<span style="color: #0f9876;cursor: pointer"
                                                  @click="$router.push('/register')">注册</span></div>
            <div style="flex: 1; text-align: right"><span style="color: #0f9876;cursor: pointer" @click="handleForgetPass">忘记密码</span></div>
          </div>
        </el-form>
      </div>
    </div>

    <el-dialog title="忘记密码" :visible.sync="forgetPassDialogVis">
      <el-form :model="forgetUserForm" label-width="80px" style="padding-right: 20px">
        <el-form-item label="用户名" >
          <el-input v-model="forgetUserForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="手机号"  >
          <el-input v-model="forgetUserForm.phone" autocomplete="off"  placeholder="请输入电话号码"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="forgetPassDialogVis = false">取 消</el-button>
        <el-button type="primary" @click="resetPassword">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import ValidCode from "@/conponents/ValidCode.vue";

export default ({
  name: "Login",
  components:{
    ValidCode
  },
  data() {

    //验证码校验
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入验证码'));
      } else if(value.toLowerCase() !== this.code){
        callback(new Error('验证码错误'));
      }else {
        callback()
      }
    };

    return{
      forgetUserForm: {},
      forgetPassDialogVis: false,
      code: '',//验证码组件传过来的code
      user:{
        code: '', //表单里用户输入的code
        username: '',
        password: '',
      },
      rules: {
        username: [
          { required: true ,message: '请输入账号',trigger: 'blur'}
        ],
        password: [
          { required: true ,message: '请输入密码',trigger: 'blur'}
        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ],
      }
    }
  },
  created() {

  },
  methods: {
    resetPassword() {
      this.$request.put('/password', this.forgetUserForm).then(res => {
        if (res.code === '200') {
          this.$message.success('重置成功')
          this.forgetPassDialogVis = false
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleForgetPass(){  //初始化表单
      this.forgetUserForm = {}
      this.forgetPassDialogVis =true
    },

    getCode(code){
      console.log(code)
      this.code = code.toLowerCase()
    },
    login(){
      this.$refs['loginRef'].validate((valid) =>{
        if(valid){
          //验证通过
          console.log(this.user)
          this.$request.post('/login', this.user).then(res =>{
            if (res.code === '200'){
              this.$router.push('/')
              this.$message.success('登录成功')
              localStorage.setItem("honey-user",JSON.stringify(res.data)) //储存用户信息
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
})
</script>
<style scoped>

</style>