<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据字典管理管理</el-breadcrumb-item>
      <el-breadcrumb-item>数据字典列表</el-breadcrumb-item>
    </el-breadcrumb>
    <!--卡片区域-->
    <el-card class="manage-card">
      <!-- 搜索框 -->
      <el-row class="searchRow" :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入数据字典value关键字"
            class="inputSearch"
            v-model="queryInfo.dataName"
            clearable
            @clear="getDataDictionaryList"
          >
            <el-button slot="append" icon="el-icon-search" @click="getDataDictionaryList"></el-button>
          </el-input>
        </el-col>
        <el-table :data="dataDictionaryList" stripe>
          <el-table-column label="#" type="index" fixed></el-table-column>
          <el-table-column label="DictName" prop="dictDescription"></el-table-column>
          <el-table-column label="DataName" prop="dictName"></el-table-column>
          <el-table-column label="备注"></el-table-column>
          <el-table-column label="操作" width="200" fixed="right">
            <template slot-scope="scope">
              <!-- 编辑数据字典按钮 -->
              <el-button
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="showEditDialog(scope.row)"
              ></el-button>
              <!-- 删除数据字典按钮 -->
              <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeDD(scope.row)"
            ></el-button>
              <!-- 查看数据项 -->
              <el-button
                type="danger"
                icon="el-icon-more"
                size="mini"
                @click="getText(scope.row)"
              ></el-button>
<!--              <router-link :to="{name:'/data-dict-edit',query:{itemname: scope.row.dataName,idemname: scope.row.dictName}}">跳转数据项</router-link>-->
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
      </el-row>
    </el-card>
    <!-- 详情页对话框 -->
    <el-dialog
      title="数据字典详情"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <el-form :model="editDDForm" :rules="editFormRules" ref="editDDFormRef" label-width="80px">
        <el-form-item label="原key">
          <el-input v-model="editDDForm.dictName" disabled></el-input>
        </el-form-item>
        <el-form-item label="原value">
          <el-input v-model="editDDForm.dataName" disabled></el-input>
        </el-form-item>
        <el-form-item label="排序">
          <el-input v-model="editDDForm.dataOrder"></el-input>
        </el-form-item>
        <el-form-item label="新key" prop="newDictName">
          <el-input v-model="editDDForm.newDictName"></el-input>
        </el-form-item>
        <el-form-item label="新value" prop="newDataName">
          <el-input v-model="editDDForm.newDataName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editDD">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      total: 0,
      itemname: '',
      // 详情页显示与隐藏
      editDialogVisible: false,
      queryInfo: {
        getAll: 1,
        dictName: '',
        dataName: '',
        // 当前的页数
        page: 1,
        // 当前每页显示多少条数据
        pageSize: 10
      },
      dataDictionaryList: [],
      // 编辑数据字典信息
      editDDForm: {
        dictName: '',
        dataName: '',
        dataOrder: '',
        newDictName: '',
        newDataName: ''
      },
      // 添加表单的验证规则对象
      editFormRules: {
        newDictName: [
          { required: true, message: '请输入新key', trigger: 'blur' },
          { min: 2, max: 15, message: '用户名长度在2-15之间', trigger: 'blur' }
        ],
        newDataName: [
          { required: true, message: '请输入新value', trigger: 'blur' },
          { min: 1, max: 15, message: '长度在1-15之间', trigger: 'blur' }
        ]
      }
    }
  },
  created () {
    this.getDataDictionaryList()
  },
  methods: {
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    async getDataDictionaryList () {
      const { data: res } = await this.$http.get('/dataDictionary', {
        params: this.queryInfo
      })
      console.log(res)
      if (res.state !== 'success') {
        return this.$message.error('获取数据字典列表失败')
      }
      this.$message.success('获取数据字典列表成功')
      this.total = res.result.length
      this.dataDictionaryList = res.result
    },
    // 监听 pagesize 改变的事件
    handleSizeChange (newSize) {
      this.queryInfo.pageSize = newSize
      this.getDataDictionaryList()
    },
    // 监听 页码值 的改变
    handleCurrentChange (newPage) {
      this.queryInfo.page = newPage
      this.getDataDictionaryList()
    },
    showEditDialog (res) {
      this.editDDForm.dictName = res.dictName
      this.editDDForm.dataName = res.dataName
      this.editDialogVisible = true
    },
    // 监听修改用户对话框的关闭事件
    editDialogClosed () {
      this.$refs.editDDFormRef.resetFields()
    },
    // 修改数据字典
    editDD () {
      // 提交请求前，表单预验证
      this.$refs.editDDFormRef.validate(async valid => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.put(
          'dataDictionary',
          this.editDDForm
        )
        if (res.state !== 'success') {
          this.$message.error('更新数据字典失败！')
        }
        // 隐藏添加用户对话框
        this.editDialogVisible = false
        this.$message.success('更新数据字典成功！')
        this.getDataDictionaryList()
      })
    },
    // 删除数据字典
    async removeDD (dataForm) {
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
      console.log(dataForm)
      const { data: res } = await this.$http.delete('dataDictionary?dictName=' + dataForm.dictName + '&dataName=' + dataForm.dataName)
      if (res.state !== 'success') return this.$message.error('删除数据字典失败！')
      this.$message.success('删除数据字典成功！')
      this.getDataDictionaryList()
    },
    // 获取数据项
    async getText (dataForm) {
      this.$router.push({
        path: '/data-dict-edit',
        query: {
          itemname: dataForm.dictName,
          idemname: dataForm.dictDescription
        }
      })
    }
  }
}
</script>

<style lang="less" scoped>
</style>
