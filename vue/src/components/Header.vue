<template>
  <!--  Header 头部-->
  <!--  整行布局-->
  <el-row>
    <!--    第一列-->
    <el-col :span="6">
      <div style="max-height: 60px; font-weight: bold; color: cornflowerblue; overflow: hidden;">沈阳城市建设学院校园综合论坛管理后台
      </div>
    </el-col>
    <!--    第二列-->
    <el-col :span="15"></el-col>
    <!--    第三列-->
    <el-col :span="3">
      <el-dropdown style="min-width: 80px">
        <span class="el-dropdown-link" style="color: steelblue;">
          <el-avatar :size="30" :src="user.avatar" style="position: relative; top: 10px"></el-avatar>
          {{ user.userName }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/a/Person')">个人信息</el-dropdown-item>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-col>
    <!--    第三列结束-->
  </el-row>
  <!--  行布局结束-->
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Header",
  // props: ['user'],
  data() {
    return {
    user: {},
    }
  },
  created() {
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)

    // request.get("/user/" + this.user.userId).then(res => {
    //
    // })
  }
}
</script>

<style scoped>

</style>