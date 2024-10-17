<template>
    <div @contextmenu.prevent @keydown="handleKeyPressF12">
        <div class="login">
            <div
                style="width: 400px; height: 400px; margin: 10% auto ;margin-right: 15%; background-color:rgba(107,149,224,0.5); border-radius: 10px;box-shadow: 20px 20px 20px #cfcfcf;">
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
                            <el-input v-model="admin.password" show-password prefix-icon="el-icon-lock"
                                style="width: 80%" placeholder="请输入密码"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <div style="display: flex; justify-content: center">
                                <el-input v-model="admin.verCode" prefix-icon="el-icon-user"
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
                        message: `登录成功${this.admin.name}`,
                        type: 'success'
                    });
                    // console.log(res.data);
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
        handleKeyPressF12(event) {
            // console.log(event);
            
            if (event.key === 'F12' || event.keyCode === 123) {
                event.preventDefault(); // 阻止F12键的默认行为
                this.$message({ message: '禁止使用F12键', type: 'error' });
            }
            if(event.key==='Enter'||event.keyCode===13){
                this.login();
            }

        },

    },

}
</script>
<style scoped>
.login {
    width: 100%;
    height: 100%;
    background-size: cover;
    background-position: center;
    background-image: url('../assets/images/back.jpg');
    position: absolute;
    top: 0px;
}
</style>