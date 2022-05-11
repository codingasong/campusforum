<template>

  <div >
    <el-button @click="add" type="primary" size="large" >发布一条</el-button>
    <el-row>
      <el-col>
        <div v-infinite-scroll="load"  class="infinite-list" style="overflow:auto; scrollbar-width:none;">
          <el-card v-for="i in list" :key="i"  infinite-scroll-disabled="disabled" style="margin: 10px 10px">
            <el-row>

              <el-col :span="24">
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
                      {{i.jobName}}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          介绍
                        </div>
                      </template>
                      {{i.jobDetail}}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          价格
                        </div>
                      </template>
                      <el-tag size="small">{{i.jobPrice}}</el-tag>
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          分类
                        </div>
                      </template>
                      {{i.jobType}}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          发布时间
                        </div>
                      </template>
                      {{i.jobPubdate}}
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
                      <el-button type="text" size="small" @click="$router.push({path:'/jobDetail',query:{jobId:i.jobId}})">查看详情</el-button>
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






    <!--    新增用户的弹窗-->
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="兼职名">
          <el-input v-model="form.jobName" style="width: 70%"></el-input>
        </el-form-item>
        <el-form-item label="兼职详情">
          <el-input v-model="form.jobDetail" style="width: 70%"></el-input>
        </el-form-item>

        <el-form-item label="兼职类型">
          <el-select v-model="value" placeholder="请选择兼职类型">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="兼职价格">
          <el-input v-model="form.jobPrice" style="width: 70%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </span>
      </template>
    </el-dialog>



  </div>


  <el-backtop :right="100" :bottom="100" />


</template>

<script>

import request from "@/utils/request";
import {nextTick, ref} from "vue";
let userStr = sessionStorage.getItem("user") || "{}"
let user = JSON.parse(userStr)
let userName = user.userName

export default {
  name: 'JobFront',
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
          "/job",
          {params}
      )
          .then(res => {
            console.log(res);
            this.list = this.list.concat(res.data.records); //
            this.totalPages = res.data.pages;
          })
          .catch(err => {
            console.log(err);
          });
    },
    //新增按钮的方法
    add() {
      this.dialogVisible = true;
      this.form = {};
    },
    //保存到数据库
    save() {

      // let author = user.userName
      // console.log(user.userName+ "----------------------------------");
      if (this.form.author == null) {
        this.form.author = user.userName //赋值用户名
        // console.log(this.form.author +"--------------------------------------------------");
      }
      this.form.jobType = this.value
      request.post("/job", this.form).then(res => {
        console.log(res);
        if (this.form.jobId) {
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
            // this.load();
          } else {
            this.$message({
              type: "error",
              message: res.msg
            })
          }
        }

        this.dialogVisible = false;
      })
    },

  },

  setup() {
    return {
      options: ref([
        {
          value: '跑腿',
          label: '跑腿',
        },
        {
          value: '食堂兼职',
          label: '食堂兼职',
        },
        {
          value: '代取快递',
          label: '代取快递',
        },
        {
          value: '其他',
          label: '其他',
        }
      ]),
      value: ref(''),
    }
  },
};
</script>

<style scoped>
body{
  background-color: #edf2f6;
}
</style>
