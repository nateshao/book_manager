<template>
  <div class="login-container">
    <el-card class="login-card">
      <h2>图书管理系统登录</h2>
      <el-form :model="form" @submit.prevent="onLogin">
        <el-form-item label="用户名">
          <el-input v-model="form.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" autocomplete="off" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onLogin" :loading="loading">登录</el-button>
        </el-form-item>
        <el-button type="success" @click="openWeChatDialog" style="width:100%;margin-top:10px">微信扫码登录</el-button>
        <el-dialog v-model="wechatDialog" title="微信扫码登录" width="300px" :close-on-click-modal="false">
          <div style="text-align:center">
            <canvas ref="qrcodeCanvas"></canvas>
            <div style="margin-top:10px">请使用微信扫码登录</div>
            <el-form v-if="needBind" :model="bindForm" style="margin-top:20px">
              <el-form-item label="账号"><el-input v-model="bindForm.username" /></el-form-item>
              <el-form-item label="密码"><el-input v-model="bindForm.password" type="password" /></el-form-item>
              <el-button type="primary" @click="bindAccount">绑定并登录</el-button>
            </el-form>
          </div>
        </el-dialog>
        <el-alert v-if="error" :title="error" type="error" show-icon class="mt-2" />
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../utils/axios'
import QRCode from 'qrcode'

const router = useRouter()
const form = ref({ username: '', password: '' })
const loading = ref(false)
const error = ref('')
const wechatDialog = ref(false)
const qrUrl = ref('')
const qrcodeCanvas = ref<HTMLCanvasElement | null>(null)
let pollTimer: any = null
let pollState = ''
const needBind = ref(false)
const openid = ref('')
const bindForm = ref({ username: '', password: '' })

const onLogin = async () => {
  loading.value = true
  error.value = ''
  try {
    const res = await axios.post('/api/auth/login', form.value)
    localStorage.setItem('token', res.data.token)
    router.push('/')
  } catch (e: any) {
    error.value = e.response?.data?.message || '登录失败'
  } finally {
    loading.value = false
  }
}

const openWeChatDialog = async () => {
  const res = await axios.get('/api/auth/wechat/login')
  qrUrl.value = res.data.url
  pollState = res.data.state
  wechatDialog.value = true
  QRCode.toCanvas(qrcodeCanvas.value, qrUrl.value)
  pollTimer = setInterval(pollWeChatLogin, 1500)
}

const pollWeChatLogin = async () => {
  if (!pollState) return
  const res = await axios.get('/api/auth/wechat/poll', { params: { state: pollState } })
  if (res.data.token) {
    localStorage.setItem('token', res.data.token)
    wechatDialog.value = false
    clearInterval(pollTimer)
    router.push('/')
  } else if (res.data.openid) {
    openid.value = res.data.openid
    needBind.value = true
    clearInterval(pollTimer)
  }
}

const bindAccount = async () => {
  const res = await axios.post('/api/auth/wechat/bind', {
    openid: openid.value,
    username: bindForm.value.username,
    password: bindForm.value.password
  })
  localStorage.setItem('token', res.data.token)
  wechatDialog.value = false
  router.push('/')
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: #f5f6fa;
}
.login-card {
  width: 350px;
  padding: 30px 20px;
}
</style> 