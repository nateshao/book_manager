<template>
  <el-dialog v-model="visible" title="微信支付" width="300px" :close-on-click-modal="false">
    <div style="text-align:center">
      <img :src="codeUrl" v-if="codeUrl" style="width:200px;height:200px" />
      <div style="margin-top:10px">请使用微信扫码支付</div>
    </div>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, watch, onUnmounted } from 'vue'
import axios from '../utils/axios'

const props = defineProps<{ orderId: number, codeUrl: string, modelValue: boolean }>()
const emit = defineEmits(['update:modelValue', 'paid'])
const visible = ref(props.modelValue)
const codeUrl = ref(props.codeUrl)
let timer: any = null

watch(() => props.modelValue, v => {
  visible.value = v
  if (v) startPoll()
  else stopPoll()
})

function startPoll() {
  timer = setInterval(async () => {
    const res = await axios.get('/api/pay/status', { params: { id: props.orderId } })
    if (res.data.status === 'PAID') {
      emit('paid')
      emit('update:modelValue', false)
      stopPoll()
    }
  }, 2000)
}
function stopPoll() {
  if (timer) clearInterval(timer)
}
onUnmounted(stopPoll)
</script> 