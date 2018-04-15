<template>
  <div class="verify-box-card">
    <h1 class="verify-title">众智平台</h1>
    <el-card>
      <div>
        <el-form size="medium" class="login-verify-form" label-position="left" label-width="60px" :model="form">
          <el-form-item label="邮箱">
            <el-input v-model="form.email"></el-input>
          </el-form-item>
          <el-form-item label="密码">
            <el-input type="password" v-model="form.password"></el-input>
          </el-form-item>
          <el-button @click="login" class="verify-btn" type="success">
            登录
          </el-button>
          <router-link class="register-link" :to="{name: 'Register'}">还没有账号?创建一个</router-link>
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

  .login-verify-form {
    padding: 0 6px 0;
  }

  .verify-btn {
    width: 100%;
    margin-top: 15px;
  }

  .verify-title {
    font-family: "Helvetica Neue";
    font-weight: lighter;
    padding: 0 auto;
    text-align: center;
  }

  .register-link {
    color: #0366d6;
    text-decoration: none;
    width: 100%;
    font-size: 10px;
    float: right;
    text-align: right;
    margin: 5px 0 20px 0;
  }
</style>

<script>
  import AuthService from '@/services/authService'
  import { Message } from 'element-ui'
  export default {
    name: 'VerifyHome',
    data () {
      return {
        form: {
          email: '',
          password: '',
          type: ''
        }
      }
    },
    methods: {
      login () {
        let params = {
          email: this.form.email,
          password: this.form.password
        }
        AuthService.login(params).then((res) => {
          console.log('success')
          let user = res.data
          this.$cookie.set('user', user)
          if (user.role === 'isv') {
            this.$router.push({name: 'IsvAppList', params: {user_id: user.id}})
          } else {
            this.$router.push({name: 'AuditorOrderList', params: {user_id: user.id}})
          }
        }).catch((err) => {
          console.log('err')
          let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
          let options = {
            message: errMsg
          }
          Message.error(options)
        })
      }
    }
  }
</script>
