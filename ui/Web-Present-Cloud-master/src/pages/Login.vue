<template>
  <div class="login_container">
    <div class="text">到云后台管理系统</div>
    <div class="login_box">
      <div class="avatar_box">
        <img src="../assets/logo.jpg" alt />
      </div>
      <div class="login_header_title">
        <a href="javascript:;" :class="{on:loginWay}" @click="loginWay=true">密码登录</a>
        <a href="javascript:;" :class="{on: !loginWay}" @click="loginWay=false">短信登录</a>
      </div>
      <!-- 登录表单区域 -->
      <el-form v-show="loginWay"
               ref="loginFormRef"
               label-width="0px"
               class="login_form"
               :model="loginForm"
               :rules="loginFormRules"
      >
        <!-- 用户名 -->
        <el-form-item prop="username">
          <el-input prefix-icon="iconfont icon-user" placeholder="账号" v-model="loginForm.username"></el-input>
        </el-form-item>
        <!-- 密码 -->
        <el-form-item prop="password">
          <el-input
            prefix-icon="iconfont icon-lock"
            placeholder="密码"
            v-model="loginForm.password"
            type="password"
          ></el-input>
        </el-form-item>
        <!-- 验证码 -->
<!--        <el-form-item prop="verificationCode">-->
<!--          <el-row>-->
<!--            <el-col :span="18">-->
<!--              <el-input prefix-icon="iconfont icon-s-help" placeholder="验证码" v-model="loginForm.verificationCode"></el-input>-->
<!--            </el-col>-->
<!--            <el-col :span="4"  class="codeClass">-->
<!--              <span @click="changeCode">-->
<!--                <img :src="imgCode">-->
<!--              </span>-->
<!--            </el-col>-->
<!--          </el-row>-->
<!--        </el-form-item>-->
        <!-- 按钮区域 -->
        <el-form-item class="btns-login">
          <el-button type="primary" @click="login" style="width:410px;color=#46a6ff">登录</el-button>
          <router-link to="/Register">
            <button>注册</button>
          </router-link>
          <router-link to="/Forgetpasword">
            <button>忘记密码</button>
          </router-link>
<!--            <button onclick="register()">注册</button>-->
        </el-form-item>
      </el-form>

      <el-form v-show="!loginWay"
               ref="loginFormRef1"
               label-width="0px"
               class="login_form"
               :model="loginForm1"
               :rules="loginFormRules1"
      >
        <!-- 手机号 -->
        <el-form-item prop="phone">
          <el-input :span="18" prefix-icon="iconfont icon-user" placeholder="手机号" v-model="loginForm1.username"></el-input>
<!--          <button :disabled=false class="get_verification" @click.prevent="sendcode">获取验证码</button>-->
          <el-button class="get_verification" @click.prevent="sendcode" :disabled=false>{{loginForm1.btntxt}}</el-button>
        </el-form-item>
        <!-- 验证码 -->
        <el-form-item prop="verificationCode">
          <el-input
            prefix-icon="iconfont icon-lock"
            placeholder="请输入获取到的验证码"
            v-model="loginForm1.verificationCode"
          ></el-input>
        </el-form-item>
        <!-- 按钮区域 -->
        <el-form-item class="btns-login">
          <el-button type="primary" @click="login" style="width:410px;color=#46a6ff">登录</el-button>
          <router-link to="/Register">注册</router-link>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import SocialSign from '@/components/SocialSignin/socialsignin.vue'
