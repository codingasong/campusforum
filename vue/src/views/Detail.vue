<template>

  <div id="main">
    <el-page-header content="详情" @back="goBack" />
    <el-card class="box-card" style="padding: 20px; margin-top: 20px;">
<!--      <div id="img" style="margin: 0 auto; text-align: center;">-->
<!--        <img :src="goodsVO.goodsImg" style="width: 300px;">-->
<!--      </div>-->
<!--      <ul>-->
<!--        <li>{{this.goodsVO.goodsName}}</li>-->
<!--        <li>{{this.goodsVO.goodsDetail}}</li>-->
<!--        <li>{{this.goodsVO.goodsPrice}}</li>-->
<!--        <li></li>-->
<!--        <li></li>-->
<!--      </ul>-->
      <div id="img" style="margin: 0 auto; text-align: center;">
      <el-image
          :src="this.goodsVO.goodsImg"
          :preview-src-list="this.goodsVO.goodsImgs"
          style="margin:0 auto;text-align: center;width: 300px;"
      >
      </el-image>
      </div>


      <div id="details">
        <el-descriptions
            class="margin-top"
            :column="1"
            border
        >

          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                名称
              </div>
            </template>
            {{this.goodsVO.goodsName}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                价格
              </div>
            </template>
            <el-tag size="small">{{this.goodsVO.goodsPrice}}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                简介
              </div>
            </template>
            {{this.goodsVO.goodsDetail}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                分类
              </div>
            </template>
            {{this.goodsVO.goodsType}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                发布时间
              </div>
            </template>
            {{this.goodsVO.goodsPubdate}}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                发布人
              </div>
            </template>
            {{ this.goodsVO.author }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template #label>
              <div class="cell-item">
                联系方式
              </div>
            </template>
            {{ this.goodsVO.email }}
          </el-descriptions-item>

        </el-descriptions>
      </div>




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





    </el-card>
  </div>

</template>

<script >
import request from "../utils/request";
import E from "wangeditor";
let editor;
export default {
  name: "Detail",
  data(){
    return{
      goodsId: this.$route.query.goodsId,
      goodsVO: {},
      discussVO: {},
      tableData: [],
      form: {},
      dialogVisible: false,
    }
  },
  methods:{
    init(){
      this.getGoods()
      this.getDiscuss()

    },
    getGoods() {
      request.get("/goods/getVO/"+ this.goodsId).then(res=> {
        this.goodsVO = res.data
      })
    },
    getDiscuss() {
      request.get("/discuss/getVO/"+"g," + this.goodsId).then(res=> {
        this.tableData = res.data
        console.log(this.tableData);
        console.log(res.data);
      })
    },
    goBack(){
      window.location.href = "/";
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
      this.form.belong = "g,"+this.goodsId

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
  }
}

</script>

<style scoped>
</style>
