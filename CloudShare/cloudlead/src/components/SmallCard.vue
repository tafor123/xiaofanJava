vue
<template>
  <div id="tb">
    <el-table :data="tableData" style="width: 100%" height="250">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="name" label="姓名">
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <div @mouseover="showButton(scope.row)" @mouseleave="hideButton">
            <button v-show="scope.row.isButtonVisible">按钮</button>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    return {
      tableData: [
        {
          date: "2016-05-02",
          name: "王小虎",
          address: "上海市普陀区金沙江路 1518 弄",
          isButtonVisible: false
        },
        // 其他数据行...
      ]
    };
  },
  methods: {
    showButton(row) {
      row.isButtonVisible = true;
    },
    hideButton(row) {
      row.isButtonVisible = false;
    }
  }
};
</script>

<style scoped>
</style>