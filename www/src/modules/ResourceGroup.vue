<template>
  <div>
    <header>

    </header>
    <section>
      <table class="table table-hover" style="margin-bottom:0">
        <thead>
        <tr class="text-center">
          <th>#</th>
          <th v-t>资源组名</th>
          <th v-t>创建人</th>
          <th v-t>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(r, $index) in crud.p.list">
          <td>{{$index+1}}</td>
          <td>{{r.name}}</td>
          <td>{{r.owner}}</td>
          <td>
            <a @click="crud.edit(r)" v-t>编辑</a>
            <a @click="crud.remove(r)" v-t>删除</a>
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
  import Cons from '../components/Cons.js'

  export default {
    components: {

    },
    data () {
      return {
        crud: {
          p: {}
        }
      }
    },
    mounted () {
      this.$http.get(Cons.apiHost + 'api/resource-group').then(response => {
        // get body data
        this.crud.p = response.body
      }, response => {
        // error callback
      })
    }
  }
</script>
