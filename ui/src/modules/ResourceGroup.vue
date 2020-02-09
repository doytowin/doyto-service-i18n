<template>
  <div class="module-wrapper" ref="wrapper">
    <header ref="header">
      <el-button type="success" @click="crud.record={};crud.adding=true"><t>添加</t>{{$t(' ')}}<t>分组</t></el-button>
    </header>
    <section>
      <el-table :data="crud.p.list" stripe style="width: 100%" fixed>
        <el-table-column type="index" width="40" fixed align="center"/>
        <el-table-column prop="name" :label="$t('资源') + $t(' ') + $t('名称')" align="center"/>
        <el-table-column prop="label" :label="$t('label')" align="center"/>
        <el-table-column prop="createTime" :label="$t('创建') + $t(' ') + $t('时间')" align="center"/>
        <el-table-column :label="$t('操作')" width="140">
          <template slot-scope="scope">
            <el-button type="text" @click="crud.remove(scope.row)" v-t>删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </section>
    <footer ref="footer">
      <dw-page :p="crud.p"/>
    </footer>

    <el-dialog :title="$t('添加') + $t(' ') + $t('分组')" :visible.sync="crud.adding" :modal-append-to-body="false">
      <el-form label-width="100px">
        <el-form-item :label="$t('名称')">
          <el-input v-model="crud.record.group"/>
        </el-form-item>
        <el-form-item :label="$t('标签')">
          <el-input v-model="crud.record.label"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="crud.adding = false" v-t>取消</el-button>
        <el-button type="primary" @click="addGroup(crud.record)" v-t>保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script type="text/javascript">
  import Cons from '../components/Cons.js'

  export default {
  data() {
    return {
      tableHeight: 0,
      crud: new Crud(this.$resource(Cons.api('api/group/{id}')))
    }
  },
  methods: {
    addGroup (record) {
      let crud = this.crud
      axios.post(Cons.api('api/i18n/create'), record).then(
        rsp => {
          let json = rsp.data
          this.$root.$emit('loaded')
          if (json.success) {
            crud.p.load()
            crud.adding = false
          } else {
            Util.handleFailure(json)
          }
        }
      )
    }
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'init'
  }
}
</script>
