<template>
  <div class="dw-lang">
    <el-select style="padding:0 5px" placeholder="请选择"
               v-model="$root.lang" @change="$root.switchLang">
      <el-option v-for="option in $root.languages"
                 :key="option.id" :label="option.language" :value="option.locale"></el-option>
    </el-select>
  </div>

</template>
<style lang="scss">
  .dw-lang {
    .dropdown {
      position:absolute;
      bottom:0;
      left:0;
      right:0;
      .dropdown-toggle, .dropdown-menu {
        width:100%;
      }
      .dropdown-toggle {
        border-radius:0;
      }
      .dropdown-menu {
        max-height:200px;
        text-align:right;
      }
    }
  }
</style>
