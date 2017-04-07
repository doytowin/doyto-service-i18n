<template>
  <div id="app">
    <div id="north" class="card card-inverse card-info">
      <div class="card-header" style="padding:0 1.25rem" v-t>国际化管理系统</div>
    </div>
    <div id="west">
      <dw-left-menu></dw-left-menu>
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

    <div id="alert-list">
      <div v-for="(a, $i) in alerts">
        <b-alert :show="a.timeout" :state="a.type">
          <!--<b-alert v-for="(a, $i) in alerts" :key="$i" :show="a.dismissCountDown" dismissible :state="a.state" @dismiss-count-down="countDownChanged">-->
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
        alerts: []
      }
    },
    components: {
      DwLeftMenu,
      DwLang
    },
    created () {
      let vm = this
      let bus = window.bus
      bus.$on('loading', function () {
        // console.log('loading1 ' + vm.loading)
        vm.loading++
        console.log('loading ' + vm.loading)
      })
      bus.$on('loaded', function () {
        // console.log('loaded1 ' + vm.loading)
        vm.loading--
        console.log('loaded ' + vm.loading)
      })

      bus.$on('alert', function (al) {
        let a = {content: al.content, timeout: al.timeout || 3, state: al.type || 'info'}
        vm.$set(vm.alerts, vm.alerts.length, a)
          // this.alerts.unshift(a)
      })
    }
  }
</script>

<style lang="scss">
  $west-width:280px;
  $maskZIndex: 999;

  #app {
    -webkit-font-smoothing:antialiased;
    -moz-osx-font-smoothing:grayscale;
    color:#2c3e50;
    /*margin-top:60px;*/

    .mask {
      position: fixed;
      z-index: $maskZIndex;
      left:0;
      top:0;
      bottom:0;
      right:0;
      background-color:#000;
      opacity:0.2;
    }
    .loader {
      border: 16px solid #f3f3f3; /* Light grey */
      border-top: 16px solid #3498db; /* Blue */
      border-radius: 50%;
      width: 120px;
      height: 120px;
      animation: spin 2s linear infinite;
      position: fixed;
      top: 50%;
      left: 50%;
      margin-top: -60px;
      margin-left: -60px;
      z-index: $maskZIndex + 1;
    }

    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
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
      z-index:13;
      border-radius:0;
    }
    #west {
      left:0;
      width:$west-width;
      background-color:#d9edf7;
      z-index:11;
      padding:42px 1px;
    }

    #east {
      padding:50px 10px 10px;
      left:$west-width;
      right:0;
      z-index:9;
    }

    #alert-list{
      position:fixed;
      top:50px;
      right:22px;
    }

    /* !!fix */
    input[type="button"], input[type="submit"], input[type="reset"], input[type="file"]::-webkit-file-upload-button, button {
      cursor:pointer;
    }
  }
</style>
