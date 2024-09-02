import Admin from '@/views/Admin.vue'
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
    component: Login,
  },
  {
    path: '/register',
    name: 'register',
    component: Register,

  },
  {
    path: '/',
    name: 'layout',
    component: Layout,
    children: [
      {
        path: '/',
        name: 'index',
        component: Index,
      },
      {
        path:'/admin',
        name:'admin',
        component:Admin,
      }

    ]
  }



]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to ,from, next) => {
  if (to.path ==='/login' || to.path === '/register') {
    next();
    return;
  }
  const user = localStorage.getItem("user");
  if (!user && to.path !== '/login') {
    return next("/login");
  }
  next();
})
export default router
