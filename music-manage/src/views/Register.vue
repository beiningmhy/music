<template>
    <div class="register" @contextmenu.prevent>
        <div
            style="width: 400px; height: 400px; margin: 10% auto; margin-right: 15%;background-color:rgba(165,190,234,0.5); border-radius: 10px;box-shadow: 20px 20px 20px #cfcfcf;">
            <div
                style="width: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: #4a5ed0">
                欢迎注册</div>
            <div style="margin-top: 25px; text-align: center; height: 320px;">
                <el-form :model="admin">
                    <el-form-item>
                        <el-input v-model="admin.name" prefix-icon="el-icon-user" style="width: 80%"
                            placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input v-model="admin.password" prefix-icon="el-icon-lock" style="width: 80%"
                            placeholder="请输入密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-select v-model="admin.role" placeholder="请选择" style="width: 80%">    
                            <el-option label="超级管理员" value="0"></el-option>
                            <el-option label="歌曲管理员" value="1"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button style="width: 80%; margin-top: 10px" type="primary"
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
            admin: {}
        }
    },
    // 页面加载的时候，做一些事情，在created里面
    created() {
    },
    // 定义一些页面上控件出发的事件调用的方法
    methods: {
        register() {
            // this.admin.role = "ROLE_STUDENT";
            request.post("/admin/register", this.admin).then(res => {
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
    height: 100%;
    background-size: cover;
    background-position: center;
    background-image: url('../assets/images/back.jpg');
    position: absolute;
    top: 0px;
}
</style>