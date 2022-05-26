<template>
  <!--  导航栏-->
  <el-menu
      :default-active="path"
      @open="handleOpen"
      @close="handleClose"
      style=" width: 100%; "
      class="el-menu-demo"
      router
      mode="horizontal"
      background-color="#409EFF"
      text-color="#fff"
      active-text-color="#ffd04b"
  >
    <el-menu-item>
      <span style="font-size: 20px; color:white">沈阳城市建设学院校园论坛</span>
    </el-menu-item>
    <el-menu-item index="/a/goods">
      <el-dropdown style="min-width: 80px">
        <span class="el-dropdown-link" style="color: #ffffff;">
          <el-avatar :size="40" :src="user.avatar" alt="user.name" style="position: relative; "></el-avatar>
          {{ user.userName }}
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="$router.push('/login')">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </el-menu-item>
    <el-menu-item index="/index">
      <i class="el-icon-s-goods" style="color:#fff;"></i>
      <template #title>二手信息</template>
    </el-menu-item>
    <el-menu-item index="/postFront">
      <i class="el-icon-s-comment" style="color:#fff;"></i>
      <template #title>树洞信息</template>
    </el-menu-item>
    <el-menu-item index="/jobFront">
      <i class="el-icon-s-cooperation" style="color:#fff;"></i>
      <template #title>兼职信息</template>
    </el-menu-item>

  </el-menu>

</template>

<script>
import request from "@/utils/request";

export default {
  name: "HeaderFront",
  data() {
    return {
      // 默认高亮菜单
      path: this.$route.path,
      user: {},
    }
  },
  created() {
    // console.log(this.$route.path);
    let userStr = sessionStorage.getItem("user") || "{}"
    this.user = JSON.parse(userStr)

    request.get("/user/" + this.user.userId).then(res => {
      if(res.code === '0'){
        this.user = res.data
      }
    })
  },
  methods : {
    handleOpen : {

    },
    handleClose : {

    }
  }
}
</script>

<style scoped>

</style>
