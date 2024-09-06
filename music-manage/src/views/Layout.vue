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
            <el-aside :width="asideWidth" style="min-height: 100vh; background-color: #001529">
                <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center">
                    <img src="@/assets/images/logo1.png" alt="" style="width: 40px; height: 40px">
                    <span class="logo-title" v-show="!isCollapse">music</span>
                </div>

                <el-menu :collapse="isCollapse" :collapse-transition="false" router background-color="#001529"
                    text-color="rgba(255, 255, 255, 0.65)" active-text-color="#fff" style="border: none"
                    :default-active="$route.path">
                    <el-menu-item index="/">
                        <i class="el-icon-menu"></i>
                        <span slot="title">系统首页</span>
                    </el-menu-item>

                    <el-submenu index="2" v-if="user.role === '0'">
                        <template slot="title">
                            <i class="el-icon-user-solid"></i><span>用户管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/admin">管理员信息</el-menu-item>
                            <el-menu-item index="/consumer">用户信息</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                    <el-submenu index="3">

                        <template slot="title">
                            <i class="el-icon-headset"></i><span slot="title">music管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/singer">歌手信息</el-menu-item>
                            <el-menu-item index="/song">歌曲信息</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu> 
                    <el-submenu index="4">
                        <template slot="title">
                            <i class="el-icon-message-solid"></i><span>信息管理</span>
                        </template>
                        <el-menu-item-group>
                            <el-menu-item index="/address">地址信息</el-menu-item>
                            <el-menu-item index="/type">图书分类</el-menu-item>
                            <el-menu-item index="/audit">请假审核</el-menu-item>
                            <el-menu-item index="/hotel">酒店预约</el-menu-item>
                            <el-menu-item index="/reserve">预定信息</el-menu-item>
                            <el-menu-item index="/log">日志管理</el-menu-item>
                            <el-menu-item index="/notice" v-if="user.role === '0'">系统公告</el-menu-item>
                        </el-menu-item-group>
                    </el-submenu>
                </el-menu>

            </el-aside>

            <el-container>
                <!--        头部区域-->
                <el-header>

                    <i :class="collapseIcon" style="font-size: 26px" @click="handleCollapse"></i>
                    <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left: 20px">
                        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                    </el-breadcrumb>

                    <div style="flex: 1; width: 0; display: flex; align-items: center; justify-content: flex-end">
                        <i class="el-icon-quanping" style="font-size: 26px" @click="handleFull"></i>
                        <el-dropdown placement="bottom">
                            <div style="display: flex; align-items: center; cursor: default">
                                <img src="@/assets/images/logo1.png" alt=""
                                    style="width: 40px; height: 40px; margin: 0 5px">
                                <span>管理员{{ user.name }}</span>
                            </div>
                            <el-dropdown-menu slot="dropdown">
                                <el-dropdown-item>个人信息</el-dropdown-item>
                                <el-dropdown-item>修改密码</el-dropdown-item>
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
            isCollapse: false,  // 不收缩
            asideWidth: '200px',
            collapseIcon: 'el-icon-s-fold',
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        }
    },
    methods: {
        handleFull() {
            document.documentElement.requestFullscreen()
        },
        handleCollapse() {
            this.isCollapse = !this.isCollapse
            this.asideWidth = this.isCollapse ? '64px' : '200px'
            this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold' : 'el-icon-s-fold'
        },
        logout() {
            localStorage.removeItem("user");
            this.$router.push("/login");
        }
    },
    created() {


    }
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
</style>