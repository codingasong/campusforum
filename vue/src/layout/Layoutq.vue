<template>
  <el-container>
    <!--    导航栏-->
    <HeaderFront :user="user" style="padding-left: 550px;"/>
    <!--    头部-->
    <el-header></el-header>
    <!--    主体-->
    <el-container>
      <!--      侧边栏-->
<!--      <el-aside>-->
<!--      </el-aside>-->
      <!--      主体-->
      <el-main>
        <el-row>
          <el-col :span="7" style="border: 1px solid red">
          </el-col>
          <el-col :span="10" style="border: 1px solid red">
            <router-view style="flex: 1" />
          </el-col>
          <el-col :span="7" style="border: 1px solid red">
          </el-col>
        </el-row>

      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import AsideFront from "@/components/AsideFront";
import HeaderFront from "@/components/HeaderFront";

export default {
  name: "Layoutq",
  components: {
    HeaderFront,
    AsideFront,
  },
  data() {
    return {
      user: {}
    }
  },
}
</script>

<style scoped>

</style>
