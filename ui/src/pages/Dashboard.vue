<template>
  <div id="dashboard">
    <el-container style="height:100%">
      <el-aside class="west-wrapper" width="220px" :style="{left: showMenu ? 0 : '-280px'}">
        <div class="card-header" style="padding:0 1.25rem;height:40px;line-height:40px;">
          <i @click="showMenu = !showMenu" class="fa fa-bars hidden-sm-up mr-2"></i>
          <t>国际化管理系统</t> <b>{{username}}</b>
        </div>
        <dw-left-menu></dw-left-menu>
        <dw-lang></dw-lang>
      </el-aside>

      <el-container>
        <el-main>
          <transition name="fade">
            <router-view></router-view>
          </transition>
        </el-main>
      </el-container>
    </el-container>

    <div v-if="loading > 0">
      <div class="mask"></div>
      <div class="loader"></div>
    </div>
    <div v-if="showMenu">
      <div @click="showMenu=false" class="menu-mask"></div>
    </div>
  </div>
</template>

<script type="text/javascript">
import DwLeftMenu from '../partial/DwLeftMenu'
import DwLang from '../partial/DwLang'

export default {
  name: 'dashboard',
  data () {
    return {
      loading: 0,
      showMenu: true,
      alerts: [],
      user: null,
      username: ''
    }
  },
  components: {
    DwLeftMenu, DwLang
  },
  beforeCreate () {
    axios.get(Cons.apiHost + 'auth/user').then(rsp => {
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

    vm.showMenu = document.body.clientWidth > 767
    window.addEventListener('resize', function () {
      vm.showMenu = document.body.clientWidth > 767
    })
  },
  methods: {}
}
</script>
<style lang="scss">
  $maskZIndex:999;
  $appZIndex:10;

  #dashboard {
    height:100%;
    ::-webkit-input-placeholder { /* WebKit browsers */
      color:#ccc;
    }
    :-moz-placeholder { /* Mozilla Firefox 4 to 18 */
      color:#ccc;
      opacity:1;
    }
    ::-moz-placeholder { /* Mozilla Firefox 19+ */
      color:#ccc;
      opacity:1;
    }
    :-ms-input-placeholder { /* Internet Explorer 10+ */
      color:#ccc;
    }

    -webkit-font-smoothing:antialiased;
    -moz-osx-font-smoothing:grayscale;
    color:#2c3e50;
    /*margin-top:60px;*/
    .west-wrapper {
      border-right:solid 1px #e6e6e6;
      background-color:#d9edf7;
      position:relative;
      height:100%;
      max-height:100%;
      overflow-y:auto;
      .dw-lang {
        position:absolute;
        bottom:0;
        left:0;
        right:0;
      }
      .el-menu {
        border-right:none;
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
