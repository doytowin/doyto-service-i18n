<template>
  <div id="i18n-locale">
    <div class="clearfix" style="margin-bottom:10px">
      <b-btn @click="add" variant="success" class="float-right" v-t>添加</b-btn>
    </div>
    <table class="table table-hover" style="margin-bottom:0">
      <thead>
      <tr class="text-center">
        <th>#</th>
        <th v-t>标签</th>
        <th v-t>默认文本</th>
        <th v-t>文本</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(r, $index) in crud.p.list">
        <td>{{$index+1}}</td>
        <td>{{r.label}}</td>
        <td>{{r.defaults}}</td>
        <td class="dw-editor">
          <input v-model="r.value" @keypress.enter="save(r)" @focus="save(lastEdit)" @blur="lastEdit=r" :tabindex="$index + 1000"
                 type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" :placeholder="r.defaults">
          <span v-if="(r._origin_!==r.value)" class="dw-button">
              <button @click="r.value = r._origin_" class="btn btn-secondary btn-sm" style="margin-right:10px" v-t>取消</button>
              <button @click="save(r)" type="button" class="btn btn-primary btn-sm" v-t>保存</button>
            </span>
        </td>
      </tr>
      </tbody>
    </table>
    <b-modal id="addLabelModal" :title="$t('添加标签')" @ok="save(lastAdd)" @shown="beforeAdd"
             :ok-title="$t('保存')" :close-title="$t('关闭')">
      <form @submit.stop.prevent="submit">
        <b-form-input type="text" :placeholder="$t('标签')" v-model="lastAdd.label" class="mb-2"></b-form-input>
        <!--<b-form-input type="text" placeholder="默认值" v-model="lastAdd.value"></b-form-input>-->
        <b-form-input type="text" :placeholder="$t('翻译')" v-model="lastAdd.value"></b-form-input>
      </form>
    </b-modal>
  </div>
</template>
<style lang="scss">

  #i18n-locale {
    /*position:relative;*/

    @media (min-width:576px) {
      .modal-dialog {
        margin-top:80px;
      }
    }

    .table th, .table td {
      vertical-align:middle;
      padding:0.25rem;
      /*border-top: 1px solid #eceeef;*/
    }
  }
  .dw-editor {
    position:relative;
    > .dw-button {
      position:absolute;
      right:20px;
      top:50%;
      transform:translate(0, -50%);
    }
  }
</style>
<script type="text/javascript">
  import Cons from '../components/Cons'

  // 保存每条数据的原始值, 需要尝试在v-for中完成
  function _recordOrigin (json) {
    for (let i = 0; i < json.list.length; i++) {
      let resource = json.list[i]
      if (!resource.value) {
        resource.value = ''
      }
      resource._origin_ = resource.value
    }
  }

  export default {
    data () {
      return {
        crud: {
          p: {}
        },
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
        window.bus.$emit('loading')

        this.group = this.$route.params.group
        this.locale = this.$route.params.locale
        var url = '{host}api/i18n/{group}/{locale}'
                .replace(/{host}/, Cons.apiHost)
                .replace(/{group}/, this.group)
                .replace(/{locale}/, this.locale)

        this.$http.get(url).then(response => {
          // get body data
          let json = response.body
          _recordOrigin(json)
          this.crud.p = json
          window.bus.$emit('loaded')
        }, response => {
          // error callback
          window.bus.$emit('loaded')
        })
      },
      save (r) {
        // console.log(r)
        let bus = window.bus
        if (!r || r.value === r._origin_) {
          return
        }
        bus.$emit('loading')
        let params = {}
        params[r.label] = r.value

        let url = '{host}api/i18n/{group}/{locale}'
                .replace(/{host}/, Cons.apiHost)
                .replace(/{group}/, this.group)
                .replace(/{locale}/, this.locale)
        this.$http.post(url, params).then(response => {
          // get body data
          let json = response.body
          _recordOrigin(json)
          this.crud.p.list = json.list
          // 重新加载列表后清理上次编辑的记录
          this.lastEdit = undefined
          bus.$emit('loaded')
          bus.$emit('alert', {
            content: '标签保存成功: ' + r.label,
            type: 'success',
            timeout: 1
          })
        }, response => {
          // error callback
          bus.$emit('loaded')
          bus.$emit('alert', {
            content: '保存失败[' + r.label + ']',
            type: 'warning',
            timeout: 1
          })
        })
      },
      beforeAdd () {
        this.lastAdd = {}
      },
      add () {
        this.$root.$emit('show::modal', 'addLabelModal')
        this.save(this.lastEdit)
        this.lastEdit = {}
      }
    }
  }
</script>
