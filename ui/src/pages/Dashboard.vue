<template>
  <div id="dashboard" class="full-height">
    <el-container class="full-height">
      <el-header height="40px">
        <div style="font-size:1.5rem;height:40px;line-height:40px;display:inline-block">
          <span @click="$root.isCollapse=!$root.isCollapse" style="margin-right:10px;cursor:pointer">
            <i v-if="!$root.isCollapse" class="el-icon-s-fold"/>
            <i v-if="$root.isCollapse" class="el-icon-s-unfold"/>
          </span>
          <t>国际化管理系统</t>
        </div>
        <el-menu default-active="5" mode="horizontal" style="float:right">
          <el-submenu index="5" @select="$root.switchLocale" background-color="#d9edf7">
            <template slot="title">
              <t v-for="option in $root.languages" v-if="option.locale === $root.lang">{{option.language}}</t>
            </template>
            <el-menu-item v-for="(option, $i) in $root.languages" :index="'5-' + $i" @click="$root.switchLocale(option.locale)">{{option.language}}</el-menu-item>
          </el-submenu>
          <el-submenu index="1">
            <template slot="title">{{username}}</template>
            <el-menu-item index="exit" @click="logout">退出</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-header>
      <el-container>
        <el-aside :width="$root.isCollapse ? '60px' : '200px'" class="module-wrapper west-wrapper">
          <dw-left-menu/>
        </el-aside>
        <el-main style="padding-bottom:0">
          <transition name="fade">
            <router-view/>
          </transition>
        </el-main>
      </el-container>
    </el-container>

    <div v-if="loading > 0">
      <div class="mask"></div>
      <div class="loader"></div>
    </div>
  </div>
</template>

<script type="text/javascript">
  import DwLeftMenu from '../partial/DwLeftMenu'
  import axios from "axios";

  export default {
    name: 'dashboard',
    data() {
      return {
        loading: 0,
        alerts: [],
        user: null,
        username: ''
      }
    },
    components: {
      DwLeftMenu
    },
    beforeCreate() {
      axios.get(Cons.authUrl + 'user').then(rsp => {
        let json = rsp.data
        if (json.success) {
          this.user = json.data
          if (this.user) {
            this.username = this.user.username
          }
        } else {
          Util.handleFailure(json)
        }
      })
    },
  mounted () {
    let vm = this
    vm.$root.$on('loading', function () {
      // console.log('loading1 ' + vm.loading)
      vm.loading++
      console.log('loading ' + vm.loading)
    })
    vm.$root.$on('loaded', function () {
      // console.log('loaded1 ' + vm.loading)
      if (vm.loading > 0) {
        vm.loading--
        console.log('loaded ' + vm.loading)
      } else {
        console.log('WARN:  ' + vm.loading)
      }
    })
    vm.$root.$on('alert', function (al) {
      let a = {
        content: al.content,
        timeout: 3,
        state: al.state || 'success'
      }
      if (typeof (al.timeout) === 'number' && al.timeout > 0) {
        a.timeout = al.timeout
      }
      vm.alerts.push(a)
    })

  },
    methods: {
      logout() {
        axios.post(Cons.authUrl + 'logout')
        .then((response) => {
          const ret = response.data
          if (ret.success) {
            this.$router.replace('/')
          } else {
            this.$message({
              message: (ret && ret.info) || '退出失败',
              type: 'error'
            })
          }
        })
      }
    }
  }
</script>
<style lang="scss">
  $maskZIndex: 999;
  $appZIndex: 10;
  $headerHeight: 40px;

  #dashboard {

    ::-webkit-input-placeholder { /* WebKit browsers */
      color: #ccc;
    }

    :-moz-placeholder { /* Mozilla Firefox 4 to 18 */
      color: #ccc;
      opacity: 1;
    }

    ::-moz-placeholder { /* Mozilla Firefox 19+ */
      color: #ccc;
      opacity: 1;
    }

    :-ms-input-placeholder { /* Internet Explorer 10+ */
      color: #ccc;
    }

    .el-header {
      background-color: #d9edf7;
      /*padding:0;*/
      .el-menu--horizontal {
        background-color: #d9edf7;

        > .el-menu-item, > .el-submenu .el-submenu__title {
          height: $headerHeight;
          line-height: $headerHeight;
        }
      }
    }

    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
    /*margin-top:60px;*/
    .west-wrapper {
      border-right: solid 1px #e6e6e6;
      background-color: #d9edf7;
      /*position: relative;*/
      /*height: 100%;*/
      /*max-height: 100%;*/
      /*overflow-y: auto;*/

      /*.dw-lang {*/
      /*  position: absolute;*/
      /*  bottom: 0;*/
      /*  left: 0;*/
      /*  right: 0;*/
      /*}*/

      .el-menu {
        border-right: none;
      }
    }

    .mask, .menu-mask {
      position:fixed;
      z-index:$maskZIndex;
      left:0;
      top:0;
      bottom:0;
      right:0;
      background-color:#000;
      opacity:0.2;
    }
    .loader {
      border:16px solid #f3f3f3; /* Light grey */
      border-top:16px solid #3498db; /* Blue */
      border-radius:50%;
      width:120px;
      height:120px;
      animation:spin 2s linear infinite;
      position:fixed;
      top:50%;
      left:50%;
      margin-top:-60px;
      margin-left:-60px;
      z-index:$maskZIndex + 1;
    }

    @keyframes spin {
      0% {
        transform:rotate(0deg);
      }
      100% {
        transform:rotate(360deg);
      }
    }
    #alert-list {
      position:fixed;
      top:50px;
      right:22px;
      z-index:$maskZIndex + 1;
    }

    /* !!fix */
    input[type="button"], input[type="submit"], input[type="reset"], input[type="file"]::-webkit-file-upload-button, button {
      cursor:pointer;
    }

    .menu-mask {
      display:none;
    }
    @media (max-width:767px) {
      .menu-mask {
        z-index:$appZIndex;
        display:block;
      }
      #east {
        left:0;
      }

    }
  }
</style>
