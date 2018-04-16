<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect"
           background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
    <el-menu-item index="1">工单审核</el-menu-item>
    <el-menu-item index="2">资源管理</el-menu-item>
    <router-link class="exit-link" :to="{name: 'Login'}">退出登录</router-link>

  </el-menu>
</template>

<script>
    export default {
        name: "auditorNav",
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
