<template>
  <div>
    <!--面包屑导航区域-->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>课程管理</el-breadcrumb-item>
      <el-breadcrumb-item>课程列表</el-breadcrumb-item>
      <el-breadcrumb-item>课程详情</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <el-tab-pane label="班课信息" name="information">
<!--          <i class="el-icon-edit iconedit"></i>-->
          <div v-if="showInformation">
          <el-button
            type="primary"
            icon="el-icon-edit"
            size="big"
            class="icon-edit"
            @click="showEditDialog"
          ></el-button>
            <span>学校：{{informationTableData.school}}</span><br>
            <span>专业：{{informationTableData.college}}</span><br>
            <span>课程名：{{informationTableData.className}}</span><br>
            <span>班课号：{{orgCode}}</span><br>
            <span>班级：{{informationTableData.grade}}</span><br>
            <span>教材：{{informationTableData.teachingMateria}}</span><br>
            <span>开课时间：{{informationTableData.lessonStartDate}}</span><br>
            <span>结课时间：{{informationTableData.lessonEndDate}}</span><br>
            <span>介绍：{{informationTableData.introduction}}</span><br>
          </div>
          <div v-if="showEditInformation">
            <el-form :model="informationTableData" :rules="informationFormRules" ref="informationFormRef" label-width="80px">
              <el-form-item label="课程名称" prop="className">
                <el-input v-model="informationTableData.className"></el-input>
              </el-form-item>
              <el-form-item label="班课号" prop="orgCode">
                <el-input v-model="orgCode" disabled></el-input>
              </el-form-item>
              <el-form-item label="班级" prop="grade">
                <el-input v-model="informationTableData.grade"></el-input>
              </el-form-item>
              <el-form-item label="教材">
                <el-input v-model="informationTableData.teachingMateria"></el-input>
              </el-form-item>
              <el-form-item label="学校">
                <el-input v-model="informationTableData.school"></el-input>
              </el-form-item>
              <el-form-item label="专业">
                <el-input v-model="informationTableData.college"></el-input>
              </el-form-item>
              <el-form-item label="开课时间">
                <el-col :span="11">
                  <el-date-picker type="date" placeholder="选择日期" v-model="informationTableData.lessonStartDate" style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>
              <el-form-item label="结课时间">
                <el-col :span="11">
                  <el-date-picker type="date" placeholder="选择日期" v-model="informationTableData.lessonEndDate" style="width: 100%;"></el-date-picker>
                </el-col>
              </el-form-item>
              <el-form-item label="介绍" >
                <el-input v-model="informationTableData.introduction"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="editCourse">确定</el-button>
                <el-button @click="editCourseClosed">取消</el-button>
              </el-form-item>
            </el-form>

          </div>
        </el-tab-pane>
        <el-tab-pane label="成员" name="members">
          <el-table :data="memberTableData" border stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column label="姓名" prop="username"></el-table-column>
            <el-table-column label="学号" prop="studentId"></el-table-column>
            <el-table-column label="总分" prop="sumScore"></el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="签到" name="activities">
          <el-table :data="signTableData" border stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column label="活动id" prop="activityId"></el-table-column>
            <el-table-column label="签到类型" prop="answerLength" :formatter="signstyleFormat"></el-table-column>
            <el-table-column label="签到开始时间" prop="beginDate"></el-table-column>
            <el-table-column label="签到结束时间" prop="endDate"></el-table-column>
            <el-table-column label="状态" prop="isActive" :formatter="stateFormat"></el-table-column>
            <el-table-column label="操作" width="200" fixed="right">
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="查看参与详情"
                  placement="top"
                  :enterable="false"
                >
                  <el-button
                    type="warning"
                    icon="el-icon-setting"
                    size="mini"
                    circle
                    @click="getMembersInfo(scope.row.activityId)"
                  ></el-button>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="作业" name="homework">
          <el-table :data="homeworkTableData" border stripe>
            <el-table-column type="index"></el-table-column>
            <el-table-column label="活动id" prop="activityId"></el-table-column>
            <el-table-column label="作业名称" prop="activityName"></el-table-column>
            <el-table-column label="活动开始时间" prop="beginDate"></el-table-column>
            <el-table-column label="活动结束时间" prop="endDate"></el-table-column>
            <el-table-column label="状态" prop="isActive" :formatter="stateFormat"></el-table-column>
            <el-table-column label="操作" width="200" fixed="right">
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="查看参与详情"
                  placement="top"
                  :enterable="false"
                >
                  <el-button
                    type="warning"
                    icon="el-icon-setting"
                    size="mini"
                    circle
                    @click="getMembersInfo(scope.row.activityId)"
                  ></el-button>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>

