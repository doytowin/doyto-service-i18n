<template>
  <div id="i18n-locale" class="module-wrapper" ref="wrapper">
    <header ref="header">
      <el-button @click="add();beforeAdd()" type="success"><t>添加</t>{{$t(' ')}}<t>词条</t></el-button>
      <el-button @click="baiduTranslate" type="primary" style="float:right" v-t>百度翻译</el-button>
    </header>
    <section>
      <el-table :data="list" stripe style="width: 100%" fixed>
        <el-table-column type="index" width="40" fixed align="center"/>
        <el-table-column prop="label" :label="$t('标签')" width="140"/>
        <el-table-column prop="defaults" :label="$t('默认文本')" width="140"/>
        <el-table-column>
          <template slot-scope="scope" slot="header">
            <el-dropdown @command="changeLocale">
              <span class="el-dropdown-link">
                <t>locale_{{locale}}</t>
                <i class="el-icon-arrow-down el-icon--right"/>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="item.locale" v-for="(item) in locales" v-t>locale_{{item.locale}}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </template>
          <template slot-scope="scope">
            <div class="dw-editor">
              <el-input type="text" v-model="scope.row.value"
                        @keyup.enter.native="save(scope.row)" @focus="save(lastEdit)" @blur="lastEdit=scope.row"
                        :tabindex="scope.$index + 1000 + ''" :placeholder="scope.row.defaults"/>
              <span v-if="(scope.row._origin_!==scope.row.value)" class="dw-button">
                <el-button @click="scope.row.value = scope.row._origin_" type="text" style="margin-right:10px;color:#aaa" v-t>取消</el-button>
                <el-button @click="save(scope.row)" type="text" v-t>保存</el-button>
              </span>
            </div>
          </template>
        </el-table-column>
      </el-table>
    </section>
    <el-dialog :title="$t('添加') + $t(' ') + $t('词条')" :visible.sync="adding" :modal-append-to-body="false">
      <el-form label-width="100px">
        <el-form-item :label="$t('标签')">
          <el-input v-model="lastAdd.label" ref="newLabel"/>
        </el-form-item>
        <el-form-item :label="$t('默认文本')">
          <el-input v-model="lastAdd.defaults"/>
        </el-form-item>
        <el-form-item :label="$t('翻译')">
          <el-input v-model="lastAdd.value"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="adding=false" v-t>取消</el-button>
        <el-button type="primary" @click="save(lastAdd);adding=false" v-t>保存</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style lang="scss">
  #i18n-locale {
    .dw-editor {
      position:relative;
      > .dw-button {
        position:absolute;
        right:20px;
        top:50%;
        transform:translate(0, -50%);
      }
    }
  }
</style>
<script type="text/javascript">
  import Cons from '../components/Cons'

  // 保存每条数据的原始值, 需要尝试在v-for中完成
function _recordOrigin (list) {
  for (let i = 0; i < list.length; i++) {
    let resource = list[i]
    if (!resource.value) {
      resource.value = ''
    }
    resource._origin_ = resource.value
  }
}

export default {
  data () {
    return {
      locales: [],
      group: '',
      locale: '',
      list: undefined,
      adding: false,
      lastAdd: {},
      lastEdit: undefined
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
      // this.console = console
      this.$root.$emit('loading')

      this.group = this.$route.params.group
      this.locale = this.$route.params.locale
      var url = '{host}api/i18n/{group}/{locale}'
        .replace(/{host}/, Cons.apiHost)
        .replace(/{group}/, this.group)
        .replace(/{locale}/, this.locale)

      axios.get(url).then(
        response => {
          // get body data
          let json = response.data
          if (json.success) {
            _recordOrigin(json.data)
            this.list = json.data
          } else {
            this.list = []
            this.$root.$emit('alert', {
              content: this.$t(json.info) + ':' + this.locale,
              state: 'danger',
              timeout: 3
            })
          }
          this.$root.$emit('loaded')
        },
        response => {
          // error callback
          // let json = response.data
          this.$root.$emit('loaded')
        }
      )
      axios.get(Cons.api('api/locale?group=' + this.group)).then(
        response => {
          let json = response.data
          if (json.success) {
            this.locales = json.data
          }
        }
      )
    },
    save (r) {
      // console.log(r)
      let bus = this.$root
      if (!r || r.value === r._origin_) {
        return
      }
      bus.$emit('loading')
      let params = {}
      params[r.label] = r.value

      let url = Cons.api('api/i18n/{group}/{locale}')
        .replace(/{group}/, this.group)
        .replace(/{locale}/, this.locale)
      axios.post(url, params).then(response => {
        // get body data
        let json = response.data
        bus.$emit('loaded')
        if (!json.success) {
          return Util.handleFailure(json)
        }

        let data = json.data
        _recordOrigin(data)
        this.list = data
        // 重新加载列表后清理上次编辑的记录
        this.lastEdit = undefined
        bus.$emit('alert', {
          content: '标签保存成功: ' + r.label,
          state: 'success',
          timeout: 1
        })
      }, response => {
        // error callback
        // console.log(response)
        bus.$emit('loaded')
        bus.$emit('alert', {
          content: '保存失败[' + r.label + ']',
          state: 'warning',
          timeout: 1
        })
      })
    },
    beforeAdd () {
      this.lastAdd = {}
      /* var el = this.$refs.newLabel.$el
      this.$nextTick(function () {
        el && el.focus()
      }) */
    },
    add () {
      this.save(this.lastEdit)
      this.lastEdit = {}
      this.adding = true
    },
    baiduTranslate () {
      this.$root.$emit('loading')
      let url = '{host}api/i18n/{group}/{locale}/auto'
        .replace(/{host}/, Cons.apiHost)
        .replace(/{group}/, this.group)
        .replace(/{locale}/, this.locale)
      axios.post(url).then(
        response => {
          let json = response.data
          this.list = json.data
          this.$root.$emit('loaded')
        },
        response => {
          this.$root.$emit('loaded')
        }
      )
    },
    changeLocale: (command, vm) => {
      vm.$router.replace(command)
    }
  }
}
</script>
