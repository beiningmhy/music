import request from '@/utils/request'
import Address from '@/views/Address.vue'
import Admin from '@/views/Admin.vue'
import AdminSpace from '@/views/AdminSpace.vue'
import Banner from '@/views/Banner.vue'
import ChangePassword from '@/views/ChangePassword.vue'
import Collect from '@/views/Collect.vue'
import Comment from '@/views/Comment.vue'
import Consumer from '@/views/Consumer.vue'
import Feedback from '@/views/Feedback.vue'
import Index from '@/views/Index.vue'
import Layout from '@/views/Layout.vue'
import Log from '@/views/Log.vue'
import Login from '@/views/Login.vue'
import Notice from '@/views/Notice.vue'
import Order from '@/views/Order.vue'
import Product from '@/views/Product.vue'
import RankList from '@/views/RankList.vue'
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
			{
				path: '/comment',
				name: '评论管理',
				component: Comment,
			},
			{
				path: '/collect',
				name: '收藏管理',
				component: Collect,
			},
			{
				path: '/rankList',
				name: '评分管理',
				component: RankList,
			},
			{
				path: '/log',
				name: '日志管理',
				component: Log,
			},
			{
				path: '/feedback',
				name: '反馈管理',
				component: Feedback,
			},
			{
				path: '/banner',
				name: '轮播图管理',
				component: Banner,
			},
			{
				path: '/notice',
				name: '公告管理',
				component: Notice,
			},
			{
				path: '/product',
				name: '商品管理',
				component: Product,
			},
			{
				path: '/order',
				name: '订单管理',
				component: Order,
			},
			{
				path: '/changePassword',
				name: '修改密码',
				component: ChangePassword,
			},
			{
				path: '/space',
				name: '个人中心',
				component: AdminSpace,
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




	const user = JSON.parse(localStorage.getItem("user"));
	if (user != null) {
		if (user.role == '1') {
			if (to.path == '/singer' || to.path == '/song' || to.path == '/songList' || to.path == '/songList2Song' || to.path == '/') {
				next();

				return;
			} else {
				next('/');
				alert('没有权限');

			}
		}
	}


	if (to.path === '/login' || to.path === '/register') {
		next();
		return;
	}

	if (!user && (to.path !== '/login' || to.path !== '/register')) {
		return next("/login");
	}
	if (user && (to.path !== '/login' || to.path !== '/register')) {
		request.get("/web/").then(res => {
			if (res.code === '0') {
				return;
			} else {
				localStorage.removeItem("user");
				next("/login");
			}
		}).catch(err => {
			// console.log(err);
			localStorage.removeItem("user");
			next("/login");
		})
	}
	next();
})
export default router
