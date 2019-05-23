<template>
  <div class="module-wrapper" ref="wrapper">
    <header ref="header">
      <a class="el-button el-button--default" :href="openapi + 'i18n/' + group + '.xlsx'"><t>导出</t><t>_</t>Excel</a>
      <!--<el-button v-t>打包JSON格式</el-button>
      <el-button v-t>打包Properties格式</el-button>
      <el-button v-t>从Excel文件导入</el-button>-->
      <!--<div class="hidden-sm-up">
        <el-button type="success" v-t>添加条目</el-button>
      </div>-->
      <!--<div style="float:right">
        <el-button type="success" v-t>添加条目</el-button>
      </div>-->
    </header>
    <section>
      <el-table :data="crud.p.list" stripe style="width: 100%" fixed>
        <el-table-column type="index" width="40" fixed align="center"></el-table-column>
        <el-table-column prop="label" :label="$t('标签')" width="140" :show-overflow-tooltip="true" fixed></el-table-column>
        <el-table-column prop="defaults" :label="$t('默认文本')" width="140" :show-overflow-tooltip="true" fixed></el-table-column>
        <el-table-column
          v-for="(v, k) in crud.p.list[0]" v-if="k.substring(0,7) == 'locale_'"
          :key="k" :prop="k" :label="$t(k)" width="120" :show-overflow-tooltip="true"></el-table-column>
      </el-table>
    </section>
    <footer ref="footer">
      <dw-page :p="crud.p"></dw-page>
    </footer>
  </div>
</template>
<script type="text/javascript">
import Crud from '../components/Crud'
import Cons from '../components/Cons'

export default {
  data () {
    return {
      tableHeight: 0,
      openapi: Cons.openapi,
      group: this.$route.params.group,
      crud: new Crud(this.$resource(Cons.apiHost + 'api/i18n/' + this.$route.params.group))
    }
  },
  methods: {
    refresh () {
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
