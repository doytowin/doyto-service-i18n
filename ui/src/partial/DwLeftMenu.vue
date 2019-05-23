<template>
  <aside id="leftMenu">
    <el-menu :router="true">
      <el-submenu index="111">
        <template slot="title">
          <i class="el-icon-setting"></i>{{sysMenu.label}}
        </template>
        <el-menu-item-group v-for="(group) in sysMenu.sub" :key="group.id">
          <template slot="title">{{group.label}}</template>
          <el-menu-item v-for="(child) in group.sub" :key="child.url" :index="child.url">
            {{child.label}}
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
    <el-menu :router="true" v-for="(group, $i) in i18nGroups" :key="group.id" index="menu" :unique-opened="true">
      <el-submenu :index="$i+''">
        <template slot="title">
          <i class="el-icon-info"></i>{{group.label || group.name}}
        </template>
        <el-menu-item-group>
          <el-menu-item :index="'/dashboard/i18n/' + group.name" v-t>
            概览
          </el-menu-item>
          <el-menu-item :index="'/dashboard/resource/' + group.name +'/locale'">
            <t>语种</t><t>_</t><t>管理</t>
          </el-menu-item>
          <el-menu-item :index="'/dashboard/i18n/' + group.name + '/zh_CN'">
            <t>资源</t><t>_</t><t>翻译</t>
          </el-menu-item>
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </aside>
</template>
<script type="text/javascript">
export default{
  data () {
    return {
      // 系统配置
      sysMenu: {
        'label': '后台管理面板',
        'name': 'admin',
        'sub': [{
          'label': '系统配置',
          'name': 'sysconf',
          'sub': [
            {
              'label': '资源分组管理',
              'url': '/dashboard/resource/group'
            }
          ]
        }]
      },
      i18nGroups: [
        {
          'id': 1,
          'name': 'i18n',
          'label': '多语言'
        }
      ]
    }
  },
  created () {
    // let vm = this
    console.log(Cons.apiHost)
    axios.get('/api/resource-group').then(rsp => {
      let ret = rsp.data
      if (ret.success) {
        // vm.i18nGroups = ret.data.list
      }
    })
  }
}
</script>
