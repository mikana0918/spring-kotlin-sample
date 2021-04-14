<template>
  <div style="z-index: 100; width: 90%; position: fixed">
    <transition name="fade">
      <v-alert
        v-model="alert"
        dismissible
        fixed
        top
        :color="color"
        border="left"
        elevation="2"
        colored-border
        :icon="icon"
      >
        {{ message }}
      </v-alert>
    </transition>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
  props: {
    message: {
      type: String,
      default: '',
    },
    show: {
      type: Boolean,
      default: false,
    },
    icon: {
      type: String,
      default: 'mdi-check',
    },
    color: {
      type: String,
      default: 'primary',
    },
  },
  data() {
    return {
      alert: false,
    }
  },
  watch: {
    show(newValue: boolean) {
      this.alert = newValue
    },
  },
})
</script>

<style lang="scss" scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
