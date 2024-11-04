import Index from '@/views/Index.vue'
import Layout from '@/views/Layout.vue'
import Singer from '@/views/Singer.vue'
import SingerDetails from '@/views/SingerDetails.vue'
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
        path: '/singerDetails',
        name: '歌手详情页面',
        component:SingerDetails,
      },
      {
        path: '/songListDetails',
        name: '歌单详情页面',
        component:SongListDetails,
      },
    ]
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
