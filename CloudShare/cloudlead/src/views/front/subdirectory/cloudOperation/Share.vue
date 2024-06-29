<template>
  <div>
    <h2>创建文件夹</h2>
    <!-- 显示树形结构 -->
    <el-tree
        :data="treeData"
        :props="defaultProps"
        :default-expanded-keys="[defaultExpandedKey]"
        @node-click="handleNodeClick"
    ></el-tree>

    <!-- 文件夹名称输入框和创建按钮 -->
    <el-form :model="folderForm" ref="folderForm" label-width="80px">
      <el-form-item label="文件夹名称" prop="name">
        <el-input v-model="folderForm.name" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="createFolder">创建</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
import {defineComponent} from 'vue'

export default defineComponent({
  name: "Share",
  data() {
    return {
      // 树形结构数据
      treeData: [
        {
          id: 1,
          label: '根文件夹',
          children: [
            {
              id: 2,
              label: '子文件夹 1',
            },
            {
              id: 3,
              label: '子文件夹 2',
            },
          ],
        },
      ],
      // 树形结构配置
      defaultProps: {
        children: 'children',
        label: 'label',
      },
      // 默认展开的节点
      defaultExpandedKey: [1],
      // 创建文件夹表单数据
      folderForm: {
        name: '', // 文件夹名称
      },
      // 选中的父文件夹的id
      selectedParentFolderId: null,
    };
  },
  methods: {
    handleNodeClick(data) {
      // 当用户点击树节点时，记录选中的父文件夹id
      this.selectedParentFolderId = data.id;
    },
  }
})
</script>


<style scoped>

</style>