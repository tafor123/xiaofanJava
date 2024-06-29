<template>
  <div>
    <div style="margin:80px auto;text-align: center;width: 500px;height: 500px">
      <img src="@/assets/img/加载失败.png" width="100%" height="100%" >
      <span style="font-size: large">找不到页面啦</span>
      <br/>
      <span @click="$router.push('/login')" >请前往<span style="color: #1357bd;cursor: pointer">登录界面</span>或</span>
      <span v-model="user" @click="check" style="color: #409EFF;cursor: pointer">当前页面</span>
    </div>
  </div>
</template>

<script>
export default ({
  name: "403",
  data(){
    return{

    }
  },
  methods:{
    check(){
      let user = JSON.parse(localStorage.getItem('cloud-user') || '{}')
      if(user.token){
        if(user.role === '管理员'){
          this.$router.push('/backHome')
        }else if(user.role === '用户'){
          this.$router.push('/')
        }
      }
    }
  }

})
</script>



<style scoped>

</style>