export default {
  name: 'Tab',

  data () {
    return {
      queryInfo: {
        page: 1,
        pageSize: 10
      },
      i: 1,
      activeName: 'information',
      showInformation: true,
      showEditInformation: false,
      // 课程id
      orgCode: 0,
      memberTableData: [],
      activityTableData: [],
      signTableData: [],
      homeworkTableData: [],
      informationTableData: {
        // classCloud:{
        //     school:'',
        //     college:'',
        //     className:'',
        //     grade:'',
        //     teachingMateria:'',
        //     lessonStartDate:'',
        //     lessonEndDate:'',
        //     introduction:''
        // }
      },
      informationFormRules: {
        className: [
          {required: true, message: '请输入课程名称', trigger: 'blur'},
          {min: 1, max: 15, message: '课程名称长度在1-10个', trigger: 'blur'}
        ]
      },
      createActivityInfo: {
        activityTypeId: 1,
        activityDescription: '第一次签到',
        // eslint-disable-next-line no-tabs
        maxscore: 4,			// 活动分数
        orgCode: 10007
      }
    }
  },
  watch: {

  },
  created () {
    this.getParamsData()
    // this.createdActivity()
  },
  methods: {
    getActivityData () {

    },
    handleTabClick () {
      console.log(this.activeName)
      this.getParamsData()
    },
    // 获取参数的列表数据
    async getParamsData () {
      this.orgCode = this.$route.query.orgCode
      console.log(this.orgCode)
      if (this.activeName == 'members') {
        const {data: res} = await this.$http.get(`/activities/orgMemberScore?orgCode=` + this.orgCode, {
          params: this.queryInfo
        })
        if (res.state !== 'success') {
          return this.$message.error('获取成员列表失败')
        }
        console.log(res)
        this.memberTableData = res.result
        // eslint-disable-next-line eqeqeq
      } else if (this.activeName == 'activities') {
        const {data: res} = await this.$http.get(`/activities/class/self?orgCode=` + this.orgCode)
        if (res.state !== 'success') {
          return this.$message.error('获取活动列表失败')
        }
        console.log(res)
        // eslint-disable-next-line eqeqeq
        if (this.i == 1) {
          for (var i = 0; i < res.result.length; i++) {
            // eslint-disable-next-line eqeqeq
            if (res.result[i].activityTypeId == 1) {
              this.signTableData.push(res.result[i])
            } else {
              this.homeworkTableData.push(res.result[i])
            }
          }
        }
        this.i = this.i + 1
        // eslint-disable-next-line eqeqeq
      } else if (this.activeName == 'information') {
        const {data: res} = await this.$http.get(`/cloudClass?orgCode=` + this.orgCode)
        if (res.state !== 'success') {
          return this.$message.error('获取课程详情列表失败')
        }
        this.informationTableData = res.result.classCloud
        // this.orgCode =
        console.log(this.informationTableData)
      }
    },
    async createdActivity () {
      const {data: res} = await this.$http.post('/activities', this.createActivityInfo)
      if (res.state !== 'success') {
        return this.$message.error('创建活动失败')
      }
      console.log(res)
      this.memberTableData = res.result
    },
    showEditDialog () {
      this.showInformation = !this.showInformation
      this.showEditInformation = !this.showEditInformation
    },

    editCourse () {
      this.$refs.informationFormRef.validate(async valid => {
        if (!valid) return
        const { data: res } = await this.$http.put(
          'cloudClass?orgCode=' + this.orgCode,
          this.informationTableData
        )
        if (res.state !== 'success') {
          this.$message.error('更新课程信息失败！')
        }

        this.showEditDialog()
        this.getParamsData()
        // this.getCourseList()
        this.$message.success('更新课程信息成功')
      })
    },
    editCourseClosed () {
      this.$refs.informationFormRef.resetFields()
      this.showEditDialog()
    },
    getMembersInfo (activityId) {
      this.$router.push({
        path: '/members',
        // eslint-disable-next-line standard/object-curly-even-spacing
        query: { 'activityId': activityId}
      })
    },
    signstyleFormat (row, column) {
      // eslint-disable-next-line eqeqeq
      if (row.answerLength == 0) {
        return '手势签到'
      } else {
        return '一键签到'
      }
    },
    stateFormat (row, column) {
      // eslint-disable-next-line eqeqeq
      if (row.answerLength == 1) {
        return '进行中'
      } else {
        return '已结束'
      }
    }
  }
}
</script>s

<style scoped>
.icon-edit{
  float: right;
  margin-right: 40px;
}
</style>
