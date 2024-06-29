<template>
  <div style="z-index: -11;" class="geng">
<!--  打字机效果  -->
      <xin></xin>
    <div style="height: 700px;overflow: hidden;margin-top: 700px;background-color: #fff">
<!--   左边盒子   -->
      <div style="display: flex;margin: 100px 100px ">
        <div style="width: 30%;">
          <el-card class="bj">
            <div  v-if="user.username">
              <img :src="user.avatar" width="100px" height="100px" style="border-radius: 50%" alt=""/>
              <div>
                <span>{{user.username}}</span>
              </div>
              <div>
                <span style="font-size: 15px">清风拂面，穿越时空的长廊，执手岁月，我许你一世情缘。</span>
              </div>
            </div>
            <div v-else >
              <h2>文件选择</h2>
              <span>今天的你开心吗</span>
            </div>
          </el-card>

          <el-card style="margin-top: 40px;
           background: linear-gradient(to right, rgb(53, 139, 255), rgb(21, 198, 255));
           border-radius: 20px;" >
            <span style="cursor: pointer" @click="$router.push('/music')">速览</span>
            <h1>音乐</h1>
            <span>放松心情，快来听听音乐吧</span>
          </el-card>

        </div>
<!--   右边盒子     -->
        <div style="width: 60%;margin-left: 10%">
          <el-card style="margin-top: 10px;
           background: linear-gradient(to right, rgb(53, 139, 255), rgb(21, 198, 255));
           border-radius: 20px;"
                   v-for=" item in demos"  :key="demos"
          >
            <span>速览</span>
            <h1>生活倒影</h1>
            <span>记录美好生活</span>
          </el-card>
        </div>



      </div>
    </div>
  </div>
</template>

<script>
import Xin from "@/components/Xin.vue";
export default ({
  name: "Outline",
  components:{
    Xin
  },
  data(){
    return{
      user: JSON.parse(localStorage.getItem('cloud-user') || '{}'),
      demos: [
        {
          title: 'Demo 1',
          content: 'This is the content of Demo 1.',
        },
        {
          title: 'Demo 2',

          content: 'This is the content of Demo 2.',
        },
        {
          title: 'Demo 3',

          content: 'This is the content of Demo 3.',
        },
        {
          title: 'Demo 4',
          content: 'This is the content of Demo 4.',
        }
      ],
    }
  },

})
</script>

<style scoped>
.geng{
  background: url("@/assets/img/背景1.jpg") no-repeat fixed;
  background-size: 100%;
  overflow: hidden;
}
/deep/.bj{
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  overflow: hidden;
  background: linear-gradient(-45deg,#e8d8b9,#eccec5,#a3e9eb,#bdbdf0,#eec1ea);
  background-size: 400% 400%;
  animation: gradientBG 10s ease infinite;
}
@keyframes gradientBG {
  0% {
    background-position: 0 50%;
  }

  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0 50%;
  }

}

.bj{
  font-weight: bold;
  font-size: 24px;
  color: white;
  background-color: rgb(0 0 0 / 50%);
  padding: 10px;
  border-radius: 10px;
}
</style>