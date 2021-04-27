<template>
  <div class="login_container">
    <div class="title">忘记密码</div>
     <el-form label-width="120px" ref="forgetpwdform" :model="forgetpwdform" :rules="rules">
     <el-row :gutter="20">
    <el-col :span="14">
   <el-form-item prop="phone" label="手机号：">
     <el-input v-model="forgetpwdform.phone" maxlength="11"></el-input>
     </el-form-item>
     </el-col>
     <el-col :span="10">
     <el-button @click="sendcodeMsg" :disabled="disabled">{{sendcode}}</el-button>
     </el-col>
     </el-row>
      <el-row>
     <el-col :span="18">
     <el-form-item prop="code" label="验证码：">
     <el-input v-model="forgetpwdform.code"></el-input>
     </el-form-item>
   </el-col>
    </el-row>
       <el-col :span="18">
     <el-form-item prop="newpassword" label="新密码: ">
     <el-input type="password" v-model="forgetpwdform.newpassword"></el-input>
     </el-form-item>
     <el-form-item prop="repeatpassword" label="确认密码: ">
     <el-input type="password" v-model="forgetpwdform.repeatpassword"></el-input>
     </el-form-item>
       </el-col>
     </el-form>
     <template slot="footer">
     <el-button type="default" @click="handleClose">取消</el-button>
     <el-button type="primary" @click="handleSubmit">提交</el-button>
     </template>
  </div>
  </template>

<script>
import qs from 'qs'
export default {
  name: 'forgetpwd',
  props: ['centerDialogVisibleSecond'],
  data () {
    return {
      getCode: '',
      sendcode: '发送验证码',
      disabled: false,
      forgetpwdform: {
        phone: '',
        code: '',
        newpassword: '',
        repeatpassword: ''
      },
      rules: {
        phone: [
          {required: true, message: '手机号不能为空', trigger: 'blur'}
        ],
        code: [
          {
            required: true, message: '验证码不能为空', trigger: 'blur'
          }
        ],
        newpassword: [
          {
            required: true, message: '新密码不能为空', trigger: 'blur'
          }
        ],
        repeatpassword: [
          {
            required: true, message: '重置密码不能为空', trigger: 'blur'
          }
        ]
      }
    }
  },
  methods: {
    // 手机号正则判断
    judgePhone () {
      const reg = /^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\d{8}$/
      // var url="/nptOfficialWebsite/apply/sendSms?mobile="+this.ruleForm.phone;
      // eslint-disable-next-line eqeqeq
      if (this.forgetpwdform.phone === '') {
        this.$message('请输入手机号码')
        return false
      } else if (!reg.test(this.forgetpwdform.phone)) {
        this.$message('手机号格式不正确')
        return false
      } else {
        return true
      }
    },
    // 发送验证码
    sendcodeMsg () {
      let _this = this
      if (_this.judgePhone()) {
        var num = 60
        var timer = setInterval(function () {
          num--
          _this.disabled = true
          _this.sendcode = num + '秒后重新获取'
          if (num === 0) {
            _this.sendcode = '获取验证码'
            _this.disabled = false
            clearInterval(timer)
          }
        }, 1000)
        _this.$http.post('你的接口', qs.stringify(
          {phoneNumber: _this.forgetpwdform.phone}
        )).then((res) => {
          _this.getCode = res
        })
      }
    },
    // 关闭页面
    handleClose () {
      this.centerDialogVisibleSecond = false
      this.$emit('tyonke', this.centerDialogVisibleSecond)
    },

    // 表单提交
    handleSubmit () {
      let _this = this
      _this.$refs['forgetpwdform'].validate(valid => {
        if (valid) {
          // eslint-disable-next-line eqeqeq
          if (_this.forgetpwdform.code != _this.getCode) {
            this.$message({
              type: 'error',
              message: '修改失败',
              duration: 1000
            })
            return false
          }
          return this.$message({
            type: 'success',
            message: '修改成功',
            duration: 1000
          })
        } else {

        }
      })
    }
  },
  watch: {
    centerDialogVisibleSecond (newValue, oldValue) {
      this.centerDialogVisibleSecond = newValue
    }
  }
}
</script>

<style scoped>
.login_container {
  background-color: #2d3a4b;
  height: 100%;
}
.title {
  color: #eee6e6;
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
  font-size: 30px;
}
</style>
