<template>
    <div>
        <el-container>
            <el-aside width="200px">
                <div style="height: 60px; color: white; display: flex; align-items: center; justify-content: center"
                    @click="$route.path == '/' ? '' : $router.push('/')">
                    <img src="@/assets/images/logo1.png" alt="" style="width: 40px; height: 40px">
                    <span class="logo-title"  style="color: black;">music</span>
                </div>
                <div>
                    <!-- 登录 -->
                </div>
            </el-aside>
            <el-container>
                <el-header>
                    <!-- 头部 -->
                    <div style="display: flex;flex-direction: row;">
                        <div
                            style="background-color: rgba(200, 200, 200,0.8);width: 40px;border-radius: 50%;height: 40px;line-height: 40px;margin-top: 10px; box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);">
                            <i class="el-icon-back"></i>
                        </div>
                        <div
                            style="display: flex;flex-direction: row;margin-left: 100px;height: 40px;line-height: 40px;margin-top: 10px;">
                            <!-- 目录 -->
                            <div
                                style="margin-left: 30px; background-color:rgba(200, 200, 200,0.2) ;box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);border-radius: 40%;width: 60px;">
                                首页
                            </div>
                            <div
                                style="margin-left: 30px; background-color:rgba(200, 200, 200,0.2) ;box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);border-radius: 40%;width: 60px;">
                                歌手
                            </div>
                            <div
                                style="margin-left: 30px; background-color:rgba(200, 200, 200,0.2) ;box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.3);border-radius: 40%;width: 60px;">
                                歌单
                            </div>
                        </div>
                    </div>



                </el-header>
                <el-main>

                    <router-view></router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script>
export default {
    name: 'Layout'
}
</script>
<style scoped>
.el-header {
    background-color: rgba(240, 240, 240, 0.5);
    color: #333;
    text-align: center;
    line-height: 60px;


}

.el-aside {
    background-color: #dbdee1;
    color: #333;
    text-align: center;
    line-height: 200px;
    min-height: 100vh;
    box-shadow: 5px 0px 10px rgba(0, 0, 0, 0.3);
    border-radius: 2%;
}

.el-main {
    background-color: rgba(180, 176, 176, 0.158);
    

}


</style>