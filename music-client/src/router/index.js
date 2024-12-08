import NotFound404 from '@/components/NotFound404.vue'
import request from '@/utils/request'
import Agreement from '@/views/Agreement.vue'
import Collect from '@/views/Collect.vue'
import Comment from '@/views/Comment.vue'
import Index from '@/views/Index.vue'
import Layout from '@/views/Layout.vue'
import Login from '@/views/Login.vue'
import Order from '@/views/Order.vue'
import Product from '@/views/Product.vue'
import ProductDetails from '@/views/ProductDetails.vue'
import Register from '@/views/Register.vue'
import Search from '@/views/Search.vue'
import Singer from '@/views/Singer.vue'
import SingerDetails from '@/views/SingerDetails.vue'
import SongDetails from '@/views/SongDetails.vue'
import SongList from '@/views/SongList.vue'
import SongListDetails from '@/views/SongListDetails.vue'
import UserSpace from '@/views/UserSpace.vue'

import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'layout',
    component: Layout,
    children: [
      {
        path: '/',
        name: '首页',
        component: Index,
      },
      {
        path: '/singer',
        name: '歌手页面',
        component: Singer,
      },
      {
        path: '/songList',
        name: '歌单页面',
        component: SongList,
      },
      {
        path: '/singerDetails',
        name: '歌手详情页面',
        component: SingerDetails,
      },
      {
        path: '/songListDetails',
        name: '歌单详情页面',
        component: SongListDetails,
      },
      {
        path: '/songDetails',
        name: '歌曲详情页面',
        component: SongDetails,
      },
      {
        path: '/search',
        name: '搜索页面',
        component: Search,
      },
      {
        path: '/space',
        name: '用户空间',
        component: UserSpace,
      },
      {
        path: '/comment',
        name: '用户评论页面',
        component: Comment,
      },
      {
        path: '/collect',
        name: '用户收藏页面',
        component: Collect,
      },
      {
        path: '/product',
        name: '商城页面',
        component: Product,
      },
      {
        path: '/productDetails',
        name: '商品详情页面',
        component: ProductDetails,
      },
      {
        path: '/order',
        name: '订单页面',
        component: Order,
      },
      {
        path: '/agreement',
        name: '协议页面',
        component: Agreement,
      },
    ]
  },
  {
    path: '/login',
    name: '登录页面',
    component: Login,
  },
  {
    path: '/register',
    name: '注册页面',
    component: Register,
  },
  {
    path: '/404',
    name: '404页面',
    component: NotFound404,
  },
  {
    path: '*',
    redirect: '/404'
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : '';
  request.get("/web/user").then(res => {
    // console.log(res);
    if (res.code === '0') {
      // next();
      return;
    } else {
      console.log("系统错误");

      return;
    }
  })
  if (to.path === '/space' && user === '') {
    return next("/");
  }
  if (to.path === '/comment' && user === '') {
    return next("/");
  }
  if (to.path === '/collect' && user === '') {
    return next("/");
  }
  next();
})

export default router
