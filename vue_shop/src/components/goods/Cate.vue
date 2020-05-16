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
          <el-button type="primary" @click="showAddCateDialog">添加分类</el-button>
        </el-col>
      </el-row>

      <tree-table
        :data="cateList"
        :columns="columns"
        :selection-type="false"
        class="treeTable"
        :expand-type="false"
        show-index
        index-text="#"
        border
      >
        <!-- 是否有效 -->
        <template slot="isok" slot-scope="scope">
          <i class="el-icon-success" v-if="scope.row.cat_deleted === 0" style="color: lightgreen;"></i>
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
          <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            @click="delCate(scope.row.cat_id)"
          >删除</el-button>
        </template>
      </tree-table>
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
    </el-card>

    <el-dialog
      title="添加分类"
      :visible.sync="addCateDialogVisible"
      width="50%"
      @close="addCateDialogClosed"
    >
      <el-form
        :model="addCateForm"
        :rules="addCateFormRules"
        ref="addCateFormRef"
        label-width="100px"
      >
        <el-form-item label="分类名称：" prop="cat_name">
          <el-input v-model="addCateForm.cat_name"></el-input>
        </el-form-item>
        <el-form-item label="父级分类：">
          <el-cascader
            v-model="selectedKeys"
            :options="parentCateList"
            :props="{ expandTrigger: 'hover', value:'cat_id', label:'cat_name', children:'children',checkStrictly:true }"
            @change="parentCateChanged"
            clearable
            change-on-select
          ></el-cascader>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addCateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCate">确 定</el-button>
      </span>
    </el-dialog>
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
      columns: [
        {
          label: "分类名称",
          prop: "cat_name"
        },
        {
          label: "是否有效",
          //表示，将当前列定义为模板列
          type: "template",
          //表示当前这一列使用模板名称
          template: "isok"
        },
        {
          label: "排序",
          //表示，将当前列定义为模板列
          type: "template",
          //表示当前这一列使用模板名称
          template: "order"
        },
        {
          label: "操作",
          //表示，将当前列定义为模板列
          type: "template",
          //表示当前这一列使用模板名称
          template: "opt"
        }
      ],
      addCateDialogVisible: false,
      addCateForm: {
        cat_name: "",
        cat_pid: 0,
        cat_level: 0
      },
      addCateFormRules: {
        cat_name: [
          { required: true, message: "请输入分类名称", trigger: "blur" }
        ]
      },
      parentCateList: [],
      //选中的父级分类的id数组
      selectedKeys: []
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
    },
    showAddCateDialog() {
      this.getParentCateList();
      this.addCateDialogVisible = true;
    },
    getParentCateList() {
      this.$http({
        method: "GET",
        url: "getCategories"
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取父级分类数据失败！");
          }
          console.log(resp.data.data);
          this.parentCateList = resp.data.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    //级联选择项发生变化触发这个函数
    parentCateChanged() {
      //如果selectedKeys数组中的length大于0，证明选中的父级分类
      //反之，就说明没有选中任何父级分类
      if (this.selectedKeys.length > 0) {
        //父级分类的id
        this.addCateForm.cat_pid = this.selectedKeys[
          this.selectedKeys.length - 1
        ];
        //为当前分类的等级赋值
        this.addCateForm.cat_level = this.selectedKeys.length;
        return;
      } else {
        this.addCateForm.cat_pid = 0;
        this.addCateForm.cat_level = 0;
      }
    },
    addCate() {
      this.$refs.addCateFormRef.validate(valid => {
        if (!valid) return;
        this.$http({
          method: "POST",
          url: "addCategory",
          data: {
            cat_name: this.addCateForm.cat_name,
            cat_pid: this.addCateForm.cat_pid,
            cat_level: this.addCateForm.cat_level
          }
        })
          .then(resp => {
            if (resp.data.code !== 200) {
              return this.$message.error("添加分类失败！");
            }
            this.$message.success("添加分类成功！");
            this.getCateList();
            this.addCateDialogVisible = false;
          })
          .catch(error => {
            console.log(error);
          });
      });
    },
    addCateDialogClosed() {
      this.$refs.addCateFormRef.resetFields();
      this.selectedKeys = [];
      this.addCateForm.cat_level = 0;
      this.addCateForm.cat_pid = 0;
    },
    delCate(id) {
        console.log(id)
      this.$confirm("此操作将永久删除该分类, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(resp => {
          this.$http({
            method: "PUT",
            url: "delCategoryById?id=" + id
          })
            .then(resp => {
              if (resp.data.code === 402) {
                return this.$message.error(
                  "删除分类失败,当前分类存在下级分类！"
                );
              }
              if (resp.data.code !== 200 && resp.data.code !== 402) {
                return this.$message.error("删除分类失败！");
              }
              this.$message.success("删除分类成功！");
              this.getCateList();
              this.addCateDialogVisible = false;
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
    }
  }
};
</script>

<style lang="less" scoped>
.treeTable {
  margin-top: 15px;
}
.el-cascader {
  width: 100%;
}
</style>