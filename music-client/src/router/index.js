import request from '@/utils/request'
import Index from '@/views/Index.vue'
import Layout from '@/views/Layout.vue'
import Singer from '@/views/Singer.vue'
import SingerDetails from '@/views/SingerDetails.vue'
import SongDetails from '@/views/SongDetails.vue'
import SongList from '@/views/SongList.vue'
import SongListDetails from '@/views/SongListDetails.vue'
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
        component:Index,
      },
      {
        path: '/singer',
        name: '歌手页面',
        component:Singer,
      },
      {
        path: '/songList',
        name: '歌单页面',
        component:SongList,
      },
      {
        path: '/singerDetails',
        name: '歌手详情页面',
        component:SingerDetails,
      },
      {
        path: '/songListDetails',
        name: '歌单详情页面',
        component:SongListDetails,
      },
      {
        path: '/songDetails',
        name: '歌曲详情页面',
        component:SongDetails,
      },
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


router.beforeEach((to, from, next) => {
  request.get("/web/user").then(res => {
    // console.log(res);
    if (res.code === '0') {
      next();
    } else {
      console.log("系统错误");
      
      return;
    }
  })
  
})

export default router
