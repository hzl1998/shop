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
          <el-button type="primary" @click="addDialogVisible = true">添加角色</el-button>
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
                  closable
                  @close="removeRightById(scope.row,item1.id)"
                  v-if="item1.checked == 1"
                >{{item1.name}}</el-tag>
                <i class="el-icon-caret-right" v-if="item1.checked == 1"></i>
              </el-col>
              <!-- 渲染二级权限和三级权限 -->
              <el-col :span="19">
                <el-row
                  :class="[i2 === 0 ? '' : 'bdtop', 'vcenter']"
                  v-for="(item2, i2) in item1.children"
                  :key="item2.id"
                >
                  <!-- 二级权限 -->
                  <el-col :span="7">
                    <el-tag
                      type="success"
                      closable
                      @close="removeRightById(scope.row,item2.id)"
                      v-if="item2.checked == 1"
                    >{{item2.name}}</el-tag>
                    <i class="el-icon-caret-right" v-if="item2.checked == 1"></i>
                  </el-col>
                  <!-- 三级权限 -->
                  <el-col :span="17">
                    <div v-for="(item3) in item2.children" :key="item3.id" style="float: left;">
                      <el-tag
                        type="warning"
                        closable
                        :key="item3.id"
                        :disable-transitions="false"
                        v-if="item3.checked == 1"
                        @close="removeRightById(scope.row,item3.id)"
                      >{{item3.name}}</el-tag>
                    </div>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </template>
        </el-table-column>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="角色名称" prop="role_name"></el-table-column>
        <el-table-column label="角色描述" prop="description"></el-table-column>
        <el-table-column label="操作" width="400px">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              @click="showEditDialog(scope.row.id)"
            >编辑</el-button>
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              v-if="scope.row.role_name == '超级管理员'"
              disabled
            >删除</el-button>
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              v-else
              @click="removeRoleById(scope.row.id)"
            >删除</el-button>
            <el-button
              type="success"
              size="mini"
              icon="el-icon-s-operation"
              @click="showSetMenuDialog(scope.row)"
            >分配菜单</el-button>
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

    <el-dialog
      title="分配权限"
      :visible.sync="setRightDialogVisible"
      width="50%"
      @close="setRightDialogClosed"
    >
      <!-- 树形控件 -->
      <el-tree
        ref="treeRef"
        :data="rightsList"
        :props="treeProps"
        show-checkbox
        node-key="id"
        default-expand-all
        :default-checked-keys="defKeys"
      ></el-tree>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRightDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotRights(defKeys)">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="分配菜单"
      :visible.sync="setMenuDialogVisible"
      width="50%"
      @close="setMenuDialogClosed"
    >
      <!-- 树形控件 -->
      <el-tree
        ref="mtreeRef"
        :data="MenuList"
        :props="treeProps"
        show-checkbox
        node-key="id"
        default-expand-all
        :default-checked-keys="mKeys"
      ></el-tree>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="setMenuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="allotMenus(mKeys)">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 添加角色的对话框 -->
    <el-dialog title="添加角色" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="角色名" prop="role_name">
          <el-input v-model="addForm.role_name"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="addForm.description"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRole">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改角色的对话框 -->
    <el-dialog
      title="修改角色信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <el-form-item label="角色名" prop="role_name">
          <el-input v-model="editForm.role_name"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="editForm.description"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editRole">确 定</el-button>
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
      setMenuDialogVisible: false,
      rightsList: [],
      MenuList: [],
      treeProps: {
        children: "children",
        label: "name"
      },
      defKeys: [],
      mKeys: [],
      roleId: "",
      addDialogVisible: false,
      addForm: {
        role_name: "",
        description: ""
      },
      addFormRules: {
        role_name: [
          { required: true, message: "请输入角色名", trigger: "blur" }
        ],
        description: [
          { required: true, message: "请输入描述", trigger: "blur" }
        ]
      },
      editFormRules: {
        role_name: [
          { required: true, message: "请输入角色名", trigger: "blur" }
        ],
        description: [
          { required: true, message: "请输入描述", trigger: "blur" }
        ]
      },
      editDialogVisible: false,
      editForm: {
        role_name: "",
        description: ""
      }
    };
  },
  created() {
    this.getRolesList();
    this.getMenuList();
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
      this.$confirm("此操作将永久删除该权限, 是否继续?", "提示", {
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
      this.roleId = role.id;
      this.$http({
        method: "GET",
        url: "rights/selectRights"
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取权限数据失败！");
          }
          this.rightsList = resp.data.data;

          //递归获取三级节点的id
          this.getLeafKeys(role, this.defKeys);
          console.log(role);
          this.setRightDialogVisible = true;
        })
        .catch(error => {
          console.log(error);
        });
    },
    showSetMenuDialog(role) {
      this.roleId = role.id;
      this.$http({
        method: "GET",
        url: "getMenusByRoleId?roleId=" + this.roleId
      })
        .then(resp => {
          if (resp.data.data != null) {
            resp.data.data.forEach(item => {
              item.children.forEach(item1 => {
                if (item1.checked == 1) {
                  this.mKeys.push(item1.id);
                }
              });
            });
          }
          //mKeys改变之后没有重新渲染选中的数据，所以用this.$refs.mtreeRef.setCheckedKeys()来改变选中状态，但此时又会有另一个问题,当弹框没有渲染的时候,由于tree控件dom没有加载,setCheckedKeys是不存在的,会报错,所以我们需要使用this.$nextTick(callback)方法,该方法会在dom加载完毕之后,执行回调函数
          this.$nextTick(() => {
            this.$refs.mtreeRef.setCheckedKeys(this.mKeys);
          });
          console.log(this.mKeys);
          this.setMenuDialogVisible = true;
        })
        .catch(error => {
          console.log(error);
        });
    },
    getMenuList() {
      this.$http({
        method: "GET",
        url: "allMenus"
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("获取全部菜单数据失败！");
          }
          this.MenuList = resp.data.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    //通过递归的形式，获取角色下所有三级权限的id，并保存到defKeys数组中
    getLeafKeys(node, arr) {
      //如果当前node节点不包含children属性，则是三级节点
      if (!node.children) {
        if (node.checked === 1) {
          return arr.push(node.id);
        } else {
          return;
        }
      }

      node.children.forEach(item => {
        this.getLeafKeys(item, arr);
      });
    },
    setRightDialogClosed() {
      this.defKeys = [];
    },
    setMenuDialogClosed() {
      this.mKeys = [];
    },
    allotRights(ParentNode) {
      const keys = [
        //获取选中节点的id
        ...this.$refs.treeRef.getCheckedKeys(),
        //获取半选中节点的id
        ...this.$refs.treeRef.getHalfCheckedNodes()
      ];

      this.$http({
        method: "POST",
        url: "rights/addRp",
        data: {
          roleId: this.roleId,
          rightsId: keys
        }
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("分配权限失败！");
          }
          this.$message.success("分配权限成功！");
          this.getRolesList();
          this.setRightDialogVisible = false;
        })
        .catch(error => {
          console.log(error);
        });
    },
    allotMenus(ParentNode) {
      const keys = [
        //获取选中节点的id
        ...this.$refs.mtreeRef.getCheckedKeys(),
        //获取半选中节点的id
        ...this.$refs.mtreeRef.getHalfCheckedNodes()
      ];

      this.$http({
        method: "POST",
        url: "addRm",
        data: {
          roleId: this.roleId,
          mids: keys
        }
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("分配菜单失败！");
          }
          this.$message.success("分配菜单成功！");
          this.setMenuDialogVisible = false;
        })
        .catch(error => {
          console.log(error);
        });
    },
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    addRole() {
      this.$refs.addFormRef.validate(valid => {
        if (!valid) return;
        this.$http({
          method: "POST",
          url: "roles/addRole",
          data: {
            role_name: this.addForm.role_name,
            description: this.addForm.description
          },
          dataType: "json"
        })
          .then(resp => {
            if (resp.data.code !== 200) {
              return this.$message.error("添加角色失败！");
            }
            this.$message.success("添加角色成功！");
            //隐藏添加用户的对话框
            this.addDialogVisible = false;
            this.getRolesList();
          })
          .catch(error => {
            console.log(error);
          });
      });
    },
    showEditDialog(id) {
      //console.log(id)
      this.$http({
        method: "GET",
        url: "roles/getRoleById?id=" + id
      })
        .then(resp => {
          if (resp.data.code !== 200) {
            return this.$message.error("查询角色信息失败！");
          }
          this.editForm = resp.data.data;
          this.editDialogVisible = true;
        })
        .catch(error => {
          console.log(error);
        });
    },
    editDialogClosed() {
      this.editForm = {};
      this.$refs.editFormRef.resetFields();
    },
    editRole() {
      this.$refs.editFormRef.validate(valid => {
        if (!valid) return;
        console.log(this.editForm.id);
        this.$http({
          method: "PUT",
          url: "roles/updateRoleById",
          data: {
            id: this.editForm.id,
            role_name: this.editForm.role_name,
            description: this.editForm.description
          },
          dataType: "json"
        })
          .then(resp => {
            console.log(resp.data);
            if (resp.data.code !== 200) {
              return this.$message.error("更新角色信息失败！");
            }
            this.$message.success("更新角色信息成功！");
            this.editDialogVisible = false;
            this.getRolesList();
          })
          .catch(error => {
            console.log(error);
          });
      });
    },
    removeRoleById(id) {
      this.$confirm("此操作将永久删除该角色, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(resp => {
          this.$http({
            method: "DELETE",
            url: "roles/delRoleById?id=" + id
          })
            .then(resp => {
              if (resp.data.code !== 200) {
                return this.$message.error("删除角色失败！");
              }
              this.$message.success("删除角色成功！");
              this.getRolesList();
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