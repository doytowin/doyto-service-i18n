<template>
  <div style="position:relative" ref="out">
    <div class="mb-2">
      <a class="btn btn-secondary" :href="openapi + '/openapi/i18n/' + group + '.xlsx'" v-t>导出Excel</a>
      <b-btn v-t>打包JSON格式</b-btn>
      <b-btn v-t>打包Properties格式</b-btn>
      <b-btn v-t>从Excel文件导入</b-btn>
      <b-btn class="btn-success float-right ml-2" v-t>添加标签</b-btn>
      <b-btn class="btn-success float-right" v-t>添加语种</b-btn>
    </div>
    <section style="overflow-y:auto" :style="{maxHeight: (tableHeight) + 'px'}">
      <table class="table table-hover" style="margin-bottom:0;">
        <thead>
        <tr class="text-center">
          <th>#</th>
          <th v-for="(v, k) in crud.p.list[0]"><t>{{k == 'value' ? '默认值' : k}}</t></th>
        </tr>
        </thead>
        <tbody style="background-color: #f7f7f9;">
        <tr v-for="(group, $index) in crud.p.list">
          <td>{{$index+1}}</td>
          <td v-for="(v, k) in crud.p.list[0]">{{group[k]}}</td>
        </tr>
        </tbody>
      </table>
    </section>
    <footer>
      <div v-if="crud.p.pages" class="flow page-footer">
        <div class="btn-group btn-group-sm">
          <button class="btn btn-secondary" @click="crud.p.first"><i class="fa fa-step-backward"></i></button>
          <button class="btn btn-secondary" @click="crud.p.prev"><i class="fa fa-backward"></i></button>
          <b-dropdown size="sm" :text="crud.p.page + ' / ' + crud.p.pages" dropup class="page-number">
            <b-dropdown-item v-for="(s, $i) in crud.p.pages" :key="$i" @click="crud.p.goto($i+1)">
              {{$i+1}} / {{crud.p.pages}}
            </b-dropdown-item>
          </b-dropdown>
          <button class="btn btn-secondary" @click="crud.p.next"><i class="fa fa-forward"></i></button>
          <button class="btn btn-secondary" @click="crud.p.last"><i class="fa fa-step-forward"></i></button>
          <button class="btn btn-secondary" @click="crud.p.load"><i class="fa fa-refresh"></i></button>
        </div>
        <div class="pull-right">
          <span class="hidden-xs">共有{{crud.p.total}}条数据，显示第{{crud.p.from}}条到第{{crud.p.to}}条，每页</span>
          <b-dropdown size="sm" :text="crud.p.limit + ''" dropup :right="true" class="page-size">
            <b-dropdown-item v-for="(s, $i) in [5,10,20,30,50]" :key="$i" @click="crud.p.size(s)" class="text-right">{{s}}</b-dropdown-item>
          </b-dropdown>
          条
        </div>
      </div>
    </footer>
  </div>
</template>
<style lang="scss">
  .page-footer {
    border-top:1px #eee solid;
    padding-top:6px;
    position:absolute;
    left:0;
    right:0;
    bottom:-40px;

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
        /*text-align:right;*/
      }
      .dropdown-menu {
        max-height:200px;
        overflow-y:scroll;
      }
    }
  }
</style>
<script type="text/javascript">
  import Crud from '../components/Crud'
  import Util from '../components/Util'
  import Cons from '../components/Cons'

  export default {
    data () {
      return {
        tableHeight: 200,
        openapi: Cons.apiHost,
        group: this.$route.params.group,
        crud: {
          p: {}
        }
      }
    },
    components: {},
    created () {
      // 组件创建完后获取数据，
      // 此时 data 已经被 observed 了
      this.init()
    },
    mounted () {
      this.handleTableHeight()
      window.addEventListener('resize', this.handleTableHeight)
    },
    beforeDestroy: function () {
      window.removeEventListener('resize', this.handleTableHeight)
    },
    watch: {
      // 如果路由有变化，会再次执行该方法
      '$route': 'init',
      'crud.p.loading' () {
        if (this.crud.p.loading) {
          window.bus.$emit('loading')
        } else {
          window.bus.$emit('loaded')
        }
      }
    },
    methods: {
      init () {
        this.crud = new Crud(this.$resource(this.openapi + 'api/i18n/' + this.group), function (data) {
          if (data.success) {
            this.crud.p.load()
          } else {
            Util.handleFailure(data)
          }
        })
      },
      handleTableHeight () {
        this.tableHeight = this.$el.parentElement.clientHeight - 50 - 60 - 45
      }
    }
  }
</script>
