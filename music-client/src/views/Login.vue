<template>
    <div @contextmenu.prevent @keydown="handleKeyPressF12">
        <div class="login">
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
                            <el-input v-model="user.password" show-password prefix-icon="el-icon-lock"
                                style="width: 80%" placeholder="请输入密码"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <div style="display: flex; justify-content: center">
                                <el-input v-model="user.verCode" prefix-icon="el-icon-user"
                                    style="width: 42%; margin-right: 10px" placeholder="请输入验证码"></el-input>
                                <img :src="captchaUrl" @click="clickImg()" width="140px" height="33px" />
                            </div>
                        </el-form-item>
                        <el-form-item>
                            <el-button style="width: 80%; margin-top: 10px" type="primary"
                                @click="login()">登录</el-button>

                        </el-form-item>
                        <el-form-item>
                            <el-button style="width: 80%;" type="warning"
                                @click="$router.push('/register')">还没有账号？点击注册</el-button>

                        </el-form-item>
                    </el-form>
                    <!-- <a-button type="primary">
                    Primary
                </a-button> -->
                </div>
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
            user: {
                username: '',
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
        async login() {
            request.post("/consumer/login?key=" + this.key, this.user).then(async res => {
                if (res.code === '0') {
                    this.$message({
                        message: `登录成功${this.user.username}，100积分已自动加入您的账户`,
                        type: 'success'
                    });
                    let user = res.data;
                    if (res.data.singerType == '1') {
                        let p = {
                            name: res.data.username,
                            pageNum: 1,
                            pageSize: 1
                        }
                        let singerId;
                        await request.get('/singer/search', {
                            params: p
                        }).then(res => {
                            if (res.code === '0') {
                                // console.log(res.data);
                                singerId = res.data.list[0].id;
                                user.singerId = singerId;
                                // console.log(user);

                            } else {
                                this.$message.error(res.msg);

                            }
                        })

                    }
                    // console.log(user);


                    // console.log(res.data);
                    localStorage.setItem("user", JSON.stringify(user));
                    this.$router.push('/');
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                    this.user.verCode = "";
                    this.key = Math.random();
                    this.captchaUrl = "http://localhost:8080/api/captcha?key=" + this.key;

                }
            })

        },
        clickImg() {
            this.key = Math.random();
            this.captchaUrl = "http://localhost:8080/api/captcha?key=" + this.key;
            let that = this;
            this.user = {
                verCode: '',
                username: that.user.username,
                password: that.user.password,
            }

        },
        handleKeyPressF12(event) {
            // console.log(event);

            if (event.key === 'F12' || event.keyCode === 123) {
                event.preventDefault(); // 阻止F12键的默认行为
                this.$message({ message: '禁止使用F12键', type: 'error' });
            }
            if (event.key === 'Enter' || event.keyCode === 13) {
                this.login();
            }

        },

    },

}
</script>
<style scoped>
.login {
    width: 100%;
    height: 100vh;
    background-size: cover;
    background-position: center;
    background-image: url('../assets/images/back.jpg');
    position: absolute;
    top: 0;
}
</style>