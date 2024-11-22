<!-- <template>
    <div>
        <el-container>
            <el-header style="background-color: #ffcccc">
                <img src="@/assets/images/logo1.png" alt="" style="width: 40px; position: relative; top: 10px;">
                <span style="font-size: 20px; margin-left: 15px; color: black">毕设</span>
                
                    <el-dropdown style="float: right; height: 60px; line-height: 60px">
                        <span class="el-dropdown-link" style="color: black; font-size: 16px">{{ user.name }}<i
                                class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item>
                                <div @click="logout()">退出登录</div>
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                

            </el-header>
        </el-container>

        <el-container>
            <el-aside style="overflow: hidden; min-height: 100vh; background-color: #caabdb; width: 250px">
                <el-menu :default-active="$route.path" router background-color="#caabdb" text-color="#000"
                    active-text-color="#ffd04b">
                    <el-menu-item index="/">
                        <i class="el-icon-s-home"></i>
                        <span slot="title">系统首页</span>
                    </el-menu-item>
                    <el-submenu index="2" v-if="user.role==='ROLE_ADMIN'">
                        <template slot="title">
                            <i class="el-icon-location"></i><span>用户管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/admin">管理员信息</el-menu-item>
                            <el-menu-item index="/user">用户信息</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                    <el-submenu index="3">
                        <template slot="title">
                            <i class="el-icon-location"></i><span>信息管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/book">图书信息</el-menu-item>
                            <el-menu-item index="/type">图书分类</el-menu-item>
                            <el-menu-item index="/audit">请假审核</el-menu-item>
                            <el-menu-item index="/hotel">酒店预约</el-menu-item>
                            <el-menu-item index="/reserve">预定信息</el-menu-item>
                            <el-menu-item index="/log">日志管理</el-menu-item>
                            <el-menu-item index="/notice" v-if="user.role==='ROLE_ADMIN'">系统公告</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <el-main>

                <router-view />
            </el-main>
        </el-container>
    </div>
</template>

<script>

export default {
    name: "LAYOUT",
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        }
    },
    methods: {
        logout() {
            localStorage.removeItem("user");
            this.$router.push("/login");
        }
    }
}
</script>
<style lang="scss" scoped>
.el-menu {
    border-right: none !important;
}
</style> -->

