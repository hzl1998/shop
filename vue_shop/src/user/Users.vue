<template>
  <div style="padding: 20px">
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
          <el-input placeholder="请输入用户名称" v-model="username" clearable @clear="searchUserList">
            <el-button slot="append" icon="el-icon-search" @click="searchUserList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
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
            <el-switch v-model="scope.row.enabled" disabled v-if="scope.row.role_name === '超级管理员'"></el-switch>
            <el-switch v-model="scope.row.enabled" @change="userStateChanged(scope.row)" v-else></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" prop="create_time" :formatter="dateForma"></el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.id)"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeUserById(scope.row.id)"
            ></el-button>

            <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="mini"
                @click="setRole(scope.row)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[6, 8, 10, 20]"
        :page-size="rows"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>

    <!-- 添加用户的对话框 -->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="addForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户的对话框 -->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <el-form-item label="用户名">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="editForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分配角色的对话框 -->
    <el-dialog
      title="分配角色"
      :visible.sync="setRoleDialogVisible"
      width="50%"
      @close="setRoleDialogClosed"
    >
      <div>
        <p>当前的用户： {{userInfo.username}}</p>
        <p>当前的角色： {{userInfo.role_name}}</p>
        <p>
          分配新角色：
          <el-select v-model="selectedRoleId" placeholder="请选择">
            <el-option
              v-for="item in roleList"
              :key="item.id"
              :label="item.role_name"
              :value="item.id"
            ></el-option>
          </el-select>
        </p>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import moment from "moment"; //导入文件
export default {
  data() {
    // 手机号验证
    var checkPhone = (rule, value, callback) => {
      const phoneReg = /^1[3|4|5|6|7|8][0-9]{9}$/;
      if (!value) {
        return callback(new Error("请输入手机号码"));
      }
      setTimeout(() => {
        if (!Number.isInteger(+value)) {
          callback(new Error("请输入数字值"));
        } else {
          if (phoneReg.test(value)) {
            callback();
          } else {
            callback(new Error("电话号码格式不正确"));
          }
        }
      }, 100);
    };
    return {
      username: "",
      page: 1,
      rows: 6,
      userList: [],
      total: 0,
      addDialogVisible: false,
      addForm: {
        username: "",
        password: "",
        email: "",
        phone: ""
      },
      addFormRules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 15, message: "长度在 6 到 15 个字符", trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          { type: "email", message: "请输入正确的邮箱地址", trigger: "blur" }
        ],
        phone: [{ required: true, validator: checkPhone, trigger: "blur" }]
      },
      editDialogVisible: false,
      editForm: {},
      editFormRules: {
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          { type: "email", message: "请输入正确的邮箱地址", trigger: "blur" }
        ],
        phone: [{ required: true, validator: checkPhone, trigger: "blur" }]
      },
      setRoleDialogVisible: false,
      userInfo: {},
      roleList: [],
      selectedRoleId: ""
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
          if (resp.data.code === 403) {
            return this.$message.error("无权访问！");
          }
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
      return moment(data).format("YYYY-MM-DD HH:mm:ss");
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
    },
    //监听switch开关状态的改变
    userStateChanged(userinfo) {
      this.$http({
        method: "PUT",
        url:
          "users/status?userId=" + userinfo.id + "&enabled=" + userinfo.enabled
      })
        .then(resp => {
          if (resp.data.code === 403) {
            return this.$message.error("无权访问！");
          }
          if (resp.data.code !== 200) {
            userinfo.enabled = !userinfo.enabled;
            return this.$message.error("更新用户状态失败！");
          }
          this.$message.success("更新用户状态成功！");
        })
        .catch(error => {
          console.log(error);
        });
    },
    searchUserList() {
      this.page = 1;
      this.getUserList();
    },
    //监听添加用户对话框的关闭事件
    addDialogClosed() {
      this.$refs.addFormRef.resetFields();
    },
    addUser() {
      this.$refs.addFormRef.validate(valid => {
        if (!valid) return;
        this.$http({
          method: "POST",
          url: "users/addUser",
          data: {
            username: this.addForm.username,
            password: this.addForm.password,
            email: this.addForm.email,
            phone: this.addForm.phone
          },
          dataType: "json"
        })
          .then(resp => {
            if (resp.data.code === 403) {
              return this.$message.error("无权访问！");
            }
            if (resp.data.code !== 200) {
              return this.$message.error("添加用户失败！");
            }
            this.$message.success("添加用户成功！");
            //隐藏添加用户的对话框
            this.addDialogVisible = false;
            this.getUserList();
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
        url: "users/getUserById?id=" + id
      })
        .then(resp => {
          if (resp.data.code === 403) {
            return this.$message.error("无权访问！");
          }
          if (resp.data.code !== 200) {
            return this.$message.error("查询用户信息失败！");
          }
          this.editForm = resp.data.data;
          this.editDialogVisible = true;
        })
        .catch(error => {
          console.log(error);
        });
      this.editDialogVisible = true;
    },
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
    editUserInfo() {
      this.$refs.editFormRef.validate(valid => {
        if (!valid) return;
        console.log(this.editForm);
        this.$http({
          method: "PUT",
          url: "users/updateUserById",
          data: {
            id: this.editForm.id,
            email: this.editForm.email,
            phone: this.editForm.phone
          },
          dataType: "json"
        })
          .then(resp => {
            if (resp.data.code === 403) {
              return this.$message.error("无权访问！");
            }
            console.log(resp.data);
            if (resp.data.code !== 200) {
              return this.$message.error("更新用户信息失败！");
            }
            this.$message.success("更新用户信息成功！");
            this.editDialogVisible = false;
            this.getUserList();
          })
          .catch(error => {
            console.log(error);
          });
      });
    },
    removeUserById(id) {
      this.$confirm("此操作将永久删除该用户, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(resp => {
          this.$http({
            method: "DELETE",
            url: "users/delUserById?id=" + id
          })
            .then(resp => {
              if (resp.data.code === 403) {
                return this.$message.error("无权访问！");
              }
              if (resp.data.code !== 200) {
                return this.$message.error("删除用户失败！");
              }
              this.$message.success("删除用户成功！");
              this.getUserList();
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
    setRole(user) {
      this.userInfo = user;
      this.$http({
        method: "GET",
        url: "roles/getAllRoleName"
      })
        .then(resp => {
          if (resp.data.code === 403) {
            return this.$message.error("无权访问！");
          }
          if (resp.data.code !== 200) {
            return this.$message.error("获取角色列表失败！");
          }
          this.roleList = resp.data.data;
        })
        .catch(error => {
          console.log(error);
        });
      this.setRoleDialogVisible = true;
    },
    saveRoleInfo() {
      if (!this.selectedRoleId) {
        return this.$message.error("请选择要分配的角色！");
      }
      this.$http({
        method: "POST",
        url:
          "roles/addRu?roleId=" +
          this.selectedRoleId +
          "&userId=" +
          this.userInfo.id
      })
        .then(resp => {
          if (resp.data.code === 403) {
            return this.$message.error("无权访问！");
          }
          if (resp.data.code !== 200) {
            return this.$message.error("更新角色失败！");
          }
          this.$message.success("更新角色成功！");
          this.getUserList();
          this.setRoleDialogVisible = false;
        })
        .catch(error => {
          console.log(error);
        });
    },
    setRoleDialogClosed() {
      this.selectedRoleId = "";
      this.userInfo = {};
    }
  }
};
</script>

<style lang="less" scoped>
</style>