<template>
  <div>
    <el-button type="primary" @click="showAdd = true">新增分类</el-button>
    <el-table :data="categories" style="margin-top: 20px">
      <el-table-column prop="name" label="分类名称" />
      <el-table-column prop="description" label="描述" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button size="small" @click="editCategory(row)">编辑</el-button>
          <el-button size="small" type="danger" @click="deleteCategory(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog v-model="showAdd" title="新增分类">
      <el-form :model="form">
        <el-form-item label="分类名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showAdd = false">取消</el-button>
        <el-button type="primary" @click="saveCategory">保存</el-button>
      </template>
    </el-dialog>
    <el-dialog v-model="showEdit" title="编辑分类">
      <el-form :model="form">
        <el-form-item label="分类名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="描述"><el-input v-model="form.description" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showEdit = false">取消</el-button>
        <el-button type="primary" @click="updateCategory">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import axios from '../utils/axios'

const categories = ref([])
const showAdd = ref(false)
const showEdit = ref(false)
const form = ref({ id: null, name: '', description: '' })

const fetchCategories = async () => {
  const res = await axios.get('/api/categories')
  categories.value = res.data
}
onMounted(fetchCategories)

const saveCategory = async () => {
  await axios.post('/api/categories', form.value)
  showAdd.value = false
  fetchCategories()
}
const editCategory = (row: any) => {
  form.value = { ...row }
  showEdit.value = true
}
const updateCategory = async () => {
  await axios.put('/api/categories', form.value)
  showEdit.value = false
  fetchCategories()
}
const deleteCategory = async (id: number) => {
  await axios.delete(`/api/categories/${id}`)
  fetchCategories()
}
</script> 