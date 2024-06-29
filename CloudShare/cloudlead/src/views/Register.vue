<template>
  <div>
    <div class="login_box">
      <div class="login_form">
        <div style="margin: 20px 0; text-align: center; font-size: 34px; color:black;"><span>欢迎注册后台管理系统</span></div>
        <el-form :model="user" :rules="rules" ref="registerRef">
          <el-form-item prop="username">
            <el-input  style="width: 300px" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input  show-password style="width: 300px" prefix-icon="el-icon-lock" placeholder="请输入账号" v-model="user.password"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPass">
            <el-input style="width: 300px"  prefix-icon="el-icon-lock"  show-password placeholder="请确认密码" v-model="user.confirmPass"></el-input>
          </el-form-item>
          <el-form-item prop="role" style="justify-content: left">
            <el-radio-group v-model="user.role">
              <el-radio label="用户"></el-radio>
              <el-radio label="商家"></el-radio>
            </el-radio-group>
          </el-form-item>
          <el-button type="primary" style="width: 300px;" @click="register">注册</el-button>
          <div style="margin-top: 10px">
            <span>有账号？</span>
            <span style="cursor: pointer" @click="$router.push('/login')">登录</span>
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

export default {
  name: "Register",
  data() {
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('请确认密码'));
      } else if(confirmPass !== this.user.password){
        callback(new Error('密码不一样'));
      }else {
        callback()
      }
    };
    return {
      user:{
        code: '', //表单里用户输入的code
        username: '',
        password: '',
        confirmPass: '',
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
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        role: [
          { required: true ,message: '请选择商家',trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
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