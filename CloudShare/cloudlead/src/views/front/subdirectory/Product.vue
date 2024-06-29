<template>
  <div style="margin-top: 60px;height: 100%;width: 100%;display: flex">
<!--  左侧  -->
    <div class="menu-container">
      <div v-for="item in menus" :key="item.menuCode" @click="toggleSubMenu(item)"
           :class="['menu-item', item === activeMenu ? 'active' : '']">
        <div style="font-size: 25px" :class="item.icon"></div>
        <div class="title">{{ item.title }}</div>
      </div>
    </div>
    <div class="submenu-container">
      <div v-if="activeMenu">
        <div v-for="child in activeMenu.children" :key="child.category" @click="jump(child)"
             :class="['submenu-item', child.category === activeSubMenu ? 'active' : '']">
          <div style="font-size: 20px" :class="child.icon"></div>
          <div class="title">{{ child.title }}</div>
        </div>
      </div>
    </div>
<!--  右侧  -->
    <div style="background-color: #bdbdf0;height: 92vh;width: 80%">
      <router-view />
    </div>

   </div>
</template>

<script>

export default ({
  name: "Product",
  data(){
   return{
     activeMenu: '', // 当前选中的一级菜单项
     activeSubMenu: '', // 当前选中的二级菜单项
     menus:[
         {
           icon: 'el-icon-cloudy',
           title: '首页',
           menuCode: 'main',
           path: '/main/all',
           allShow: 'true',
           children: [
             {
               icon: 'el-icon-document',
               title: '全部',
               category: 'all',
               path: '/main/all'
             },
             {
               icon: 'el-icon-film',
               title: '视频',
               category: 'video',
               path: '/main/video'
             },
             {
               icon: 'el-icon-headset',
               title: '音频',
               category: 'music',
               path: '/main/music'
             },
             {
               icon: 'el-icon-reading',
               title: 'Word',
               category: 'word',
               path: '/main/word'
             }
           ]
         },
       {
         icon: 'el-icon-s-promotion',
         title: '分享',
         menuCode: 'Share',
         path: '/myShare',
         allShow: 'true',
         children: [
           {
             title: '全部',
             path: '/main/all'
           }
         ]
       },
       {
         icon: 'el-icon-delete-solid',
         title: '回收站',
         menuCode: 'recycle',
         path: '/recycle',
         allShow: 'true',
         tips: '回收站为你保存10天记录',
         children: [
           {
             title: '全部',
             path: '/main/all'
           }
         ]
       }
     ]
   }

  },
  methods: {
    toggleSubMenu(item) {
      this.activeMenu = this.activeMenu === item ? null : item;
      this.activeSubMenu = null; // 清空二级菜单的选中状态
       this.$router.push(item.path)
    },
    jump(child) {
      this.activeSubMenu = child.category;
      // 进行跳转操作...
      this.$router.push(child.category)
      // 如果跳转的路由，现实的页面从同一个file文件中查取后缀，显示出来
    }
  }
})
</script>

<style scoped>
.menu-container {
  width: 80px;
  height: 100vh;
  box-shadow: 15px 5px 5px 0px #999
}
.menu-item {
  text-align: center;
  padding: 10px;
  transition: 0.4s;
  justify-content: center;
}
.menu-item:hover {
  cursor: pointer;
  background-color: darkgray;
}
.menu-item.active {
  background-color: bisque; /* 设置选中菜单项的背景颜色 */
  color: #1357bd; /* 设置选中菜单项的文本颜色 */
}

.submenu-container {
  width: 200px;
}
.active{
  .iconfont{
    color: #06a7ff;
  }
  .text{
    color: #06a7ff;
  }
}
.sub-menu {
  /* 二级菜单的样式 */
}

.submenu-item {
  /* 二级菜单项的样式 */
  display: flex;
  text-align: right;
  padding: 10px;
  transition: 0.4s;
  align-items: center;
  justify-content: center;
}
.submenu-item:hover{
  cursor: pointer;
  background-color: darkgray;
}
.submenu-item.active {
  background-color: #409EFF; /* 设置选中二级菜单项的背景颜色 */
  color: #1357bd; /* 设置选中二级菜单项的文本颜色 */
  border-radius: 10px;
}

.active {
  /* 选中二级菜单项的样式 */
}

.title {
  /* 标题的样式 */
/*  width: 80px;*/
}
</style>