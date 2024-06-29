<template>
  <div>
<!--  导航栏  -->
    <div >
      <el-container>
        <el-header style="width: 100%;background-color: transparent;
        position: fixed;top: 0;
        box-shadow: 0 0 5px 1px #999;
        left: 0;z-index: 100">
          <div style="display: flex;">
            <div style="flex: 1; display: flex; align-items: center; justify-content:left;">
              <span style="font-weight: bold;font-size: 20px;margin-top: 2px">云盘系统</span>
            </div>
            <div style="flex: 1; align-items: center;justify-content: center;">
              <el-menu mode="horizontal"
                        router background-color="transparent" :default-active="$route.path"
                       text-color="black" style="border: none"  active-text-color="blue"
              >
                <el-menu-item style="flex: 1" index="/home">首页</el-menu-item>
                <el-menu-item style="flex: 1" index="/outline">记录</el-menu-item>
                <el-menu-item style="flex: 1" index="/product">产品</el-menu-item>
              </el-menu>
            </div>
            <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
                <div  style="display: flex;align-items: center;cursor: default">
                  <div v-if="!user.username">
                    <el-button size="medium" style="border-radius: 20px" @click="$router.push('/login')">登录</el-button>
                    <el-button size="medium" style="border-radius: 20px" @click="$router.push('/register')">注册</el-button>
                  </div>
                  <el-dropdown v-else placement="bottom">
                    <div style="display: flex;align-items: center;cursor: default">
                      <img :src=" user.avatar || 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png\n'" width="50px" height="50px" style="border-radius: 50%">
                      <span>{{user.username}}</span>
                    </div>
                    <el-dropdown-menu slot="dropdown">
                      <el-dropdown-item @click.native="$router.push('/person')">个人信息</el-dropdown-item>
                      <el-dropdown-item @click.native="$router.push('/password')">修改密码</el-dropdown-item>
                      <el-dropdown-item @click.native="exit">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                  </el-dropdown>
                </div>
            </div>
          </div>
        </el-header>
      </el-container>
    </div>
    <!--   主体     -->
    <router-view @update:user="updateUser" />
  </div>
</template>

<script>

export default ({
  name: 'Layout',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('cloud-user') || '{}')
    }
  },
  methods:{
    updateUser(user) {   // 获取子组件传过来的数据  更新当前页面的数据
      this.user = JSON.parse(JSON.stringify(user))  // 让父级的对象跟子级的对象毫无关联
    },
    exit(){
      localStorage.removeItem('cloud-user') //清除用户信息和token
      this.$router.push('/')
    }
  },
})
</script>


<style scoped>

.el-menu-item.is-active {
  background-color: #1cd4b2 !important;
  border-radius: 5px !important;

  margin-left: 4px;
}
</style>