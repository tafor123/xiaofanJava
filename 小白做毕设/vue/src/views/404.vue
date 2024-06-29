<script>


import user from "@/views/manager/User.vue";

export default ({
  name: "404"
})
</script>

<template>
  <div>
    <div style="display: flex;overflow: hidden;align-items: center;justify-content: center;height: 100vh;">
      <div style="font-size: 40px">找不到页面<router-link to="/" >返回首页</router-link></div>
    </div>

  </div>
</template>

<style scoped>

</style>