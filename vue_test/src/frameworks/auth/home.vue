<template>
  <v-container fill-height justify-center align-center >
    <!-- <v-layout row > -->
    <v-flex xs12 sm3>
      <!-- <h1> &nbsp;</h1> -->
      <v-card class="mt-0 pt-0">
        <v-card-title class="blue darken-1">
          <h4 style="color:white">众智平台</h4>
        </v-card-title>
        <v-card-text>
          <form>
            <v-layout row>
              <v-text-field class="input-group--focused"  v-model="form.email" label="email" required></v-text-field>
            </v-layout>
            <v-layout row>
              <v-text-field class="input-group--focused" type="password" v-model="form.password" label="password" required></v-text-field>
            </v-layout>
            <v-btn @click = "login">login</v-btn>
          </form>
        </v-card-text>
      </v-card>
    </v-flex>
    <!-- </v-layout> -->
  </v-container>
</template>

<style lang="stylus">
  @import '../../stylus/main'
</style>

<script>
  import AuthService from '@/services/authService'
  export default {
    name: 'VerifyHome',
    data () {
      return {
        form: {
          email: '',
          password: ''
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
          let user = res.data
          this.$cookie.set('userId', user.id,{ expires: '30M' })
          this.$cookie.set('username', user.username,{ expires: '30M' })
          this.$cookie.set('userRole', user.role,{ expires: '30M' })
          this.$cookie.set('userDeptId', user.deptId,{ expires: '30M' })
          console.log(user)
          if (user.role === 'isv') {
            this.$router.push({name: 'IsvFrame', params: {user_id: user.id}})
          } else {
            this.$router.push({name: 'AuditorFrame', params: {user_id: user.id}})
          }
        }).catch((err) => {
          console.log(err)
          let errMsg = (err.response) ? err.response.data.message : '服务器连接出错'
        })
      }
    },

  }
</script>

