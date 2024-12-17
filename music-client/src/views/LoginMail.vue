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
                            <el-input v-model="user.email" prefix-icon="el-icon-user" style="width: 80%"
                                placeholder="请输入邮箱"></el-input>
                        </el-form-item>

                        <el-form-item>
                            <div style="display: flex; justify-content: center">
                                <el-input v-model="user.verCode" prefix-icon="el-icon-user"
                                    style="width: 42%; margin-right: 10px" placeholder="请输入验证码"></el-input>
                                <el-button style="width: 35%;" type="primary" @click="getMail()" :disabled="disable">获取验证码{{ time }}{{ timeStr }}</el-button>
                                <!-- <div style="width: 10%;margin-left: 5%;">
                                    
                                </div> -->
                            </div>
                        </el-form-item>
                        <el-form-item>
                            <el-button style="width: 80%; margin-top: 62px" type="primary"
                                @click="login()">登录</el-button>

                        </el-form-item>
                        <el-form-item>
                            <el-button style="width: 37%; " type="primary"
                                @click="$router.push('/login')">账密登录</el-button>
                            <el-button style="width: 40%;" type="warning"
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
                email: '',
                verCode: ''
            },
            key: '',
            time:'',
            timeStr:'',
            disable: false,
        }
    },
    mounted() {
    },
    methods: {
        async login() {
            request.post("/consumer/login/mail?key=" + this.key, this.user).then(async res => {
                if (res.code === '0') {
                    console.log(res.data);

                    this.$message({
                        message: `登录成功${res.data.username}，100积分已自动加入您的账户`,
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
                }
            })

        },
        getMail() {
            if (this.user.email === '') {
                this.$message({ message: '请输入邮箱', type: 'error' });
                return;
            }
            this.key = Math.random();
            request.post('/MailCode?toEmail=' + this.user.email + '&key=' + this.key).then(res => {
                if (res.code === '0') {
                    this.$message({ message: '验证码已发送，请查收', type: 'success' });
                    this.disable = true;
                    this.time=10;
                    this.timeStr='s';
                    this.startCountDown();
                } else {
                    this.$message({ message: '验证码发送失败', type: 'error' });
                }
            })
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
        startCountDown() {
            this.interval = setInterval(() => {
                if (this.time > 0) {
                    this.time--;
                } else {
                    this.disable = false;
                    this.time='';
                    this.timeStr='';
                    clearInterval(this.interval);
                }
            }, 1000);
        }

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