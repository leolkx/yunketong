<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据字典管理管理</el-breadcrumb-item>
      <el-breadcrumb-item>数据字典数据项编辑</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
<!--          <el-button @click="showAddDialog()">新增数据项</el-button>-->
<!--          <el-button type="primary" @click="submitForm('addForm')">提交</el-button>-->
          <!--el-button @click="addDomain">新增值名</el-button-->
<!--          <el-button @click="resetForm('addForm')">重置</el-button>-->
      <el-form label-width="100px" class="addForm-dynamic">
        <el-form-item
          prop="dictName"
          label="字典键名称: "
        >
          <el-input v-model="editForm.dictName"></el-input>
        </el-form-item>
        <el-form-item
          prop="dataName"
          label="字典标识: "
        >
          <el-input v-model="editForm.dataName"></el-input>
        </el-form-item>
<!--        <el-form-item label="数据顺序">-->
<!--          <el-input v-model="editForm.dataOrder"></el-input>-->
<!--        </el-form-item>-->
      </el-form>
    </el-card>
    <el-card>
      <el-table :data="dataItemList" stripe>
        <el-table-column label="序号" prop="dataOrder"></el-table-column>
        <el-table-column label="值" prop="textValue"></el-table-column>
        <el-table-column label="文本" prop="dataName"></el-table-column>
        <el-table-column label="默认值" prop="textDefault"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <!-- 删除数据项按钮 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeItem(scope.row)"
            ></el-button>
<!--            编辑数据项-->
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row)"
            ></el-button>
            <el-button
              type="primary"
              icon="el-icon-arrow-up"
              size="mini"
              @click="upOrder(scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog
      title="新增数据项"
      :visible.sync="addItemVisible"
      width="50%"
      @close="addItemClosed"
    >
      <el-form :model="addItemForm" ref="addFormRef" label-width="80px">
        <el-form-item label="值">
          <el-input v-model="addItemForm.textValue"></el-input>
        </el-form-item>
        <el-form-item label="名">
          <el-input v-model="addItemForm.textName"></el-input>
        </el-form-item>
        <el-form-item label="默认值">
          <el-input v-model="addItemForm.textDefault"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addItemVisible = false">取 消</el-button>
        <el-button type="primary" @click="addItem">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 编辑对话框 -->
    <el-dialog
      title="数据字典详情"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed"
    >
      <el-form :model="editDDForm" ref="editDDFormRef" label-width="80px">
        <el-form-item label="原文本">
          <el-input v-model="editDDForm.dataName" disabled></el-input>
        </el-form-item>
<!--        <el-form-item label="原默认值">-->
<!--          <el-input v-model="editDDForm.textDefault" disabled></el-input>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="排序">-->
<!--          <el-input v-model="editDDForm.dataOrder"></el-input>-->
<!--        </el-form-item>-->
        <el-form-item label="新文本" prop="newDictName">
          <el-input v-model="editDDForm.newDataName"></el-input>
        </el-form-item>
        <el-form-item label="新默认值" prop="newDataName">
          <el-input v-model="editDDForm.textDefault"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editDD">确 定</el-button>
      </span>
    </el-dialog>
    <el-button @click="showAddDialog()">新增数据项</el-button>
    <el-button type="primary" @click="submitForm('editForm')">提交</el-button>
  </div>
</template>

