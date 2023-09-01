<template>
<div>
<!--  <el-row :gutter="20">
    <el-col :span="2">
      <div style="height: 300px; width: 100%;background-color: #42b983"></div>
    </el-col>
    <el-col :span="12">
      <div style="height: 300px; width: 100%;background-color: red"></div>
    </el-col>
  </el-row>-->
  <el-row>
    <el-col :span="6">
     <div style="padding: 10px;border: 1px solid #ccc; text-align: center">
       <img style="width: 100%"  src="@/assets/logo.png">
       <div style="text-align: center">这是一个很牛的布局</div>
     </div>
    </el-col>
    <el-col :span="6">
     <div style="padding: 10px;border: 1px solid #ccc; text-align: center">
       <img style="width: 100%"  src="@/assets/logo.png">
       <div style="text-align: center">这是一个很牛的布局</div>
     </div>
    </el-col>
    <el-col :span="6">
     <div style="padding: 10px;border: 1px solid #ccc; text-align: center">
       <img style="width: 100%"  src="@/assets/logo.png">
       <div style="text-align: center">这是一个很牛的布局</div>
     </div>
    </el-col>
    <el-col :span="6">
     <div style="padding: 10px;border: 1px solid #ccc; text-align: center">
       <img style="width: 100%"  src="@/assets/logo.png">
       <div style="text-align: center">这是一个很牛的布局</div>
     </div>
    </el-col>
  </el-row>

  <el-row>
    <el-col :span="24">
      <button>按钮</button>
      <el-button>按钮</el-button>
      <el-button type="primary">主要按钮</el-button>
      <el-button type="danger">危险按钮</el-button>

    </el-col>
  </el-row>


  <el-row>
    <el-col :span="24">
      <el-input style="width: 200px" v-model="value" placeholder="请输入" prefix-icon="el-icon-search"></el-input>
      <el-input show-password style="width: 200px" v-model="password" suffix-icon="el-icon-search">请输入密码</el-input>
    </el-col>
  </el-row>


  <el-row>
    <el-col :span="24">
      <el-autocomplete style="width: 300px" placeholder="请输入内容，我来帮你猜一猜" :fetch-suggestions="querySearch"
                       :trigger-on-focus="false" v-model="value2"></el-autocomplete>

    </el-col>
  </el-row>
</div>
</template>
<script>
export default {
  name: 'Element',
  data(){
    return{
      value:'',
      password: '',
      value2:'',
      coffees: [{ value: '1星巴克咖啡' },{ value: '1栖巢咖啡' }, {value: '2瑞幸咖啡'}, {value: '3库迪咖啡'}]// 值必须带value
    }
  },
  methods: {
    querySearch(query, cb){
      let result =  query ? this.coffees.filter(v => v.value.includes(query)) : this.coffees
      console.log(result)
      cb(result);
    }
  }
}
</script>