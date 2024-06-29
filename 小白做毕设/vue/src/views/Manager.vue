<template>
  <div>
    <el-container>
<!--    侧边栏  -->
      <el-aside :width="asideWidth" style="min-height: 100vh;background-color: #001529">
        <div style="height: 60px; color: white; display: flex;
        align-items: center;justify-content: center">
          <img src="@/assets/img/logo1.png" style="width: 40px; height: 40px">
          <span class="logoTitle"  v-show="!isCollaspe">honey2024</span>
        </div>

        <el-menu :collapse="isCollaspe" :collaspe-transition="false"  router background-color="#001529" text-color="rgba(255,255,255, 0.65)"
                 active-text-color="#fff" style="border: none;"  :default-active="$route.path">
          <el-menu-item index="/home">
              <i class=" el-icon-s-home"></i>
              <span>系统首页</span>
          </el-menu-item>

          <el-submenu index="info" v-if="user.role === '管理员'">
            <template slot="title">
              <i class=" el-icon-menu"></i>
              <span>用户首页</span>
            </template>
            <el-menu-item index="/user">用户信息</el-menu-item>
          </el-submenu>

        </el-menu>

      </el-aside>

      <el-container>
<!--    头部区域    -->
        <el-header>

          <i :class="change" style="width: 26px; font-size: 26px" @click="handelCollaspe"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: $router.path }">{{ $route.meta.name }}</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size:26px;" @click="handelFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex;align-items: center;cursor: default">
                <img :src="user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\n'" alt="" style="width: 40px;height: 40px;border-radius: 50%">
                <span>{{user.name}}</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="$router.push('/perso')">个人信息</el-dropdown-item>
                <el-dropdown-item @click.native="$router.push('/password')">修改密码</el-dropdown-item>
                <el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-header>
<!--     主体区域   -->
        <el-main>
          <router-view @update:user="updateUser"/>
        </el-main>

      </el-container>

    </el-container>
  </div>
</template>

<script>
import request from "@/utils/request";
import router from "@/router";

export default {
  name: 'HomeView',
  data() {
    return{
      isCollaspe: false ,//不收缩
      asideWidth: '200px',
      change:'el-icon-s-fold',
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),

    }
  },
  mounted() {  //页面加载完成之后触发
    if (!this.user.id) {   // 当前的浏览器没有用户信息
      this.$router.push('/login')
    }
  },
  methods:{
    updateUser(user) {   // 获取子组件传过来的数据  更新当前页面的数据
      this.user = JSON.parse(JSON.stringify(user))  // 让父级的对象跟子级的对象毫无关联
    },
    router() {
      return router
    },

    logout(){
      localStorage.removeItem('honey-user')  // 清除当前的token和用户数据
      this.$router.push('/login')
    },
    handelCollaspe(){
      this.isCollaspe = !this.isCollaspe
      this.asideWidth =this.isCollaspe ? '64px':'200px'
      this.change = this.isCollaspe ? 'el-icon-s-fold':'el-icon-s-unfold'
    },
    handelFull(){
      document.documentElement.requestFullscreen()
    },
  }

}
</script>

<style>
.el-menu--inline{
  background-color: #000c17 !important;
}
.el-menu--inline .el-menu-item{
  background-color: #000c17 !important;
  padding-left: 49px;
}
.el-menu-item:hover, .el-submenu__title:hover{
  color: #fff !important;
}
.el-submenu__title:hover i{
  color: #fff !important;
}
.el-menu-item.is-active{
  background-color: #1890ff !important;
  border-radius: 5px !important;
  margin: 4px !important;
}
.el-menu-item{
  height: 40px !important;
  line-height: 40px !important;
  margin: 4px !important;
}
.el-submenu__title{
  height: 40px !important;
  line-height: 40px !important;
  margin: 4px !important;
}
.el-aside{
  transition: width .3s;

}
.logoTitle{
  margin-left: 5px;
  font-size: 20px;
  transition: all .3s;
}
.el-header{
  box-shadow: 2px 0 6px rgba(0,21,41,.35);
  display: flex;
  align-items: center;
}
</style>