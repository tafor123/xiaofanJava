<template>
  <div>
    <div style="margin: 0 10px">
      <el-input v-model="name"  prefix-icon="el-icon-search" placeholder="请输入姓名" style="width: 200px;margin: 0 10px"></el-input>
      <el-input v-model="username"  prefix-icon="el-icon-search" placeholder="请输入用户名" style="width: 200px;margin: 0 10px"></el-input>
     <el-button style="margin: 0 10px" type="primary" @click="load(1)">搜索</el-button>
     <el-button style="margin: 0 10px" type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 10px">
      <el-button style="margin: 10px 10px" type="primary" plain @click="handleAdd">新增</el-button>
      <el-button style="margin: 10px 10px" type="warning" plain @click="delBeath">批量删除</el-button>
    </div>
    <el-table :data="tableData"  stripe style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="username" label="用户名" width="100"></el-table-column>
      <el-table-column prop="name" label="姓名" width="100"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="avatar" label="头像">
        <template v-slot="scope">
          <div style="display: flex;align-items: center">
            <el-image style="width: 50px;height: 50px;border-radius: 50%"
                      v-if="scope.row.avatar" :src="scope.row.avatar"
                      :preview-src-list="[scope.row.avatar]" lazy
            ></el-image>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="role" label="角色"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope" >
          <el-button size="mini" type="primary" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[100, 200, 300, 400]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="数据处理" :visible.sync="fromVisible" width="30%">
      <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phone">
          <el-input v-model="form.phone" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="textarea" v-model="form.address" placeholder="地址"></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-radio-group v-model="form.role">
            <el-radio label="管理员"></el-radio>
            <el-radio label="用户"></el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:9090/file/upload"
              :headers="{ token: user.token }"
              :file-list="form.avatar? [form.avatar] : []"
              list-type="picture"
              :on-success="handleAvatarSuccess"
          >
            <el-button type="primary">上传头像</el-button>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fromVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import request from "@/utils/request";

export default ({
  name: "User",
  data(){
    return{
      tableData: [], //所有的数据
      pageNum: 1, // 当前的页码
      pageSize: 5, // 每页显示的个数
      username: '',
      name: '',
      total: 0,
      ids: [],
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('cloud-user') || '{}'),
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ]
      },
    }
  },
  created() {
    this.load()
  },
  /*mounted(){
    request.get('user/selectAll').then(res => {
      this.user = res.data
    })
  },*/
  methods: {
    handleAvatarSuccess(response, file, fileList){
      // 把user的头像属性换成上传的图片的链接
      this.form.avatar = response.data
    },
    handleAdd(){
      this.form = { role: '用户' }  // 新增数据的时候清空数据
      this.fromVisible = true
    },
    handleEdit(row) {   // 编辑数据
      this.form = JSON.parse(JSON.stringify(row))  // 给form对象赋值  注意要深拷贝数据
      this.fromVisible = true   // 打开弹窗
    },
    save(){
      this.$refs.formRef.validate((valid) =>{
        if(valid){
          this.$request({
            url: this.form.id ? '/user/update': '/user/add',
            method: this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res =>{
            if(res.code === '200'){
              this.$message.success('保存成功')
              this.load(1)
              this.fromVisible = false
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    delBeath(){
      if(!this.ids.length){
        this.$message.error("请选数据")
        return
      }
      this.$confirm('你确认批量删除吗','确认删除',{type: "warning"}).then(response =>{
        this.$request.delete('/user/deleteMore/batch',{data: this.ids}).then(res =>{
          if(res.code === '200'){
            this.$message.success('批量删除成功')
            this.load(1)
          }else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {})
    },
    handleSelectionChange(rows){  // 当前选中的所有的行数据
      this.ids = rows.map(v => v.id)
    },
    del(id){
      this.$confirm('你确认删除吗','确认删除',{type: "warning"}).then(response =>{
        this.$request.delete('/user/delete/' + id).then(res =>{
          if(res.code === '200'){
            this.$message.success('删除成功')
            this.load(1)
          }else {
            this.$message.error(res.msg) //弹出错误信息
          }
        })
      }).catch(() => {})
    },

    reset(){
      this.name= ''
      this.username = ''
      this.load()
    },
    load(pageNum){
      if(pageNum){
        this.pageNum = pageNum
      }
      this.$request.get('/user/selectByPage',{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          name: this.name
        }
      }).then(res => {
        if(res.code === '200'){
          this.tableData = res.data.list
          this.total =res.data.total
        }
      })
    },
    handleCurrentChange(pageNum){
      this.load(pageNum)
    }

  }
})
</script>

<style scoped>

</style>