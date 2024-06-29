<template>
  <div>
    <div  style="position: relative;">
      <div style="position: absolute;top: 370px;left: 535px;">
        <span ref="text" class="bj" ></span>
      </div>
    </div>
  </div>
</template>
<script setup>
/**
 * 打字机效果
 * npm install typeit
 * https://www.typeitjs.com/docs/vanilla/usage/#configuration-basics
 */
import { ref ,onMounted} from "vue";
import TypeIt from "typeit";
const props = defineProps({ strings: { type:Array } })
const text =ref(null)
onMounted(()=>{
  new (TypeIt)(text.value, {
    strings: ['林深初见鹿，梦醒时见你 ',' 江村独归处，寂寞养残生。' ,' 小男供饵妇搓丝，溢榼香醪倒接罹。','原神怎么你了？'],
    cursorChar: "<span class='cursorChar'>|<span>",//用于光标的字符。HTML也可以
    speed: 100,
    lifeLike: true,// 使打字速度不规则
    cursor: true,//在字符串末尾显示闪烁的光标
    breakLines: false,// 控制是将多个字符串打印在彼此之上，还是删除这些字符串并相互替换
    loop: true,//是否循环
  }).go()
})

</script>

<style>
.bj{
  font-weight: bold;
  font-size: 24px;
  color: white;
  background-color: rgba(255,255,255,0.7);
  padding: 10px;
  border-radius: 10px;
}

</style>