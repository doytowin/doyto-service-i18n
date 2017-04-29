<template>
  <div style="position:relative">
    <div class="mb-2 clearfix ">
      <div class="hidden-sm-up">
        <b-btn class="btn-success" v-t>添加语种</b-btn>
      </div>
      <div class="hidden-sm-down float-right">
        <b-btn @click="$root.$emit('show::modal', 'addLocaleModal')" class="btn-success" v-t>添加语种</b-btn>
      </div>
    </div>
    <section>
      <table class="table table-hover" style="margin-bottom:0">
        <thead>
        <tr class="text-center">
          <th>#</th>
          <th v-t>语种</th>
          <th v-t>语言原称</th>
          <th v-t>百度翻译语言</th>
          <th v-t>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(r, $index) in crud.p.list">
          <td>{{$index+1}}</td>
          <td>{{r.locale}}</td>
          <td>{{r.language}}</td>
          <td>{{r.baiduTranLang}}</td>
          <td>
            <a class="btn btn-link" @click="crud.edit(r);$root.$emit('show::modal', 'editLocaleModal')" v-t>编辑</a>
            <a class="btn btn-link" @click="crud.remove(r)" v-t>删除</a>
          </td>
        </tr>
        </tbody>
      </table>
    </section>
    <footer class="page-footer">
      <dw-page :p="crud.p"></dw-page>
    </footer>
    <b-modal id="addLocaleModal"
             @ok="crud.record.groupId=groupId;crud.save(crud.record)"
             @shown="crud.record={}"
             :title="$t('添加语种')"
             :ok-title="$t('保存')"
             :close-title="$t('关闭')">
      <form @submit.stop.prevent="submit">
        <input type="text" :placeholder="$t('语种')" v-model="crud.record.locale" class="form-control mb-2">
        <input type="text" :placeholder="$t('语言原称')" v-model="crud.record.language" class="form-control mb-2">
        <input type="text" :placeholder="$t('百度翻译语言')" v-model="crud.record.baiduTranLang" class="form-control mb-2">
        <span>查看 <a href="http://api.fanyi.baidu.com/api/trans/product/apidoc#languageList" target="_blank">http://api.fanyi.baidu.com/api/trans/product/apidoc</a></span>
      </form>
    </b-modal>
    <b-modal id="editLocaleModal"
             @ok="crud.record.groupId=groupId;crud.save(crud.record)"
             :title="$t('编辑语种')" :ok-title="$t('保存')" :close-title="$t('关闭')">
      <form @submit.stop.prevent="submit">
        <input type="text" :placeholder="$t('语种')" v-model="crud.record.locale" class="form-control mb-2">
        <input type="text" :placeholder="$t('语言原称')" v-model="crud.record.language" class="form-control mb-2">
        <input type="text" :placeholder="$t('百度翻译语言')" v-model="crud.record.baiduTranLang" class="form-control mb-2">
        <span>查看 <a href="http://api.fanyi.baidu.com/api/trans/product/apidoc#languageList" target="_blank">http://api.fanyi.baidu.com/api/trans/product/apidoc</a></span>
      </form>
    </b-modal>
  </div>
</template>
<style lang="scss">
</style>
<script type="text/javascript">
  import Cons from '../components/Cons.js'
  import Crud from '../components/Crud.js'
  import DwPage from '../partial/DwPage'

  export default {
    components: {
      DwPage
    },
    data () {
      return {
        group: undefined,
        crud: {
          p: {},
          record: {}
        }
      }
    },
    created () {
      this.init()
    },
    methods: {
      init () {
        this.groupId = this.$route.params.groupId
        this.group = this.$route.params.group
        var url = '{host}api/resource/{groupId}_{group}/locale{/id}'
                .replace(/{host}/, Cons.apiHost)
                .replace(/{groupId}/, this.groupId)
                .replace(/{group}/, this.group)

        this.crud = new Crud(this.$resource(url))
      }
    },
    watch: {
      // 如果路由有变化，会再次执行该方法
      '$route': 'init'
    }
  }
</script>
