<template>
  <div class="login_container">
  <el-form :model="registerform"  ref="registerformRef">
    <!--表单名称-->
    <div class="title">到云后台管理系统</div>
    <!--用户名-->
    <input class="username" placeholder="User name" v-model="registerform.username"/>
    <!--错误提示，下同-->
    <div class="error">{{registerform.uError}}</div>
    <!--密码-->
    <input class="password" placeholder="Password" v-model="registerform.userpwd"/>
    <div class="error">{{registerform.pError}}</div>
    <!--确认密码-->
    <input class="pwdagain" placeholder="Confirm Password" v-model="registerform.pwdagain"/>
    <div class="error">{{registerform.aError}}</div>
    <!--手机号-->
    <input class="phone" placeholder="phone" v-model="registerform.phone"/>
    <!--邮箱-->
    <input class="email" placeholder="email" v-model="registerform.email"/>
    <!--注册按钮-->
    <button class="register-btn" @click="userRegister">注册</button>
    <!--这里我们用路由跳转到登陆组件-->
    <router-link to="/Login">已有账号，登陆</router-link>
  </el-form>
  </div>
</template>

<script>
export default {
  // 这里是组件的名字，无影响
  name: 'Register',
  data () {
    return {
      // 这里主要是初始化用户输入的信息，以及错误提示
      registerform: {
        username: '',
        userpwd: '',
        pwdagain: '',
        phone: '',
        email: '',
        uError: '',
        pError: '',
        aError: ''
      }
    }
  },
  // methods 用于定义的函数。
  methods: {
    // 这里定义了一个用户注册函数
    userRegister: function () {
      // 简单进行一下验证，判断是否为空
      // 正经项目肯定需要更多的验证规则，这里只是提供一个思路
      if (this.registerform.username === '') {
        this.registerform.uError = '用户名不能为空！'
      } else {
        this.registerform.uError = ''
      }
      if (this.registerform.userpwd === '') {
        this.registerform.pError = '密码不能为空！'
      } else {
        this.registerform.pError = ''
      }
      // eslint-disable-next-line eqeqeq
      if (this.registerform.userpwd !== this.registerform.pwdagain) {
        this.registerform.aError = '两次密码不一致！'
      } else {
        this.registerform.aError = ''
      }
      if (this.registerform.uError === '' && this.registerform.pError === '' && this.registerform.aError === '') {
        //  验证通过后通过axios来完成ajax请求，将注册信息传到后端
        //  这里不涉及后端内容，仅提供思路
        //  this.$axios.get('路径?参数').then(res => ())
        //  this.$axios.post('路径',{参数}).then(res => ())
        //  默认注册成功，跳转到登陆页面
        this.$refs.registerformRef.validate(async valid => {
          if (!valid) return
          this.registerform.param = true
          const {data: res} = await this.$http.post('接口', this.registerform)
          if (res.state !== 'success') return this.$message.error(res.msg)
          this.$message.success('注册成功')
          // window.localStorage.setItem('username', this.registerform.username)
          // window.localStorage.setItem('token', res.result.token)
          // this.$router.push({
          //   path: '/welcome'
          // })
          console.log(this.registerform)
          //this.$router.push('/Login')
        })
      }
    }
  }
}
</script>

<style scoped>
form {
  margin: auto;
  width: 500px;
  color: white;
  // border: 1px solid white;
  border-radius: 10px;
  background-color: #2d3a4b;
}
.title {
  margin-top: 20px;
  margin-bottom: 20px;
  font-size: 30px;
}
.login_container {
  background-color: #2d3a4b;
  height: 100%;
}
input {
  width: 400px;
  height: 40px;
  background-color: rgb(255, 255, 255);
  padding-left: 5px;
  border-radius: 5px;
  color: #130909;
}
button {
  width: 100px;
  height: 30px;
  margin-bottom: 20px;
}
form a{
  display: block;
  margin-bottom: 10px;
  cursor: pointer;
}
.error {
  font-size: 14px;
  color: red;
  height: 20px;
}
</style>
