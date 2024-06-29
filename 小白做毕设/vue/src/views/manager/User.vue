<template>
  <div>
    <div>
      <el-input style="width: 200px" placeholder="查询用户名" v-model="username"></el-input>
      <el-input style="width: 200px;margin: 10px" placeholder="查询姓名" v-model="name" ></el-input>
      <el-button type="primary" @click="load(1)">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="warning" plain @click="handleAdd">新增</el-button>
      <el-button type="danger" plain @click="delBatch">批量删除</el-button>
      <el-button type="info" plain @click="exportData">批量导出</el-button>
      <el-upload action="http://localhost:9090/user/import "
                 :show-file-list="false"
                 :on-success="handlerImport"
                 :headers="{token: user.token}" style="display: inline-block;margin-left: 10px">
        <el-button type="primary" plain>批量导入</el-button>
      </el-upload>
    </div>
    <el-table :data="tableData" stripe :header-cell-style="{backgroundColor: 'aliceblue', color: '#666'}"
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="序号" width="50px"></el-table-column>
      <el-table-column prop="username" label="用户名" ></el-table-column>
      <el-table-column prop="name" label="姓名" width="50px"></el-table-column>
      <el-table-column prop="phone" label="电话"></el-table-column>
      <el-table-column prop="address" label="地址"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column  label="头像">
        <template v-slot="scope">
          <el-image style="width: 50px;height: 50px;border-radius: 50px"
                    v-if="scope.row.avatar" :src="scope.row.avatar" :preview-src-list="[scope.row.avatar]"></el-image>
        </template>
      </el-table-column>
      <el-table-column prop="role" label="身份" width="80px"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button type="primary" plain @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" plain @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="margin: 10px 0">
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[100, 200, 300, 400]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="收货地址" :visible.sync="fromVisible">
    <el-form :model="form" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
      <div style="margin: 15px; text-align: center">

      </div>
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
      <el-form-item label="身份" prop="role">
        <el-radio-group v-model="form.role">
          <el-radio label="管理员"></el-radio>
          <el-radio label="商家"></el-radio>
          <el-radio label="用户"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="头像">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/file/upload"
            :headers="{ token: user.token }"
            :file-list=" form.avatar ? [form.avatar] : []"
            list-type="picture"
            :on-success="handleAvatarSuccess"
        >
          <el-button type="primary"> 上传头像</el-button>
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


export default ({
  name: "User",
  data(){
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 3,
      username: '',
      name: '',
      total: 0,
      fromVisible: false,
      form: {},
      user: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      rules: {
        username: [
          { required: true ,message: '请输入用户名',trigger: 'blur'}
        ],
        name: [
          { required: true ,message: '请输入姓名',trigger: 'blur'}
        ],
        phone: [
          { required: true ,message: '请输入电话',trigger: 'blur'}
        ],
        address: [
          { required: true ,message: '请输入地址',trigger: 'blur'}
        ]
      },
      ids: [],
    }
  },
  created() {
    this.load()
  },
  methods:{
    handlerImport(res ,file ,fileList){
      if (res.code === '200'){
        this.$message.success('操作成功')
        this.load(1)
      }else {
        this.$message.error(res.msg)
      }
    },
    exportData(){ //批量导出
      if(!this.ids.length){ //没选择行时，全部导出，或者根据我的搜索条件导出
        window.open('http://localhost:9090/user/export?token=' + this.user.token +
            "&username=" + this.username +"&name="+this.name) //要把条件传进去
      }else {
        let adStr = this.ids.join(',')
        window.open('http://localhost:9090/user/export?token='+ this.user.token +'&ids=' + adStr ) //要把条件传进去
      }
    },
    delBatch(){
      if(!this.ids.length){
        this.$message.warning('请选择数据')
        return
      }
      this.$confirm('你确认批量删除这些数据吗', '确认删除', {type: "warning"}).then(response =>{
        this.$request.delete('/user/delete/batch' , {data: this.ids}).then(res =>{
          if (res.code === '200'){
            this.$message.success('操作成功')
            this.load(1)
          }else {
            this.$message.error(res.msg)
          }
        })
        this.$message.success("删除成功")
      }).catch(()=> {})
    },
    handleSelectionChange(rows){
      this.ids = rows.map(v => v.id)
    },
    del(id){
      this.$confirm('你确认删除吗', '确认删除', {type: "warning"}).then(response =>{
        this.$request.delete('/user/delete/' + id).then(res =>{
          if (res.code === '200'){
            this.$message.success('操作成功')
            this.load(1)
          }else {
            this.$message.error(res.msg)
          }
        })
        this.$message.success("删除成功")
      }).catch(()=> {})
    },
    handleEdit(row){
      this.form = JSON.parse(JSON.stringify(row)) //给form对象赋值，是深拷贝
      this.fromVisible = true //打开弹窗
    },
    handleAdd(){
      this.form = { role : '用户'}
      this.fromVisible = true
    },
    save(){
      this.$refs.formRef.validate((valid) => {
        if(valid){
          this.$request({
            url: this.form.id ? '/user/update' : '/user/add',
            method : this.form.id ? 'PUT' : 'POST',
            data: this.form
          }).then(res => {
            if (res.code === '200'){
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
    reset(){
      this.name= ''
      this.username= ''
      this.load()
    },
    load(pageNum){  //分页查询
      if (pageNum){
        this.pageNum =pageNum
      }
      this.$request.get('/user/selectPage',{
        params:{
          pageNum: this.pageNum ,
          pageSize: this.pageSize,
          username: this.username,
          name: this.name
        }
      }).then(res =>{
        this.tableData = res.data.records
        this.total = res.data.total
        console.log(res.data)
      })

    },
    handleCurrentChange(pageNum){
      this.load(pageNum)
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.form.avatar = response.data
    },
  }
})
</script>
<style scoped>

</style>