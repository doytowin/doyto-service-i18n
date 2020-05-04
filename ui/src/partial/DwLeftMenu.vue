<template>
  <section>
    <el-menu :router="true" :collapse="$root.isCollapse">
      <el-menu-item index="/dashboard/resource/group">
        <i class="el-icon-menu"/>
        <span slot="title">资源分组</span>
      </el-menu-item>
      <el-submenu index="translation">
        <template slot="title">
          <i class="el-icon-goods"/>
          <span slot="title" v-t>资源翻译</span>
        </template>
        <el-submenu :router="true" v-for="group in i18nGroups" :key="group.id" :index="'menu-' + group.id" :unique-opened="true" :collapse="$root.isCollapse">
          <template slot="title">
            <i class="el-icon-s-grid"/>{{group.label || group.name}}
          </template>
          <el-menu-item-group>
            <el-menu-item :index="'/dashboard/i18n/' + group.name" v-t>
              <t>概览</t>
            </el-menu-item>
            <el-menu-item :index="'/dashboard/resource/' + group.name +'/locale'">
              <t>语种</t><t></t><t>管理</t>
            </el-menu-item>
            <el-menu-item :index="'/dashboard/i18n/' + group.name + '/zh_CN'">
              <t>词条</t><t></t><t>翻译</t>
            </el-menu-item>
          </el-menu-item-group>
        </el-submenu>
      </el-submenu>
    </el-menu>
  </section>
</template>
<script type="text/javascript">
export default{
  data () {
    return {
      i18nGroups: []
    }
  },
  created () {
    axios.get(Cons.api('group')).then(rsp => {
      let data = rsp.data
      if (data.success) {
        this.i18nGroups = data.data.list
      } else {
        Util.handleFailure(data)
      }
    })
  }
}
</script>
