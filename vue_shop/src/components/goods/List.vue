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
          <el-input v-model="goods_name" placeholder="请输入内容" prefix-icon="el-icon-search" clearable @clear="searchGoods">
            <el-button slot="append" icon="el-icon-search" @click="searchGoods"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="goAddpage">添加商品</el-button>
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
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="goUpdatepage(scope.row.goods_id)"></el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeById(scope.row.goods_id)"></el-button>
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
      total:0,
      goods_name:''
    };
  },
  created(){
    this.getGoodsList()
  },
  methods:{
    getGoodsList() {
      this.$http({
        method: "GET",
        url: "goods" + "?page=" + this.page + "&rows=" + this.rows + "&goods_name="+this.goods_name
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
    searchGoods(){
      this.page = 1
      this.getGoodsList()
    },
    removeById(id){
      this.$confirm("此操作将永久删除该商品, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(resp => {
          this.$http({
            method: "PUT",
            url: "goods/delGood?goods_id=" + id
          })
            .then(resp => {
              if (resp.data.code !== 200) {
                return this.$message.error("删除商品失败！");
              }
              this.$message.success("删除商品成功！");
              this.getGoodsList();
            })
            .catch(error => {
              console.log(error);
            });
        })
        .catch(error => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    goAddpage() {
      this.$router.push('/goods/add')
    },
    goUpdatepage(id) {
      this.$router.push('/goods/add/'+id)
    }
  }
};
</script>

<style lang="less" scoped>
</style>