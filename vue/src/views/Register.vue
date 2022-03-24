<template>
  <div style=" background-color: black;width: 100%; height: 100vh;">
    <div style="width: 330px; margin: 0 auto; padding-top: 120px; text-align: center ">
      <h1 style="font-size: xx-large; color: #fff;margin-bottom: 40px;">欢迎注册</h1>
      <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleForm"
          size="normal"
      >
        <el-form-item prop="userName">
          <el-input
              v-model="ruleForm.userName"
              autocomplete="off"
              prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
              type="password"
              v-model="ruleForm.password"
              autocomplete="off"
              prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item prop="checkPass">
          <el-input
              type="password"
              v-model="ruleForm.checkPass"
              autocomplete="off"
              prefix-icon="el-icon-lock"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="register" style="width:100%">注册</el-button>
<!--          <el-button @click="resetForm('ruleForm')" style="width:50%">重置</el-button>-->
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Register",

  data() {

    let validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass')
        }
        callback()
      }
    }
    let validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    let validatePass1 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'))
      } else {
        callback()
      }
    }
    return {
      ruleForm: {
        userName: '',
        password: '',
        checkPass: '',
      },
      rules: {
        userName:[{ validator: validatePass1, trigger: 'blur'}],
        password: [{ validator: validatePass, trigger: 'blur' }],
        checkPass: [{ validator: validatePass2, trigger: 'blur' }],

      },
    }
  },
  methods: {
    register() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          request.post("/user/register",this.ruleForm).then( res =>{
            if (res.code === '0') {
              this.$message({
                type: "success",
                message: "注册成功，请登录"
              })
              this.$router.push("/login")
            } else {
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          })
        } else {
          this.$message({
            type: "error",
            message: "请填写信息"
          })
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
  },

}
</script>

<style scoped>

</style>