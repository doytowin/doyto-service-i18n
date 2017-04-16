<template>
  <div style="position:relative" ref="out">
    <div class="mb-2 clearfix ">
      <a class="btn btn-secondary" :href="openapi + '/openapi/i18n/' + group + '.xlsx'" v-t>导出Excel</a>
      <b-btn v-t>打包JSON格式</b-btn>
      <b-btn v-t>打包Properties格式</b-btn>
      <b-btn v-t>从Excel文件导入</b-btn>
      <div class="hidden-sm-up">
        <b-btn class="btn-success mr-2" v-t>添加标签</b-btn>
        <b-btn class="btn-success" v-t>添加语种</b-btn>
      </div>
      <div class="hidden-sm-down float-right">
        <b-btn class="btn-success mr-2" v-t>添加标签</b-btn>
        <b-btn class="btn-success" v-t>添加语种</b-btn>
      </div>
    </div>
    <section style="overflow-y:auto" :style="{maxHeight: (tableHeight) + 'px'}">
      <table class="table table-hover table-responsive2" style="margin-bottom:0;">
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
    <footer class="page-footer">
      <dw-page :p="crud.p"></dw-page>
    </footer>
  </div>
</template>
<style lang="scss">
  .table td {
    word-break: keep-all
  }
  .page-footer {
    border-top:1px #eee solid;
    padding-top:6px;
    position:absolute;
    left:0;
    right:0;
    bottom:-40px;
  }
</style>
<script type="text/javascript">
  import Crud from '../components/Crud'
  import Util from '../components/Util'
  import Cons from '../components/Cons'
  import DwPage from '../partial/DwPage'

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
    components: {
      DwPage
    },
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
      '$route': 'init'
    },
    methods: {
      init () {
        this.group = this.$route.params.group
        this.crud = new Crud(this.$resource(this.openapi + 'api/i18n/' + this.group), function (data) {
          if (data.success) {
            this.crud.p.load()
          } else {
            Util.handleFailure(data)
          }
        })
        this.$nextTick(function () {
          this.crud.p.load()
        })
      },
      handleTableHeight () {
        this.tableHeight = this.$el.parentElement.clientHeight - 50 - 60 - 45
      }
    }
  }
</script>
