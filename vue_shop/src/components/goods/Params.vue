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
          <el-button type="primary" size="mini" :disabled="isBtnDisabled" @click="addDialogVisible = true">添加参数</el-button>
          <el-table :data="manyTableData" border stripe>
              <el-table-column type="expand">
                  <template slot-scope="scope">
                      <el-tag v-for="(item, i) in scope.row.attr_vals" :key="i" closable>
                          {{item}}
                      </el-tag>
                  </template>
              </el-table-column>
              <el-table-column type="index" label="#"></el-table-column>
              <el-table-column label="参数名称" prop="attr_name"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.attr_id)">编辑</el-button>
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeParams(scope.row.attr_id)">删除</el-button>
                </template>
              </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="静态属性" name="only">
          <el-button type="primary" size="mini" :disabled="isBtnDisabled" @click="addDialogVisible = true">添加属性</el-button>
          <el-table :data="onlyTableData" border stripe>
              <el-table-column type="expand"></el-table-column>
              <el-table-column type="index" label="#"></el-table-column>
              <el-table-column label="属性名称" prop="attr_name"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.attr_id)">编辑</el-button>
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeParams(scope.row.attr_id)">删除</el-button>
                </template>
              </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <el-dialog
  :title="'添加'+titleText"
  :visible.sync="addDialogVisible"
  width="50%"
  @close="addDialogClosed"
 >
  <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
  <el-form-item :label="titleText" prop="attr_name">
    <el-input v-model="addForm.attr_name"></el-input>
  </el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="addDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addParams">确 定</el-button>
  </span>
</el-dialog>

<el-dialog
  :title="'修改'+titleText"
  :visible.sync="editDialogVisible"
  width="50%"
  @close="editDialogClosed"
 >
  <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
  <el-form-item :label="titleText" prop="attr_name">
    <el-input v-model="editForm.attr_name"></el-input>
  </el-form-item>
  </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="editDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="editParams">确 定</el-button>
  </span>
</el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      cateList: [],
      selectedCateKeys: [],
      activeName: "many",
      manyTableData:[],
      onlyTableData:[],
      addDialogVisible:false,
      editDialogVisible:false,
      addForm:{
          attr_name:''
      },
      addFormRules:{
          attr_name:[{
              required: true, message: "请输入参数名称", trigger: "blur" 
          }]
      },
      editForm:{
          attr_name:''
      },
      editFormRules:{
          attr_name:[{
              required: true, message: "请输入参数名称", trigger: "blur" 
          }]
      }
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
      this.getParamsData()
    },
    handleTabClick() {
      console.log(this.activeName);
      this.getParamsData()
    },
    getParamsData() {
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
          resp.data.data.forEach(item => {
              item.attr_vals = item.attr_vals ? item.attr_vals.split(' '): []
          });
          console.log(resp.data.data);
          if(this.activeName === 'many'){
              this.manyTableData = resp.data.data
          }else{
              this.onlyTableData = resp.data.data
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    addDialogClosed() {
        this.$refs.addFormRef.resetFields()
    },
    addParams() {
        this.$refs.addFormRef.validate(valid =>{
            if(!valid) return
            this.$http({
        method: "POST",
        url: "addAttributes",
        data:{
            cat_id:this.cateId,
            attr_name:this.addForm.attr_name,
            attr_sel:this.activeName
        }
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("添加参数失败！");
          }
          this.$message.success('添加参数成功！')
          this.addDialogVisible = false
          this.getParamsData()
        })
        .catch(error => {
          console.log(error);
        });
        })
    },
    showEditDialog(attr_id) {
        this.$http({
        method: "GET",
        url: "getAttributesById?id="+attr_id
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取参数信息失败！");
          }
          this.editForm = resp.data.data
          this.editDialogVisible = true
        })
        .catch(error => {
          console.log(error);
        });
    },
    editDialogClosed() {
        this.$refs.editFormRef.resetFields()
        this.editForm = {}
    },
    editParams(){
        this.$refs.editFormRef.validate(valid =>{
            if(!valid) return
            this.$http({
        method: "PUT",
        url: "updateAttribute",
        data:{
            cat_id:this.cateId,
            attr_id:this.editForm.attr_id,
            attr_name:this.editForm.attr_name,
            attr_sel:this.activeName
        }
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("修改参数失败！");
          }
          this.$message.success("修改参数成功！")
          this.getParamsData()
          this.editDialogVisible = false
        })
        .catch(error => {
          console.log(error);
        });
        })
    },
    removeParams(attr_id){
            this.$confirm("此操作将永久删除该参数, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(resp => {
          this.$http({
            method: "DELETE",
            url: "delAttribute?attr_id=" + attr_id +"&cat_id="+this.cateId
          })
            .then(resp => {
              if (resp.data.code !== 200) {
                return this.$message.error("删除参数失败！");
              }
              this.$message.success("删除参数成功！");
              this.getParamsData();
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
              return this.selectedCateKeys[2]
          }
          return null
      },
      titleText() {
          if(this.activeName === 'many'){
              return '动态参数'
          }
          return '静态属性'
      }
  }
};
</script>

<style lang="less" scoped>
.cat_opt {
  margin: 15px 0;
}
.el-tag {
    margin: 10px;
}
</style>