<template>
    <div>
        <el-container>
            <!--    侧边栏  -->
            <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #001529;">
                <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center"
                    @click="$route.path == '/' ? '' : $router.push('/')">
                    <img src="@/assets/images/logo1.png" alt="" style="width: 40px; height: 40px">
                    <span class="logo-title" v-show="!isCollapse">music</span>
                </div>

                <el-menu :collapse="isCollapse" :collapse-transition="false" router background-color="#001529"
                    text-color="rgba(255, 255, 255, 0.65)" active-text-color="#fff" style="border: none"
                    :default-active="$route.path">
                    <el-menu-item index="/" @click="handleClick($route)">
                        <i class="el-icon-menu"></i>
                        <span slot="title">系统首页</span>
                    </el-menu-item>

                    <el-submenu index="2" v-if="user.role === '0'">
                        <template slot="title">
                            <i class="el-icon-user-solid"></i><span>用户管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/admin" @click="handleClick($route)">管理员信息</el-menu-item>
                            <el-menu-item index="/consumer" @click="handleClick($route)">用户信息</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                    <el-submenu index="3">

                        <template slot="title">
                            <i class="el-icon-headset"></i><span slot="title">music管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/singer" @click="handleClick($route)">歌手信息</el-menu-item>
                            <el-menu-item index="/song" @click="handleClick($route)">歌曲信息</el-menu-item>
                            <el-menu-item index="/songList" @click="handleClick($route)">歌单信息</el-menu-item>
                            <!-- <el-menu-item index="/songList2Song" @click="handleClick($route)">歌单歌曲信息</el-menu-item> -->
                        </el-menu-item-group>
                    </el-submenu>
                    <el-submenu index="4">
                        <template slot="title">
                            <i class="el-icon-message-solid"></i><span>信息管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/banner" @click="handleClick($route)">轮播图管理</el-menu-item>
                            <el-menu-item index="/comment" @click="handleClick($route)">评论管理</el-menu-item>
                            <el-menu-item index="/collect" @click="handleClick($route)">收藏管理</el-menu-item>
                            <el-menu-item index="/rankList" @click="handleClick($route)">评分管理</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                    <el-submenu index="5">
                        <template slot="title">
                            <svg t="1732173892429" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="4277"
                                data-spm-anchor-id="a313x.search_index.0.i5.53db3a81cyrfU2" width="18" height="18">
                                <path
                                    d="M315.211 283.467v-56.681C315.211 101.496 403.336 0 512 0c108.664 0 196.789 101.557 196.789 226.786v56.621h-90.353v-56.621c0-78.005-50.718-136.433-106.436-136.433S405.564 148.78 405.564 226.786v56.621h-90.353z m90.353 0h-90.353v-56.681C315.211 101.496 403.336 0 512 0c108.664 0 196.789 101.557 196.789 226.786v56.621h-90.353v-56.621c0-78.005-50.718-136.433-106.436-136.433S405.564 148.78 405.564 226.786v56.621z"
                                    p-id="4278" data-spm-anchor-id="a313x.search_index.0.i3.53db3a81cyrfU2" class=""
                                    fill="#ffffff"></path>
                                <path
                                    d="M230.4 282.142l-66.259 585.97c-0.662 6.324 3.855 12.046 10.24 12.709l701.44 0.12a11.505 11.505 0 0 0 11.505-11.565l-77.101-587.415a11.505 11.505 0 0 0-11.445-9.999H241.845a11.505 11.505 0 0 0-11.445 10.24z m-89.75-10.12a101.858 101.858 0 0 1 101.195-90.413H798.78c51.14 0 94.329 37.888 101.015 88.546l77.04 585.97a101.858 101.858 0 0 1-101.014 115.17H175.646A101.858 101.858 0 0 1 74.451 857.93l66.198-585.908z m220.28 140.47c-48.37 0-87.341-39.334-87.341-87.703 0-48.309 38.972-87.643 87.34-87.643 48.31 0 87.342 39.334 87.342 87.643 0 48.369-39.032 87.702-87.341 87.702z m0-90.354c-1.807 0-3.012 1.265-3.012 2.65 0 1.386 1.205 2.711 3.012 2.711 1.747 0 3.012-1.325 3.012-2.71s-1.265-2.65-3.012-2.65z m302.863 90.353c-48.309 0-87.341-39.333-87.341-87.702 0-48.309 39.032-87.643 87.34-87.643 48.31 0 87.342 39.334 87.342 87.643 0 48.369-39.032 87.702-87.341 87.702z m0-90.353c-1.747 0-3.012 1.265-3.012 2.65 0 1.386 1.265 2.711 3.012 2.711 1.807 0 3.012-1.325 3.012-2.71s-1.205-2.65-3.012-2.65z"
                                    p-id="4279" data-spm-anchor-id="a313x.search_index.0.i2.53db3a81cyrfU2" class=""
                                    fill="#ffffff"></path>
                            </svg><span style="margin-left: 10px;">商品管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/product" @click="handleClick($route)">商品管理</el-menu-item>
                            <el-menu-item index="/order" @click="handleClick($route)">订单管理</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                    <el-submenu index="6">
                        <template slot="title">
                            <svg t="1731910968612" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="2348" width="18" height="18">
                                <path
                                    d="M924 708h-40.54a142.81 142.81 0 0 0-15.41-37.14l28.69-28.68a36 36 0 0 0 0-50.92 36 36 0 0 0-50.92 0L817.14 620A142.81 142.81 0 0 0 780 604.54V564a36 36 0 0 0-36-36 36 36 0 0 0-36 36v40.54A142.81 142.81 0 0 0 670.86 620l-28.68-28.69a36 36 0 0 0-50.92 0 36 36 0 0 0 0 50.92L620 670.86A142.81 142.81 0 0 0 604.54 708H564a36 36 0 0 0-36 36 36 36 0 0 0 36 36h40.54A142.81 142.81 0 0 0 620 817.14l-28.69 28.68a36 36 0 0 0 0 50.92 36 36 0 0 0 50.91 0l28.68-28.69a142.81 142.81 0 0 0 37.1 15.41V924a36 36 0 0 0 36 36 36 36 0 0 0 36-36v-40.54a142.81 142.81 0 0 0 37.14-15.41l28.68 28.69a36 36 0 0 0 50.92 0 36 36 0 0 0 0-50.92l-28.69-28.68A142.81 142.81 0 0 0 883.46 780H924a36 36 0 0 0 36-36 36 36 0 0 0-36-36zM744 816a72 72 0 1 1 72-72 72 72 0 0 1-72 72z"
                                    fill="#ffffff" p-id="2349"></path>
                                <path
                                    d="M860 64H164c-55 0-100 45-100 100v516c0 55 45 100 100 100h220v108H244a36 36 0 0 0-36 36 36 36 0 0 0 36 36h176a36 36 0 0 0 36-36V744a36 36 0 0 0-36-36H186a50.15 50.15 0 0 1-50-50v-59h284a36 36 0 0 0 36-36 36 36 0 0 0-36-36H136V186a50.15 50.15 0 0 1 50-50h652a50.15 50.15 0 0 1 50 50v234a36 36 0 0 0 36 36 36 36 0 0 0 36-36V164c0-55-45-100-100-100z"
                                    fill="#ffffff" p-id="2350"></path>
                            </svg><span style="margin-left: 10px;">系统管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/notice" @click="handleClick($route)">公告信息</el-menu-item>
                            <el-menu-item index="/address" @click="handleClick($route)">地址信息</el-menu-item>
                            <el-menu-item index="/log" @click="handleClick($route)">日志信息</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>

            </el-aside>

            <el-container>
                <!--        头部区域-->
                <el-header>

                    <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
                    <!-- <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
                        <el-breadcrumb-item :to="{ path: '/' }">首页

                        </el-breadcrumb-item>
                        <el-breadcrumb-item v-for="item in this.$route.matched" :key="item.path" :to="item">{{ item.name
                            }}</el-breadcrumb-item>
                    </el-breadcrumb> -->

                    <el-tag v-for="tag in tagList" :key="tag.name" closable
                        :type="tag.path == $route.path ? 'success' : 'success'" @close="removeTag(tag)"
                        @click="tagClick(tag)" size="medium" hit style="margin-left: 10px"
                        :effect="tag.path == $route.path ? 'dark' : 'light'">
                        {{ tag.name }}
                    </el-tag>


                    <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
                        <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i>
                        <el-dropdown placement="bottom">
                            <div style="display: flex; align-items: center; cursor: default">
                                <img src="@/assets/images/logo1.png" alt=""
                                    style="width: 40px; height: 40px; margin: 0 5px">
                                <span>管理员{{ user.name }}</span>
                            </div>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item>
                                    <div @click="handleClick($route), goto('/space')">
                                        个人信息
                                    </div>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <div @click="handleClick($route), goto('/changePassword')">修改密码</div>
                                </el-dropdown-item>
                                <el-dropdown-item>
                                    <div @click="logout()">退出登录</div>
                                </el-dropdown-item>
                            </el-dropdown-menu>
                        </el-dropdown>
                    </div>

                </el-header>

                <!--        主体区域-->
                <el-main>

                    <router-view />
                </el-main>

            </el-container>


        </el-container>
    </div>
