<template>
  <!--  主内容-->
  <div style="padding: 10px;">
    <!--    菜单项-->
    <el-row style="margin-bottom: 10px">
      <!--      操作按钮-->
      <el-col :span="12">
        <el-button type="primary" @click="add">新增</el-button>
      </el-col>
      <!--      搜索-->
      <!--      居右显示 text-align: end-->
      <el-col :span="12" style="text-align: end">
        <!--        可以清空输入内容 clearable-->
        <el-input
            v-model="search"
            placeholder="请输入内容"
            prefix-icon="el-icon-search"

            style="width: 130px; margin-right: 10px;"
            clearable
        >
          <!--            v-model="input1"-->
        </el-input>
        <el-button type="primary" @click="load">查询</el-button>
      </el-col>
    </el-row>
    <!--    斑马纹 stripe-->
    <el-table :data="tableData" border stripe style="width: 100%">
      <el-table-column prop="postsId" label="ID" width="40%"></el-table-column>
      <el-table-column prop="postsTitle" label="标题"></el-table-column>
      <el-table-column prop="postsPubdate" label="时间"></el-table-column>
      <el-table-column prop="author" label="发布人"></el-table-column>
      <el-table-column prop="postsDetail" label="详情" width="80%">
        <template #default="scope">
          <el-button size="mini" @click="xiangQing(scope.row)">详情</el-button>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200%">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.postsId)">
            <template #reference>
              <el-button size="mini" type="danger">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--    分页-->
    <el-row style="margin-top: 10px;">
      <el-col>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[5, 10, 20,]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
           :total="total"
            background
        >
        </el-pagination>
      </el-col>
    </el-row>

    <!--    新增的弹窗-->
    <el-dialog title="发帖" v-model="dialogVisible" width="70%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="标题">
          <el-input v-model="form.postsTitle" style="width: 40%"></el-input>
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
<!--详情弹窗-->
    <el-dialog title="详情" v-model="postsDetailDialog" width="70%">
      <el-card>
<!--        <div v-text="postsXq.postsTitle" style="font-weight: bold; display: flex"></div>-->
        <span style="font-size: large">标题：</span>
        <span v-text="postsXq.postsTitle" style="font-weight: bold; font-size: large"></span>
      </el-card>
      <el-card style="min-height: 300px">
        <div v-html="postsXq.postsDetail" style="image:100px 100px;"></div>
      </el-card>
    </el-dialog>
  </div>


</template>

<script>


import {ref} from "vue";
import request from "@/utils/request";
import E from 'wangeditor'

let editor;

let userStr = sessionStorage.getItem("user") || "{}"
let user = JSON.parse(userStr)
let userName = user.userName
let role = user.role
let author = user.userName
export default {
  name: 'Posts',
  setup() {
    return {
      search: ref(''),
    }
  },
  components: {},
  // 页面加载时
  created() {
    this.load();
    // this.$nextTick(() => {
    //   editor = new E('#div1')
    //   editor.create()
    // })
  },
  methods: {
    handleEdit(row) {
      // 对数据深拷贝
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true

      // 如果编辑器不存在，则创建编辑器，并把帖子详情赋值给编辑器
      this.$nextTick(() => {
        if(!editor){
          editor = new E('#div1')
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:8888/files/editor/upload'
          editor.config.uploadFileName = "file" //设置上传图片名称
          editor.create()
        }
        editor.txt.html(row.postsDetail)
      })
      // 当编辑器存在时，清空编辑器
      if(editor){
        editor.txt.clear()
      }

    },
    handleDelete(postsId) {
      console.log(postsId);
      request.delete("/posts/" + postsId).then(res => {
        if (res.code === '0') {
          this.$message({
            type: "success",
            message: "删除成功"
          })
        } else {
          this.$message({
            type: "error",
            message: res.msg
          })
        }
        this.load()
      })
    },
    // pageSize 改变时会触发
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    // 改currentPage 改变时会触发
    handleCurrentChange(pageNum) {
      this.currentPage = pageNum
      this.load();
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
      this.form.postsDetail = editor.txt.html(); //获取编辑器里的内容，赋值到实体中
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)
      if(this.form.author == null){
        this.form.author = user.userName //赋值用户名
      }
      request.post("/posts", this.form).then(res => {
        console.log(res);
        if (this.form.postsId) {
          if (res.code === '0') {
            this.$message({
              type: "success",
              message: "更新成功"
            })
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        } else {
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
        }
        this.load();
        this.dialogVisible = false;
      })
    },
    // 页面加载时加载列表展示数据
    load() {
      if(role != 1){
        request.get("/posts/signal", {
              params: {
                pageSize: this.pageSize,
                pageNum: this.currentPage,
                search: this.search,
                author: author
              }
            }
        ).then(res => {
          console.log(res);
          this.tableData = res.data.records
          this.total = res.data.total
        })
      }else {
        request.get("/posts", {
              params: {
                pageSize: this.pageSize,
                pageNum: this.currentPage,
                search: this.search,
                author: author
              }
            }
        ).then(res => {
          console.log(res);
          this.tableData = res.data.records
          this.total = res.data.total
        })
      }

    },
    xiangQing(row) {
      this.postsXq = row
      this.postsDetailDialog = true
    }
  },
  data() {
    return {
      form: {},
      // 新增弹出框
      dialogVisible: false,
      // 当前页
      currentPage: 1,
      // 当前页有几条数据
      pageSize: 10,
      // 总条数
      total: 0,
      // 表格数据项
      tableData: [],
      //搜索
      //search : '',
      // 新增下拉列表数据项
      options: [],
      // user: {}
      postsXq: {},
      postsDetailDialog: false
    }
  }


}
</script>


<style>


</style>