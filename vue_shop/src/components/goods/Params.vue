<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>参数列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <el-alert title="注意：只允许为第三级分类设置相关参数！" type="warning" :closable="false" show-icon></el-alert>
      <el-row class="cat_opt">
        <el-col>
          <span>选择商品分类：</span>
          <el-cascader
            v-model="selectedCateKeys"
            :options="cateList"
            @change="handleChange"
            :props="{ expandTrigger: 'hover', value:'cat_id', label:'cat_name', children:'children',checkStrictly:true}"
            clearable
          ></el-cascader>
        </el-col>
      </el-row>

      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="动态参数" name="many">
          <el-button type="primary" size="mini" :disabled="isBtnDisabled">添加参数</el-button>
        </el-tab-pane>
        <el-tab-pane label="静态参数" name="only">
          <el-button type="primary" size="mini" :disabled="isBtnDisabled">添加参数</el-button>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cateList: [],
      selectedCateKeys: [],
      activeName: "many"
    };
  },
  created() {
    this.getCateList();
  },
  methods: {
    getCateList() {
      this.$http({
        method: "GET",
        url: "getAllCategories"
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取商品分类失败！");
          }
          this.cateList = resp.data.data;
          console.log(this.cateList);
        })
        .catch(error => {
          console.log(error);
        });
    },
    handleChange() {
      if (this.selectedCateKeys.length !== 3) {
        this.selectedCateKeys = [];
        return;
      }

      console.log(this.selectedCateKeys);

      this.$http({
        method: "GET",
        url: "attributes?id="+this.cateId+"&sel="+this.activeName
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取参数列表失败！");
          }
          console.log(resp.data.data);
        })
        .catch(error => {
          console.log(error);
        });
    },
    handleTabClick() {
      console.log(this.activeName);
    }
  },
  computed: {
      //如果按钮需要被禁用，则返回true，否则返回false
      isBtnDisabled() {
        if(this.selectedCateKeys.length !== 3){
            return true
        }
        return false
      },
      cateId() {
          if(this.selectedCateKeys.length === 3){
              this.selectedCateKeys = this.selectedCateKeys[2]
              return this.selectedCateKeys
          }
          return null
      }
  }
};
</script>

<style lang="less" scoped>
.cat_opt {
  margin: 15px 0;
}
</style>