<template>
  <div>
    <el-table :data="records" style="margin-top: 20px">
      <el-table-column prop="username" label="借阅人" />
      <el-table-column prop="bookTitle" label="图书" />
      <el-table-column prop="borrowDate" label="借出时间" />
      <el-table-column prop="returnDate" label="归还时间" />
      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button v-if="row.status === 'BORROWED'" size="small" @click="returnBook(row.id)">还书</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from '../utils/axios'

const records = ref([])
const fetchRecords = async () => {
  const res = await axios.get('/api/borrows')
  records.value = res.data
}
onMounted(fetchRecords)

const returnBook = async (id: number) => {
  await axios.post(`/api/borrows/return/${id}`)
  fetchRecords()
}
</script> 