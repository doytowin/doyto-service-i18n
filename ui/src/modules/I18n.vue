<template>
  <div class="module-wrapper" ref="wrapper">
    <header ref="header">
      <a class="el-button el-button--default" :href="exportGroup(group)" style="text-decoration:none"><t>导出</t><t/>Excel</a>
      <!--<el-button v-t>打包JSON格式</el-button>
      <el-button v-t>打包Properties格式</el-button>
      <el-button v-t>从Excel文件导入</el-button>-->
    </header>
    <section>
      <el-table :data="crud.p.list" stripe style="width: 100%" fixed>
        <el-table-column type="index" width="40" fixed align="center"/>
        <el-table-column prop="LABEL" :label="$t('标签')" width="100" :show-overflow-tooltip="true" fixed/>
        <el-table-column prop="DEFAULTS" :label="$t('默认文本')" width="100" :show-overflow-tooltip="true" fixed/>
        <el-table-column width="140" v-for="(v, k) in crud.p.list[0]" v-if="k.substring(0,7) === 'LOCALE_'"
          :key="k" :prop="k" :label="$t(k)" :show-overflow-tooltip="true"/>
        <el-table-column prop="MEMO" :label="$t('备注')" width="200" :show-overflow-tooltip="true" fixed/>
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
      group: this.$route.params.group,
      crud: new Crud(this.$resource(Cons.apiHost + 'api/i18n/' + this.$route.params.group))
    }
  },
  methods: {
    exportGroup(group) {
      return Cons.openApi('i18n/' + group + '.xlsx')
    },
    refresh() {
      this.group = this.$route.params.group
      this.crud = new Crud(this.$resource(Cons.apiHost + 'api/i18n/' + this.$route.params.group))
    }
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'refresh'
  }
}
</script>
