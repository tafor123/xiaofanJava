<template>
  <div>
    <el-card style="width: 50%;margin: 100px">
      <el-form ref="formRef" :rules="rules" :model="user" label-width="80px" style="padding-right: 20px" >
        <el-form-item label="原始密码" prop="password">
          <el-input show-password v-model="user.password" placeholder="原始密码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input show-password v-model="user.newPassword" placeholder="新密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input show-password v-model="user.confirmPassword" placeholder="确认密码"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">确认修改</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>


export default ({
  name: "Password",
  data(){
    const validatePassword = (rule,value,callback) => {
      if(value === ''){
        callback(new Error('请输入密码'))
      }else if(value !== this.user.newPassword){
        callback(new Error('两次密码不一样'))
      }else {
        callback()
      }
    }
    return{
      user: JSON.parse(localStorage.getItem('cloud-user') || '{}'),
      rules: {
        password: [
          { required: true, message : '请输入密码', trigger: 'blur'}
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      }
    }
  },
  methods:{
    update(){
     this.$refs.formRef.validate((valid) =>{
       if(valid){
         this.user.password = this.user.newPassword
         //保存到数据库中
         this.$request.put('/user/update',this.user).then(res =>{
           if(res.code === '200'){
             this.$message.success("修改密码成功")
             this.$router.push('/login')
           }else{
             this.$message.error(res.msg)
           }
         })
       }
     })
    }
  }
})
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
</style>