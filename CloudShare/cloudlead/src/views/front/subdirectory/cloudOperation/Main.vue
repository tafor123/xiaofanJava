<template>
  <div>
    <div style="margin: 10px">
      <el-button class="el-icon-upload" type="primary" @click="handleUpdate">上传文件</el-button>
      <el-button class="el-icon-magic-stick" type="warning" @click="handleAdd">新建文件夹</el-button>
      <el-button type="danger" plain>批量删除</el-button>
      <el-button type="info">批量移动</el-button>
      <el-input prefix-icon="el-icon-search" style="width: 100px;margin: 0 10px"></el-input>
      <el-button>搜索</el-button>
    </div>
    <el-dialog title="上传文件" :visible.sync="fileVisible1" width="30%">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :headers="{ token: user.token }"
          :file-list="fileList"
          :on-success="handleAvatarSuccess">
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
    </el-dialog>
    <el-table
        :data="tableData"
        height="500"
        style="width: 100%"
        @cell-mouse-enter="mouseEnter"
        @cell-mouse-leave="mouseLeave"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="dir" width="60" align="center">
        <template slot-scope="scope">
          <img :src="setFileImg(scope.row)" style="height: 30px"/>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="文件名" :sort-by="['dir', 'name']" sortable show-overflow-tooltip>
        <template slot-scope="scope">
          <div style="cursor:pointer;" @click="clickFileName(scope.row)">
            {{ scope.row.name}}
          </div>
        </template>
      </el-table-column>

<!--      <el-table-column  prop="name" label="文件名" width="300"></el-table-column>-->
      <el-table-column>
        <template slot-scope="scope">
          <el-button
              type="text"
              size="small"
              class="el-icon-edit"
              v-show="showEditFileNameButton && rowId === scope.row.__ob__.dep.id"
              @click="handleClick(scope)"
          >编辑</el-button
          >
          <el-button
              type="text"
              size="small"
              class="el-icon-delete"
              v-show="showEditFileNameButton && rowId === scope.row.__ob__.dep.id"
              @click="handleClick(scope)"
          >回收站</el-button
          >
          <el-button
              type="text"
              size="small"
              class="el-icon-share"
              v-show="showEditFileNameButton && rowId === scope.row.__ob__.dep.id"
              @click="handleClick(scope)"
          >分享</el-button
          >
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column prop="size" label="大小" :formatter="formatData" ></el-table-column>
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

    <el-dialog title="数据处理" :visible.sync="fileVisible" width="30%">
      <el-form :model="formFile" label-width="80px" style="padding-right: 20px" :rules="rules" ref="formRef">
        <el-form-item label="父级" prop="filePid">
          <el-input v-model="formFile.filePid" placeholder="文件夹名称"></el-input>
        </el-form-item>
        <el-form-item label="文件名" prop="name">
          <el-input v-model="formFile.name" placeholder="文件夹名称"></el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="fileVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {filenameComplete} from "@/filters";
