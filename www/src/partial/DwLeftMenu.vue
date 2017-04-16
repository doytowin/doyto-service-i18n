<template>
  <aside id="leftMenu">
    <div style="margin-bottom:10px">
      <div v-for="(menu, $i) in sysMenu.sub" style="margin-top:5px">
        <b-btn v-b-toggle="'menu-' + menu.id" variant="info" v-t>{{menu.label}}</b-btn>
        <b-collapse :id="'menu-' + menu.id">
          <div class="list-group">
            <router-link v-for="sub in menu.sub" :key="sub.id" :to="sub.url" class="list-group-item list-group-item-action"
                         active-class="list-group-item-warning" exact v-t>
              {{sub.label}}
            </router-link>
          </div>
        </b-collapse>
      </div>
    </div>

    <div v-for="(menu, $i) in i18nMenus" style="margin-bottom:10px">
      <b-btn v-b-toggle="'menu-' + menu.id" variant="info">{{menu.label}}</b-btn>
      <b-collapse :id="'menu-' + menu.id">
        <div class="list-group">
          <router-link v-for="sub in menu.sub" :key="sub.id" :to="sub.url" class="list-group-item list-group-item-action"
                       active-class="list-group-item-warning" exact>
            {{sub.label}}
          </router-link>
        </div>
      </b-collapse>
    </div>
  </aside>
</template>
<style lang="scss">
  #leftMenu {
    .list-group {
      .list-group-item:first-child {
        border-top-left-radius:0;
        border-top-right-radius:0;
      }
    }
    .btn {
      width:100%;
      border-radius:0;
      text-align:left
    }
  }
</style>
<script type="text/javascript">
  export default{
    data () {
      return {
        sysMenu: {
          'id': 1,
          'parentId': 0,
          'label': '后台管理面板',
          'name': 'admin',
          'rank': 1,
          'sequence': 1,
          'asState': false,
          'sub': [{
            'id': 2,
            'parentId': 1,
            'label': '系统配置',
            'name': 'sysconf',
            'rank': 1,
            'sequence': 1,
            'sub': [
              {
                'parentId': 2,
                'label': '资源分组管理',
                'url': '/resource/group'
              }
            ]
          }]
        },
        i18nMenus: [
          {
            'id': 21,
            'label': '国际化系统',
            'name': 'system_i18n',
            'sub': [
              {
                'label': '概览',
                'name': 'i18n',
                'url': '/i18n/i18n'
              },
              {
                'label': '语种管理',
                'name': 'locale',
                'url': '/resource/1/i18n/locale'
              },
              {
                'label': '中文',
                'name': 'i18n_zh_CN',
                'url': '/i18n/i18n/zh_CN'
              },
              {
                'label': 'English',
                'name': 'i18n_en_US',
                'url': '/i18n/i18n/en_US'
              }]
          },
          {
            'id': 22,
            'label': 'd1m国际化系统',
            'name': 'resource_d1m',
            'sub': [
              {
                'label': '概览',
                'name': 'i18n',
                'url': '/i18n/d1m'
              },
              {
                'label': '语种管理',
                'name': 'locale',
                'url': '/resource/2/d1m/locale'
              },
              {
                'label': '中文',
                'name': 'i18n_zh_CN',
                'url': '/i18n/d1m/zh_CN'
              },
              {
                'label': 'English',
                'name': 'i18n_en_US',
                'url': '/i18n/d1m/en_US'
              }]
          }
        ]
      }
    },
    mounted () {
      this.$root.$emit('collapse::toggle', 'menu-2')
    }
  }
</script>
