<template>
  <div class="verify-box-card">
    <h1 class="verify-title">众智平台</h1>
    <el-card>
      <div>
        <el-form size="medium" class="register-verify-form" label-position="right" label-width="70px" :model="form">

          <el-form-item label="用户名称">
            <el-input v-model="form.name"></el-input>
          </el-form-item>

          <el-form-item label="注册邮箱">
            <el-input v-model="form.email"></el-input>
          </el-form-item>

          <el-form-item label="密码">
            <el-input type="password" v-model="form.password"></el-input>
          </el-form-item>

          <el-button @click="registerAndLogin" class="verify-btn" type="primary">
            注册并登录
          </el-button>
          <router-link class="register-link" :to="{name: 'Login'}">已有账户,直接登陆</router-link>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<style>
  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .verify-box-card {
    width: 312px;
    margin: 120px auto;
  }

  .register-verify-form {
    padding: 0 6px 0;
  }

  .verify-btn {
    width: 100%;
    margin-top: 15px;
    margin-bottom: 10px;
  }

  .verify-title {
    font-family: "Helvetica Neue";
    font-weight: lighter;
    padding: 0 auto;
    text-align: center;
  }
</style>

<script>
  import AuthService from '@/services/authService'
  import { Message } from 'element-ui'
  export default {
    name: 'Register',
    data () {
      return {
        form: {
          name: '',
          password: '',
          email: ''
        }
      }
    },
    methods: {
      registerAndLogin () {

      }
    }
  }
</script>
