<template>
  <div class="module-wrapper" ref="wrapper">
    <header ref="header">
      <a class="el-button el-button--default" :href="exportGroup()" style="text-decoration:none"><t>导出</t><t/>Excel</a>
      <!--<el-button v-t>打包JSON格式</el-button>
      <el-button v-t>打包Properties格式</el-button>
      <el-button v-t>从Excel文件导入</el-button>-->
    </header>
    <section>
      <el-table :data="crud.p.list" stripe style="width: 100%" fixed>
        <el-table-column type="index" width="40" fixed align="center"/>
        <el-table-column prop="LABEL" :label="$t('标签')" width="120" :show-overflow-tooltip="true" fixed/>
        <el-table-column prop="DEFAULTS" :label="$t('默认文本')" width="100" :show-overflow-tooltip="true" fixed/>
        <template v-for="(v, k) in crud.p.list[0]">
          <el-table-column min-width="140" v-if="/^locale_/i.test(k)" :key="k" :prop="k" :label="$t(k.toLowerCase())" :show-overflow-tooltip="true"/>
        </template>
        <el-table-column prop="MEMO" :label="$t('备注')" width="200" :show-overflow-tooltip="true" fixed="right"/>
      </el-table>
    </section>
    <footer ref="footer">
      <dw-page :p="crud.p"/>
    </footer>
  </div>
</template>
<script type="text/javascript">
import Crud from '../components/Crud'
import Cons from '../components/Cons'

export default {
    data() {
      return {
        crud: undefined
      }
    },
    created() {
      this.refresh()
    },
    methods: {
      exportGroup() {
        return Cons.api('api/i18n/' + this.$route.params.group + '.xlsx')
      },
      refresh() {
        this.crud = new Crud(this.$resource(Cons.api('i18n/' + this.$route.params.group)))
      }
    },
    watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'refresh'
  }
}
</script>