<script>
export default {
  data () {
    let dataname
    let dictname
    dataname = this.$route.query.itemname
    dictname = this.$route.query.idemname
    return {
      options: [
        { value: true, label: '是' },
        { value: false, label: '否' }
      ],
      total: 0,
      // 详情页显示与隐藏
      addItemVisible: false,
      editDialogVisible: false,
      editDDForm: {
        dictName: this.$route.query.itemname,
        // dictName: this.$route.query.itemname,
        dataName: '',
        // textDefault: '',
        // dataOrder: '',
        newDataName: '',
        textDefault: ''
      },
      queryInfo: {
        getAll: false,
        // dictDescription: this.$route.query.idemname,
        dictName: this.$route.query.itemname,
        page: 1,
        pagesize: 100
      },
      dataItemList: [],
      editForm: {
        dataName: this.$route.query.itemname,
        dictName: this.$route.query.idemname
        // dataOrder: ''
      },
      addItemForm: {
        textValue: '',
        textName: '',
        textDefault: '',
        dictName: this.$route.query.idemname,
        dataName: this.$route.query.itemname,
        dataOrder: ''
      }
    }
  },
  created () {
    this.getDataItemList()
  },
  methods: {
    addItemClosed () {
      this.$refs.addFormRef.resetFields()
    },
    async getDataItemList () {
      // const { data: res } = await this.$http.get('/dataDictionary', {
      //   params: this.queryInfo
      // })
      const { data: res } = await this.$http.get('/dataDictionary?' + 'dictName=' + this.queryInfo.dictName + '&getAll=' + this.queryInfo.getAll + '&page=' + this.queryInfo.page + '&pageSize=' + this.queryInfo.pagesize)
      console.log("把我刚刚添加进去的数据项全部显示出来")
      console.log(this.queryInfo)
      console.log(res)
      if (res.state !== 'success') {
        return this.$message.error('获取数据项列表失败')
      }
      this.$message.success('获取数据项列表成功')
      this.total = res.result.length
      this.dataItemList = res.result
    },
    // 新增数据项
    showAddDialog () {
      this.addItemVisible = true
    },
    addItem () {
      // 提交请求前，表单预验证
      this.$refs.addFormRef.validate(async valid => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.put(
          'updateTextData',
          this.addItemForm
        )
        if (res.state !== 'success') {
          this.$message.error('新增数据项失败！')
        }
        // 隐藏添加用户对话框
        this.addItemVisible = false
        this.$message.success('新增数据项成功！')
        this.getDataItemList()
      })
    },
    // 编辑
    showEditDialog (res) {
      this.editDDForm.dataName = res.dataName
      // this.editDDForm.textDefault = res.textDefault
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
        console.log(this.editDDForm)
        if (res.state !== 'success') {
          this.$message.error('更新数据字典失败！')
        }
        // 隐藏添加用户对话框
        this.editDialogVisible = false
        this.$message.success('更新数据字典成功！')
        this.getDataItemList()
      })
    },
    // 删除数据项
    async removeItem (dataName) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该数据项, 是否继续?',
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
      console.log(dataName)
      const { data: res } = await this.$http.delete('dataItem?' + 'textValue=' + dataName.textValue)
      if (res.state !== 'success') return this.$message.error('删除数据项失败！')
      this.$message.success('删除数据项成功！')
      this.getDataItemList()
    },
    submitForm (formName) {
      this.$refs.addForm.validate(async valid => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        console.log(this.addForm)
        const { data: res } = await this.$http.post(
          '/修改数据字典提交接口',
          this.editForm
        )
        console.log(res)
        if (res.state !== 'success') {
          this.$message.error('修改数据字典失败！')
        }
        this.$message.success('修改数据字典成功！')
      })
    },
    // submitForm (formName) {
    //   this.$refs.addForm.validate(async valid => {
    //     // console.log(valid)
    //     // 表单预校验失败
    //     if (!valid) return
    //     console.log(this.addForm)
    //     const { data: res } = await this.$http.post(
    //       '/dataDictionary',
    //       this.addForm
    //     )
    //     console.log(res)
    //     if (res.state !== 'success') {
    //       this.$message.error('添加数据字典失败！')
    //     }
    //     this.$message.success('添加数据字典成功！')
    //   })
    // },
     resetForm (formName) {
       this.$refs[formName].resetFields()
     },
    // removeDomain (item) {
    //   var index = this.addForm.dataName.indexOf(item)
    //   if (index !== -1) {
    //     this.addForm.dataName.splice(index, 1)
    //   }
    // },
    // addDomain () {
    //   this.addForm.dataName.push({
    //     value: '',
    //     key: Date.now()
    //   })
    // }
  }
}
</script>

<style lang="less">
</style>
