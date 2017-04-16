<template>
  <div style="position:relative">
    <section>
      <table class="table table-hover" style="margin-bottom:0">
        <thead>
        <tr class="text-center">
          <th>#</th>
          <th v-t>语种</th>
          <th v-t>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(r, $index) in crud.p.list">
          <td>{{$index+1}}</td>
          <td>{{r.locale}}</td>
          <td>
            <a @click="crud.edit(r)" v-t>编辑</a>
            <a @click="crud.remove(r)" v-t>删除</a>
          </td>
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
</style>
<script type="text/javascript">
  import Cons from '../components/Cons.js'
  import Crud from '../components/Crud.js'
  import DwPage from '../partial/DwPage'

  export default {
    components: {
      DwPage
    },
    data () {
      return {
        group: undefined,
        crud: {
          p: {}
        }
      }
    },
    created () {
      this.init()
    },
    methods: {
      init () {
        this.group = this.$route.params.group
        var url = '{host}api/resource/{group}/locale'
                .replace(/{host}/, Cons.apiHost)
                .replace(/{group}/, this.group)

        this.crud = new Crud(this.$resource(url))
        this.$nextTick(function () {
          this.crud.p.load()
        })
        //
      }
    },
    watch: {
      // 如果路由有变化，会再次执行该方法
      '$route': 'init'
    }
  }
</script>
