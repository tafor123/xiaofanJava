<template>
  <div>
    <div style="display: flex;" class="item">
      <img class="menu-img" :src="dataList[currentIndex]"  style="margin: 0 auto;width: 100%; height: 100%">
    </div>
    <div class="page" v-if="this.dataList.length > 1">
      <ul>
        <li v-for="(item,index) in dataList"
            :key="index"
            @click="gotoPage(index)"
            :class="{'current':currentIndex === index}"
            style="list-style: none;"
        >
        </li>
      </ul>
    </div>

    <el-row :gutter="20" style="margin-top: 30px; padding-bottom: 30px;border-bottom-style: dashed;border-bottom-color: #d9d9d9">
      <el-col :span="6" v-for="(demo ,index) in demos" :key="index">
        <div style="display: flex; align-items: center;justify-content: center">
          <img :src="demo.img" alt="" style="width: 70px;height: 70px" />
          <div>
            <span style="font-size: 18px;font-weight: bold">{{demo.title}}</span><br>
            <span style="font-size: 12px;">{{demo.content}}</span>
          </div>
        </div>
      </el-col>
    </el-row>
    <!--  三个卡片  -->
    <el-row :gutter="20" style="margin-top: 30px;padding: 10px 30px;">
      <el-col :span="6" :offset="1" class="size"
              v-for="(list ,index) in lists" :key="index"
      >
        <div style="display:flex;position: absolute;z-index: 10">
          <img :src="list.beijing" alt=""  style="position: relative;" />
          <div class="box-font">
            <span style="font-size: 24px; color: rgb(19, 87, 189);">{{list.title}}</span>
            <div style="margin-top: 10px">
              <span style="font-size: 14px; color:#1357bd;">{{ list.title1 }}</span>
            </div>
            <div style="margin-top: 70px">
              <span style="font-size: 19px;color: #666">{{ list.titleSen }}</span>
              <div style="margin: 17px 0px">{{ list.titleSen1 }}</div>
            </div>
            <div style="margin: 9px 0px">
              <el-button>弹性灵沾</el-button>
              <el-button>适用场景丰富</el-button>
            </div>
            <div style="margin-top: 40px">
              <span style="font-size: 19px;color: #666">{{ list.titleTir }}</span>
              <div style="margin: 17px 0px">{{ list.titleSen1 }}</div>
            </div>
            <div style="margin: 9px 0px">
              <el-button>弹性灵沾</el-button>
              <el-button>适用场景丰富</el-button>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
    <!-- 下部  -->
<!--    <div style="">
      <el-row :gutter="20" style="margin-top: 30px;padding: 10px 30px;">
        <el-col :span="6"  :offset="3" >
          <el-card style="margin: 20px;" class="box-card">
            <div style="display: flex">
              <div style="width: 50%;flex: 1">
                <h1>你好呀</h1>
                <span>这是文字</span>
              </div>
              <div style="width: 50%;flex: 1">
                这是图片
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6"  :offset="3" ><el-card style="margin: 20px" class="box-card">
          <img src="../../../assets/img/beijing01.png"></el-card></el-col>
        <el-col :span="6"  :offset="3" ><el-card style="margin: 20px" class="box-card" >
          <img src="../../../assets/img/beijing01.png"></el-card></el-col>
        <el-col :span="6"  :offset="3" ><el-card style="margin: 20px" class="box-card">
          <img src="../../../assets/img/beijing01.png"></el-card></el-col>

      </el-row>
    </div>-->
    <div style="background: linear-gradient(180deg,#e0f1ff,#e0f1ff);">
      <div style="display: flex;margin: 30px 100px;">
        <div style="width: 500px;height: 500px;overflow: hidden">
          <img src="@/assets/img/个人信息展示.png" width="100%" height="100%">
        </div>
        <div style="text-align: center;margin: 100px;">
          <h1>资料随心传输 上传/下载快人一步</h1>
          <span style="font-size: 16px">支持高效、稳定地数据下载，同时也支持将本地文件快速上传到网盘云端。
          此外，基于智能算法的多任务上传/下载功能，
          能判断和识别任务的优先级和网络状况，让文件传输更加快捷灵活
        </span>
        </div>
      </div>
     </div>
<!-- 备案号  -->
        <div style="font-size:15px; text-align: center;margin-bottom: 20px">
          <div style="margin-top: 20px">
            <span >云想衣裳花想容， 春风拂槛露华浓。</span>
          </div>
          <div >
            <div class="w328 clearfix">Copyright © 2023 EyouCMS 版权所有　备案号：<a href="#" rel="nofollow" target="_blank">琼ICP备2023092578号-6</a>　<a href="#">网站地图</a></div>
          </div>
        </div>

  </div>
</template>

<script>
export default({
  name: "Home",
  data() {
    return {
      lists: [{
        beijing: require("@/assets/img/beijing01.png"),
        title: '敏捷迭代',
        title1: '打造优质敏捷的数字新底座',
        titleSen: '云服务器',
        titleSen1: '灵活稳定的弹性计算服务',
        titleTir: '机器学习平台'
      },
        {
          beijing: require("@/assets/img/beijing02.png"),
          title: '数据驱动',
          title1: '基于数据让业务决策更科学',
          titleSen: '客户数据平台VeCDP',
          titleSen1: '灵活稳定的弹性计算服务',
          titleTir: '云服务器'
        },
        {
          beijing: require("@/assets/img/beijing03.png"),
          title: '体验创新',
          title1: '持续提升较终用户互动体验',
          titleSen: '实时音视频',
          titleSen1: '灵活稳定的弹性计算服务',
          titleTir: '视频直播'
        },

      ],
      dataList: [
        require("@/assets/img/lunbo3.jpg"),
        require("@/assets/img/lunbo4.jpg"),
        require("@/assets/img/lunbo5.jpg"),
      ],
      demos: [
        {
          title: '服务',
          img: require("@/assets/img/1收藏A.png"),
          content: '本产品致力于界面优良',
        },
        {
          title: '应答',
          img: require("@/assets/img/5信息A.png"),
          content: '全天候急速应答',
        },
        {
          title: '客户',
          img: require("@/assets/img/1我的A.png"),
          content: '从服务到价值',
        },
        {
          title: '安全',
          img: require("@/assets/img/_安全中心.png"),
          content: '透明可信的软件',
        }
      ],
      currentIndex: 0,
      timer: null,
    }
  },
  computed: {
    //上一张
    prevIndex() {
      if (this.currentIndex === 0) {
        return this.dataList.length - 1;
      } else {
        return this.currentIndex - 1;
      }
    },
    //下一张
    nextIndex() {
      if (this.currentIndex === this.dataList.length - 1) {
        return 0;
      } else {
        return this.currentIndex + 1;
      }
    }
  },
  methods: {

    //定时器
    runInv() {
      this.timer = setInterval(() => {
        this.gotoPage(this.nextIndex);
      }, 3000);
    },
    gotoPage(index) {
      this.currentIndex = index;
    }
  },
  mounted() {
    this.runInv();
  }
 })

</script>

<style scoped>
.item{
  width: 100%;
  height: 511px;
  overflow: hidden;
}
.demo-block {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 10px;

}
.size{
  width: 420px;
  height: 531px;
//background-color: rgb(255,123,134,0.5);
  border-radius: 10px;
}
.size1{
  width: 531px;
  height: 300px ;
  margin: 20px;
  background-color: rgb(255,123,134,0.5);
  border-radius: 10px;

}
.box-font{
  z-index: 12;
  position: absolute;
  display: inline;
  top: 53px;
  left: 75px;
}
.box-card {
  width: 480px;
}
</style>