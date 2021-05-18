<template>
  <div class="sa">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
      <el-breadcrumb-item>菜单列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="menulist-card">
      <el-row>
        <el-col :span="11">
          <el-button type="primary" @click="addDialogVisible = true">添加菜单</el-button>
        </el-col>
      </el-row>
      <el-table :data="menuInfo">
        <el-table-column type="expand">
          <template slot-scope="scopes">
            <el-table :data="scopes.row.children" style="width: 100%">
              <el-table-column type="index" label="#"></el-table-column>
              <el-table-column label="菜单名" prop="menuName"></el-table-column>
              <el-table-column label="跳转路径" prop="path"></el-table-column>
              <el-table-column label="ID" prop="id"></el-table-column>
              <el-table-column label="父ID" prop="parentId"></el-table-column>
              <el-table-column label="排序" prop="orderNum"></el-table-column>
              <el-table-column label="操作">
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
                    @click="removeMenuById(scope.row.id)"
                  ></el-button>
              </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="菜单名" prop="menuName"></el-table-column>
        <el-table-column label="跳转路径" prop="path"></el-table-column>
        <el-table-column label="ID" prop="id"></el-table-column>
        <el-table-column label="父ID" prop="parentId"></el-table-column>
        <el-table-column label="排序" prop="orderNum"></el-table-column>
        <el-table-column label="操作">
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
              @click="removeMenuById(scope.row.id)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog title="添加菜单" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
        <el-form-item label="菜单ID" prop="id">
          <el-input v-model="addForm.id"></el-input>
        </el-form-item>
        <el-form-item label="菜单名" prop="menuName">
          <el-input v-model="addForm.menuName"></el-input>
        </el-form-item>
        <el-form-item label="父菜单ID" prop="parentId">
          <el-input v-model="addForm.parentId"></el-input>
        </el-form-item>
        <el-form-item label="父菜单名" prop="parentName">
          <el-input v-model="addForm.parentName"></el-input>
        </el-form-item>
        <el-form-item label="显示顺序" prop="orderNum">
          <el-input v-model="addForm.orderNum"></el-input>
        </el-form-item>
        <el-form-item label="路由地址" prop="path">
          <el-input v-model="addForm.path"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="path">
          <el-input v-model="addForm.path"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMenu">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改菜单的对话框 -->
    <el-dialog
      title="修改菜单信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <!-- 内容主体 -->
      <el-form
        :model="editMenuForm"
        ref="editMenuFormRef"
        :rules="editMenuFormRules"
        label-width="100px"
      >
        <el-form-item label="菜单ID">
          <el-input v-model="editMenuForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="菜单名" prop="menuName">
          <el-input v-model="editMenuForm.menuName"></el-input>
        </el-form-item>
        <el-form-item label="父菜单ID" prop="parentId">
          <el-input v-model="editMenuForm.parentId"></el-input>
        </el-form-item>
        <el-form-item label="父菜单名" prop="parentName">
          <el-input v-model="editMenuForm.parentName"></el-input>
        </el-form-item>
        <el-form-item label="显示顺序" prop="orderNum">
          <el-input v-model="editMenuForm.orderNum"></el-input>
        </el-form-item>
        <el-form-item label="路由地址" prop="path">
          <el-input v-model="editMenuForm.path"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="MType">
          <el-select v-model="editMenuForm.MType" placeholder="请选择菜单类型" filterable allow-create default-first-option>
            <el-option v-for="item in menuTypeList" :label="item.value" :value="item.id" :key="item.id"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editMenu">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      username: localStorage.getItem('username'),
      menuTypeList: [
        {id: 'M', value: '目录'},
        {id: 'C', value: '菜单'},
        {id: 'F', value: '按钮'}
      ],
      menuInfo: [],
      queryInfo: {},
      addDialogVisible: false,
      addForm: {
        id: '',
        menuName: '',
        parentName: '',
        parentId: '',
        orderNum: '',
        path: '',
        MType: ''
      },
      addFormRules: {
        id: [
          { required: true, message: '请输入菜单ID', trigger: 'blur' }
        ],
        menuName: [
          { required: true, message: '请输入菜单名', trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '请输入父菜单ID', trigger: 'blur' }
        ],
        parentName: [
          { required: true, message: '请输入父菜单名', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '请输入显示顺序', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '请输入路由路径', trigger: 'blur' }
        ]
      },
      editDialogVisible: false,
      editMenuForm: {
        id: '',
        menuName: '',
        parentName: '',
        parentId: '',
        orderNum: '',
        path: ''
      },
      editMenuFormRules: {
        id: [
          { required: true, message: '请输入菜单ID', trigger: 'blur' }
        ],
        menuName: [
          { required: true, message: '请输入菜单名', trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '请输入父菜单ID', trigger: 'blur' }
        ],
        parentName: [
          { required: true, message: '请输入父菜单名', trigger: 'blur' }
        ],
        orderNum: [
          { required: true, message: '请输入显示顺序', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '请输入路由路径', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getMenuList()
  },
  methods: {
    async getMenuList () {
      const { data: res1 } = await this.$http.get('/super/users?username=' + this.username)
      const { data: res } = await this.$http.get('/userMenuTree/' + res1.result.roleId)
      if (res.state !== 'success') return this.$message.error('获取菜单信息失败')
      this.menuInfo = res.result
    },
    addMenu () {
      // 提交请求前，表单预验证
      this.$refs.addFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.post('menuAdd', this.addForm)
        if (res.state !== 'success') {
          this.$message.error('添加菜单失败！')
          return
        }
        this.$message.success('添加菜单成功！')
        // 隐藏添加对话框
        this.addDialogVisible = false
      })
    },
    // 监听添加用户对话框关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 删除菜单
    async removeMenuById (id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该菜单, 是否继续?',
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
      const { data: res } = await this.$http.delete('menuDelete/' + id)
      if (res.state !== 'success') return this.$message.error('删除菜单失败！')
      this.$message.success('删除菜单成功！')
    },
    showEditDialog (id) {
      this.editMenuForm.id = id
      this.editDialogVisible = true
    },
    editMenu () {
      // 提交请求前，表单预验证
      this.$refs.editMenuFormRef.validate(async valid => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.put(
          'menuEdit',
          this.editMenuForm
        )
        this.editDialogVisible = false
        if (res.state !== 'success') {
          return this.$message.error('更新菜单信息失败！')
        }
        // 隐藏编辑菜单对话框
        this.$message.success('更新菜单信息成功！')
      })
    },
    editDialogClosed () {
      this.$refs.editMenuFormRef.resetFields()
    }
  }
}
</script>

<style lang="less" scoped>
</style>
