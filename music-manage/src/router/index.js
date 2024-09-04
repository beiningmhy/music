import request from '@/utils/request'
import Admin from '@/views/Admin.vue'
import Consumer from '@/views/Consumer.vue'
import Index from '@/views/Index.vue'
import Layout from '@/views/Layout.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import Singer from '@/views/Singer.vue'
import Song from '@/views/Song.vue'
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
      },
      {
        path:'/consumer',
        name:'consumer',
        component:Consumer,
      },
      {
        path:'/singer',
        name:'singer',
        component:Singer,

      },
      {
        path:'/song',
        name:'song',
        component:Song,
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
  const user = localStorage.getItem("user");
  request.get("/web/").then(res => {
    // console.log(res);
    
    if (res.code === '0') {

      return;
    } else {
      localStorage.removeItem("user");
      next("/login");
    }
  })
  if (to.path ==='/login' || to.path === '/register') {
    next();
    return;
  }
  

  if (!user && to.path !== '/login') {
    return next("/login");
  }
  next();
})
export default router
