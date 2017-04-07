<template>
  <div id="i18n-locale">
    <section>
      <table class="table table-hover" style="margin-bottom:0">
        <thead>
        <tr class="text-center">
          <th>#</th>
          <th v-t>标签</th>
          <th v-t>默认翻译</th>
          <th v-t>文本</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(r, $index) in crud.p.list">
          <td>{{$index+1}}</td>
          <td>{{r.label}}</td>
          <td>{{r.defaults}}</td>
          <td class="dw-editor">
            <input v-model="r.value" @focus="save(lastEdit);lastEdit=r" :tabindex="$index + 1000"
                   type="text" class="form-control mb-2 mr-sm-2 mb-sm-0" :placeholder="r.defaults">
            <span v-if="r._origin_ && r._origin_!=r.value" class="dw-button">
              <button @click="r.value = r._origin_" class="btn btn-secondary btn-sm" style="margin-right:10px" v-t>取消</button>
              <button @click="save(r)" type="button" class="btn btn-primary btn-sm" v-t>保存</button>
            </span>
          </td>
        </tr>
        </tbody>
      </table>
    </section>
    <div class="alert-list">
      <div v-for="(a, $i) in alerts">
        <b-alert :show="a.timeout" :state="a.state">
        <!--<b-alert v-for="(a, $i) in alerts" :key="$i" :show="a.dismissCountDown" dismissible :state="a.state" @dismiss-count-down="countDownChanged">-->
          {{a.content}}
        </b-alert>
      </div>
    </div>
  </div>
</template>
<style lang="scss">

  #i18n-locale {
    /*position:relative;*/

    >.alert-list{
      position:fixed;
      top:50px;
      right:22px;
    }
    .table th, .table td {
      vertical-align: middle;
      padding: 0.25rem;
      /*border-top: 1px solid #eceeef;*/
    }
  }
  .dw-editor {
    position:relative;
    >.dw-button {
      position:absolute;
      right:20px;
      top: 50%;
      transform: translate(0, -50%);
    }
  }
</style>
<script type="text/javascript">
  import Cons from '../components/Cons'

  export default {
    data () {
      return {
        crud: {
          p: {}
        },
        alerts: [],
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
          for (let i = 0; i < json.list.length; i++) {
            let resource = json.list[i]
            resource._origin_ = resource.value
          }
          this.crud.p = json
          window.bus.$emit('loaded')
        }, response => {
          // error callback
          window.bus.$emit('loaded')
        })
      },
      save (r) {
        if (!r || r.value === r._origin_) {
          return
        }
        window.bus.$emit('loading')
        let params = {}
        params[r.label] = r.value

        let url = '{host}api/i18n/{group}/{locale}'
                .replace(/{host}/, Cons.apiHost)
                .replace(/{group}/, this.group)
                .replace(/{locale}/, this.locale)
        this.$http.post(url, params).then(response => {
          // get body data
          let json = response.body
          console.log(json)
          for (let i = 0; i < json.list.length; i++) {
            let resource = json.list[i]
            resource._origin_ = resource.value
            console.log(resource)
          }
          this.crud.p.list = json.list
          window.bus.$emit('loaded')
          this.showAlert('标签保存成功: ' + r.label, 'success')
        }, response => {
          // error callback
          window.bus.$emit('loaded')
          this.showAlert('保存失败[' + r.label + ']', 'warning')
        })
      },
      showAlert (content, state, timeout) {
        var a = {content: content, timeout: timeout || 3, state: state || 'info'}
        this.$set(this.alerts, this.alerts.length, a)
        // this.alerts.unshift(a)
      }
    }
  }
</script>
