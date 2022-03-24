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
      <el-table-column prop="majorId" label="ID"></el-table-column>
      <el-table-column prop="majorName" label="专业"></el-table-column>


      <el-table-column label="操作" width="200%">
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)"
          >编辑
          </el-button
          >
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.majorId)">
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

    <!--    新增用户的弹窗-->
    <el-dialog
        title="提示"
        v-model="dialogVisible"
        width="30%"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="专业名称">
          <el-input v-model="form.majorName" style="width: 70%"></el-input>
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

<script>


import {ref} from "vue";
import request from "@/utils/request";

export default {
  name: 'Major',
  setup() {
    return {
      search: ref(''),
    }
  },
  components: {},
  // 页面加载时
  created() {
    this.load();
  },
  methods: {
    handleEdit(row) {
      // 对数据深拷贝
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true
    },
    handleDelete(majorId) {
      console.log(majorId);
      request.delete("/major/" + majorId).then(res => {
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
    },
    //保存到数据库
    save() {
      request.post("/major", this.form).then(res => {
        console.log(res);
        if (this.form.majorId) {
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
      request.get("/major", {
            params: {
              pageSize: this.pageSize,
              pageNum: this.currentPage,
              search: this.search
            }
          }
      ).then(res => {
        console.log(res);
        this.tableData = res.data.records
        this.total = res.data.total
      })
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
      // message : this.form.userId ? "更新成功":"新增成功"
    }
  }


}
</script>


<style>


</style>