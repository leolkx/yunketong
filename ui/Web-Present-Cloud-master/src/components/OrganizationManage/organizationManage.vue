<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
    <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
    <el-breadcrumb-item>组织管理</el-breadcrumb-item>
    <el-breadcrumb-item>组织列表</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="org-card">
      <template>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="学校信息" name="school">
            <el-row>
              <el-col :span="11">
                <el-button type="primary" @click="addSchoolDialogVisible = true">添加学校</el-button>
              </el-col>
            </el-row>
            <el-table :data="schoolInfo" stripe>
              <el-table-column label="#" type="index"></el-table-column>
              <el-table-column label="学校id" prop="id"></el-table-column>
              <el-table-column label="学校名" prop="schoolName"></el-table-column>
              <el-table-column label="学校描述" prop="schoolDesc"></el-table-column>
              <el-table-column label="操作">
                <template slot-scope="scope">
                  <!-- 修改按钮 -->
                  <el-button
                    type="primary"
                    icon="el-icon-edit"
                    size="mini"
                    @click="showSchoolEditDialog(scope.row)"
                  ></el-button>
                  <!-- 删除按钮 -->
                  <el-button
                    type="danger"
                    icon="el-icon-delete"
                    size="mini"
                    @click="removeSchoolById(scope.row.id)"
                  ></el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <!-- 学院信息 -->
          <el-tab-pane label="学院信息" name="college">
            <el-row>
              <el-col :span="11">
                <el-button type="primary" @click="addCollegeDialogVisible = true">添加学院</el-button>
              </el-col>
              <el-col :span="13">
                <el-select v-model="collegeOfSchoolID" placeholder="请选择学校" filterable allow-create default-first-option>
                  <el-option v-for="item in schoolIDInfo" :label="item.label" :value="item.value" :key="item.value"></el-option>
                </el-select>
                <el-button type="primary" @click="searchCollage">查询学校相关学院</el-button>
              </el-col>
            </el-row>
            <template>
              <el-table :data="collegeInfo" stripe>
                <el-table-column label="#" type="index"></el-table-column>
                <el-table-column label="学校id" prop="schoolId"></el-table-column>
                <el-table-column label="学院id" prop="id"></el-table-column>
                <el-table-column label="学院名" prop="collegeName"></el-table-column>
                <el-table-column label="学院描述" prop="collegeDesc"></el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <!-- 修改按钮 -->
                    <el-button
                      type="primary"
                      icon="el-icon-edit"
                      size="mini"
                      @click="showCollegeEditDialog(scope.row)"
                    ></el-button>
                    <!-- 删除按钮 -->
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="removeCollegeById(scope.row.id)"
                    ></el-button>
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-tab-pane>
          <!-- 专业信息 -->
          <el-tab-pane label="专业信息" name="major">
            <el-row>
              <el-col :span="8">
                <el-button type="primary" @click="addMajorDialogVisible = true">添加专业</el-button>
              </el-col>
              <el-col :span="8">
                <el-select v-model="collegeOfSchoolID" placeholder="请选择学校" filterable allow-create default-first-option>
                  <el-option v-for="item in schoolIDInfo" :label="item.label" :value="item.value" :key="item.value"></el-option>
                </el-select>
                <el-button type="primary" @click="searchCollage">查询学校相关学院</el-button>
              </el-col>
              <el-col :span="8">
                <el-select v-model="majorOfCollegeID" placeholder="请选择学院" filterable allow-create default-first-option>
                  <el-option v-for="item in collegeInfo" :label="item.collegeName" :value="item.id" :key="item.id"></el-option>
                </el-select>
                <el-button type="primary" @click="searchMajor">查询学院相关专业</el-button>
              </el-col>
            </el-row>
            <el-table :data="majorInfo" stripe>
              <el-table-column label="#" type="index"></el-table-column>
              <el-table-column label="学院ID" prop="collegeId"></el-table-column>
              <el-table-column label="专业ID" prop="id"></el-table-column>
              <el-table-column label="专业名称" prop="majorName"></el-table-column>
              <el-table-column label="专业简介" prop="majorDesc"></el-table-column>
              <el-table-column label="操作">
                  <template slot-scope="scope">
                    <!-- 修改按钮 -->
                    <el-button
                      type="primary"
                      icon="el-icon-edit"
                      size="mini"
                      @click="showMajorEditDialog(scope.row)"
                    ></el-button>
                    <!-- 删除按钮 -->
                    <el-button
                      type="danger"
                      icon="el-icon-delete"
                      size="mini"
                      @click="removeMajorById(scope.row.id)"
                    ></el-button>
                  </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </template>

      <!-- 添加学校对话框 -->
      <el-dialog
        title="添加学校"
        :visible.sync="addSchoolDialogVisible"
        width="50%"
        @close="addSchoolDialogClosed">
        <el-form :model="addSchoolForm" :rules="addSchoolFormRules" ref="addSchoolFormRef" label-width="100px">
          <el-form-item label="学校名称" prop="schoolName">
            <el-input v-model="addSchoolForm.schoolName"></el-input>
          </el-form-item>
          <el-form-item label="学校描述" prop="schoolDesc">
            <el-input v-model="addSchoolForm.schoolDesc"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addSchoolDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addSchool">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 编辑学校对话框 -->
      <el-dialog
        title="编辑学校信息"
        :visible.sync="editSchoolDialogVisible"
        width="50%"
        @close="editSchoolDialogClosed">
        <el-form :model="editSchoolForm" :rules="editSchoolFormRules" ref="editSchoolFormRef" label-width="100px">
          <el-form-item label="学校名称" prop="schoolName">
            <el-input v-model="editSchoolForm.schoolName"></el-input>
          </el-form-item>
          <el-form-item label="学校描述" prop="schoolDesc">
            <el-input v-model="editSchoolForm.schoolDesc"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editSchoolDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editSchool">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 添加学院对话框 -->
      <el-dialog
        title="添加学院"
        :visible.sync="addCollegeDialogVisible"
        width="50%"
        @close="addCollegeDialogClosed">
        <el-form :model="addCollegeForm" :rules="addCollegeFormRules" ref="addCollegeFormRef" label-width="100px">
          <el-form-item label="学校" prop="schoolId">
            <el-select v-model="addCollegeForm.schoolId" placeholder="请选择学校" filterable allow-create default-first-option>
              <el-option v-for="item in schoolIDInfo" :label="item.label" :value="item.value" :key="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="学院名称" prop="collegeName">
            <el-input v-model="addCollegeForm.collegeName"></el-input>
          </el-form-item>
          <el-form-item label="学院描述" prop="collegeDesc">
            <el-input v-model="addCollegeForm.collegeDesc"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addCollegeDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addCollege">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 编辑学院对话框 -->
      <el-dialog
        title="编辑学院信息"
        :visible.sync="editCollegeDialogVisible"
        width="50%"
        @close="editCollegeDialogClosed">
        <el-form :model="editCollegeForm" :rules="editCollegeFormRules" ref="editCollegeFormRef" label-width="100px">
          <el-form-item label="学院ID" prop="id">
            <el-input v-model="editCollegeForm.id" disabled></el-input>
          </el-form-item>
          <el-form-item label="学校ID" prop="schoolId">
            <el-input v-model="editCollegeForm.schoolId" disabled></el-input>
          </el-form-item>
          <el-form-item label="学院名称" prop="collegeName">
            <el-input v-model="editCollegeForm.collegeName"></el-input>
          </el-form-item>
          <el-form-item label="学院描述" prop="collegeDesc">
            <el-input v-model="editCollegeForm.collegeDesc"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editCollegeDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editCollege">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 添加专业对话框 -->
      <el-dialog
        title="添加专业"
        :visible.sync="addMajorDialogVisible"
        width="50%">
        <el-form :model="addMajorForm" :rules="addMajorFormRules" ref="addMajorFormRef" label-width="100px">
          <el-form-item label="学校" prop="schoolId">
            <el-select v-model="collegeOfSchoolID" placeholder="请选择学校" filterable allow-create default-first-option>
              <el-option v-for="item in schoolIDInfo" :label="item.label" :value="item.value" :key="item.value"></el-option>
            </el-select>
            <el-button type="primary" @click="searchCollage">查询学校相关学院</el-button>
          </el-form-item>
          <el-form-item label="学院" prop="collegeId">
            <el-select v-model="addMajorForm.collegeId" placeholder="请选择学院" filterable allow-create default-first-option>
              <el-option v-for="item in collegeInfo" :label="item.collegeName" :value="item.id" :key="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="专业名称" prop="majorName">
            <el-input v-model="addMajorForm.majorName"></el-input>
          </el-form-item>
          <el-form-item label="专业简介" prop="majorDesc">
            <el-input v-model="addMajorForm.majorDesc"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="addMajorDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="addMajor">确 定</el-button>
        </span>
      </el-dialog>

      <!-- 编辑专业对话框 -->
      <el-dialog
        title="编辑专业信息"
        :visible.sync="editMajorDialogVisible"
        width="50%"
        @close="editMajorDialogClosed">
        <el-form :model="editMajorForm" :rules="editMajorFormRules" ref="editMajorFormRef" label-width="100px">
          <el-form-item label="专业ID" prop="id">
            <el-input v-model="editMajorForm.id" disabled></el-input>
          </el-form-item>
          <el-form-item label="学院ID" prop="collegeId">
            <el-input v-model="editMajorForm.collegeId" disabled></el-input>
          </el-form-item>
          <el-form-item label="专业名称" prop="majorName">
            <el-input v-model="editMajorForm.majorName"></el-input>
          </el-form-item>
          <el-form-item label="专业简介" prop="majorDesc">
            <el-input v-model="editMajorForm.majorDesc"></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editMajorDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editMajor">确 定</el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
