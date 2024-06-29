<template>
  <div>
    <el-container>
<!--  侧边栏    -->
      <el-aside width="200px"  style=" min-height: 100vh;background-color: #4976e7">
<!--    头部    -->
        <div style="display: flex;justify-content: center; margin: 10px 0">
          <div>
            <img src="@/assets/img/logo1.png" width="50px" height="50px">
          </div>
          <div style="text-align: center;margin-top: 10px">
            <span style="font-size: 20px" >CloudShare</span>
          </div>
        </div>
<!--    菜单    -->
        <el-menu  style="border: none"
                  text-color="#fff"
                  active-text-color="#ffd04b"
                  background-color="#4976e7"
                  :default-active="$route.path"
                  router
        >
          <el-menu-item index="/backHome" style="">
            <i class="el-icon-s-home"></i>
            <span>系统首页</span>
          </el-menu-item>
          <el-submenu index="/user">
            <template slot="title"><i class="el-icon-menu"></i><span>信息管理</span></template>
            <el-menu-item index="/user">用户管理</el-menu-item>
            <el-menu-item index="/admin">管理员管理</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-container>
<!--      头部  -->
        <el-header style="box-shadow: 2px 0 6px rgba(0,21,41,.35);">
          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
          <el-dropdown placement="bottom">
            <div style="display: flex;align-items: center;cursor: default">
              <img :src=" user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\n'" width="50px" height="50px" style="border-radius: 50%">
              <span>{{user.name}}</span>
            </div>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="$router.push('/personB')">个人信息</el-dropdown-item>
              <el-dropdown-item @click.native="$router.push('/passwordB')">修改密码</el-dropdown-item>
              <el-dropdown-item @click.native="exit">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
          </div>
        </el-header>

        <!--    主体    -->
        <el-main>
          <router-view @update:user="updateUser" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>

export default ({
  data(){
    return{
      user: JSON.parse(localStorage.getItem('cloud-user') || '{}'),
    }
  },
  mounted() { //页面加载完成之后触发
    if(!this.user.id){ // 当前的浏览器没有用户信息
      this.$router.push('/login')
    }
  },
  methods:{
    updateUser(user) {   // 获取子组件传过来的数据  更新当前页面的数据
      this.user = JSON.parse(JSON.stringify(user))  // 让父级的对象跟子级的对象毫无关联
    },
    exit(){
      localStorage.removeItem('cloud-user') //清除用户信息和token
      this.$router.push('/login')
    }
  }
})
</script>


<style scoped>
.el-menu--inline {
  background-color: #044489 !important;
}
.el-menu--inline .el-menu-item {
  background-color: #044489 !important;
  padding-left: 49px !important;
}
.el-menu-item:hover, .el-submenu__title:hover {
  color: #fff !important;
}
.el-submenu__title:hover i {
  color: #fff !important;
}
.el-menu-item:hover i {
  color: #fff !important;
}
.el-menu-item.is-active {
  background-color: #1cd4b2 !important;
  border-radius: 5px !important;
  width: calc(100% - 8px);
  margin-left: 4px;
}
.el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip{
  margin-left: -4px;
}
.el-menu-item {
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu__title {
  height: 40px !important;
  line-height: 40px !important;
}
.el-submenu .el-menu-item {
  min-width: 0 !important;
}
.el-menu--inline .el-menu-item.is-active {
  padding-left: 45px !important;
}
/*.el-submenu__icon-arrow {*/
/*  margin-top: -5px;*/
/*}*/

.el-aside {
  transition: width .3s;
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
}
.logo-title {
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;   /* 0.3s */
}
.el-header {
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  display: flex;
  align-items: center;
}
</style>