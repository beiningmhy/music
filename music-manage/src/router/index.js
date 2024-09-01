import Index from '@/views/Index.vue'
import Layout from '@/views/Layout.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'login',
    component:Login,
  },
  {
    path: '/register',
    name: 'register',
    component:Register,

  },
  {
    path: '/',
    name: 'layout',
    component:Layout,
    children:[
      {
        path:'/',
        name:'index',
        component:Index,
      },

    ]
  }
  


]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
