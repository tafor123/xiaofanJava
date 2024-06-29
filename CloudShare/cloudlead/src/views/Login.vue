<template>
  <div>
    <div class="login_box">
      <div class="login_form">
        <div style="margin: 20px 0; text-align: center; font-size: 34px; color:black;"><span>欢迎登录后台管理系统</span></div>
        <el-form :model="user" :rules="rules" ref="userForm">
          <el-form-item prop="username">
            <el-input  style="width: 300px" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input  show-password style="width: 300px" prefix-icon="el-icon-lock" placeholder="请输入账号" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="code">
            <div style="display: flex;text-align: center;">
              <el-input  style="width: 150px;margin-left: 140px" prefix-icon="el-icon-lock" placeholder="验证码" v-model="user.code"></el-input>
              <div style="flex: 1;width: 150px" >
                <ValidCode style="justify-content: left;margin: 0 20px;margin-top: -5px"  @update:value="getCode"/>
              </div>
            </div>
          </el-form-item>
          <el-button type="primary" style="width: 300px;" @click="login">登录</el-button>
          <div style="margin-top: 10px">
            <span>没有账号？</span>
            <span style="cursor: pointer" @click="$router.push('/register')">注册</span>
            <span style="margin-left: 20px;cursor: pointer">忘记密码</span>
          </div>
        </el-form>
      </div>
    </div>
    <particles-bg
        class="login-bg"
        color="withe"
        :particleOpacity="0.7"
        :particlesNumber="100"
        shapeType="circle"
        :particleSize="4"
        linesColor="#8DD1FE"
        :linesWidth="1"
        :lineLinked="true"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="grab"
        :clickEffect="true"
        clickMode="push"
    >
    </particles-bg>
  </div>
</template>

<script>
import ValidCode from "@/components/ValidCode.vue";
export default {
  name: "Login",
  components:{
    ValidCode
  },
  data() {
    //校验验证码
    const validateCode = (rule ,value ,callback) =>{
      if(value === ''){
        callback(new Error("请输入验证码"))
      }else if(value.toLowerCase()!==this.code){
        callback(new Error("验证码输入有误"))
      }else {
        callback()
      }
    }
    return {
      redirect: this.$route.query.redirect,
      code: '', // 验证码组件传递过来的code
      user: {
        code: '', // 表单里用户输入的code 验证码
        username: '',
        password: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 3, max: 10, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
        code: [
          {validator: validateCode, trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    getCode(code){
      this.code = code.toLowerCase()
    },
    login(){
      this.$refs['userForm'].validate((valid) =>{
        if (valid){
          // 验证通过
          this.$request.post('/login', this.user).then(res => {
            if (res.code === '200') {
              localStorage.setItem("cloud-user", JSON.stringify(res.data))  // 存储用户数据
              if(res.data.role === '管理员'){
                this.$router.push('HomeBackground')
              }else {
                this.$router.push('/')
              }
              this.$message.success('登录成功')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
.login-bg{
  width: 100%;
  height: 100%;
//background: #3E3E3E;
  position: absolute;
  z-index: -15;
}
.login_box {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.login_form{
  background-color: #fff;
  width: 600px;
  height: 430px;
  padding: 10px;
  border-radius: 10px;
  position: absolute;
  background-color: rgba(255, 255, 255, 0.3);
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 2;
  box-shadow: 10px 10px 20px 0px rgba(0, 0, 0, 0.7);
  text-align: center;
}
/deep/.el-form-item {
  margin-bottom: 20px;
}
</style>