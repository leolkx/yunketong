<template>
  <div class="main-div" :style="conTop">
    <div class="text">
      欢迎您使用到云后台管理系统
    </div>
    </div>
</template>

<script>
export default {
  data () {
    return {
      conTop: {
        backgroundImage: 'url(' + require('../../assets/welcome5.gif') + ')'
      }
    }
  }
}
</script>

<style lang="less">
.main-div {
  // margin-top: 0px;
 // margin-left: 50px;
  height: 100%;
  background: no-repeat center top;
  background-size: 100% 100%;
}
.text {
  text-align: center;
  line-height: 150px;
  color: #f6f1f1;
  font-size: 45px;
}
</style>
