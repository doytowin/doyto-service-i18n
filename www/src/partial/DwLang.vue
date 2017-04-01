<template>
  <div>
    <b-dropdown v-if="$root.lang" :text="$t('语言') + ': ' + $t('locale_' + $root.lang)" dropup variant="primary">
      <b-dropdown-item @click="$root.switchLang('zh_CN')">简体中文</b-dropdown-item>
      <b-dropdown-item @click="$root.switchLang('en_US')">English(US)</b-dropdown-item>
    </b-dropdown>
  </div>
</template>
<style lang="scss">
  #west {
    .dropdown {
      position:absolute;
      bottom:0;
      left:0;
      right:0;
      .dropdown-toggle, .dropdown-menu {
        width:100%;
      }
      .dropdown-menu {
        max-height:200px;
        text-align:right;
      }
    }
  }
</style>
<script type="text/javascript">
  // import Vue from 'vue'

  export default{
    data () {
      return {}
    }
  }
</script>
