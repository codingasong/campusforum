<template>

  <div >
    <el-button @click="add" type="primary" size="large" >发布一条</el-button>
    <el-row>
      <el-col>
        <div v-infinite-scroll="load"  class="infinite-list" style="overflow:auto; scrollbar-width:none;">
          <el-card v-for="i in list" :key="i"  infinite-scroll-disabled="disabled" style="margin: 10px 10px">
            <el-row>
              <el-col >

                <div id="details">
                  <el-descriptions
                      class="margin-top"
                      :column="1"
                      border
                  >

                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          标题
                        </div>
                      </template>
                      {{i.postsTitle}}
                    </el-descriptions-item>
                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          详细信息
                        </div>
                      </template>
                      <span v-html="i.postsDetail"></span>
                    </el-descriptions-item>

                    <el-descriptions-item>
                      <template #label>
                        <div class="cell-item">
                          发布时间
                        </div>
                      </template>
                      {{i.postsPubdate}}
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
                      <el-button type="text" size="small" @click="$router.push({path:'/postDetail',query:{postsId:i.postsId}})">查看详情</el-button>
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
    <el-dialog title="发帖" v-model="dialogVisible" width="70%">
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="标题">
          <el-input v-model="form.postsTitle" style="width: 40%"></el-input>
        </el-form-item>
        <el-form-item label="内容">
          <div id="div1"></div>
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
import E from "wangeditor";
let userStr = sessionStorage.getItem("user") || "{}"
let user = JSON.parse(userStr)
let userName = user.userName
let editor;
export default {
  name: 'PostFront',
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
          "/posts",
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
      // 当编辑器存在时，清空编辑器
      if(editor){
        editor.txt.clear()
      }
      // 如果编辑器不存在，则创建编辑器
      this.$nextTick(() => {
        if(!editor){
          editor = new E('#div1')
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:8888/files/editor/upload'
          editor.config.uploadFileName = "file" //设置上传图片名称
          editor.create()
        }
      })
    },
    //保存到数据库
    save() {
      this.form.postsDetail = editor.txt.html(); //获取编辑器里的内容，赋值到实体中
      let userStr = sessionStorage.getItem("user") || "{}"
      let user = JSON.parse(userStr)
      if(this.form.author == null){
        this.form.author = user.userName //赋值用户名
      }
      request.post("/posts", this.form).then(res => {
        console.log(res);
        if (this.form.postsId) {
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

  },
  setup() {
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
  background-color: #edf2f6;
}
</style>
