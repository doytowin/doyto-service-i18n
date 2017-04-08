<template>
  <div>
    <div class="mb-2">
      <a class="btn btn-secondary" :href="openapi + '/openapi/i18n/' + group + '.xlsx'" v-t>导出Excel</a>
      <b-btn v-t>打包JSON格式</b-btn>
      <b-btn v-t>打包Properties格式</b-btn>
      <b-btn v-t>从Excel文件导入</b-btn>
      <b-btn class="btn-success float-right" v-t>添加</b-btn>
    </div>
    <section>
      <table class="table table-hover" style="margin-bottom:0">
        <thead>
        <tr class="text-center">
          <th>#</th>
          <th v-for="(v, k) in crud.p.list[0]"><t>{{k == 'value' ? '默认值' : k}}</t></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(group, $index) in crud.p.list">
          <td>{{$index+1}}</td>
          <td v-for="(v, k) in crud.p.list[0]">{{group[k]}}</td>
        </tr>
        </tbody>
      </table>
    </section>
    <footer>
      <div v-if="crud.p.pages" class="flow page-ul dropup">
        <div class="btn-group">
          <button class="btn btn-default" @click="crud.p.first"><i class="fa fa-step-backward"></i></button>
          <button class="btn btn-default" @click="crud.p.prev"><i class="fa fa-backward"></i></button>
          <b-dropdown :text="crud.p.page + ' / ' + crud.p.pages">
            <ul slot="dropdown-menu" class="dropdown-menu" style="max-height: 200px;overflow-y:scroll;">
              <li v-for="(s, $index) in crud.p.pages">
                <a @click="crud.p.goto($index+1)">{{$index+1}} / {{crud.p.pages}}</a>
              </li>
            </ul>
          </b-dropdown>
          <button class="btn btn-default" @click="crud.p.next"><i class="fa fa-forward"></i></button>
          <button class="btn btn-default" @click="crud.p.last"><i class="fa fa-step-forward"></i></button>
          <button class="btn btn-default" @click="crud.p.load"><i class="fa fa-refresh"></i></button>
        </div>
        <div class="pull-right">
          <span class="hidden-xs">共有{{crud.p.total}}条数据，显示第{{crud.p.from}}条到第{{crud.p.to}}条，每页</span>
          <b-dropdown :text="crud.p.limit + ''">
            <ul slot="dropdown-menu" class="dropdown-menu pull-right" style="min-width:55px;">
              <li v-for="s in [5,10,20,30,50]" class="text-right"><a @click="crud.p.size(s)">{{s}}</a></li>
            </ul>
          </b-dropdown>
          条
        </div>
      </div>
    </footer>
  </div>
</template>
<style lang="scss">
</style>
<script type="text/javascript">
  import Crud from '../components/Crud'
  import Util from '../components/Util'
  import Cons from '../components/Cons'

  export default {
    data () {
      return {
        loading: false,
        openapi: Cons.apiHost,
        group: this.$route.params.group,
        crud: null
      }
    },
    components: {},
    created () {
      // 组件创建完后获取数据，
      // 此时 data 已经被 observed 了
      this.group = this.$route.params.group
      this.init()
    },
    watch: {
      // 如果路由有变化，会再次执行该方法
      '$route': 'init'
    },
    methods: {
      init () {
        this.loading = true
        this.crud = new Crud(this.$resource(this.openapi + 'api/i18n/' + this.group), function (data) {
          if (data.success) {
            this.crud.p.load()
          } else {
            Util.handleFailure(data)
          }
          this.loading = false
        })
      }
    }
  }
</script>
