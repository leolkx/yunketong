<template>
  <div class="sa">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>参数设置</el-breadcrumb-item>
      <el-breadcrumb-item>参数设置</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="menulist-card">
      <!-- 搜索框 -->
      <el-row class="searchRow" :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入搜索内容" class="inputSearch">
            <el-button slot="append" icon="el-icon-search"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加参数</el-button>
        </el-col>
      </el-row>
      <el-table :data="params">
        <el-table-column type="index" label="#" fixed></el-table-column>
<!--        <el-table-column label="orgCode" prop="orgCode"></el-table-column>-->
        <el-table-column label="参数编码" prop="paramCode"></el-table-column>
        <el-table-column label="参数名称" prop="paramName"></el-table-column>
        <el-table-column label="参数信息" prop="paramDesc"></el-table-column>
        <el-table-column label="操作" prop="other">
          <template slot-scope="scope">
            <!-- 修改参数按钮 -->
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row)"
            ></el-button>
            <!-- 删除参数按钮 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeParamById(scope.row.id)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 添加参数的对话框 -->
    <el-dialog title="添加参数" :visible.sync="addDialogVisible" width="50%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="80px">
        <el-form-item label="org码" prop="orgCode">
          <el-input v-model="addForm.orgCode"></el-input>
        </el-form-item>
        <el-form-item label="参数ID" prop="paramCode">
          <el-input v-model="addForm.paramCode"></el-input>
        </el-form-item>
        <el-form-item label="参数名称" prop="paramName">
          <el-input v-model="addForm.paramName"></el-input>
        </el-form-item>
        <el-form-item label="参数值" prop="paramDesc">
          <el-input v-model="addForm.paramDesc"></el-input>
        </el-form-item>
      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addParms">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改参数的对话框 -->
    <el-dialog
      title="修改参数信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
      center
    >
      <!-- 内容主体 -->
      <el-form
        :model="editParamForm"
        ref="editParamFormRef"
        :rules="editParmFormRules"
        label-width="100px"
      >
        <el-form-item label="ID">
          <el-input v-model="editParamForm.id" disabled></el-input>
        </el-form-item>
        <el-form-item label="paramCode">
          <el-input v-model="editParamForm.paramCode" clearable></el-input>
        </el-form-item>
        <el-form-item label="paramName">
          <el-input v-model="editParamForm.paramName" clearable></el-input>
        </el-form-item>
        <el-form-item label="paramDesc">
          <el-input v-model="editParamForm.paramDesc" clearable></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editParam">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      queryInfo: {
        orgCode: '10003',
        // 当前的页数
        page: 1,
        // 当前每页显示多少条数据
        pageSize: 5
      },
      params: [],
      total: 0,
      // 控制添加参数的显示与隐藏
      addDialogVisible: false,
      // 添加参数的表单数据
      addForm: {
        orgCode: '',
        paramCode: '',
        paramName: '',
        paramDesc: ''
      },
      // 添加表单的验证规则对象
      addFormRules: {
        paramCode: [
          { required: true, message: '请输入paramCode', trigger: 'blur' },
          { min: 1, max: 15, message: '用户名长度在1-15之间', trigger: 'blur' }
        ],
        paramName: [
          { required: true, message: '请输入paramName', trigger: 'blur' },
          { min: 1, max: 15, message: '长度在1-15个字符', trigger: 'blur' }
        ],
        paramDesc: [
          { required: true, message: '请输入paramDesc', trigger: 'blur' },
          { min: 1, max: 15, message: '长度在1-15个字符', trigger: 'blur' }
        ]
      },
      // 修改参数
      editParamForm: {
        id: '',
        paramCode: '',
        paramName: '',
        paramDesc: ''
      },
      editDialogVisible: false,
      // 编辑参数表单的验证规则对象
      editParmFormRules: {
        paramCode: [
          { required: true, message: '请输入paramCode', trigger: 'blur' },
          { min: 1, max: 15, message: '用户名长度在1-15之间', trigger: 'blur' }
        ],
        paramName: [
          { required: true, message: '请输入paramName', trigger: 'blur' },
          { min: 1, max: 15, message: '长度在1-15个字符', trigger: 'blur' }
        ],
        paramDesc: [
          { required: true, message: '请输入paramDesc', trigger: 'blur' },
          { min: 1, max: 15, message: '长度在1-15个字符', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getParamsList()
  },
  methods: {
    async getParamsList () {
      const { data: res } = await this.$http.get('params/class', {
        params: this.queryInfo
      })
      if (res.state !== 'success') {
        return this.$message.error('获取参数列表失败')
      }
      console.log(res)
      this.showUsersList = true
      this.params = res.result
    },
    // 新增参数
    addParms () {
      // 提交请求前，表单预验证
      this.$refs.addFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.post(
          'params/class',
          this.addForm
        )
        if (res.state !== 'success') {
          this.$message.error('添加参数失败！')
        }
        this.$message.success('添加参数成功！')
        // 隐藏添加用户对话框
        this.addDialogVisible = false
        // 重新获取用户列表
        this.getParamsList()
      })
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.getUserList()
    },
    // 监听添加参数对话框关闭事件
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    // 监听 页码值 的改变
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getUserList()
    },
    // 修改参数对话框显示
    showEditDialog (data) {
      this.editParamForm.id = data.id
      this.editParamForm.paramCode = data.paramCode
      this.editParamForm.paramName = data.paramName
      this.editParamForm.paramDesc = data.paramDesc
      this.editDialogVisible = true
    },
    // 提交修改参数
    editParam () {
      // 提交请求前，表单预验证
      this.$refs.editParamFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.put(
          'params/class',
          this.editParamForm
        )
        if (res.state !== 'success') {
          this.$message.error('修改参数失败！')
        }
        this.$message.success('修改参数成功！')
        // 隐藏添加用户对话框
        this.editDialogVisible = false
        // 重新获取用户列表
        this.getParamsList()
      })
    },
    // 监听修改参数对话框的关闭事件
    editDialogClosed () {
      this.$refs.editParamFormRef.resetFields()
    },
    // 删除参数
    async removeParamById (id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该参数, 是否继续?',
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
      const { data: res } = await this.$http.delete('params/class?paramId=' + id)
      if (res.state !== 'success') return this.$message.error('删除参数失败！')
      this.$message.success('删除参数成功！')
      this.getParamsList()
    }
  }
}
</script>

<style lang="less" scoped>
</style>
