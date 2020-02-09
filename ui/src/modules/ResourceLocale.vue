<template>
  <div class="module-wrapper" ref="wrapper">
    <header ref="header">
      <el-button type="success" @click="crud.record={'group':group};crud.adding=true"><t>添加</t>{{$t(' ')}}<t>语种</t></el-button>
    </header>
    <section>
      <el-table :data="crud.p.list" stripe style="width: 100%" fixed>
        <el-table-column type="index" width="40" fixed align="center"/>
        <el-table-column prop="locale" :label="$t('语种')"/>
        <el-table-column prop="language" :label="$t('语言本称')"/>
        <el-table-column prop="baiduLocale" :label="$t('百度翻译') + $t('语言')"/>
        <el-table-column :label="$t('操作')" width="140">
          <template slot-scope="scope">
            <el-button type="text" @click="crud.edit(scope.row);crud.editing=true" v-t>编辑</el-button>
            <el-button type="text" @click="crud.remove(scope.row)" v-t>删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </section>
    <footer ref="footer">
      <dw-page :p="crud.p"/>
    </footer>

    <el-dialog :title="$t('添加') + $t(' ') + $t('语种')" :visible.sync="crud.adding" :modal-append-to-body="false">
      <el-form label-width="100px">
        <el-form-item :label="$t('语种')">
          <el-input v-model="crud.record.locale"/>
        </el-form-item>
        <el-form-item :label="$t('语言本称')">
          <el-input v-model="crud.record.language"/>
        </el-form-item>
        <el-form-item :label="$t('百度翻译') + $t('语言')">
          <el-select v-model="crud.record.baiduLocale" placeholder="请选择">
            <el-option v-for="option in baiduLanguages" :key="option.value" :label="option.label" :value="option.value"/>
          </el-select>
          <p><el-link type="primary" href="http://api.fanyi.baidu.com/api/trans/product/apidoc#languageList" :underline="false" target="_blank">查看语言列表</el-link></p>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="crud.adding = false" v-t>取消</el-button>
        <el-button type="primary" @click="crud.add(crud.record);crud.adding=false" v-t>保存</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="$t('编辑') + $t(' ') + $t('语种')" :visible.sync="crud.editing" :modal-append-to-body="false">
      <el-form label-width="100px">
        <el-form-item :label="$t('语种')">
          <el-input v-model="crud.record.locale"/>
        </el-form-item>
        <el-form-item :label="$t('语言本称')">
          <el-input v-model="crud.record.language"/>
        </el-form-item>
        <el-form-item :label="$t('百度翻译') + $t('语言')">
          <el-select v-model="crud.record.baiduLocale" placeholder="请选择">
            <el-option v-for="option in baiduLanguages" :key="option.value" :label="option.label" :value="option.value"/>
          </el-select>
          <p><el-link type="primary" href="http://api.fanyi.baidu.com/api/trans/product/apidoc#languageList" :underline="false" target="_blank">查看语言列表</el-link></p>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="crud.editing = false" v-t>取消</el-button>
        <el-button type="primary" @click="crud.save(crud.record);crud.editing=false" v-t>保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script type="text/javascript">
export default {
  data () {
    return {
      baiduLanguages: [
        {
          label: '自动检测',
          value: 'auto'
        },
        {
          label: '简体中文',
          value: 'zh'
        },
        {
          label: '英语',
          value: 'en'
        },
        {
          label: '日语',
          value: 'jp'
        },
        {
          label: '韩语',
          value: 'kor'
        },
        {
          label: '法语',
          value: 'fra'
        },
        {
          label: '西班牙语',
          value: 'spa'
        },
        {
          label: '泰语',
          value: 'th'
        },
        {
          label: '阿拉伯语',
          value: 'ara'
        },
        {
          label: '俄语',
          value: 'ru'
        },
        {
          label: '葡萄牙语',
          value: 'pt'
        },
        {
          label: '德语',
          value: 'de'
        },
        {
          label: '意大利语',
          value: 'it'
        },
        {
          label: '希腊语',
          value: 'el'
        },
        {
          label: '荷兰语',
          value: 'nl'
        },
        {
          label: '波兰语',
          value: 'pl'
        },
        {
          label: '保加利亚语',
          value: 'bul'
        },
        {
          label: '爱沙尼亚语',
          value: 'est'
        },
        {
          label: '丹麦语',
          value: 'dan'
        },
        {
          label: '芬兰语',
          value: 'fin'
        },
        {
          label: '捷克语',
          value: 'cs'
        },
        {
          label: '罗马尼亚语',
          value: 'rom'
        },
        {
          label: '斯洛文尼亚语',
          value: 'slo'
        },
        {
          label: '瑞典语',
          value: 'swe'
        },
        {
          label: '匈牙利语',
          value: 'hu'
        },
        {
          label: '繁体中文',
          value: 'cht'
        },
        {
          label: '越南语',
          value: 'vie'
        }
      ],
      group: this.$route.params.group,
      crud: new Crud(this.$resource(this.getUrl()))
    }
  },
  created() {
    this.refresh()
  },
  methods: {
    getUrl () {
      let group = this.$route.params.group
      return ('{host}api/locale/{id}?group=' + group).replace(/{host}/, Cons.apiHost)
    },
    refresh () {
      this.group = this.$route.params.group
      const crud = new Crud(this.$resource(this.getUrl()))
      // this.crud.record.baiduLocale = 'auto'
      crud.add = (record) => {
        axios.post(Cons.apiHost + 'api/i18n/addLocale', record).then(rps => {
          crud.p.load()
        })
      }
      this.crud = crud
    }
  },
  watch: {
    // 如果路由有变化，会再次执行该方法
    '$route': 'refresh'
  }
}
</script>
