<template>
  <aside id="leftMenu">
    <div style="margin-bottom:10px">
      <div v-for="(menu, $i) in sysMenu.sub" style="margin-top:5px">
        <b-btn v-b-toggle="'menu-' + menu.id" variant="info" style="width:100%;border-radius:0;" v-t>{{menu.label}}</b-btn>
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
      <b-btn v-b-toggle="'menu-' + menu.id" variant="info" style="width:100%;border-radius:0;">{{menu.label}}</b-btn>
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
      .list-group-item:first-child{
        border-top-left-radius:0;
        border-top-right-radius:0;
      }
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
            'sub': [{
              'id': 6,
              'parentId': 2,
              'label': '国际化管理',
              'name': 'i18n',
              'url': '/i18n/i18n',
              'rank': 1,
              'sequence': 1
            }, {
              'id': 34,
              'parentId': 2,
              'label': '资源管理',
              'url': '/group',
              'rank': 1,
              'sequence': 2
            }]
          }]
        },
        i18nMenus: [{
          'label': '国际化系统',
          'name': 'system_i18n',
          'sub': [{
            'label': '概览',
            'name': 'i18n',
            'url': '/i18n/i18n'
          }, {
            'label': '中文',
            'name': 'i18n_zh_CN',
            'url': '/i18n/i18n/zh_CN'
          }, {
            'label': 'English',
            'name': 'i18n_en_US',
            'url': '/i18n/i18n/en_US'
          }]
        }]
      }
    }
  }
</script>
