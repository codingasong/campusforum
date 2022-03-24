<template>
  <el-row style="margin-top: 20px">
    <el-col :span="16">
      <el-calendar v-model="value">
      </el-calendar>
      <!--      <div class="infinite-list-wrapper" style="overflow:auto">-->
      <!--        <ul-->
      <!--            class="list"-->
      <!--            v-infinite-scroll="load"-->
      <!--            infinite-scroll-disabled="disabled">-->
      <!--          <li v-for="i in count" class="list-item">-->
      <!--            <span>{{ i.username }}</span>-->
      <!--            <span>{{ i.createTime }}</span>-->
      <!--          </li>-->
      <!--        </ul>-->
      <!--        <p v-if="loading">加载中...</p>-->
      <!--        <p v-if="noMore">没有更多了</p>-->
      <!--      </div>-->


    </el-col>
    <el-col :span="8"><div class="grid-content bg-purple-dark"></div></el-col>



    <div style="position: fixed;
  top: 40px;
  right: 100px;
width: 360px;
height: 700px;">
      <el-calendar v-model="value"></el-calendar>
    </div>
  </el-row>
</template>

<script>
export default {
  name: "beiyong"
}
</script>

<style scoped>

</style>