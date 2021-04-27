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
          label="字典值: "
          :rules="[{ required: true, message: '请输入值', trigger: 'blur' }]"
        >
          <el-input v-model="addForm.dataName"></el-input>
        </el-form-item>
        <!--
          <el-form-item
          v-for="(domain, index) in addForm.dataName"
          :label="'字典值名' + index + ':'"
          :key="domain.key"
          :prop="'dataName.' + index + '.value'"
          :rules="{required: true, message: '值名不能为空', trigger: 'blur'}"
        >
          <el-input v-model="domain.value"></el-input>
          <el-button @click.prevent="removeDomain(domain)">删除</el-button>
        </el-form-item>
        -->

        <el-form-item label="数据顺序">
          <el-input v-model="addForm.dataOrder"></el-input>
        </el-form-item>
        <el-form-item align="right">
          <el-button type="primary" @click="submitForm('addForm')">提交</el-button>
          <!--el-button @click="addDomain">新增值名</el-button-->
          <el-button @click="resetForm('addForm')">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      options: [
        { value: true, label: '是' },
        { value: false, label: '否' }
      ],
      addForm: {
        dictName: '',
        dataName: '',
        dataOrder: ''
        // dataName: [
        //   {
        //     value: ''
        //   }
        // ]
      }
    }
  },
  created () {},
  methods: {
    submitForm (formName) {
      this.$refs.addForm.validate(async valid => {
        // console.log(valid)
        // 表单预校验失败
        if (!valid) return
        console.log(this.addForm)
        const { data: res } = await this.$http.post(
          '/dataDictionary',
          this.addForm
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
