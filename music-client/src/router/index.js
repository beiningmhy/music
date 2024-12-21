import NotFound404 from '@/components/NotFound404.vue'
import request from '@/utils/request'
import Agreement from '@/views/Agreement.vue'
import Chat from '@/views/Chat.vue'
import Collect from '@/views/Collect.vue'
import Comment from '@/views/Comment.vue'
import Im from '@/views/Im.vue'
import Index from '@/views/Index.vue'
import Layout from '@/views/Layout.vue'
import Login from '@/views/Login.vue'
import LoginMail from '@/views/LoginMail.vue'
import Order from '@/views/Order.vue'
import Product from '@/views/Product.vue'
import ProductDetails from '@/views/ProductDetails.vue'
import Register from '@/views/Register.vue'
import Search from '@/views/Search.vue'
import Singer from '@/views/Singer.vue'
import SingerDetails from '@/views/SingerDetails.vue'
import SongCenter from '@/views/SongCenter.vue'
import SongDetails from '@/views/SongDetails.vue'
import SongList from '@/views/SongList.vue'
import SongList2Song from '@/views/SongList2Song.vue'
import SongListCenter from '@/views/SongListCenter.vue'
import SongListDetails from '@/views/SongListDetails.vue'
import StatementCenter from '@/views/StatementCenter.vue'
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
      {
        path: '/songCenter',
        name: '歌曲发表页面',
        component: SongCenter,
      },
      {
        path: '/songListCenter',
        name: '歌单发表页面',
        component: SongListCenter,
      },
      {
        path: '/songList2Song',
        name: '歌单歌曲管理页面',
        component: SongList2Song,
      },
      {
        path: '/statementCenter',
        name: '报表页面',
        component: StatementCenter,
      },
      {
        path: '/im',
        name: '聊天1页面',
        component: Im,
      },
      {
        path: '/chat',
        name: '聊天2页面',
        component: Chat,
      },
    ]
  },
  {
    path: '/login',
    name: '登录页面',
    component: Login,
  },
  {
    path: '/loginmail',
    name: '邮箱登录页面',
    component: LoginMail,
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
  // request.get("/web/user").then(res => {
  //   // console.log(res);
  //   if (res.code === '0') {
  //     // next();
  //     return;
  //   } else {
  //     console.log("系统错误");

  //     return;
  //   }
  // })
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
