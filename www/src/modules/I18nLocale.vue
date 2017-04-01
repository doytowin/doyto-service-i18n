<template>
  <div>
    <section>
      <table class="table table-hover" style="margin-bottom:0">
        <thead>
        <tr class="text-center">
          <th>#</th>
          <th v-t>标签</th>
          <th v-t>文本</th>
          <th v-t>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(r, $index) in crud.p.list">
          <td>{{$index+1}}</td>
          <td>{{r.label}}</td>
          <td>{{r.value}}</td>
          <td>
            <a @click="crud.edit(r)" v-t>编辑</a>
            <!--<a @click="crud.remove(r)" v-t>删除</a>-->
          </td>
        </tr>
        </tbody>
      </table>
    </section>
  </div>
</template>
<style lang="scss">
</style>
<script type="text/javascript">

  export default {
    data () {
      return {
        loading: false,
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
    watch: {
      // 如果路由有变化，会再次执行该方法
      '$route': 'init'
    },
    methods: {
      init () {
        this.loading = true

        var group = this.$route.params.group
        var locale = this.$route.params.locale
        this.$http.get('http://localhost:9001/api/i18n/' + group + '/' + locale).then(response => {
          // get body data
          this.crud.p = response.body
          this.loading = false
        }, response => {
          // error callback
          this.loading = false
        })
      }
    }
  }
</script>
