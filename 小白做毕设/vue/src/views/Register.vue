<template>
  <div style="height: 100vh;overflow: hidden;display: flex;
  align-items: center;justify-content: center;background-color: #3b8fe7">
    <div style="display: flex;background-color: white;width: 50%;border-radius: 30px;overflow: hidden">
      <div style="flex: 1">
        <img src="@/assets/img/register.png" style="width: 100%">
      </div>
      <div style="flex: 1;display: flex; align-items: center;justify-content: center">
        <el-form :model="user" style="width: 80%" :rules="rules" ref="registerRef">
          <div style="font-size: 20px;text-align: center; font-weight: bold;margin-bottom: 20px"> 欢迎注册后台管理系统</div>
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input prefix-icon="el-icon-lock" show-password placeholder="请输入密码" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass">
            <el-input prefix-icon="el-icon-lock"  show-password placeholder="请确认密码" v-model="user.confirmPass"></el-input>
          </el-form-item>
          <el-form-item prop="role">
            <el-radio-group v-model="user.role">
              <el-radio label="用户"></el-radio>
              <el-radio label="商家"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="register">注 册</el-button>
          </el-form-item>
          <div style="display: flex">
            <div style="flex: 1">有账号？请<span style="color: #0f9876;
            cursor: pointer" @click="$router.push('/login')">登录</span></div>
<!--            <div style="flex: 1; text-align: right"><span style="color: #0f9876;cursor: pointer">忘记密码</span></div>-->
          </div>
        </el-form>
      </div>
    </div>

  </div>
</template>

<script>

import ValidCode from "@/conponents/ValidCode.vue";
import user from "@/views/manager/User.vue";

export default ({
  name: "Register",

  data() {
    //验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'));
      } else if(confirmPass !== this.user.password){
        callback(new Error('密码不一样'));
      }else {
        callback()
      }
    };

    return{
      user:{
        code: '', //表单里用户输入的code
        username: '',
        password: '',
        confirmPass: '',
      },
      rules: {
        username: [
          { required: true ,message: '请输入账号',trigger: 'blur'}
        ],
        password: [
          { required: true ,message: '请输入密码',trigger: 'blur'}
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        role: [
          { required: true ,message: '请选择商家',trigger: 'blur'}
        ],
      }
    }
  },
  created() {
  },
  methods: {

    getCode(code){
      console.log(code)
      this.code = code.toLowerCase()
    },
    register(){
      this.$refs['registerRef'].validate((valid) =>{
        if(valid){
          //验证通过
          this.$request.post('/register',this.user).then(res =>{
            if (res.code === '200'){
              this.$router.push('/login')
              this.$message.success('注册成功')
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