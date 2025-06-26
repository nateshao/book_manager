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
        <el-alert v-if="error" :title="error" type="error" show-icon class="mt-2" />
      </el-form>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from '../utils/axios'

const router = useRouter()
const form = ref({ username: '', password: '' })
const loading = ref(false)
const error = ref('')

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