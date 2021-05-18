<template>
  <div class="login_container">
  <form>
    <!--表单名称-->
    <div class="title">到云后台管理系统</div>
    <!--用户名-->
    <input class="username" placeholder="User name" v-model="username"/>
    <!--错误提示，下同-->
    <div class="error">{{uError}}</div>
    <!--密码-->
    <input class="password" placeholder="Password" v-model="userpwd"/>
    <div class="error">{{pError}}</div>
    <!--确认密码-->
    <input class="pwdagain" placeholder="Confirm Password" v-model="pwdagain"/>
    <div class="error">{{aError}}</div>
    <!--手机号-->
    <input class="phone" placeholder="phone" v-model="phone"/>
    <!--邮箱-->
    <input class="email" placeholder="email" v-model="email"/>
    <!--注册按钮-->
    <button class="register-btn" @click="userRegister">注册</button>
    <!--这里我们用路由跳转到登陆组件-->
    <router-link to="/Login">已有账号，登陆</router-link>
  </form>
  </div>
</template>

<script>
export default {
  // 这里是组件的名字，无影响
  name: 'Register',
  data: function () {
    return {
      // 这里主要是初始化用户输入的信息，以及错误提示
      username: '',
      userpwd: '',
      pwdagain: '',
      phone: '',
      email: '',
      uError: '',
      pError: '',
      aError: ''
    }
  },
  // methods 用于定义的函数。
  methods: {
    // 这里定义了一个用户注册函数
    userRegister: function () {
      // 简单进行一下验证，判断是否为空
      // 正经项目肯定需要更多的验证规则，这里只是提供一个思路
      if (this.username === '') {
        this.uError = '用户名不能为空！'
      } else {
        this.uError = ''
      }
      if (this.userpwd === '') {
        this.pError = '密码不能为空！'
      } else {
        this.pError = ''
      }
      // eslint-disable-next-line eqeqeq
      if (this.userpwd !== this.pwdagain) {
        this.aError = '两次密码不一致！'
      } else {
        this.aError = ''
      }
      if (this.uError === '' && this.pError === '' && this.aError === '') {
        //  验证通过后通过axios来完成ajax请求，将注册信息传到后端
        //  这里不涉及后端内容，仅提供思路
        //  this.$axios.get('路径?参数').then(res => ())
        //  this.$axios.post('路径',{参数}).then(res => ())
        //  默认注册成功，跳转到登陆页面
        this.$router.push('/Login')
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
  color: #ffffff;
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
