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
          <el-menu-item index="/">
            <template>
              <i class=" el-icon-house"></i>
              <span>系统首页</span>
            </template>
          </el-menu-item>

          <el-menu-item index="1">
            <template>
              <i class=" el-icon-house"></i>
              <span>系统首页</span>
            </template>
          </el-menu-item>
          <el-menu-item index="2">
            <template>
              <i class=" el-icon-house"></i>
              <span>系统首页</span>
            </template>
          </el-menu-item>


          <el-submenu>
            <template slot="title">
              <i class=" el-icon-menu"></i>
              <span>用户首页</span>
            </template>
            <el-menu-item>用户信息</el-menu-item>
            <el-menu-item>管理员信息</el-menu-item>
          </el-submenu>

        </el-menu>

      </el-aside>

      <el-container>
<!--    头部区域    -->
        <el-header>

          <i :class="change" style="width: 26px; font-size: 26px" @click="handelCollaspe"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/user' }">用户管理</el-breadcrumb-item>
          </el-breadcrumb>

          <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
            <i class="el-icon-quanping" style="font-size:26px;" @click="handelFull"></i>
            <el-dropdown placement="bottom">
              <div style="display: flex;align-items: center;cursor: default">
                <img src="@/assets/img/logo1.png" alt="" style="width: 40px;height: 40px">
                <span>管理员</span>
              </div>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>个人信息</el-dropdown-item>
                <el-dropdown-item>修改密码</el-dropdown-item>
                <el-dropdown-item @click="">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>

        </el-header>
<!--     主体区域   -->
        <el-main>
          <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
            早安，骚年，祝你开心每一天！
          </div>
          <el-card style="width: 500px">
            <div slot="header" class="clearfix">
              <span>青哥哥带你做毕设2024</span>
            </div>
            <div>
              2024毕设正式开始了！青哥哥带你手把手敲出来！
              <div style="margin-top: 20px">
                <div style="margin: 10px 0"><strong>主题色</strong></div>
                <el-button type="primary">按钮</el-button>
                <el-button type="success">按钮</el-button>
                <el-button type="warning">按钮</el-button>
                <el-button type="danger">按钮</el-button>
                <el-button type="info">按钮</el-button>
              </div>
            </div>
          </el-card>
        </el-main>

      </el-container>

    </el-container>
  </div>
</template>

<script>
export default {
  name: 'HomeView',
  data() {
    return{
      isCollaspe: true ,//不收缩
      asideWidth: '200px',
      change:'el-icon-s-fold'
    }
  },
  methods:{
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