export default {
  components: {SocialSign},
  data() {
    return {
      // imgCode: 'http://219.229.153.200:8081/verification/code',
      // 登录表单数据绑定对象
      loginForm: {
        username: '',
        password: '',
        // verificationCode: ''
      },
      loginForm1: {
        username: '',
        verificationCode: '',
        timer: null,
        time: 0,
        rightPhone: '',
        btntxt: '获取验证码'
      },
      // 表单的验证规则对象
      loginFormRules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 2, max: 10, message: '长度在2-10个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 1, max: 15, message: '长度在1-15个字符', trigger: 'blur'}
        ]
        // verificationCode: [
        //   {required: true, message: '请输入验证码', trigger: 'blur'}
        // ]
      },
      loginFormRules1: {
        username: [
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {min: 6, max: 11, message: '长度在6-11个字符', trigger: 'blur'}
        ],
        verificationCode: [
          {required: true, message: '请输入验证码', trigger: 'blur'},
          {min: 4, max: 6, message: '长度在4-6个字符', trigger: 'blur'}
        ]
      },
      loginWay: true
      // // 登录界面的背景图
      // backgroundDiv: {
      //   backgroundImage:
      //     'url(' + require('../assets/login_background.jpg') + ')',
      //   backgroundRepeat: 'no-repeat',
      //   backgroundSize: '100% 100%'
      // }
    }
  },
  created () {
    // this.changeCode()
    // this.sendcode()
  },
  // computed: {
  //  rightPhone () {
  // 利用正则对手机号匹配
  // return /^1[3456789]\d{9}$/.test(this.loginForm.phone)
  //    return this.phone
  //   }
  // },
  methods: {
    // changeCode() {
    //   var num = Math.ceil(Math.random() * 10)
    //   this.imgCode = this.imgCode + '?' + num
    // },
    register () {
      this.$router.push({
        path: '/Register'
      })
    },
    sendcode () {
      const reg = 11 && /^((13|14|15|17|18)[0-9]{1}\d{8})$/
      // eslint-disable-next-line eqeqeq
      console.log(this.loginForm1.username)
      if (this.loginForm1.username === '') {
        this.$message({
          message: '手机号不能为空',
          center: true
        })
        return
      }
      if (!reg.test(this.loginForm1.username)) {
        this.$message({
          message: '请输入正确的手机号',
          center: true
        })
      } else {
        this.time = 60
        this.disabled = true
        this.timer()
        // 发送手机号，请求验证码
        this.$refs.loginFormRef1.validate(async valid => {
          // if (!valid) return
          this.loginForm1.param = true
          // console.log(22222)
          const {data: res} = await this.$http.get('phonecode', {params: {
            'phone': this.loginForm1.username
          }}, this.loginForm1)
          // console.log(11111)
          if (res.state !== 'success') return this.$message.error(res.msg)
          this.$message.success('发送成功')
          window.localStorage.setItem('phone', this.loginForm1.username)
        //  window.localStorage.setItem('token', res.result.token)
        })
      }
    },
    // 60S倒计时
    timer() {
      if (this.time > 0) {
        this.time--
        this.loginForm1.btntxt = this.time + 's后重新获取'
        setTimeout(this.timer, 1000)
      } else {
        this.time = 0
        this.loginForm1.btntxt = '获取验证码'
        this.disabled = false
        console.log(this.loginForm1.btntxt)
      }
    },
    login () {
      if (this.loginWay) {
        this.$refs.loginFormRef.validate(async valid => {
          if (!valid) return
          this.loginForm.param = true
          const {data: res} = await this.$http.post('signin', this.loginForm)
          if (res.state !== 'success') return this.$message.error(res.msg)
          this.$message.success('登录成功')
          /*
            1.将登录成功之后的token保存到客户端的sessionStorage中
                项目中除了登录之外的其他API接口，必须在登录之后才能访问
                token只应在当前网站打开期间有效，所以将token保存在sessionStorage中
            2.通过编程式导航跳转到后台主页，路由地址是 /welcome
        */
          window.localStorage.setItem('username', this.loginForm.username)
          window.localStorage.setItem('token', res.result.token)
          this.$router.push({
            path: '/welcome'
          })
        })
      } else {
        this.$refs.loginFormRef1.validate(async valid => {
          if (!valid) return
          this.loginForm1.param = true
          const {data: res} = await this.$http.post('signinbyphone', this.loginForm1)
          if (res.state !== 'success') return this.$message.error(res.msg)
          this.$message.success('登录成功')
          /*
            1.将登录成功之后的token保存到客户端的sessionStorage中
                项目中除了登录之外的其他API接口，必须在登录之后才能访问
                token只应在当前网站打开期间有效，所以将token保存在sessionStorage中
            2.通过编程式导航跳转到后台主页，路由地址是 /welcome
        */
          window.localStorage.setItem('phone', this.loginForm1.username)
          window.localStorage.setItem('token', res.result.token)
          this.$router.push({
            path: '/welcome'
          })
        })
      }
      //  console.log(this.loginForm.rightPhone)
    }
  }
}
</script>

<style lang="less" scoped>
.codeClass {
  text-align: center;
  top: 30%;
}
.text {
  text-align: center;
  line-height: 150px;
  color: #ffffff;
  font-size: 55px;
}

.login_container {
  background-color: #2d3a4b;
  height: 100%;
}
.login_header_title a{
  text-decoration: none;
  font-size:14px;
  color: #eee6e6;
  padding-bottom:4px;
}
.login_header_title a:first-child{
  margin-right:320px;
}
.login_header_title a.on{
  color :#02a774;
  font-weight: bolder;
  border-bottom:2px solid #02a774;
}
.login_box {
  width: 450px;
  height: 50%;
  background-color: #2d3a4b;
  //background-color: #e7eab4;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 52%;
  transform: translate(-50%, -30%);

  .avatar_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -60%);
    background-color: #00ff80;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}

.login_form {
  position: absolute;
  top: 100px;
  width: 100%;
  padding: 0 20px;
  box-sizing: border-box;
}

@media only screen and (max-width: 470px) {
  .thirdparty-button {
    display: none;
  }
}
</style>
