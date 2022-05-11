<template>
<div>
  <div class="detail">
    <el-page-header content="详情" @back="goBack" />
    <el-card style="margin-top: 10px;">

      <el-row>
        <el-col :span="6">

          <div style="background-color: #f6ffff;text-align: center;height:100%;padding: 20px;">
            <el-avatar
                :src=this.jobVO.avatar
                :size=100
            />
            <p style="margin: 40px 0;">{{this.jobVO.author}}</p>
            <p v-html="jobVO.email" style="font-size: 12px;margin-bottom: 20px;" ></p>
            <p v-html="jobVO.jobPubdate" style="font-size: 12px;"></p>
          </div>



        </el-col>
        <el-col :span="18">
          <div style="padding: 20px;">
            <h1 v-html="jobVO.jobName"></h1><br/>

            <el-tag>兼职类型：</el-tag>&nbsp;&nbsp;{{this.jobVO.jobType}}<br><br>
            <el-tag>兼职金额：</el-tag>&nbsp;&nbsp;{{this.jobVO.jobPrice}}<br><br>
            <el-tag>兼职详情：</el-tag>&nbsp;&nbsp;{{this.jobVO.jobDetail}}
<!--            <span>兼职类型：</span><el-tag>{{this.jobVO.jobType}}</el-tag><br><br>-->
<!--            <span>兼职金额：</span><el-tag>{{this.jobVO.jobPrice}}</el-tag><br><br>-->
<!--            <span>兼职详情：</span><el-tag>{{this.jobVO.jobDetail}}</el-tag>-->
          </div>

        </el-col>
      </el-row>

      <el-divider />

      <el-row>

        <el-col :span="20"><h1>&nbsp;评论区</h1></el-col>
        <el-col :span="4"><el-button type="primary" @click="add">发表评论</el-button></el-col>
      </el-row>
      <br>


      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="expand">
          <template #default="props">
            <div m="4">
              <p m="t-0 b-2">专业: {{ props.row.major }}</p>
              <p m="t-0 b-2">性别: {{ props.row.gender }}</p>
              <p m="t-0 b-2">年龄: {{ props.row.age }}</p>
              <p m="t-0 b-2">联系方式: {{ props.row.email }}</p>
              <p m="t-0 b-2">评论时间: {{ props.row.pubdate }}</p>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="用户" prop="userName" />
        <el-table-column label="评论内容" >
          <template #default="props">
            <div v-html="props.row.content"></div>
          </template>
        </el-table-column>
      </el-table>


    </el-card>


    <!--    新增的弹窗-->
    <el-dialog title="发帖" v-model="dialogVisible" width="70%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="">
          <h1>请在下方输入评论</h1>
        </el-form-item>
        <el-form-item label="内容">
          <div id="div1"></div>
        </el-form-item>
      </el-form>
      <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </span>
      </template>
    </el-dialog>


  </div>


</div>
</template>

<script>
import request from "@/utils/request";
import E from "wangeditor";

let editor;

export default {
  name: "JobDetail",
  data() {
    return {
      jobId: this.$route.query.jobId,
      jobVO: {},
      discussVO: {},
      tableData: [],
      form: {},
      dialogVisible: false,

    }
  },
  methods: {
    init() {
      this.get()
      this.getDiscuss()
    },
    get(){
      request.get("/job/getVO/"+this.jobId).then(res=> {
        this.jobVO = res.data
        console.log(res.data);

      })
    },
    getDiscuss() {
      request.get("/discuss/getVO/"+"j," + this.jobId).then(res=> {
        this.tableData = res.data
        console.log(this.tableData);
        console.log(res.data);

      })

    },
    goBack(){
      window.location.href = "/JobFront";
    },

    //新增按钮的方法
    add() {
      this.dialogVisible = true;
      this.form = {};
      // 当编辑器存在时，清空编辑器
      if(editor){
        editor.txt.clear()
      }
      // 如果编辑器不存在，则创建编辑器
      this.$nextTick(() => {
        if(!editor){
          editor = new E('#div1')
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:8888/files/editor/upload'
          editor.config.uploadFileName = "file" //设置上传图片名称
          editor.create()
        }
      })
    },

    //保存到数据库
    save() {
      this.form.content = editor.txt.html(); //获取编辑器里的内容，赋值到实体中
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)
      if(this.form.userName == null){
        this.form.userName = user.userName //赋值用户名
      }
      this.form.belong = "j," + this.jobId
      request.post("/discuss", this.form).then(res => {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "新增成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        this.init();
        this.dialogVisible = false;
      })
    },
  },
  created() {
    this.init()
  },
}
</script>

<style scoped>

</style>
