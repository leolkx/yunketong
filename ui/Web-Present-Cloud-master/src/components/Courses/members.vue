<template>
  <div>
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>课程管理</el-breadcrumb-item>
      <el-breadcrumb-item>课程列表</el-breadcrumb-item>
      <el-breadcrumb-item>课程详情</el-breadcrumb-item>
      <el-breadcrumb-item>活动参与详情</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card">
      <el-table :data="memberlist">
        <el-table-column type="index" label="#"></el-table-column>
        <el-table-column label="成员姓名" prop="username"></el-table-column>
        <el-table-column label="成员学号" prop="studentId"></el-table-column>
        <el-table-column label="是否参与" prop="isPar"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'Members',
  data () {
    return {
      activityId: '',
      memberlist: []
    }
  },
  created () {
    this.getMemberList()
  },
  methods: {
    async getMemberList () {
      this.activityId = this.$route.query.activityId
      console.log(this.activityId)
      const {data: res} = await this.$http.get(`/activities/orgParState?activityId=` + this.activityId)
      if (res.state !== 'success') {
        return this.$message.error('获取成员列表失败')
      }
      console.log(res)
      this.memberlist = res.result
    }
  }
}
</script>

<style scoped>

</style>
