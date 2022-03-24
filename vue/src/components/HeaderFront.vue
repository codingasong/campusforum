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
      background-color="#667c99"
      text-color="#bdc6d2"
      active-text-color="#ffd04b"
  >
    <el-menu-item index="/a/Person">
      <el-avatar :size="50" :src="user.avatar" alt="user.name" style="position: relative; "></el-avatar>
      {{ user.userName }}
    </el-menu-item>

    <el-menu-item index="/a/goods">
      <i class="el-icon-s-goods"></i>
      <template #title>二手信息</template>
    </el-menu-item>
    <el-menu-item index="/a/posts">
      <i class="el-icon-s-comment"></i>
      <template #title>树洞信息</template>
    </el-menu-item>
    <el-menu-item index="/a/job">
      <i class="el-icon-s-cooperation"></i>
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
