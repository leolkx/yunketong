<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="users-card">
      <!-- 搜索框 -->
      <el-row class="searchRow" :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入用户名"
            class="inputSearch"
            v-model="queryText"
            clearable
            @clear="getUserList"
          >
            <el-button slot="append" icon="el-icon-search" @click="searchUsers"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>
      <!-- 用户列表区域 -->
      <el-table v-if="showUsersList" :data="userslist" stripe>
        <el-table-column type="index" label="#" fixed></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="联系方式" prop="phone"></el-table-column>
        <el-table-column label="角色" prop="roleName"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <!-- 修改用户按钮 -->
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.id)"
            ></el-button>
            <!-- 删除用户按钮 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeUserById(scope.row.id)"
            ></el-button>
            <!-- 分配角色按钮 -->
            <el-tooltip
              class="item"
              effect="dark"
              content="分配角色"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="mini"
                circle
                @click="showSetRole(scope.row)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <el-table v-if="!showUsersList" :data="searchUserslist" stripe>
        <el-table-column type="index" label="#" fixed></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="联系方式" prop="phone"></el-table-column>
        <el-table-column label="角色" prop="roleName"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <!-- 修改用户按钮 -->
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.id)"
            ></el-button>
            <!-- 删除用户按钮 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeUserById(scope.row.id)"
            ></el-button>
            <!-- 分配角色按钮 -->
            <el-tooltip
              class="item"
              effect="dark"
              content="分配角色"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="mini"
                circle
                @click="showSetRole(scope.row)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        align="right"
      ></el-pagination>
    </el-card>

    <!-- 添加用户的对话框 -->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="角色ID" prop="roleId">
          <el-input v-model="addForm.roleId"></el-input>
        </el-form-item>
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
    <el-dialog
      title="修改用户信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!-- 内容主体 -->
      <el-form
        :model="editUserForm"
        ref="editUserFormRef"
        :rules="editUserFormRules"
        label-width="70px"
      >
        <el-form-item label="用户ID">
          <el-input v-model="editUserForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="editUserForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="editUserForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editUserForm.email"></el-input>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="editUserForm.phone"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分配角色对话框 -->
    <el-dialog
      title="分配角色"
      :visible.sync="setRoleDialogVisible"
      width="50%"
      @close="setRoleDialogClosed"
    >
      <div>
        <p>当前用户：{{userInfo.username}}</p>
        <p>当前角色：{{userInfo.roleName}}</p>
        <p>
          分配角色：
          <el-select
            v-model="selectRoleId"
            filterable
            allow-create
            default-first-option
            placeholder="请选择角色"
          >
            <el-option
              v-for="item in rolesLsit"
              :key="item.id"
              :label="item.roleName"
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
import {getUserListPage, adUser, showRoleSet, saveRoleSet, requestLogin} from '../../api/api'