</template>

<script>

export default {
    name: 'HomeView',
    data() {
        return {
            // router: this.$router.currentRoute.name,
            isCollapse: false,  // 不收缩
            asideWidth: '150px',
            collapseIcon: 'el-icon-s-fold',
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            tagList: [],
            route1: this.$route.path ? this.$route.path : '',
            windowWidth: window.innerWidth, // 初始化窗口宽度
        }
    },
    methods: {
        handleFull() {
            document.documentElement.requestFullscreen()
        },
        handleCollapse() {
            this.isCollapse = !this.isCollapse
            this.asideWidth = this.isCollapse ? '64px' : '150px'
            this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
        },
        logout() {
            localStorage.removeItem("user");
            this.$router.push("/login");
        },
        handleClick(router) {
            // console.log(router);
            // console.log(this.tagList);

            const exists = this.tagList.some(item => item.name === router.name);
            // console.log(exists);

            if (!exists) { // 如果不存在，则添加新的路由项
                // console.log(11111);

                this.tagList.push({
                    name: router.name,
                    title: router.name,
                    content: router.name,
                    path: router.path,
                });
            }
            // 当 tagList 数量超过5时，删除第一个元素
            if (this.tagList.length > 5) {
                this.tagList.shift(); // 删除数组的第一个元素
            }
        },
        removeTag(tag) {
            // console.log(tag);
            if (this.$route.path === tag.path) {
                this.$message({ message: '不能关闭当前页', type: 'error', duration: '500' });
                return
            }
            this.tagList = this.tagList.filter(item => item.path !== tag.path);
        },
        tagClick(tag) {
            if (tag.path === this.$route.path) {
                return;
            } else {
                this.$router.push(tag.path);
            }

        },
        initializeTagList() {
            // 获取当前路由信息
            const currentRoute = this.$route;
            // 检查当前路由信息是否有效
            if (currentRoute) {
                // 构建 tag 对象并加入到 tagList 中
                this.tagList.push({
                    name: currentRoute.name || '未命名',
                    title: currentRoute.meta.title || '未命名',
                    content: currentRoute.name || '未命名',
                    path: currentRoute.path
                });
            }
        },
        handleResize() {
            this.windowWidth = window.innerWidth;
            // console.log(this.windowWidth);
            if (this.windowWidth * 0.15 < 120 && this.isCollapse == false) {
                this.handleCollapse();
            } else if (this.windowWidth * 0.15 >= 120 && this.isCollapse == true) {
                this.handleCollapse();
            }

        },
        goto(route) {
            // console.log(route);
            let name = '';
            if (route === '/changePassword') {
                name = '修改密码';
            } else if (route === '/space') {
                name = '个人中心';
            }
            const exists = this.tagList.some(item => item.name === name);
            if (exists && route !== this.$route.path) {
                this.$router.push(route);
            }
            if (route !== this.$route.path) {

                this.$router.push(route);
                this.tagList.push({
                    name: name,
                    title: name,
                    content: name,
                    path: route
                });
            }
        },
    },
    created() {
        this.initializeTagList();
        this.handleResize();

    },

    mounted() {
        window.addEventListener('resize', this.handleResize);
    },
    watch: {
        $route() {
            // console.log(this.$route);
            this.handleClick(this.$route);
        }
    },
}
</script>

