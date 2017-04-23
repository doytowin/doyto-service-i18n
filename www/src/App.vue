<template>
  <div id="app">
    <div id="north" class="card card-inverse card-info">
      <div class="card-header" style="padding:0 1.25rem">
        <i @click="showMenu = !showMenu" class="fa fa-bars hidden-sm-up mr-2"></i>
        <t>国际化管理系统</t>
      </div>
    </div>
    <div id="west" ref="west" :style="{left: showMenu ? 0 : '-280px'}">
      <div class="west-wrapper">
        <dw-left-menu></dw-left-menu>
      </div>
      <dw-lang></dw-lang>
    </div>
    <div id="east">
      <transition>
        <router-view></router-view>
      </transition>
    </div>

    <div v-if="loading > 0">
      <div class="mask"></div>
      <div class="loader"></div>
    </div>
    <div v-if="showMenu">
      <div @click="showMenu=false" class="menu-mask"></div>
    </div>

    <div id="alert-list">
      <div v-for="(a, $i) in alerts">
        <b-alert :show="a.timeout" :state="a.state" v-on:dismissed="alerts.splice($i, 1)">
          {{a.content}}
        </b-alert>
      </div>
    </div>
  </div>
</template>

<script type="text/javascript">
  import DwLeftMenu from './partial/DwLeftMenu'
  import DwLang from './partial/DwLang'

  export default {
    name: 'app',
    data () {
      return {
        loading: 0,
        showMenu: true,
        alerts: []
      }
    },
    components: {
      DwLeftMenu,
      DwLang
    },
    mounted () {
      let vm = this
      let bus = window.bus
      bus.$on('loading', function () {
        // console.log('loading1 ' + vm.loading)
        vm.loading++
        console.log('loading ' + vm.loading)
      })
      bus.$on('loaded', function () {
        // console.log('loaded1 ' + vm.loading)
        if (vm.loading > 0) {
          vm.loading--
          console.log('loaded ' + vm.loading)
        } else {
          console.log('WARN:  ' + vm.loading)
        }
      })

      bus.$on('alert', function (al) {
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
<style src="bootstrap/dist/css/bootstrap.css" lang="scss"></style>
<style src="bootstrap-vue/dist/bootstrap-vue.css" lang="scss"></style>
<style src="./assets/css/fix.scss" lang="scss"></style>
<style lang="scss">
  $west-width:280px;
  $maskZIndex:999;
  $appZIndex:10;

  #app {
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

    #west, #east {
      position:fixed;
      top:0;
      bottom:0;
      overflow:auto;
    }
    #north {
      position:fixed;
      top:0;
      left:0;
      right:0;
      bottom:0;
      overflow:auto;
      height:40px;
      line-height:40px;
      z-index:$appZIndex + 3;
      border-radius:0;
    }
    #west {
      left:0;
      width:$west-width;
      background-color:#d9edf7;
      z-index:$appZIndex + 1;
      padding:42px 1px;
      > div.west-wrapper {
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
      }
    }

    #east {
      padding:50px 10px 10px;
      left:$west-width;
      right:0;
      z-index:$appZIndex - 1;
      background-color:#fff;
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
      #west {
        left:-$west-width;
      }
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

