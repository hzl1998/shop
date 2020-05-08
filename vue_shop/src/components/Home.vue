<template>
    <el-container class="home-container">
        <!-- 头部区域 -->
  <el-header>
      <div>
          <el-avatar src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
          <span>电商后台管理系统</span>
      </div>
      <el-button type="info" @click="logout">退出</el-button>
      </el-header>
  <!-- 页面主体区域 -->
  <el-container>
      <!-- 侧边栏 -->
    <el-aside width="200px">Aside</el-aside>
    <!-- 右侧主体内容 -->
    <el-main>Main</el-main>
  </el-container>
</el-container>
</template>

<script>
export default {
    methods: {
        logout() {
            window.sessionStorage.clear();
            this.$router.push('/login');
        }
    }
}
</script>

<style lang="less" scoped>
.home-container {
    height: 100%;
}
.el-header {
    background-color: #373d41;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #fff;
    font-size: 20px;
    > div {
        display: flex;
        align-items: center;
        span {
            margin-left: 15px;
        }
    }
}
.el-aside {
    background-color: #333744;
}
.el-main {
    background-color: #eaedf1;
}
</style>