<template>
  <div>
    <!-- 歌曲搜索
        1按下回车v-on.enter
        2查询数据axios接口 v-model
        3渲染数据v-for 数组 图哈特
        歌曲播放 切换audio src地址
        1点击播放v-on自定义参数
        2歌曲地址获取（接口歌曲id
        3歌曲地址设置（v-bind）
        歌曲封面
        1点击播放
        2歌曲封面获取
        3歌曲封面设置
        歌曲评论
        1点击播放
        2歌曲评论获取
        3歌曲评论渲染
        歌曲动画
        1监听音乐播放 v-on play
        2监听音乐暂停 v-on pause
        3操纵类名 v-bind 对象
        MV 播放
        1mv图标显示 v-if
        2mv地址获取 mvid
        3遮罩层 v-show v-on
        4mv地址设置 v-bind-->


      <!-- 播放器主体区域 -->
      <div class="play_wrap" id="player">
        <div class="search_bar">
          <span style="margin: 20px;font-size: 30px;font-family: 汉仪雪君体繁" >长谷深风</span>
          <img src="@/assets/images/返回舱.png" width="30px" height="30px" @click="$router.push('/outline')">
          <!-- 搜索歌曲 -->
          <input type="text" autocomplete="off" v-model="query" @keyup.enter="searchMusic" />
        </div>
        <div class="center_con">
          <!-- 搜索歌曲列表 -->
          <div class='song_wrapper'>
            <ul class="song_list">
              <li v-for="(item,index) in musicList">
                <a href="javascript:;" @click="playMusic(item.id)"></a>
                <b>{{item.name}}</b>
                <span v-if="item.mvid!==0" @click="playMv(item.mvid)"><i></i></span>
              </li>
            </ul>
            <img src="@/assets/images/line.png" class="switch_btn" alt="">
          </div>
          <!-- 歌曲信息容器 -->
          <div class="player_con" :class="{playing:isPlaying}">
            <img src="@/assets/images/player_bar.png" class="play_bar" />
            <!-- 黑胶碟片 -->
            <img src="@/assets/images/disc.png" class="disc autoRotate" />
            <img :src="musicCover" class="cover autoRotate" />
          </div>
          <!-- 评论容器 -->
          <div class="comment_wrapper">
            <h5 class='title'>热门留言</h5>
            <div class='comment_list'>
              <dl v-for="item in hotComments">
                <dt>
                  <img :src="item.user.avatarUrl" alt="" />
                </dt>
                <dd class="name">{{item.user.nickname}}</dd>
                <dd class="detail">
                  {{item.content}}
                </dd>
              </dl>
            </div>
            <img src="@/assets/images/line.png" class="right_line">
          </div>
        </div>
        <div class="audio_con">
          <audio ref='audio' @play="play" @pause="pause" :src="musicUrl" controls autoplay loop class="myaudio"></audio>
        </div>
        <div class="video_con" style="display: none;" v-show="isShow">
          <video controls="controls" :src="mvUrl"></video>
          <div class="mask" @click="hide"></div>
        </div>
      </div>



  </div>
</template>

<script>
import '@/assets/music.css'
import axios from "axios";

export default ({
  name: "Music",
  data(){
    return{
      query: '王贰浪',
      musicList: [],
      musicUrl:"",
      musicCover: '',
      hotComments: [],
      mvUrl:"",
      isShow:false,
      isPlaying:false
    }
  },
  methods:{
    searchMusic() {
      var that = this
      axios.get('https://autumnfish.cn/search?keywords=' + this.query)
          .then(function(res) {
            // console.log(res);
            that.musicList = res.data.result.songs
          }, function(err) {
            console.log(err);
          })
    },
    playMusic(musicId) {
      // console.log(musicId);
      var that = this
      axios.get("https://autumnfish.cn/song/url?id=" + musicId)
          .then(function(res) {
            // console.log(res.data.data[0].url);
            // console.log(res);
            that.musicUrl = res.data.data[0].url
          }, function(err) {
            console.log(err);
          })
      // 歌曲详情获取
      axios.get('https://autumnfish.cn/song/detail?ids=' + musicId)
          .then(function(res) {
            that.musicCover = res.data.songs[0].al.picUrl
            // console.log(res.data.songs[0].al.picUrl);
          }, function(err) {
            console.log(err);
          })
      // 歌曲评论获取
      axios.get('https://autumnfish.cn/comment/hot?type=0&id=' + musicId)
          .then(function(res) {
            that.hotComments = res.data.hotComments
          }, function(err) {
            console.log(err);
          })
    },
    play() {
      this.isPlaying = true
    },
    pause() {
      this.isPlaying = false
    },
    playMv(mvid) {
      var that = this;
      axios.get("https://autumnfish.cn/mv/url?id=" + mvid).then(
          function(response) {
            // console.log(response);
            console.log(response.data.data.url);
            that.isShow = true;
            that.mvUrl = response.data.data.url;
          },
          function(err) {}
      );
    },
    hide() {
      this.isShow = false
    }
  }
})
</script>



<style scoped>

</style>