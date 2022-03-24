<template>
  <el-container>
    <!--    头部-->
    <el-header style="line-height: 60px; border-bottom: 1px solid #cccccc; ">
      <Header :user="user"/>
    </el-header>
    <!--    主体-->
    <el-container>
      <!--      侧边栏-->
      <el-aside style="width: 15%;">
        <Aside/>
      </el-aside>
      <!--      内容-->
      <el-main>
        <router-view style="flex: 1" />
<!--        @userInfo="refreshUser"-->
      </el-main>
    </el-container>
  </el-container>
</template>


<script>
import Header from "@/components/Header";
import Aside from "@/components/Aside";
import request from "@/utils/request";
export default {
  name: "Layout",
  components: {
    Header,
    Aside
  },
  data() {
    return {
      user: {}
    }
  },
  // created() {
  //   this.refreshUser()
  // },
  // methods: {
  //   refreshUser() {
  //     let userJson = sessionStorage.getItem("user");
  //     if (!userJson) {
  //       return
  //     }
  //     let userId = JSON.parse(userJson).userId
  //     request.get("/user/" + userId).then(res => {
  //       this.user = res.data
  //     })
  //   }
  // }

}
</script>

<style scoped>

</style>
