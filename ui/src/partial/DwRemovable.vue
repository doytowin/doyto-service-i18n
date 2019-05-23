<template>
  <div class="removable-box">
    <i class="removable-box-icon" @click="remove" v-show="show"></i>
    <slot></slot>
  </div>
</template>
<script type="text/javascript">
  export default {
    name: 'DmRemovable',
    props: {
      removeOptions: {
        type: Object,
        required: true,
        'default': {
          message: '是否删除当前条目?',
          onConfirm () {},
          onCancel () {}
        }
      },
      show: {
        type: Boolean,
        'default': true
      }
    },
    methods: {
      remove() {
        const opt = this.removeOptions
        this.$confirm(opt.message)
          .then(function () {
            if (typeof opt.onConfirm === 'function') {
              opt.onConfirm()
            }
          }).catch(function () {
            if (typeof opt.onCancel === 'function') {
              opt.onCancel()
            }
          })
      }
    }
  }
</script>
<style lang="scss">
  .removable-box {
    position:relative;
    background-color:#fff;
    border-radius:10px;

    > .removable-box-icon {
      &:before {
        content:"\E635";
      }
      position:absolute;
      top:-12px;
      right:-14px;
      height:32px;
      width:28px;
      font-size:32px;
      padding:0 2px;
      content:"\E635";
      font-family:element-icons !important;
      font-style:normal;
      speak:none;
      background-color:white;
      color:red;
      border-radius:16px;

    }
  }
</style>