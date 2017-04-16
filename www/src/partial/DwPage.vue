<template>
  <!--<div v-if="p.pages" class="flow page-footer">-->
  <div class="flow page-bar">
    <div class="btn-group btn-group-sm">
      <button class="btn btn-secondary" @click="p.first"><i class="fa fa-step-backward"></i></button>
      <button class="btn btn-secondary" @click="p.prev"><i class="fa fa-backward"></i></button>
      <b-dropdown size="sm" :text="(p.page || 0) + ' / ' + (p.pages || 0)" dropup class="page-number">
        <b-dropdown-item v-for="(s, $i) in p.pages" :key="$i" @click="p.goto($i+1)">
          {{$i+1}} / {{p.pages}}
        </b-dropdown-item>
      </b-dropdown>
      <button class="btn btn-secondary" @click="p.next"><i class="fa fa-forward"></i></button>
      <button class="btn btn-secondary" @click="p.last"><i class="fa fa-step-forward"></i></button>
      <button class="btn btn-secondary" @click="p.load"><i class="fa fa-refresh"></i></button>
    </div>
    <div class="hidden-sm-down pull-right">
      <span class="hidden-xs">共有{{p.total}}条数据，显示第{{p.from}}条到第{{p.to}}条，每页</span>
      <b-dropdown size="sm" :text="(p.limit || 10) + ''" dropup :right="true" class="page-size">
        <b-dropdown-item v-for="(s, $i) in [5,10,20,30,50]" :key="$i" @click="p.size(s)" class="text-right">{{s}}</b-dropdown-item>
      </b-dropdown>
      条
      <span v-for="a in p"></span>
    </div>
  </div>
</template>
<style lang="scss" >
  .page-bar {
    .page-size {
      .dropdown-toggle, .dropdown-menu {
        min-width:85px;
      }
    }
    .page-number {
      .dropdown-toggle, .dropdown-item, .dropdown-menu  {
        min-width:80px;
      }
      .dropdown-item {
        padding:3px 0.75rem;
      }
      .dropdown-menu {
        max-height:200px;
        overflow-y:scroll;
      }
    }
  }
</style>
<script type="text/javascript">
  import Page from '../components/Page'
  export default{
    props: {
      p: {
        type: Page,
        default: null,
        required: true,
        validator: function (value) {
          return true
        }
      }
    },
    created () {
      this.p.load()
    },
    watch: {
      'p' () {
        this.p.load()
      },
      'p.loading' () {
        if (this.p.loading) {
          window.bus.$emit('loading')
        } else {
          window.bus.$emit('loaded')
        }
      }
    },
    methods: {
      reload () {
        this.p.load()
      }
    }
  }
</script>
