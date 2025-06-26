<template>
  <div>
    <el-button type="primary" @click="showAdd = true">新增图书</el-button>
    <el-table :data="books" style="margin-top: 20px">
      <el-table-column prop="title" label="书名" />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="category" label="分类" />
      <el-table-column prop="isbn" label="ISBN" />
      <el-table-column prop="stock" label="库存" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button size="small" @click="editBook(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteBook(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="showAdd" title="新增图书">
      <el-form :model="form">
        <el-form-item label="书名"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="作者"><el-input v-model="form.author" /></el-form-item>
        <el-form-item label="分类"><el-input v-model="form.category" /></el-form-item>
        <el-form-item label="ISBN"><el-input v-model="form.isbn" /></el-form-item>
        <el-form-item label="库存"><el-input-number v-model="form.stock" :min="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAdd = false">取消</el-button>
        <el-button type="primary" @click="saveBook">保存</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="showEdit" title="编辑图书">
      <el-form :model="form">
        <el-form-item label="书名"><el-input v-model="form.title" /></el-form-item>
        <el-form-item label="作者"><el-input v-model="form.author" /></el-form-item>
        <el-form-item label="分类"><el-input v-model="form.category" /></el-form-item>
        <el-form-item label="ISBN"><el-input v-model="form.isbn" /></el-form-item>
        <el-form-item label="库存"><el-input-number v-model="form.stock" :min="0" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEdit = false">取消</el-button>
        <el-button type="primary" @click="updateBook">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from '../utils/axios'

const books = ref([])
const showAdd = ref(false)
const showEdit = ref(false)
const form = ref({ id: null, title: '', author: '', category: '', isbn: '', stock: 0 })

const fetchBooks = async () => {
  const res = await axios.get('/api/books')
  books.value = res.data
}
onMounted(fetchBooks)

const saveBook = async () => {
  await axios.post('/api/books', form.value)
  showAdd.value = false
  fetchBooks()
}
const editBook = (row: any) => {
  form.value = { ...row }
  showEdit.value = true
}
const updateBook = async () => {
  await axios.put('/api/books', form.value)
  showEdit.value = false
  fetchBooks()
}
const deleteBook = async (id: number) => {
  await axios.delete(`/api/books/${id}`)
  fetchBooks()
}
</script> 