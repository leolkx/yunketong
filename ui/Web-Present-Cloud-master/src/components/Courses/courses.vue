<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>课程管理</el-breadcrumb-item>
      <el-breadcrumb-item>课程列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="box-card">
      <el-row class="searchRow" :gutter="20">
        <el-col :span="8">
          <el-input
            placeholder="请输入课程名称"
            v-model="queryInfo.query"
            class="inputSearch"
            clearable
            @clear="getCourseList"
          >
            <el-button slot="append" icon="el-icon-search" @click="getCourseList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">创建课程</el-button>
        </el-col>
      </el-row>

      <el-table :data="courselist">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="课程名称" prop="cloudClass.className"></el-table-column>
        <el-table-column label="班课号" prop="orgCode"></el-table-column>
        <el-table-column label="年级" prop="cloudClass.grade"></el-table-column>
        <el-table-column label="教材" prop="cloudClass.teachingMateria"></el-table-column>
        <el-table-column label="专业" prop="cloudClass.college"></el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template slot-scope="scope">
            <!-- 删除课程按钮 -->
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeCourseById(scope.row.orgCode)"
            ></el-button>

            <el-tooltip
              class="item"
              effect="dark"
              content="详情"
              placement="top"
              :enterable="false"
            >
              <el-button
                type="warning"
                icon="el-icon-setting"
                size="mini"
                circle
                @click="getCourseInfo(scope.row.orgCode)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pagenum"
        :page-sizes="[10, 15, 20]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        align="right"
      ></el-pagination>
    </el-card>

    <!--添加课程对话框-->
    <el-dialog
      title="创建课程"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed"
      >
      <!--内容主体区域-->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="课程名" prop="className">
          <el-input v-model="addForm.className"></el-input>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="addForm.grade"></el-input>
        </el-form-item>
        <el-form-item label="教材" prop="teachingMateria">
          <el-input v-model="addForm.teachingMateria"></el-input>
        </el-form-item>
        <el-form-item label="学校" prop="school">
          <el-input v-model="addForm.school"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="college">
          <el-input v-model="addForm.college"></el-input>
        </el-form-item>
        <el-form-item label="开课时间" prop="lessonStartDate">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="addForm.lessonStartDate" style="width: 100%;"></el-date-picker>
          </el-col>
<!--          <el-input v-model="addForm.lessonStartDate"></el-input>-->
        </el-form-item>
        <el-form-item label="结课时间" prop="lessonEndDate">
          <el-col :span="11">
            <el-date-picker type="date" placeholder="选择日期" v-model="addForm.lessonEndDate" style="width: 100%;"></el-date-picker>
          </el-col>
<!--          <el-input v-model="addForm.lessonEndDate"></el-input>-->
        </el-form-item>
        <el-form-item label="介绍" prop="introduction">
          <el-input v-model="addForm.introduction"></el-input>
        </el-form-item>
      </el-form>
      <!--底部区域-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCourse" >确 定</el-button>
      </span>
    </el-dialog>
   </div>
</template>

<script>
export default{
  data () {
    return {
      // 获取列表参数对象
      queryInfo: {
        query: '',
        page: 1,
        pageSize: 10
      },
      courselist: [],
      total: 0,
      /* 控制创建课程对话框的显示与隐藏 */
      addDialogVisible: false,
      /* 添加用户的表单数据 */
      addForm: {
        className: '',
        teacherName: '',
        grade: '',
        teachingMateria: '',
        school: '',
        college: '',
        lessonStartDate: '',
        lessonEndDate: '',
        introduction: ''
      },
      /* 添加表单的验证规则对象 */
      addFormRules: {
        className: [
          {required: true, message: '请输入课程名称', trigger: 'blur'},
          {min: 1, max: 10, message: '课程名称长度在1-10个', trigger: 'blur'}
        ]
      }

    }
  },
  created () {
    this.getCourseList()
  },
  methods: {
    async getCourseList () {
      const {data: res} = await this.$http.get('/user/createdClass', {
        params: this.queryInfo
      })

      if (res.state !== 'success') {
        return this.$message.error('获取课程列表失败')
      }
      this.courselist = res.result
      console.log(res.result)
      this.total = res.result.length
    },
    handleSizeChange (newSize) {
      console.log(newSize)
      this.queryInfo.pagesize = newSize
    },
    handleCurrentChange (newPage) {
      console.log(newPage)
      this.queryInfo.pagenum = newPage
    },
    /* 监听添加用户对话框的关闭事件 */
    addDialogClosed () {
      this.$refs.addFormRef.resetFields()
    },
    addCourse () {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return
        const {data: res} = await this.$http.post('cloudClass', this.addForm)
        console.log(res)
        if (res.state !== 'success') {
          this.$message.error('创建课程失败！')
          return
        }
        this.$message.success('创建课程成功！')
        this.addDialogVisible = false
        this.getCourseList()
      })
    },

    async removeCourseById (orgCode) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该课程, 是否继续?',
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
      const { data: res } = await this.$http.delete('cloudClass?orgCode=' + orgCode)
      if (res.state !== 'success') return this.$message.error('删除用户失败！')
      this.$message.success('删除课程成功！')
      this.getCourseList()
    },
    getCourseInfo (orgCode) {
      this.$router.push({
        path: '/course-info',
        query: {'orgCode': orgCode}
      })
    }

  }
}
</script>

<style lang="less" scoped>

</style>
