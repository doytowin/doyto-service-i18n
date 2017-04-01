<template>
  <div id="app">
    <div id="north" class="card card-inverse card-info">
      <div class="card-header" style="padding:0.5rem 1.25rem" v-t>国际化管理系统</div>
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
  </div>
</template>

<script type="text/javascript">
  import DwLeftMenu from './partial/DwLeftMenu'
  import DwLang from './partial/DwLang'

  export default {
    name: 'app',
    data () {
      return {}
    },
    components: {
      DwLeftMenu,
      DwLang
    }
  }
</script>

<style lang="scss">
  $west-width:220px;

  #app {
    -webkit-font-smoothing:antialiased;
    -moz-osx-font-smoothing:grayscale;
    color:#2c3e50;
    /*margin-top:60px;*/

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

    /* !!fix */
    input[type="button"], input[type="submit"], input[type="reset"], input[type="file"]::-webkit-file-upload-button, button {
      cursor:pointer;
    }
  }
</style>