export default {
  created () {
    this.getShollInfo()
  },
  data: function () {
    return {
      activeName: 'school',
      addSchoolDialogVisible: false,
      addCollegeDialogVisible: false,
      editSchoolDialogVisible: false,
      // 学校
      schoolInfo: [],
      schoolIDInfo: [],
      addSchoolForm: {
        schoolName: '',
        schoolDesc: ''
      },
      editSchoolForm: {},

      addSchoolFormRules: {
        schoolName: [
          { required: true, message: '请输入学校名称', trigger: 'blur' }
        ],
        schoolDesc: [
          { required: true, message: '请输入学校描述信息', trigger: 'blur' }
        ]
      },
      editSchoolFormRules: {
        schoolName: [
          { required: true, message: '请输入学校名称', trigger: 'blur' }
        ],
        schoolDesc: [
          { required: true, message: '请输入学校描述信息', trigger: 'blur' }
        ]
      },

      // 学院相关
      collegeInfo: [],
      editCollegeForm: [],
      // 查询特定学校的ID
      collegeOfSchoolID: '',
      editCollegeDialogVisible: false,
      addCollegeForm: {
        schoolId: '',
        collegeName: '',
        collegeDesc: ''
      },
      addCollegeFormRules: {
        schoolId: [
          { required: true, message: '请选择学校', trigger: 'blur' }
        ],
        collegeName: [
          { required: true, message: '请输入学院名称', trigger: 'blur' }
        ],
        collegeDesc: [
          { required: true, message: '请输入学院描述信息', trigger: 'blur' }
        ]
      },
      editCollegeFormRules: {
        collegeName: [
          { required: true, message: '请输入学院名称', trigger: 'blur' }
        ],
        collegeDesc: [
          { required: true, message: '请输入学院描述信息', trigger: 'blur' }
        ]
      },

      // 以下是专业的数据
      // eslint-disable-next-line vue/no-dupe-keys
      addMajorDialogVisible: false,
      addMajorForm: {
        id: '',
        collegeId: '',
        majorName: '',
        majorDesc: ''
      },
      addMajorFormRules: {
        majorName: [
          { required: true, message: '请输入专业名称', trigger: 'blur' }
        ],
        majorDesc: [
          { required: true, message: '请输入专业简介', trigger: 'blur' }
        ]
      },
      majorOfCollegeID: '',
      MCollegeID: '',
      majorInfo: [],
      editMajorDialogVisible: false,
      editMajorForm: [],
      editMajorFormRules: {
        majorName: [
          { required: true, message: '请输入专业名称', trigger: 'blur' }
        ],
        majorDesc: [
          { required: true, message: '请输入专业简介', trigger: 'blur' }
        ]
      }
    }
  },

  methods: {
    handleClick (tab) {
      if (tab.name === 'school') this.getShollInfo()
    },
    async getShollInfo () {
      const { data: res } = await this.$http.get('structure/orgs/schools?page=1&pageSize=10')
      if (res.state !== 'success') return this.$message.error('获取学校信息失败')
      const data = res.result
      // data.forEach(element => {
      //   this.schoolIDInfo[element.id] = element.schoolName
      // })
      for (var i = 0; i < data.length; i++) {
        // eslint-disable-next-line camelcase
        var dict_data = {}
        dict_data['value'] = data[i].id
        dict_data['label'] = data[i].schoolName
        // eslint-disable-next-line camelcase
        this.schoolIDInfo[i] = dict_data
      }
      this.schoolInfo = res.result
    },
    // 添加学校相关函数
    addSchoolDialogClosed () {
      this.$refs.addSchoolFormRef.resetFields()
    },
    editSchoolDialogClosed () {
      this.$refs.editSchoolFormRef.resetFields()
    },
    addSchool () {
      // 提交请求前，表单预验证
      this.$refs.addSchoolFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.post('structure/orgs/schools', this.addSchoolForm)
        if (res.state !== 'success') {
          this.$message.error('添加学校失败！')
          return
        }
        this.$message.success('添加学校成功！')
        // 隐藏添加对话框
        this.addSchoolDialogVisible = false
      })
    },

    // 编辑学校信息
    showSchoolEditDialog (school) {
      this.editSchoolForm = school
      this.editSchoolDialogVisible = true
    },

    // 学校信息更改提交
    editSchool () {
      // 提交请求前，表单预验证
      this.$refs.editSchoolFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.put('structure/orgs/schools ', this.editSchoolForm)
        if (res.state !== 'success') {
          this.$message.error('更改学校信息失败！')
          return
        }
        this.$message.success('更改学校信息成功！')
        // 隐藏添加对话框
        this.editSchoolDialogVisible = false
      })
      this.getShollInfo()
    },

    // 删除学校
    async removeSchoolById (id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该学校, 是否继续?',
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
      const { data: res } = await this.$http.delete('structure/orgs/schools?schoolId=' + id)
      if (res.state !== 'success') return this.$message.error('删除学校失败！')
      this.$message.success('删除学校成功！')
      this.getShollInfo()
    },

    // 添加学院相关函数
    addCollegeDialogClosed () {
      this.$refs.addCollegeFormRef.resetFields()
    },
    editCollegeDialogClosed () {
      this.$refs.editCollegeFormRef.resetFields()
      this.searchCollage()
    },
    addCollege () {
      // 提交请求前，表单预验证
      this.$refs.addCollegeFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.post('structure/orgs/colleges', this.addCollegeForm)
        if (res.state !== 'success') {
          this.$message.error('添加学院失败！')
          return
        }
        this.$message.success('添加学院成功！')
        // 隐藏添加对话框
        this.addCollegeDialogVisible = false
      })
    },
    // 查询特定学校的学院信息
    async searchCollage () {
      const { data: res } = await this.$http.get('structure/orgs/colleges?schoolId=' + this.collegeOfSchoolID + '&page=1&pageSize=100')
      if (res.state !== 'success') return this.$message.error('获取学院信息失败')
      this.collegeInfo = res.result
      this.$message.success('获取学院信息成功')
    },
    // 编辑学院信息
    showCollegeEditDialog (college) {
      this.editCollegeForm = college
      this.editCollegeDialogVisible = true
    },
    // 学院信息更改提交
    editCollege () {
      // 提交请求前，表单预验证
      this.$refs.editCollegeFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        console.log(this.editCollegeForm)
        const { data: res } = await this.$http.put('structure/orgs/colleges', this.editCollegeForm)
        console.log(res)
        if (res.state !== 'success') {
          this.$message.error('更改学院信息失败！')
          return
        }
        this.$message.success('更改学院信息成功！')
        // 隐藏添加对话框
        this.editCollegeDialogVisible = false
        this.searchCollage()
      })
    },
    // 删除学院
    async removeCollegeById (id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该学校, 是否继续?',
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
      const { data: res } = await this.$http.delete('structure/orgs/colleges?collegeId=' + id)
      if (res.state !== 'success') return this.$message.error('删除学院失败！')
      this.$message.success('删除学院成功！')
      this.searchCollage()
    },
    // 新增专业
    addMajor () {
      // 提交请求前，表单预验证
      this.$refs.addMajorFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        const { data: res } = await this.$http.post('structure/orgs/majors', this.addMajorForm)
        if (res.state !== 'success') {
          this.$message.error('添加专业失败！')
          return
        }
        this.$message.success('添加专业成功！')
        // 隐藏添加对话框
        this.addMajorDialogVisible = false
      })
    },
    // 获取专业
    async searchMajor () {
      const { data: res } = await this.$http.get('structure/orgs/majors?collegeId=' + this.majorOfCollegeID + '&page=1&pageSize=100')
      if (res.state !== 'success') return this.$message.error('获取专业信息失败')
      this.majorInfo = res.result
      this.$message.success('获取专业信息成功')
    },
    async removeMajorById (id) {
      const confirmResult = await this.$confirm(
        '此操作将永久删除该专业, 是否继续?',
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
      const { data: res } = await this.$http.delete('structure/orgs/majors?majorId=' + id)
      if (res.state !== 'success') return this.$message.error('删除专业失败！')
      this.$message.success('删除专业成功！')
      this.searchMajor()
    },

    // 编辑专业信息
    showMajorEditDialog (major) {
      this.editMajorForm = major
      this.editMajorDialogVisible = true
    },
    editMajorDialogClosed () {
      this.$refs.editMajorFormRef.resetFields()
      this.searchMajor()
    },
    editMajor () {
      // 提交请求前，表单预验证
      this.$refs.editMajorFormRef.validate(async valid => {
        // 表单预校验失败
        if (!valid) return
        console.log(this.editMajorForm)
        const { data: res } = await this.$http.put('structure/orgs/majors', this.editMajorForm)
        console.log(res)
        if (res.state !== 'success') {
          this.$message.error('更改专业信息失败！')
          return
        }
        this.$message.success('更改专业信息成功！')
        // 隐藏添加对话框
        this.editMajorDialogVisible = false
        this.searchMajor()
      })
    }
  }
}
</script>

<style scoped>

</style>
