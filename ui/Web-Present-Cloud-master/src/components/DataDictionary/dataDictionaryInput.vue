<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>数据字典管理管理</el-breadcrumb-item>
      <el-breadcrumb-item>数据字典录入</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card>
      <el-form :model="addForm" ref="addForm" label-width="100px" class="addForm-dynamic">
        <el-form-item
          prop="dictName"
          label="字典键名称: "
          :rules="[{ required: true, message: '请输入键名称', trigger: 'blur' }]"
        >
          <el-input v-model="addForm.dictName"></el-input>
        </el-form-item>
        <el-form-item
          prop="dataName"
          label="字典标识: "
          :rules="[{ required: true, message: '请输入关键字', trigger: 'blur' }]"
        >
          <el-input v-model="addForm.dataName"></el-input>
        </el-form-item>
        <el-form-item label="数据顺序">
          <el-input v-model="addForm.dataOrder"></el-input>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card>
      <el-table :data="dataItemList" stripe>
<!--        <el-table-column label="序号" type="index" fixed></el-table-column>-->
        <el-table-column label="关键字" prop="textValue"></el-table-column>
        <el-table-column label="名称" prop="textName"></el-table-column>
        <el-table-column label="默认值" prop="textDefault">
          <template slot-scope="scope">
            <el-switch v-model="scope.row.textDefault" active-value="1" inactive-value="0">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="scope">
          <!--  修改-->
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="handleEdit(scope.$index, scope.row)"></el-button>
            <!-- 删除数据项按钮 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeItem(scope.row)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-form>
        <el-form-item align="right">
<!--          <el-button @click="resetForm('addForm')">新增数据项</el-button>-->
          <el-button @click="showAddDialog()">新增数据项</el-button>
          <el-button type="primary" @click="submitForm('addForm')">提交</el-button>
          <!--el-button @click="addDomain">新增值名</el-button-->
          <el-button @click="resetForm('addForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
<!--    后端先添加数据项，最后把字典值给他一起添加-->
    <el-dialog
      title="新增数据项"
      :visible.sync="addItemVisible"
      width="50%"
      @close="addItemClosed"
    >
      <el-form :model="addItemForm" ref="addFormRef" label-width="80px">
        <el-form-item label="值">
          <el-input v-model="addItemForm.textValue" @blur="handleValue(addItemForm.textValue)"></el-input>
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="addItemForm.textName"></el-input>
        </el-form-item>
        <el-form-item label="默认值">
          <el-input v-model="addItemForm.textDefault" @blur="handleDefault(addItemForm.textDefault)"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addItemVisible = false">取 消</el-button>
        <el-button type="primary" @click="addItem">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  subEditDic
} from '../../api/api'
export default {
  data () {
    return {
      options: [
        { value: true, label: '是' },
        { value: false, label: '否' }
      ],
      addFormSum: [],
      dataItemList: [],
      addItemVisible: false,
      addForm: {
        dictName: '',
        dataName: '',
        dataOrder: ''},
      total: 0,
      addItemForm: {

        textValue: '',
        textName: '',
        textDefault: ''
      }
    }
  },
  created () {
    this.getDataItemList()
  },
  methods: {
    async getDataItemList () {
      // const { data: res } = await this.$http.get('/textDataget', {
      //   // params: this.queryInfo
      // })
      // console.log('把我刚刚添加进去的数据项全部显示出来')
      // // console.log(this.queryInfo)
      // console.log(res)
      // if (res.state !== 'success') {
      //   return this.$message.error('获取数据项列表失败')
      // }
      // this.$message.success('获取数据项列表成功')
      // this.total = res.result.length
      // this.dataItemList = res.result
      // for (let i = 0; i < this.addFormSum.length && this.addFormSum.length > 0; i++) {
      //   // if (this.addItemForm.textValue) {
      //   this.dataItemList.push({
      //     textName: this.addFormSum[i].dataName,
      //     textValue: this.addFormSum[i].textValue,
      //     textDefault: this.addFormSum[i].textDefault
      //   })
      //   // }
      // }
    },
    handleValue () {
      let o = 0
      for (let i = 0; i < this.addFormSum.length; i++) {
        if (this.addFormSum[i].textValue === this.addItemForm.textValue) {
          o++
        }
      }
      if (o > 0) {
        this.$message.error('值不可重复！')
        // onmessage('值不可重复！')
      }
    },
    handleDefault () {
      let f = 0
      for (let i = 0; i < this.addFormSum.length; i++) {
        if (this.addFormSum[i].textDefault === '1') {
          f++
        }
      }
      if (f > 0) {
        this.addItemForm.textDefault = '0'
      }
    },
    addItemClosed () {
      this.$refs.addFormRef.resetFields()
    },
    showAddDialog () {
      this.addItemVisible = true
    },
    addItem () {
      // 提交请求前，表单预验证
      // this.$refs.addFormRef.validate(async valid => {
      //   // console.log(valid)
      //   // 表单预校验失败
      //   if (!valid) return
      //   const { data: res } = await this.$http.put(
      //     'muldataDictionary',
      //     this.addItemForm
      //   )
      //   if (res.state !== 'success') {
      //     this.$message.error('新增数据项失败！')
      //   }
      //   // 隐藏添加用户对话框
      //   this.addItemVisible = false
      //   this.$message.success('新增数据项成功！')
      //   this.getDataItemList()
      // })
      this.addFormSum.push({
        dictName: '',
        dictDescription: '',
        dataName: this.addItemForm.textName,
        textValue: this.addItemForm.textValue,
        textDefault: this.addItemForm.textDefault
      })
      console.log({
        dictionaries: this.addFormSum
      })
      this.dataItemList.push({
        textName: this.addItemForm.textName,
        textValue: this.addItemForm.textValue,
        textDefault: this.addItemForm.textDefault
      })
      this.addItemVisible = false
      // }
      this.getDataItemList()
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
        // console.log(this.addForm)
        for (let i = 0; i < this.addFormSum.length; i++) {
          this.addFormSum[i].dictDescription = this.addForm.dictName
          this.addFormSum[i].dictName = this.addForm.dataName
        }
        console.log(this.addFormSum)
        // const submitParams = {dictionaries: this.addFormSum}
        // subEditDic(submitParams).then(res => {
        //   // let { msg, code, user,token } = res;
        //   if (res.state !== 'success') {
        //     this.$message.error('添加数据字典失败！')
        //   } else {
        //     this.$message.success('添加数据字典成功！')
        //   }
        // })
        const { data: res } = await this.$http.post(
          '/insertAllTextData',
          {
            dictionaries: this.addFormSum
          }
        )
        console.log(res)
        if (res.state !== 'success') {
          this.$message.error('添加数据字典失败！')
        }
        this.$message.success('添加数据字典成功！')
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    },
    removeDomain (item) {
      var index = this.addForm.dataName.indexOf(item)
      if (index !== -1) {
        this.addForm.dataName.splice(index, 1)
      }
    },
    addDomain () {
      this.addForm.dataName.push({
        value: '',
        key: Date.now()
      })
    }
  }
}
</script>

<style lang="less">
</style>