export default {
  data() {
    var checkFilePid = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('不能为空'));
      }
      setTimeout(() => {
        if (!Number.isInteger(value)) {
          callback(new Error('请输入数字值'));
        }
      }, 1000);
    };


    return {
      showEditFileNameButton: "",
      rowId: "",
      tableData: [],
      pageNum: 1, // 当前的页码
      pageSize: 10, // 每页显示的个数
      name: '',
      filePid: '',
      total: 0,
      fileVisible1: false,
      fileVisible: false,
      fileList: '',
      formFile: {
        filePid: '',
        name: '',
      },
      user: JSON.parse(localStorage.getItem('cloud-user') || '{}'),
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
        ],
        filePid: [
          { validator: checkFilePid, trigger: 'blur' }
        ]
      },
    };
  },
  methods: {
    filenameComplete,
    //鼠标移入单元格事件
    mouseEnter(row) {
      this.showEditFileNameButton = true;
      this.rowId = row.__ob__.dep.id; //赋值行id，便于页面判断
    },
    //鼠标移出单元格事件
    mouseLeave(row) {
      this.showEditFileNameButton = false;
      this.rowId = "";
    },
    //  点击文件名
    clickFileName(row) {
      //  若是目录则进入目录
      if (row.type === null) {
        this.$router.push({
          query: {
            pid: row.id,
            fileType: this.fileType().current()
          }
        })
      }
      //  若是文件，则进行相应的预览
      else {
        //  若当前点击项是图片
        if (this.IMG_TYPE_LIST.includes(row.type)) {
          let data = {
            imgReviewVisible: true,
            imgReviewList: [
              {
                id: row.id,
                fileUrl: this.getDownloadPath(row.id),
                downloadLink: this.getDownloadPath(row.id),
                name: row.name,
                type: row.type
              }
            ],
            activeIndex: 0
          }
          this.$store.commit('setImgReviewData', data)
        }
        //  若当前点击项是可以使用office在线预览的
        if (['ppt', 'pptx', 'doc', 'docx', 'xls', 'xlsx'].includes(row.type)) {
          // window.open(this.getFileOnlineViewPathByOffice(row.id), '_blank')
          window.open(this.getDownloadPath(row.id), '_blank')
        }
        //  若当前点击项是pdf
        if (row.type === 'pdf') {
          window.open(this.getDownloadPath(row.id), '_blank')
        }
        //  若当前点击项是html、js、css、json
        const CODE = ['html', 'js', 'css', 'json']
        if (CODE.includes(row.type)) {
          window.open(this.getDownloadPath(row.id), '_blank')
        }
        //  若当前点击项是视频mp4格式
        if (this.VIDEO_TYPE_LIST.includes(row.type)) {
          let data = {
            visible: true,
            url: this.getDownloadPath(row.id),
            name: row.file + '.' + row.type
          }
          this.$store.commit('setVideoReviewData', data)
        }
        //  若当前点击项是视频mp3格式
        if (this.MUSIC_TYPE_LIST.includes(row.type)) {
          let data = {
            visible: true,
            url: this.getDownloadPath(row.id),
            name: row.file + '.' + row.type
          }
          this.$store.commit('setVideoReviewData', data)
        }
      }
    },

    //  根据文件扩展名设置文件图片
    setFileImg(row) {
      if (!row.type) {
        //  文件夹
        return this.FILE_IMG_MAP.dir
      } else if (!this.FILE_IMG_TYPE_LIST.includes(row.type)) {
        //  无法识别文件类型的文件
        return this.FILE_IMG_MAP.unknown
      } else if (['jpg', 'png', 'jpeg', 'gif'].includes(row.type)) {
        // 图片类型，直接显示缩略图
        console.log(this.tableData.url)
        return row.url
      } else {
        //  可以识别文件类型的文件
        return this.FILE_IMG_MAP[row.type]
      }
    },
    formatData(row, column, cellValue){
      var size = row.size;
      if (!size)
        return "";
      var num = 1024.00
      if (size < num)
        return size + "B";
      if (size < Math.pow(num, 2))
        return (size / num).toFixed(2) + "KB"; //kb
      if (size < Math.pow(num, 3))
        return (size / Math.pow(num, 2)).toFixed(2) + "MB"; //M
      if (size < Math.pow(num, 4))
        return (size / Math.pow(num, 3)).toFixed(2) + "GB"; //G
      return (size / Math.pow(num, 4)).toFixed(2) + "TB"; //T
    },
    handleUpdate(){
      this.fileVisible1 = true
    },
    handleAvatarSuccess(response, file, fileList){
      /*console.log(response,file,fileList)*/
      this.fileList = fileList
      this.load(1)
      /*this.fileVisible1 = false*/

    },
    handleAdd(){
      this.form = {}  // 新增数据的时候清空数据
      this.fileVisible = true
      console.log()
    },
    handleCurrentChange(pageNum){
      this.load(pageNum)
    },
    load(pageNum){
      if(pageNum){
        this.pageNum = pageNum
      }
      this.$request.get('/file/selectByFilePage',{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name
        }
      }).then(res => {
        if(res.code === '200'){
          this.tableData = res.data.list
          this.total =res.data.total
        }
      })
    },
    save(){
      this.$refs.formRef.validate((valid) =>{
        if(valid){
          /*console.log(this.filePid)
          console.log(this.formFile)*/
          this.fileVisible = false
          this.$request.post('/file/newFolder', this.formFile).then(res => {
            console.log(this.formFile)
            if(res.code === '200'){
              this.$message.success('保存成功')
              this.load(1)
              this.fileVisible = false
            }else {
              this.$message.error(res.msg)
            }
          }).catch((error) => {
            console.error('HTTP请求失败：', error);
            this.$message.error('目录创建失败，请重试');
          });
        }
      })
    },

  },
created() {
  this.load()
},

};
</script>