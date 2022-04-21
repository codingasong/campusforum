<template>

  <div>
    {{goodsId}}
  </div>

</template>

<script>
export default {
  name: "Detail",
  data(){
    return{
      goodsId: this.$route.query.goodsId
    }
  }
}
</script>

<style scoped>

</style>
