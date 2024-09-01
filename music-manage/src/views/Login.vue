<template>
    <div>
        <div
            style="width: 400px; height: 400px; margin: 150px auto; background-color:rgba(107,149,224,0.5); border-radius: 10px">
            <div
                style="width: 100%; height: 100px; font-size: 30px; line-height: 100px; text-align: center; color: #4a5ed0">
                欢迎登录
            </div>
            <div style="margin-top: 25px; text-align: center; height: 320px;">
                <el-form :model="admin">
                    <el-form-item>
                        <el-input v-model="admin.name" prefix-icon="el-icon-user" style="width: 80%"
                            placeholder="请输入用户名"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-input v-model="admin.password" show-password prefix-icon="el-icon-lock" style="width: 80%"
                            placeholder="请输入密码"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <div style="display: flex; justify-content: center">
                            <el-input v-model="admin.verCode" prefix-icon="el-icon-user"
                                style="width: 42%; margin-right: 10px" placeholder="请输入验证码"></el-input>
                            <img :src="captchaUrl" @click="clickImg()" width="140px" height="33px" />
                        </div>
                    </el-form-item>
                    <el-form-item>
                        <el-button style="width: 80%; margin-top: 10px" type="primary" @click="login()">登录</el-button>

                    </el-form-item>
                    <el-form-item>
                        <el-button style="width: 80%;" type="warning"
                            @click="$router.push('/register')">还没有账号？点击注册</el-button>

                    </el-form-item>
                </el-form>

            </div>
        </div>
    </div>
</template>

<script>
import request from "@/utils/request";
export default {
    name: "LOGIN",
    data() {
        return {
            admin: {
                name: '',
                password: '',
                verCode: ''
            },
            key: '',
            captchaUrl: '',
        }
    },
    mounted() {
        this.key = Math.random();
        this.captchaUrl = "http://localhost:8080/api/captcha?key=" + this.key;
    },
    methods: {
        login() {
            request.post("/admin/login?key=" + this.key, this.admin).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '登录成功',
                        type: 'success'
                    });
                    console.log(res.data);
                    localStorage.setItem("user", JSON.stringify(res.data));
                    this.$router.push('/');
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                    this.admin.verCode = "";
                    this.key = Math.random();
                    this.captchaUrl = "http://localhost:8080/api/captcha?key=" + this.key;

                }
            })

        },
        clickImg() {
            this.key = Math.random();
            this.captchaUrl = "http://localhost:8080/api/captcha?key=" + this.key;
            let that = this;
            this.admin = {
                verCode: '',
                name: that.admin.name,
                password: that.admin.password,
            }

        },

    },

}
</script>