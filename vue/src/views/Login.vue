<template>
  <div style=" background-color: darkcyan ;width: 100%; height: 100vh;">
    <div style="width: 330px; margin: 0 auto; padding-top: 120px; text-align: center ">
      <h1 style="font-size: xx-large; color: #fff;margin-bottom: 40px;">欢迎登陆</h1>
      <el-form ref="form" :model="form" :rules="rules" size="normal">
        <el-form-item prop="userName" >
          <el-input v-model="form.userName" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <el-form-item prop="password" >
          <el-input v-model="form.password" prefix-icon="el-icon-lock" show-password></el-input>
        </el-form-item>
        <el-form-item >
          <el-button type="primary" style="width: 100%; font-size: large; font-weight: bold;" @click="login">登 录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Login",
  created() {
    sessionStorage.removeItem("user")
  },
  data() {
    return {
      form: {},
      rules: {
        userName: [
          {required: true, message: "请输入用户名", trigger: 'blur'}
        ],
        password: [
          {required: true, message: "请输入密码", trigger: 'blur'}
        ]
      }
      // bgImg: {
      //   backgroundImage: "url(" + require("@/assets/bg.jpg") + ")",
      //   backgroundRepeat: "no-repeat",
      //   backgroundSize: "100% 100%"
      // }
    }
  },
  methods: {
    login() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          request.post("/user/login", this.form).then(res => {
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "登录成功"
              })
              // 保存用户登录状态
              sessionStorage.setItem("user",JSON.stringify(res.data))
              this.$router.push("/a")
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
