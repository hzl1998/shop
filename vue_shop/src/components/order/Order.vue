<template>
    <div>
        <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>订单管理</el-breadcrumb-item>
      <el-breadcrumb-item>订单列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
        <el-row>
            <el-col :span="8">
                <el-input v-model="order_number" placeholder="请输入订单编号" clearable @clear="searchOrder">
                    <el-button slot="append" icon="el-icon-search" @click="searchOrder"></el-button>
          </el-input>
            </el-col>
        </el-row>

        <el-table :data="orderList" border stripe>
            <el-table-column type="index" label="#"></el-table-column>
            <el-table-column label="订单编号" prop="order_number"></el-table-column>
            <el-table-column label="订单价格" prop="order_price"></el-table-column>
            <el-table-column label="是否付款" prop="pay_status">
                <template slot-scope="scope">
                    <el-tag type="success" v-if="scope.row.pay_status === '1'">已付款</el-tag>
                    <el-tag type="danger" v-else>未付款</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="是否发货" prop="is_send"></el-table-column>
            <el-table-column label="下单时间" prop="create_time">
                <template slot-scope="scope">
                    {{scope.row.create_time | dateFormat}}
                </template>
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini"></el-button>
                    <el-button type="success" icon="el-icon-location" size="mini"></el-button>
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
        return{
            page:1,
            rows:10,
            orderList:[],
            total:0,
            order_number:''
        }
    },
    created() {
        this.getOrderList()
    },
    methods:{
        getOrderList(){
            this.$http({
        method: "GET",
        url: "orders" + "?page=" + this.page + "&rows=" + this.rows + "&order_number="+this.order_number
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取订单列表失败！");
          }
          this.orderList = resp.data.data.resultList
          this.total = resp.data.data.totalResult
          console.log(this.orderList)
        })
        .catch(error => {
          console.log(error);
        });
        },
        //监听pagesize改变的事件
    handleSizeChange(nowSize) {
      this.rows = nowSize;
      this.getOrderList();
    },
    //监听页码值改变的事件
    handleCurrentChange(newPage) {
      this.page = newPage;
      this.getOrderList();
    },
    searchOrder(){
      this.page = 1
      this.getOrderList()
    }
    }
}
</script>

<style lang="less" scoped>

</style>