<template>
<el-row style="border: 1px solid red">
  <el-col :span="8" style="border: 1px solid red ">
    <el-card style="height: 600px;">




      <el-row>

        <el-col>
          <el-card :body-style="{ padding: '10px' }">
            <img
                :src=goodsImg[this.currentGood]
                class="image"
            />
            <div style="padding: 20px">
              <span>名称：{{ this.goodsName[this.currentGood] }}<br></span>
              <span>价钱：{{this.goodsPrice[this.currentGood]}}<br></span>
              <span>详情：{{this.goodsDetail[this.currentGood]}}<br></span>

            </div>
            <el-button-group>
              <el-button type="primary" :icon="ArrowLeft" @click="goodsLeft">上一个宝贝</el-button>
              <el-button type="primary" :icon="ArrowRight" @click="goodsChange">下一个宝贝</el-button>
            </el-button-group>
          </el-card>
        </el-col>
      </el-row>


    </el-card>
  </el-col>
  <el-col :span="8">
    <el-card style="height: 600px;">
2
    </el-card>
  </el-col>
  <el-col :span="8">
    <el-card style="height: 600px;">
3
    </el-card>
  </el-col>
</el-row>
<el-row >
  <el-card style="height: 290px;width: 100%;">

  </el-card>
</el-row>


</template>

<script>
import request from "@/utils/request";

export default {
  name: "Index",
  created() {
    this.load();
  },

  data(){
    return{
      goodsData:[],
      currentGood: 0,
      goodsName: [],
      goodsPrice: [],
      goodsDetail: [],
      goodsImg: [],
      total: 0

    }

  },
 methods:{
   load(){
     request.get("/goods", {
           params: {
             pageSize: this.pageSize,
             pageNum: this.currentPage,
             search: this.search,

           }
         }
     ).then(res => {
       console.log(res);
       this.goodsData = res.data.records
       this.total = res.data.total
       for (let i = 0; i < this.goodsData.length; i++) {
         this.goodsName[i] =  this.goodsData[i].goodsName
         this.goodsImg[i] = this.goodsData[i].goodsImg
         this.goodsPrice[i] = this.goodsData[i].goodsPrice
         this.goodsDetail[i] = this.goodsData[i].goodsDetail

         console.log(this.goodsImg[i]);
       }
     })


   },
   goodsLeft(){
     if(this.currentGood === 0){
       this.currentGood = this.total - 1
     }else {
       this.currentGood = this.currentGood - 1
     }
   },

 },


}
</script>
<style>
body{
  background-color: #edf2f6;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.text {
  font-size: 14px;
}
.image{
  height: 300px;
  text-align: center;
  margin: 0 auto;
}
</style>
<style scoped>

</style>