<style>
.el-menu--inline {
    background-color: #000c17 !important;
}

.el-menu--inline .el-menu-item {
    background-color: #000c17 !important;
    padding-left: 49px !important;
}

.el-menu-item:hover,
.el-submenu__title:hover {
    color: #fff !important;
}

.el-submenu__title:hover i {
    color: #fff !important;
}

.el-menu-item:hover i {
    color: #fff !important;
}

.el-menu-item.is-active {
    background-color: #1890ff !important;
    border-radius: 5px !important;
    width: calc(100% - 8px);
    /* width: calc(100%); */
    margin-left: 4px;
}

.el-menu-item.is-active i,
.el-menu-item.is-active .el-tooltip {
    margin-left: -4px;
}

.el-menu-item {
    height: 40px !important;
    line-height: 40px !important;
}

.el-submenu__title {
    height: 40px !important;
    line-height: 40px !important;
}

.el-submenu .el-menu-item {
    min-width: 0 !important;
}

.el-menu--inline .el-menu-item.is-active {
    padding-left: 45px !important;
}

/*.el-submenu__icon-arrow {*/
/*  margin-top: -5px;*/
/*}*/

.el-aside {
    transition: width 1s;
    box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
}

.logo-title {
    margin-left: 5px;
    font-size: 20px;
    transition: all .3s;
    /* 0.3s */
}

.el-header {
    box-shadow: 2px 0 6px rgba(0, 21, 41, .35);
    display: flex;
    align-items: center;
}

.el-menu--popup {
    min-width: 100px !important;
}

.el-menu {
    max-height: 90vh;
    overflow: auto;
    scrollbar-width: none;
    /* 隐藏滚动条 */
    -ms-overflow-style: none;
    /* 隐藏 IE 和 Edge 的滚动条 */
    overflow: -moz-scrollbars-none;
    /* 隐藏 Firefox 的滚动条 */
}
</style>