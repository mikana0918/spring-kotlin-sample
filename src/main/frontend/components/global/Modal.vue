<template>
  <v-row justify="center">
    <v-dialog
      v-model="show"
      fullscreen
      hide-overlay
      transition="dialog-bottom-transition"
    >
      <!-- activator経由のtoggleではないのでひとまずコメントアウト -->
      <!-- <template #activator="{ on, attrs }">
        <v-btn v-bind="attrs" v-on="on">ああああああ</v-btn>
      </template> -->
      <v-card>
        <v-toolbar dark color="primary">
          <v-btn icon dark @click="closeModal()">
            <v-icon>mdi-close</v-icon>
          </v-btn>
          <v-toolbar-title>Post</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn dark text @click="save({ modalClose: true })"> Save </v-btn>
          </v-toolbar-items>
        </v-toolbar>
        <v-list three-line subheader>
          <v-list-item>
            <v-list-item-content>
              <v-col cols="12">
                <v-textarea v-model="note" color="primary" counter>
                  <template #label>
                    <div>Note</div>
                  </template>
                </v-textarea>
              </v-col>
            </v-list-item-content>
          </v-list-item>
        </v-list>
        <v-divider></v-divider>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<script lang="ts">
import Vue, { PropType } from 'vue'

interface Memo {
  id: string
  text: string
}

export default Vue.extend({
  name: 'Modal',
  props: {
    shouldShow: {
      type: Boolean,
      default: false,
    },
    memo: {
      type: Object as PropType<Memo>,
      default: undefined,
    },
  },
  data(): {
    note: string
    show: Boolean
    notifications: Boolean
    sound: Boolean
    widgets: Boolean
  } {
    return {
      note: '',
      show: false,
      notifications: false,
      sound: true,
      widgets: false,
    }
  },
  watch: {
    shouldShow(newValue) {
      this.show = newValue
    },
    memo(newValue) {
      this.note = newValue.text
    },
  },
  methods: {
    showModal() {
      this.show = true
    },
    closeModal() {
      this.show = false
      this.$emit('closed')
    },
    save({ modalClose }: { modalClose: Boolean }) {
      if (modalClose) this.closeModal()
      this.$emit('save', {
        id: this.memo?.id ?? undefined,
        text: this.note,
      })
    },
    clear() {
      this.note = ''
    },
  },
})
</script>
