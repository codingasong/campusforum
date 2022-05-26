<template>
  <!--  主内容-->
  <div style="padding: 10px;">
    <!--    菜单项-->
    <el-row style="margin-bottom: 10px">
      <!--      操作按钮-->
      <el-col :span="12">
        <el-button type="primary" @click="addUser">新增</el-button>
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
      <el-table-column prop="goodsId" label="ID" width="40%"></el-table-column>
      <el-table-column prop="goodsName" label="商品名"></el-table-column>
      <el-table-column prop="goodsDetail" label="商品详情"></el-table-column>
      <el-table-column label="商品图片">
        <template #default="scope">
          <el-image
              :src="scope.row.goodsImg"
              :preview-src-list="[scope.row.goodsImg]"
          >
        </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="goodsType" label="商品类型"></el-table-column>
      <el-table-column prop="goodsPrice" label="商品价格"></el-table-column>
      <el-table-column prop="goodsPubdate" label="发布时间"></el-table-column>
      <el-table-column prop="author" label="发布人"></el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)"
          >编辑
          </el-button
          >
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.goodsId)">
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
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="商品名">
          <el-input v-model="form.goodsName" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="商品详情">
          <el-input v-model="form.goodsDetail" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="商品类型">
            <el-select v-model="value" placeholder="请选择商品类型">
              <el-option
                  v-for="item in options"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              >
              </el-option>
            </el-select>
        </el-form-item>


        <el-form-item label="商品价格">
          <el-input v-model="form.goodsPrice" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="商品图片">
          <el-upload action="http://localhost:8888/files/upload" :on-success="fileUploadSuccess"  list-type="picture" ref="uploadImg">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
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


</template>

<script lang="ts">


import {defineComponent, nextTick, ref} from "vue";
import request from "../utils/request"

let userStr = sessionStorage.getItem("user") || "{}"
let user = JSON.parse(userStr)
let userName = user.userName
let role = user.role
let author = user.userName
export default {
  name: 'Goods',
  setup() {
    // const url = ref(
    //     'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
    // )
    return {
      search: ref(''),
      // url,
      options: ref([
        {
          value: '书籍',
          label: '书籍',
        },
        {
          value: '日用品',
          label: '日用品',
        },
        {
          value: '食品',
          label: '食品',
        },
        {
          value: '护肤品',
          label: '护肤品',
        },
        {
          value: '电子设备',
          label: '电子设备',
        },
        {
          value: '其他',
          label: '其他',
        },

      ]),
      value: ref('')
    }
  },

  components: {},
  // 页面加载时
  created() {
    this.load();
  },
  methods: {
    // 上传图片
    fileUploadSuccess(res) {
      this.form.goodsImg = res.data
    },
    handleEdit(row) {
      // 对数据深拷贝
      this.form = JSON.parse(JSON.stringify(row))
      this.value = row.goodsType
      this.dialogVisible = true

      // 清空图片上传列表
      nextTick(()=> {
        this.$refs.uploadImg.clearFiles()
      })
    },
    handleDelete(goodsId) {
      console.log(goodsId);
      request.delete("/goods/" + goodsId).then(res => {
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
    addUser() {
      this.dialogVisible = true;
      this.form = {};
      // 清空图片上传列表
      nextTick(()=> {
        this.$refs.uploadImg.clearFiles()
      })

    },
    //保存到数据库
    save() {

      // console.log(user.userName+ "----------------------------------");
      if(this.form.author == null){
        this.form.author = user.userName //赋值用户名
        // console.log(this.form.author +"--------------------------------------------------");
      }
      this.form.goodsType = this.value
      request.post("/goods", this.form).then(res => {
        console.log(res);
        if (this.form.goodsId) {
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
      console.log(role+"----------------" + author);
      // let role = 2;
      // request.get("/user/" + JSON.parse(sessionStorage.getItem("user") || "{}").userId).then(res => {
      //   role = res.data.role
      //   sessionStorage.setItem("user",JSON.stringify(res.data))
      // })

      if(role != 1){
        request.get("/goods/signal", {
              params: {
                pageSize: this.pageSize,
                pageNum: this.currentPage,
                search: this.search,
                author: userName
              }
            }
        ).then(res => {
          console.log(res);
          this.tableData = res.data.records
          this.total = res.data.total
        })
      }else {
        request.get("/goods", {
              params: {
                pageSize: this.pageSize,
                pageNum: this.currentPage,
                search: this.search,
                uname: userName
              }
            }
        ).then(res => {
          console.log(res);
          this.tableData = res.data.records
          this.total = res.data.total
        })
      }

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


    }
  }


}
</script>


<style>


</style>
