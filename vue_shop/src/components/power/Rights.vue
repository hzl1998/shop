<template>
  <div style="padding: 20px">
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>权限列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-table :data="rightsList" border stripe>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="权限名称" prop="name"></el-table-column>
        <el-table-column label="路径" prop="path"></el-table-column>
        <el-table-column label="权限等级" prop="level">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.level === 0">一级</el-tag>
            <el-tag type="success" v-else-if="scope.row.level === 1">二级</el-tag>
            <el-tag type="warning" v-else>三级</el-tag>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="rows"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      rightsList: [],
      page: 1,
      rows: 10,
      total: 0
    };
  },
  created() {
    this.getRightsList();
  },
  methods: {
    getRightsList() {
      this.$http({
        method: "GET",
        url: "rights" + "?page=" + this.page + "&rows=" + this.rows
      })
        .then(resp => {
          if (resp.data.code === 403) {
            return this.$message.error("无权访问！");
          }
          if (resp.data.code !== 200) {
            return this.$message.error("获取权限列表失败！");
          }
          this.rightsList = resp.data.data.resultList;
          this.total = resp.data.data.totalResult;
        })
        .catch(error => {
          console.log(error);
        });
    },
    //监听pagesize改变的事件
    handleSizeChange(nowSize) {
      this.rows = nowSize;
      this.getRightsList();
    },
    //监听页码值改变的事件
    handleCurrentChange(newPage) {
      this.page = newPage;
      this.getRightsList();
    }
  }
};
</script>

<style lang="less">
</style>