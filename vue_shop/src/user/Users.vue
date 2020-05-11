<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card class="box-card">
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary">添加用户</el-button>
        </el-col>
      </el-row>

      <!-- 用户列表区域 -->
      <el-table :data="userList" border stripe>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="电话" prop="phone"></el-table-column>
        <el-table-column label="角色" prop="role_name"></el-table-column>
         <el-table-column label="状态" prop="enabled">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.enabled"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="create_time" :formatter="dateForma"></el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
              <el-button type="primary" icon="el-icon-edit" size="mini"></el-button>
              <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
              
               <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
      <el-button type="warning" icon="el-icon-setting" size="mini"></el-button>
    </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page"
      :page-sizes="[2, 4, 6, 10]"
      :page-size="rows"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    </el-card>
  </div>
</template>

<script>
import moment from "moment"; //导入文件
export default {
  data() {
    return {
      username: "",
      page: 1,
      rows: 2,
      userList: [],
      total: 0
    };
  },
  created() {
    this.getUserList();
  },
  methods: {
    getUserList() {
      this.$http({
        method: "GET",
        url:
          "users?username=" +
          this.username +
          "&page=" +
          this.page +
          "&rows=" +
          this.rows
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取用户列表失败！");
          }
          this.userList = resp.data.data.resultList;
          this.total = resp.data.data.totalResult;
        })
        .catch(error => {
          console.log(error);
        });
    },
    //时间格式化转换
    dateForma(row, column, data) {
      return moment.unix(data).format("YYYY-MM-DD HH:mm:ss");
    },
    //监听pagesize改变的事件
    handleSizeChange(nowSize) {
      this.rows = nowSize;
      this.getUserList();
    },
    //监听页码值改变的事件
    handleCurrentChange(newPage) {
      this.page = newPage;
      this.getUserList();
    }
  }
};
</script>

<style lang="less" scoped>
</style>