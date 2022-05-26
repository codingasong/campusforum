<template>

  <div class="bkimg">
    <el-button @click="add" type="primary" size="large" >发布商品</el-button>
    <el-row>
      <el-col>
        <div v-infinite-scroll="load"  class="infinite-list" style="overflow:auto; scrollbar-width:none;">
          <el-card v-for="i in list" :key="i"  infinite-scroll-disabled="disabled" style="margin: 10px 10px">
            <el-row>
              <el-col :span="8">
                <el-card>
                  <template #default="scope">
                    <el-image
                        :src="i.goodsImg"
                        :preview-src-list="[i.goodsImg]"
                    >
                    </el-image>
                  </template>
                </el-card>
              </el-col>
              <el-col :span="16">
                <div id="details">
                  <el-descriptions
                      class="margin-top"
                      :column="1"
                      border
                  >

                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          名称
                        </div>
                      </template>
                      {{i.goodsName}}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          价格
                        </div>
                      </template>
                      <el-tag size="small">{{i.goodsPrice}}</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          简介
                        </div>
                      </template>
                      {{i.goodsDetail}}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          分类
                        </div>
                      </template>
                      {{i.goodsType}}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          发布时间
                        </div>
                      </template>
                      {{i.goodsPubdate}}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          发布人
                        </div>
                      </template>
                      {{ i.author }}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          详情
                        </div>
                      </template>
<!--                      <el-button type="primary" size="small" @click="$router.push({path:'/detail',query:{goodsId:i.goodsId}})">查看详情</el-button>-->
<!--                      <el-tag size="small" @click="$router.push({path:'/detail',query:{goodsId:i.goodsId}})">查看详情</el-tag>-->
                      <el-button type="text" size="small" @click="$router.push({path:'/detail',query:{goodsId:i.goodsId}})">查看详情</el-button>
<!--                      <el-button type="text" size="small" @click="toDetail(i.goodsId)">查看详情</el-button>-->
                    </el-descriptions-item>

                  </el-descriptions>
                </div>
              </el-col>
            </el-row>
          </el-card>
          <el-card v-if="noMore" style="margin: 10px; "><p style="text-align: center ">已经到底了哦</p></el-card>
        </div>
      </el-col>
    </el-row>






    <!--    新增的弹窗-->
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="商品名">
          <el-input v-model="form.goodsName" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="商品详情">
          <el-input v-model="form.goodsDetail" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="商品类型">
          <el-select v-model="value" placeholder="请选择商品类型">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>


        <el-form-item label="商品价格">
          <el-input v-model="form.goodsPrice" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="商品图片">
          <el-upload action="http://localhost:8888/files/upload" :on-success="fileUploadSuccess"  list-type="picture" ref="uploadImg">
            <el-button type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
      </template>
    </el-dialog>

    <el-backtop :right="100" :bottom="100" />
  </div>





</template>

<script>

import request from "@/utils/request";
import {nextTick, ref} from "vue";
import {createRouter as $router} from "vue-router";
let userStr = sessionStorage.getItem("user") || "{}"
let user = JSON.parse(userStr)
let userName = user.userName
let src;
export default {
  name: 'Index',
  data() {
    return {
      count: 1,//起始页数值为0
      loading: false,
      totalPages: "",//取后端返回内容的总页数
      list: [], //后端返回的数组
      search: '',
      dialogVisible: false,
      form: {},
    };
  },
  computed: {
    noMore() {
      //当起始页数大于总页数时停止加载
      return this.count >= this.totalPages;
      this.loading = false;
    },
    disabled() {
      return this.loading || this.noMore;
    }
  },
  created() {
    this.getMessage();
  },
  methods: {
    load() {
      //滑到底部时进行加载
      // this.loading = true;
      console.log(user.userName);
      setTimeout(() => {
        this.count += 1; //页数+1
        this.getMessage(); //调用接口，此时页数+1，查询下一页数据
      }, 2000);
    },
    getMessage() {
      let params = {
        pageNum: this.count,
        pageSize: 5, //每页查询条数
        search: this.search,
      };
      request.get(
              "/goods",
          {params}
          )
          .then(res => {
            console.log(res);
            this.list = this.list.concat(res.data.records); //
            this.totalPages = res.data.pages;
            // this.loading = false;
            // console.log(this.count);
            // console.log(this.totalPages / 5);
          })
          .catch(err => {
            console.log(err);
          });
    },
    //新增按钮的方法
    add() {
      this.dialogVisible = true;
      this.form = {};
      // 清空图片上传列表
      nextTick(()=> {
        this.$refs.uploadImg.clearFiles()
      })

    },
    //保存到数据库
    save() {

      // console.log(user.userName+ "----------------------------------");
      if(this.form.author == null){
        this.form.author = user.userName //赋值用户名
        // console.log(this.form.author +"--------------------------------------------------");
      }
      this.form.goodsType = this.value
      request.post("/goods", this.form).then(res => {
        console.log(res);
        if (this.form.goodsId) {
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
    // 上传图片
    fileUploadSuccess(res) {
      this.form.goodsImg = res.data
    },

    // toDetail(goodsId){
    //
    //   request.get("/goods/getVO",goodsId).then(res=> {
    //     // 把专业信息赋值给select
    //     // console.log(this.goodsVO = res.data.records);
    //     // this.goodsVO = res.data.records
    //     // $router.push({path:'/detail',query:{goodsVO:res.data.records}})
    //   })
    //
    //
    //   request.get("/goods/getVO", goodsId).then(res => {
    //
    //   })
    //
    //
    //
    // }
  },
  setup() {
    // const url = ref(
    //     'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'
    // )
    return {
      search: ref(''),
      // url,
      options: ref([
        {
          value: '书籍',
          label: '书籍',
        },
        {
          value: '日用品',
          label: '日用品',
        },
        {
          value: '食品',
          label: '食品',
        },
        {
          value: '护肤品',
          label: '护肤品',
        },
        {
          value: '电子设备',
          label: '电子设备',
        },
        {
          value: '其他',
          label: '其他',
        },
      ]),
      value: ref('')
    }
  },
};
</script>

<style scoped>
body{
  /*background-color: #edf2f6;*/
  /*background-image: url("../assets/login11.jpg");*/
}
/*.bkimg{*/
/*  background-image: url("../assets/login11.jpg");*/
/*}*/
</style>
