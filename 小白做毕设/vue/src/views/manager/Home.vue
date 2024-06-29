<template>
   <div>
     <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding: 10px 20px; border-radius: 5px; margin-bottom: 10px">
       早安，{{ user.name}}，祝你开心每一天！
     </div>
     <div style="display: flex">
       <el-card style="width: 30%;margin-right: 10px">
         <div slot="header" class="clearfix">
           <span>欢迎你的来访</span>
         </div>
         <div>
           2024每一天都充满快乐！
           <div style="margin-top: 20px">
             <div style="margin: 10px 0"><strong>主题色</strong></div>
             <el-button type="primary">按钮</el-button>
             <el-button type="success">按钮</el-button>
             <el-button type="warning">按钮</el-button>
             <el-button type="danger">按钮</el-button>
             <el-button type="info" style="margin: 5px 0px">按钮</el-button>
           </div>
         </div>
       </el-card>
     </div>



   </div>
</template>
<script>


export default ({
  name: "Home",
  data() {
    return {
      users: [],
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
    }
  }
})
</script>
<style scoped>

</style>