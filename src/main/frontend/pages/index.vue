<template>
  <v-app id="app">
    <div class="container">
      <Modal
        :should-show="toggleModal({ show: modalVisible })"
        :memo="selectedMemo"
        @closed="toggleModalVisibility({ visibility: false })"
        @save="save"
      ></Modal>
      <Alert
        :show="toggleAlert({ show: alertVisible })"
        :message="alertMessage"
        :color="alertColor"
        :icon="alertIcon"
      ></Alert>
      <v-btn
        style="bottom: 3vh"
        fixed
        bottom
        color="primary"
        right
        fab
        @click="toggleModalVisibility({ visibility: true })"
      >
        <v-icon color="#FFF">mdi-plus</v-icon>
      </v-btn>

      <div v-for="note of memos" :key="note.id">
        <v-card class="mx-auto my-4" max-width="344">
          <v-card-title>{{ note.text }}</v-card-title>
          <v-card-actions>
            <v-btn icon @click="deleteById(note.id)">
              <v-icon>mdi-delete</v-icon>
            </v-btn>
            <v-btn icon @click="editById(note.id)">
              <v-icon>mdi-pencil</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </div>
    </div>
  </v-app>
</template>

<script lang="ts">
import Vue from 'vue'

interface Memo {
  id: string
  text: string
}

export default Vue.extend({
  data(): {
    memos: Array<Memo>
    modalVisible: boolean
    alertVisible: boolean
    alertMessage: string
    alertColor: string
    alertIcon: string
    selectedMemo?: Memo
  } {
    return {
      memos: [],
      modalVisible: false,
      alertVisible: false,
      alertMessage: '',
      alertColor: '',
      alertIcon: '',
      selectedMemo: undefined,
    }
  },
  async created() {
    this.selectedMemo = undefined
    this.memos = await this.$axios.get('/api/').then((res) => res.data)
  },
  methods: {
    async deleteById(id: string) {
      await this.$axios
        .delete(`/api/${id}`)
        .then(() => {
          this.toggleAlertVisibility({ visibility: true })
          this.toggleAlert({ show: this.alertVisible })
          this.alertMessage = '削除に成功しました'
          this.alertIcon = 'mdi-delete'
          this.alertColor = 'red'
        })
        .catch((e) => {
          console.warn(e)
        })
      location.reload()
    },
    async save(memo: Memo) {
      console.log('memo', memo)
      await this.$axios
        .post(`/api`, memo)
        .then(() => {
          this.toggleAlertVisibility({ visibility: true })
          this.toggleAlert({ show: this.alertVisible })
          this.alertMessage = '保存に成功しました'
        })
        .catch((e) => {
          console.warn(e)
        })
      location.reload()
    },
    editById(givenId: string) {
      this.toggleModalVisibility({ visibility: true })
      this.toggleModal({ show: this.modalVisible })
      this.selectedMemo = this.memos.find((m: Memo) => m.id === givenId)
    },
    toggleModal({ show }: { show: boolean }): boolean {
      return show
    },
    toggleModalVisibility({ visibility }: { visibility: boolean }) {
      this.modalVisible = visibility
    },
    toggleAlert({ show }: { show: boolean }): boolean {
      return show
    },
    toggleAlertVisibility({ visibility }: { visibility: boolean }) {
      this.alertVisible = visibility
    },
  },
})
</script>

<style></style>
