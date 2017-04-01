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

  export default {
    data () {
      return {
        crud: {
          p: {}
        }
      }
    },
    mounted () {
      this.$http.get('http://localhost:9001/api/i18n/i18n/zh_CN').then(response => {
        // get body data
        this.crud.p = response.body
      }, response => {

        // error callback
      })
    },
    components: {},
    methods: {
    }
  }
</script>
