import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import Books from '../views/Books.vue'
import Borrows from '../views/Borrows.vue'
import Users from '../views/Users.vue'
import Categories from '../views/Categories.vue'

const routes: Array<RouteRecordRaw> = [
  { path: '/login', name: 'Login', component: Login },
  {
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      { path: 'books', name: 'Books', component: Books },
      { path: 'borrows', name: 'Borrows', component: Borrows },
      { path: 'users', name: 'Users', component: Users },
      { path: 'categories', name: 'Categories', component: Categories },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path !== '/login' && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router 