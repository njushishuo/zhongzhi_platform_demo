<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect"
     text-color="#fff" active-text-color="#ffd04b">

    <span>众智平台</span>
    <el-menu-item index="1" align="right">我的应用</el-menu-item>
    <el-menu-item index="2" align="right">我的工单</el-menu-item>
    <router-link class="exit-link" :to="{name: 'Login'}">退出登录</router-link>
  </el-menu>
</template>

<script>
    export default {
        name: "isvNav",
        data() {
          return {
            activeIndex: '1',
          };
        },
        methods: {
          handleSelect(key, keyPath) {
            console.log(key, keyPath);
          }
        }
    }
</script>

<style scoped>

</style>
