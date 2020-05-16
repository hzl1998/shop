<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-row>
        <el-col>
          <el-button type="primary">添加分类</el-button>
        </el-col>
      </el-row>

      <tree-table :data="cateList" :columns="columns" :selection-type="false"
      :expand-type="false" show-index index-text="#" border>
        <!-- 是否有效 -->
        <template slot="isok" slot-scope="scope">
            <i class="el-icon-success" v-if="scope.row.cat_deleted === 0"
            style="color: lightgreen;"></i>
            <i class="el-icon-error" v-else style="color: red;"></i>
        </template>
        <!-- 排序 -->
        <template slot="order" slot-scope="scope">
            <el-tag size="mini" v-if="scope.row.cat_level === 0">一级</el-tag>
            <el-tag type="success" size="mini" v-else-if="scope.row.cat_level === 1">二级</el-tag>
            <el-tag type="warning" size="mini" v-else>三级</el-tag>
        </template>
        <!-- 操作 -->
        <template slot="opt" slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" size="mini">编辑</el-button>
            <el-button type="danger" icon="el-icon-delete" size="mini">删除</el-button>
        </template>
      </tree-table>
    </el-card>
    <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="rows"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cateList: [],
      page: 1,
      rows: 5,
      total: 0,
      //为table指定列的定义
      columns:[{
          label:'分类名称',
          prop: 'cat_name'
      },{
          label: '是否有效',
          //表示，将当前列定义为模板列
          type: 'template',
          //表示当前这一列使用模板名称
          template: 'isok'
      },
      {
          label: '排序',
          //表示，将当前列定义为模板列
          type: 'template',
          //表示当前这一列使用模板名称
          template: 'order'
      },
      {
          label: '操作',
          //表示，将当前列定义为模板列
          type: 'template',
          //表示当前这一列使用模板名称
          template: 'opt'
      }]
    };
  },
  created() {
    this.getCateList();
  },
  methods: {
    getCateList() {
        this.$http({
        method: "GET",
        url: "categories" + "?page=" + this.page + "&rows=" + this.rows
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取商品分类失败！");
          }
          this.cateList = resp.data.data.resultList;
          this.total = resp.data.data.totalResult;
          console.log(this.cateList)
        })
        .catch(error => {
          console.log(error);
        });
    },
    //监听pagesize改变的事件
    handleSizeChange(nowSize) {
      this.rows = nowSize;
      this.getCateList();
    },
    //监听页码值改变的事件
    handleCurrentChange(newPage) {
      this.page = newPage;
      this.getCateList();
    }
  }
};
</script>

<style lang="less" scoped>
</style>