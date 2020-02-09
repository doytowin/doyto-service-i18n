<template>
  <div style="height:100%;width:60%;margin:0 auto;display: flex;flex-direction: column;justify-content: center;">
    <div style="display: flex;flex-direction:column;justify-content:center;width:60%;margin:20% auto;padding:180px 20%;background-color: #f9f9f9;border-radius:20px">
      <h4>
        <el-badge style="vertical-align: text-bottom;">DOYTO</el-badge>
        多语种管理系统
      </h4>
      <div>
        <el-form>
          <el-form-item>
            <el-input placeholder="请输入账号" v-model="username">
              <template slot="prepend">账 号</template>
            </el-input>
          </el-form-item>
          <el-form-item>
            <el-input placeholder="请输入密码" type="password" v-model="password">
              <template slot="prepend">密 码</template>
            </el-input>
          </el-form-item>
          <el-button style="width:100%" type="button" @click="doLogin" variant="primary">登录</el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>
<script type="text/javascript">
  // import Cons from '../components/Cons'
  import axios from 'axios'

  export default {
  data () {
    return {
      username: '',
      password: ''
    }
  },
  created () {
    // 组件创建完后获取数据，
    // 此时 data 已经被 observed 了
    this.init()
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'init'
  },
  methods: {
    init () {
    },
    doLogin () {
      const params = new URLSearchParams()
      params.append('username', this.username)
      params.append('password', this.password)
      params.append('rememberMe', true)
      axios.post(Cons.authUrl + 'login', params)
        .then((response) => {
          // console.log(response)
          const ret = response.data
          if (ret.success) {
            if (this.$route.query.redirect) {
              this.$router.replace(this.$route.query.redirect)
            } else {
              this.$router.replace('/dashboard')
            }
          } else {
            this.$message({
              message: (ret && ret.info) || '登录失败',
              type: 'error'
            })
          }
        })
    }
  }
}
</script>
