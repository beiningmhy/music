<template>
    <div class="register" @contextmenu.prevent>
        <div style="width: 400px; height: 400px; margin: 10% auto ;margin-right: 15%; 
                border-radius: 30px;backdrop-filter: blur(20px);box-shadow: rgb(207, 207, 207) 0px 0px 20px 10px">
                <div
                    style="width: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: white">
                    欢迎登录
                </div>
            <div style="margin-top: 25px; text-align: center; height: 320px;">
                <el-form :model="user">
                    <el-form-item>
                        <el-input v-model="user.username" prefix-icon="el-icon-user" style="width: 80%"
                            placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input v-model="user.password" prefix-icon="el-icon-lock" style="width: 80%"
                            placeholder="请输入密码"></el-input>
                    </el-form-item>

                    <el-form-item>
                        <el-button style="width: 80%; margin-top: 62px" type="primary"
                            @click="register()">注册</el-button>
                    </el-form-item>
                    <el-form-item>
                        <el-button style="width: 80%;" type="warning"
                            @click="$router.push('/login')">已经有账号了！点击登录</el-button>
                    </el-form-item>
                </el-form>

            </div>
        </div>
    </div>
</template>

<script>

import request from "@/utils/request";

export default {
    name: "Register",
    data() {
        return {
            user: {}
        }
    },
    // 页面加载的时候，做一些事情，在created里面
    created() {
    },
    // 定义一些页面上控件出发的事件调用的方法
    methods: {
        register() {
            // this.admin.role = "ROLE_STUDENT";
            request.post("/consumer/register", this.user).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '注册成功！请及时登录，否则无法登录！',
                        type: 'success'
                    });

                    this.$router.push("/login");
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        }
    }
}
</script>
<style scoped>
.register {
    width: 100%;
    height: 100vh;
    background-size: cover;
    background-position: center;
    background-image: url('../assets/images/back.jpg');
    position: absolute;
    top: 0;

}
</style>