export default {
  data () {
    // 自定义邮箱规则
    var checkEmail = (rule, value, callback) => {
      const regEmail = /^\w+((-\w+)|(\.\w+))*@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
      if (regEmail.test(value) || !value) {
        // 合法邮箱
        return callback()
      }
      callback(new Error('请输入合法邮箱'))
    }
    // 自定义手机号规则
    var checkMobile = (rule, value, callback) => {
      const regMobile = /^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/
      if (regMobile.test(value) || !value) {
        return callback()
      }
      // 返回一个错误提示
      callback(new Error('请输入合法的手机号码'))
    }
    return {
      // 展示控制
      showUsersList: true,
      // 根据用户名模糊查询用户
      queryText: '',
      queryInfo: {
        // query: '',
        // 当前的页数
        page: 1,
        // 当前每页显示多少条数据
        pageSize: 10
      },
      userslist: [],
      searchUserslist: [],
      total: 0,
      // 控制添加用户的显示与隐藏
      addDialogVisible: false,
      // 添加用户的表单数据
      addForm: {
        roleId: '',
        username: '',
        password: '',
        email: '',
        phone: ''
      },
      // 添加表单的验证规则对象
      addFormRules: {
        roleId: [
          { required: true, message: '请输入角色ID号码', trigger: 'blur' }
        ],
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 15, message: '用户名长度在2-15之间', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 1, max: 15, message: '长度在1-15个字符', trigger: 'blur' }
        ],
        email: [{ validator: checkEmail, trigger: 'blur' }],
        phone: [
          { validator: checkMobile, trigger: 'blur' },
          { required: true, message: '请输入手机号', trigger: 'blur' }
        ]
      },
      // 修改用户
      editDialogVisible: false,
      editUserForm: {},
      // 编辑用户表单验证
      editUserFormRules: {
        password: [
          { min: 1, max: 15, message: '长度在1-15个字符', trigger: 'blur' }
        ],
        email: [{ validator: checkEmail, trigger: 'blur' }],
        phone: [{ validator: checkMobile, trigger: 'blur' }]
      },
      // 分配角色对话框
      setRoleDialogVisible: false,
      // 当前需要被分配角色的用户
      userInfo: {},
      // 所有角色数据列表
      rolesLsit: [],
      // 已选中的角色Id值
      selectRoleId: '',
      // 已选择的用户id
      userForRoleId: '',
      // 查询角色
      rQuery: {
        page: 1,
        pageSize: 10
      }
    }
  },
  created () {
    this.getUserList()
    this.getTotal()
  },
  methods: {
    // 模糊查询!!没用api分装
    async searchUsers () {
      const { data: res } = await this.$http.get(
        'super/users?username=' + this.queryText
      )
      if (res.state !== 'success') {
        return this.$message.error('查询用户列表失败')
      }
      this.showUsersList = false
      this.searchUserslist.splice(0, 1)
      this.searchUserslist.push(res.result)
      this.total = 1
    },
    async getUserList () {
      // const getuserListParams = {username: this.loginForm.username, password: this.loginForm.password}
      getUserListPage(this.queryInfo).then(res => {
        // let { msg, code, user,token } = res;
        if (res.state !== 'success') {
          this.$message.error('获取用户列表失败')
        } else {
          this.showUsersList = true
          this.userslist = res.result
        }
      })
    },
    // 获取总用户数,不分装
    async getTotal () {
      const { data: res } = await this.$http.get('super/users/total')
      this.total = res.result
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.getUserList()
    },
    // 监听添加用户对话框关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 监听 页码值 的改变
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getUserList()
    },
    // 添加用户
    addUser () {
      // 提交请求前，表单预验证
      this.$refs.addFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.post('super/users', this.addForm)
        if (res.state !== 'success') {
          console.log(res)
          this.$message.error('添加用户失败！')
          return
        }
        this.$message.success('添加用户成功！')
        // 隐藏添加用户对话框
        this.addDialogVisible = false
        // 重新获取用户列表
        this.getUserList()
      })
    },
    // 编辑用户信息,不封
    async showEditDialog (id) {
      const { data: res } = await this.$http.get('super/users?userId=' + id)
      if (res.state !== 'success') {
        return this.$message.error('查询用户信息失败！')
      }
      this.editUserForm = res.result
      this.editDialogVisible = true
    },
    // 监听修改用户对话框的关闭事件
    editDialogClosed () {
      this.$refs.editUserFormRef.resetFields()
    },
    // 修改用户信息
    editUser () {
      // 提交请求前，表单预验证
      // console.log(this.editUserForm)
      this.$refs.editUserFormRef.validate(async valid => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        const editForm = {
          password: this.editUserForm.password,
          phone: this.editUserForm.phone,
          email: this.editUserForm.email}
        const { data: res } = await this.$http.put(
          'super/users',
          editForm
          // this.editUserForm
        )
        if (res.state !== 'success') {
          this.$message.error('更新用户信息失败！')
        }
        // 隐藏添加用户对话框
        this.editDialogVisible = false
        this.$message.success('更新用户信息成功！')
        this.getUserList()
      })
    },
    // 删除用户
    async removeUserById (id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该用户, 是否继续?',
        '提示',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }
      ).catch(err => err)
      // 点击确定 返回值为：confirm
      // 点击取消 返回值为： cancel
      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消删除')
      }
      const { data: res } = await this.$http.delete('super/users?userId=' + id)// 不封
      if (res.state !== 'success') return this.$message.error('删除用户失败！')
      this.$message.success('删除用户成功！')
      this.getUserList()
    },
    // 展示分配角色的对话框
    async showSetRole (role) {
      this.userInfo = role
      // 展示对话框之前，获取所有角色列表
      showRoleSet().then(res => {
        // let { msg, code, user,token } = res;
        if (res.state !== 'success') {
          this.$message.error('获取角色列表失败！')
        } else {
          this.userForRoleId = this.userInfo.id
          this.rolesLsit = res.result
          this.setRoleDialogVisible = true
        }
      })
    },
    // 分配角色
    async saveRoleInfo () {
      if (!this.selectRoleId) {
        return this.$message.error('请选择要分配的角色')
      }
      const saveRoleParams = {id: this.userForRoleId, roleId: this.selectRoleId}
      saveRoleSet(saveRoleParams).then(res => {
        // let { msg, code, user,token } = res;
        if (res.state !== 'success') {
          this.$message.error('更新用户角色失败！')
        } else {
          this.$message.success('更新角色成功！')
          this.getUserList()
          this.setRoleDialogVisible = false
        }
      })
      // const { data: res } = await this.$http.put('super/users', {
      //   id: this.userForRoleId,
      //   roleId: this.selectRoleId
      // })
      // if (res.state !== 'success') {
      //   return this.$message.error('更新用户角色失败！')
      // }
      // this.$message.success('更新角色成功！')
      // this.getUserList()
      // this.setRoleDialogVisible = false
    },
    // 分配角色对话框关闭事件
    setRoleDialogClosed () {
      this.selectRoleId = ''
      this.userInfo = {}
    }
  }
}
</script>

<style lang="less" scoped>
</style>
