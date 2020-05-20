<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" prefix-icon="el-icon-search"></el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary">添加商品</el-button>
        </el-col>
      </el-row>

      <el-table :data="goodList" border stripe>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="商品名称" prop="goods_name"></el-table-column>
        <el-table-column label="商品价格(元)" prop="goods_price" width="95px"></el-table-column>
        <el-table-column label="商品重量" prop="goods_weight" width="70px"></el-table-column>
        <el-table-column label="创建时间" prop="add_time" width="140px">
          <template slot-scope="scope">
            {{scope.row.add_time | dateFormat}}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="130px">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
          </template>
        </el-table-column>
      </el-table>
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
      page:1,
      rows:10,
      goodList:[],
      total:0
    };
  },
  created(){
    this.getGoodsList()
  },
  methods:{
    getGoodsList() {
      this.$http({
        method: "GET",
        url: "goods" + "?page=" + this.page + "&rows=" + this.rows
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取商品列表失败！");
          }
          console.log(resp.data.data)
          this.goodList = resp.data.data.resultList
          this.total = resp.data.data.totalResult
        })
        .catch(error => {
          console.log(error);
        });
    },
    //监听pagesize改变的事件
    handleSizeChange(nowSize) {
      this.rows = nowSize;
      this.getGoodsList();
    },
    //监听页码值改变的事件
    handleCurrentChange(newPage) {
      this.page = newPage;
      this.getGoodsList();
    },
  }
};
</script>

<style lang="less" scoped>
</style>