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
      <el-table-column prop="userId" label="ID" width="40%"></el-table-column>
      <el-table-column prop="userName" label="姓名"></el-table-column>
      <el-table-column prop="gender" label="性别"></el-table-column>
      <el-table-column prop="age" label="年龄"></el-table-column>
      <el-table-column prop="major" label="专业"></el-table-column>
      <el-table-column prop="email" label="联系方式"></el-table-column>
      <el-table-column label="用户类型">
        <template #default="scope">
          <span v-if="scope.row.role === 1">管理员</span>
          <span v-if="scope.row.role === 2">普通用户</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" >
        <template #default="scope">
          <el-button size="mini" @click="handleEdit(scope.row)"
          >编辑
          </el-button
          >
          <el-popconfirm title="确定删除吗？" @confirm="handleDelete(scope.row.userId)">
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
        title="操作"
        v-model="dialogVisible"
        width="30%"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="用户名">
          <el-input v-model="form.userName" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio v-model="form.gender" label="男">男</el-radio>
          <el-radio v-model="form.gender" label="女">女</el-radio>
          <el-radio v-model="form.gender" label="未知">未知</el-radio>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="form.age" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="专业">
                    <el-select v-model="value" placeholder="请选择你的专业" @focus="getmajor">
                      <el-option
                          v-for="item in options"
                          :key="item.majorId"
                          :label="item.majorName"
                          :value="item.majorName"
                      >
                      </el-option>
                    </el-select>
        </el-form-item>
        <el-form-item label="联系方式">
          <el-input v-model="form.email" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" v-if="this.userRole === 1">
          <el-select v-model="valueRole" placeholder="请选择用户类型">
            <el-option
                v-for="item in optionsRole"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            >
            </el-option>
          </el-select>
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


import {ref, defineComponent } from "vue";
import request from "@/utils/request";
let userStr = sessionStorage.getItem("user") || "{}"
let user = JSON.parse(userStr)
export default {
  name: 'User',
  setup() {
    return {
      search: ref(''),
      options: ref(''),
      value: ref('')
    }
  },
  components: {},
  // 页面加载时
  created() {

    this.load();
  },
  methods: {
    handleEdit(row) {
      if(row.role === 1){
        this.valueRole = "管理员"
      }else{
        this.valueRole = "普通用户"
      }
      // 对数据深拷贝
      this.form = JSON.parse(JSON.stringify(row))

      // this.valueRole = row.role
      this.dialogVisible = true

      // 修改完信息，重新存储到session
      request.get("/user/" + JSON.parse(sessionStorage.getItem("user") || "{}").userId).then(res => {
        this.value = res.data.major
        sessionStorage.setItem("user",JSON.stringify(res.data))
      })


    },
    handleDelete(userId) {
      console.log(userId);
      request.delete("/user/" + userId).then(res => {
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

    // 获取专业列表
    getmajor() {
      request.get("/major",{
        params: {
          pageSize: 50,
        }
      }).then(res=> {
        // 把专业信息赋值给select
        this.options = res.data.records
      })
    },

    //保存到数据库
    save() {
      // form的专业和用户类型值
      this.form.major = this.value
      this.form.role = this.valueRole

      request.post("/user", this.form).then(res => {
        console.log(res);
        if (this.form.userId) {
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
      request.get("/user", {
            params: {
              pageSize: this.pageSize,
              pageNum: this.currentPage,
              search: this.search
            }
          }
      ).then(res => {
        //console.log(res);
        this.tableData = res.data.records
        this.total = res.data.total
        this.userRole = user.role
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
      userRole: 2,

      optionsRole: ref([
        {
          value: '1',
          label: '管理员',
        },
        {
          value: '2',
          label: '普通用户',
        },


      ]),
      valueRole: ref(''),
      //搜索
      //search : '',
      // 新增下拉列表数据项
      //options: [],
      // message : this.form.userId ? "更新成功":"新增成功"
    }
  }


}
</script>


<style>


</style>
