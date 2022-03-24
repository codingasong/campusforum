<template>
<!--  导航栏-->
  <el-affix :offset="40">

    <el-menu
        :default-active="path"
        @open="handleOpen"
        @close="handleClose"
        class="el-menu-vertical-demo"
        background-color="#fff"
        active-text-color="#ffd04b"
        router

    >
      <el-sub-menu index="1" v-if="user.role === 1">
        <template #title>
          <i class="el-icon-setting"></i>
          <span>系统管理</span>
        </template>
        <el-menu-item index="/a/user">
          <i class="el-icon-user-solid"></i>
          <template #title>用户信息</template>
        </el-menu-item>
        <el-menu-item index="/a/major">
          <i class="el-icon-user-solid"></i>
          <template #title>专业信息</template>
        </el-menu-item>
      </el-sub-menu>

      <el-menu-item index="/a/goods">
        <i class="el-icon-s-goods"></i>
        <template #title>二手信息管理</template>
      </el-menu-item>
      <el-menu-item index="/a/posts">
        <i class="el-icon-s-comment"></i>
        <template #title>树洞信息管理</template>
      </el-menu-item>
      <el-menu-item index="/a/job">
        <i class="el-icon-s-cooperation"></i>
        <template #title>兼职信息管理</template>
      </el-menu-item>
    </el-menu>

  </el-affix>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AsideFront",
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
