<template>
  <div class="module-wrapper" ref="wrapper">
    <header ref="header"></header>
    <section>
      <el-table :data="crud.p.list" stripe style="width: 100%" fixed>
        <el-table-column type="index" width="40" fixed align="center"></el-table-column>
        <el-table-column prop="name" :label="$t('资源') + $t(' ') + $t('名称')" align="center"></el-table-column>
        <el-table-column prop="label" :label="$t('label')" align="center"></el-table-column>
        <el-table-column prop="createTime" :label="$t('创建') + $t(' ') + $t('时间')" align="center"></el-table-column>
        <el-table-column :label="$t('操作')" width="140">
          <template slot-scope="scope">
            <a @click="crud.remove(scope.row)" v-t>删除</a>
          </template>
        </el-table-column>
      </el-table>
    </section>
    <footer ref="footer">
      <dw-page :p="crud.p"></dw-page>
    </footer>
  </div>
</template>
<script type="text/javascript">
import Cons from '../components/Cons.js'

export default {
  data() {
    return {
      tableHeight: 0,
      crud: new Crud(this.$resource(Cons.apiHost + 'api/resource-group'))
    }
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'init'
  }
}
</script>
