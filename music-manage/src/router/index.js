import request from '@/utils/request'
import Address from '@/views/Address.vue'
import Admin from '@/views/Admin.vue'
import Consumer from '@/views/Consumer.vue'
import Index from '@/views/Index.vue'
import Layout from '@/views/Layout.vue'
import Login from '@/views/Login.vue'
import Register from '@/views/Register.vue'
import Singer from '@/views/Singer.vue'
import Song from '@/views/Song.vue'
import SongList from '@/views/SongList.vue'
import SongList2Song from '@/views/SongList2Song.vue'
import Vue from 'vue'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
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
		path: '/',
		name: '布局',
		component: Layout,
		children: [
			{
				path: '/',
				name: '首页',
				component: Index,
			},
			{
				path: '/admin',
				name: '管理员信息',
				component: Admin,
			},
			{
				path: '/consumer',
				name: '用户信息',
				component: Consumer,
			},
			{
				path: '/singer',
				name: '歌手信息',
				component: Singer,

			},
			{
				path: '/song',
				name: '歌曲信息',
				component: Song,
			},
			{
				path: '/songList',
				name: '歌单管理',
				component: SongList,
			},
			{
				path: '/songList2Song',
				name: '歌单歌曲管理',
				component: SongList2Song,
			},
			{
				path: '/address',
				name: '地址管理',
				component: Address,
			},


		]
	}



]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
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
	if (to.path === '/login' || to.path === '/register') {
		next();
		console.log(111);

		return;
	}


	if (!user && (to.path !== '/login' || to.path !== '/register')) {
		console.log(222);

		return next("/login");
	}
	next();
})
export default router
