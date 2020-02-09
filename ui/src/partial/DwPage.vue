<template>
  <div class="page-bar">
    <span style="float:right">共 {{p.total}} 条，显示第{{p.from}}条到第{{p.to}}条
      <el-dropdown class="page-size" @command="p.resize" size="medium">
        <el-button :size="size">
          {{(p.size || 10)}}条/页<i class="el-icon-caret-top"/>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="(s, $i) in p.sizes" :key="$i" :command="s" class="text-right">{{s}}条/页</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </span>
    <el-button-group style="min-width:272px">
      <el-button :size="size" @click="p.first"><i class="fa fa-step-backward"/></el-button>
      <el-button :size="size" @click="p.prev"><i class="fa fa-backward"/></el-button>
      <el-dropdown class="page-number" style="float:left;position:relative;" size="medium" @command="p.goto">
        <el-button :size="size">
          {{(p.page || 0) + ' / ' + (p.pages || 0)}}
          <i class="el-icon-caret-top"/>
        </el-button>
        <el-dropdown-menu slot="dropdown" style="max-height:200px;overflow-y:scroll;">
          <el-dropdown-item v-for="(s, $i) in p.pages" :key="$i" :command="($i+1)" class="text-right">{{$i + 1}} / {{p.pages}}</el-dropdown-item>
        </el-dropdown-menu>
        <div></div>
      </el-dropdown>
      <el-button :size="size" @click="p.next"><i class="fa fa-forward"/></el-button>
      <el-button :size="size" @click="p.last"><i class="fa fa-step-forward"/></el-button>
      <el-button :size="size" @click="p.load"><i class="fa fa-refresh"/></el-button>
    </el-button-group>
  </div>
</template>
<script type="text/javascript">
  import Page from '../components/Page'

  export default {
  name: 'DwPage',
  props: {
    p: {
      type: Page,
      default: null,
      required: true,
      validator: function (value) {
        return true
      }
    },
    size: {
      type: String,
      default: 'mini'
    }
  },
  created() {
    this.p.load()
  },
  watch: {
    'p'() {
      this.p.load()
    },
    'p.loading'() {
      if (this.p.loading) {
        this.$root.$emit('loading')
      } else {
        this.$root.$emit('loaded')
      }
    }
  },
  methods: {
    reload() {
      this.p.load()
    }
  }
}
</script>
