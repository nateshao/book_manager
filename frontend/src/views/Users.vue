<template>
  <div>
    <el-table :data="users" style="margin-top: 20px">
      <el-table-column prop="id" label="ID" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="role" label="角色" />
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from '../utils/axios'

const users = ref([])
const fetchUsers = async () => {
  const res = await axios.get('/api/users')
  users.value = res.data
}
onMounted(fetchUsers)
</script> 