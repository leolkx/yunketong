<template>
  <div id="app">
<!--    <p class="nav">-->
      <!-- 使用 router-link 组件来导航. -->
      <!-- 通过传入 `to` 属性指定链接. -->
      <!-- <router-link> 默认会被渲染成一个 `<a>` 标签 -->
<!--      <router-link to="/Login">首页</router-link>-->
<!--      <router-link to="/Register">注册</router-link>-->
<!--    </p>-->
    <!-- 路由占位符 -->
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style lang='less' scoped>
#app{
  height: 100%;
}
</style>
