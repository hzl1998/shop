<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-row>
        <el-col>
          <el-button type="primary">添加角色</el-button>
        </el-col>
      </el-row>

      <el-table :data="roleList" border stripe>
        <!-- 展开列 -->
        <el-table-column type="expand">
          <template slot-scope="scope">
            <el-row
              :class="['bdbottom', i1 === 0 ? 'bdtop' : '','vcenter']"
              v-for="(item1, i1) in scope.row.children"
              :key="item1.id"
            >
              <!-- 渲染一级权限 -->
              <el-col :span="5">
                <el-tag
                  :key="item1.id"
                >{{item1.name}}</el-tag>
                <i class="el-icon-caret-right"></i>
              </el-col>
              <!-- 渲染二级权限和三级权限 -->
              <el-col :span="19">
                <el-row
                  :class="[i2 === 0 ? '' : 'bdtop', 'vcenter']"
                  v-for="(item2, i2) in item1.children"
                  :key="item2.id"
                >
                  <!-- 二级权限 -->
                  <el-col :span="6">
                    <el-tag
                      type="success"
                    >{{item2.name}}</el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
                  <!-- 三级权限 -->
                  <el-col :span="18">
                    <el-tag
                      type="warning"
                      v-for="(item3) in item2.children"
                      :key="item3.id"
                      closable
                      @close="removeRightById(scope.row,item3.id)"
                    >{{item3.name}}</el-tag>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </template>
        </el-table-column>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="角色名称" prop="role_name"></el-table-column>
        <el-table-column label="角色描述" prop="description"></el-table-column>
        <el-table-column label="操作" width="300px">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑</el-button>
            <el-button type="danger" size="mini" icon="el-icon-delete">删除</el-button>
            <el-button
              type="warning"
              size="mini"
              icon="el-icon-setting"
              @click="showSetRightDialog(scope.row)"
            >分配权限</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="分配权限" :visible.sync="setRightDialogVisible" width="50%" @close="serRightDialogClosed">
      <!-- 树形控件 -->
      <el-tree ref="treeRef" :data="rightsList" :props="treeProps" show-checkbox node-key="id" default-expand-all
      :default-checked-keys="defKeys"></el-tree>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotRights(defKeys)">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      roleList: [],
      setRightDialogVisible: false,
      rightsList: [],
      treeProps: {
          children: 'children',
          label: 'name'
        },
        defKeys:[],
        roleId: ''
    };
  },
  created() {
    this.getRolesList();
  },
  methods: {
    getRolesList() {
      this.$http({
        method: "GET",
        url: "roles"
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取角色列表失败！");
          }
          this.roleList = resp.data.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    removeRightById(role, rightId) {
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http({
            method: "DELETE",
            url:
              "rights/delPermission?roleId=" + role.id + "&rightId=" + rightId
          })
            .then(resp => {
              if (resp.data.code !== 200) {
                return this.$message.error("删除权限失败！");
              }
              role.children = resp.data.data;
              console.log(role.children);
              this.$message({
                type: "success",
                message: "删除成功!"
              });
            })
            .catch(error => {
              console.log(error);
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    showSetRightDialog(role) {
      this.roleId = role.id
      this.$http({
            method: "GET",
            url:
              "rights/selectRights"
          })
            .then(resp => {
              if (resp.data.code !== 200) {
                return this.$message.error("获取权限数据失败！");
              }
              this.rightsList = resp.data.data

              //递归获取三级节点的id
              this.getLeafKeys(role,this.defKeys)
              this.setRightDialogVisible = true
            })
            .catch(error => {
              console.log(error);
            });
      this.setRightDialogVisible = true;
    },
    //通过递归的形式，获取角色下所有三级权限的id，并保存到defKeys数组中
    getLeafKeys(node, arr) {
      //如果当前node节点不包含children属性，则是三级节点
      if(!node.children) {
        return arr.push(node.id)
      }

      node.children.forEach(item => {
        this.getLeafKeys(item,arr)
      });
    },
    serRightDialogClosed() {
      this.defKeys = []
    },
    allotRights(ParentNode) {
        const keys = [
          //获取选中节点的id
          ...this.$refs.treeRef
          .getCheckedKeys(),
          //获取半选中节点的id
          ...this.$refs.treeRef
          .getHalfCheckedNodes()
        ]

        this.$http({
            method: "POST",
            url:
              "rights/addRp",
            data:
            {
            roleId: this.roleId,
            rightsId: keys
          }
          })
            .then(resp => {
              if (resp.data.code !== 200) {
                return this.$message.error("分配权限失败！");
              }
              this.$message.success('分配权限成功！')
              this.getRolesList()
              this.setRightDialogVisible = false
            })
            .catch(error => {
              console.log(error);
            });
    }
  }
};
</script>

<style lang="less" scoped>
.el-tag {
  margin: 7px;
}
.bdtop {
  border-top: 1px solid #eee;
}
.bdbottom {
  border-bottom: 1px solid #eee;
}
.vcenter {
  display: flex;
  align-items: center;
}
</style>