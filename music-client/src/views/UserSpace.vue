<template>
    <div style="max-height: 89vh;overflow: auto;" class="space">
        <el-backtop target=".space" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>
        <div style="height: 480px;">
            <div
                style="width: 80%;height: 450px;background-color: rgba(244, 244, 244,0.5);margin: 0 auto;margin-top: 50px;display: flex;box-shadow:  10px 10px 6px rgba(0, 0, 0, .2);border-radius: 20px;">
                <!-- 用户头像部分 -->
                <div style="width: 150px;display: flex;flex-direction: column;margin-top: 50px;">
                    <el-image style="width: 150px; height: 150px; border-radius: 20% ;"
                        :src="'http://localhost:8080/api/files/' + user.avatar">
                    </el-image>
                    <el-upload class="upload-demo" action="http://localhost:8080/api/files/upload"
                        :on-success="uploadAvatar">
                        <el-button size="small" type="primary"
                            style="margin-left:30px;margin-top: 20px;">点击上传</el-button>
                        <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
                    </el-upload>
                    <div style="margin:0px auto ;margin-top: 20px;">
                        <span style="font-weight: bold;">UID:</span><el-tag>{{ user.uid }}</el-tag>
                    </div>
                    <div style="margin:0px auto ;margin-top: 20px">
                        <span style="font-weight: bold;">用户名:</span>{{ user.username }}
                    </div>
                </div>
                <!-- 用户信息部分 -->
                <el-tabs v-model="activeName" style="margin-left: 20px;width: 80%;margin-top: 20px;">
                    <el-tab-pane label="基本信息" name="first">
                        <div style="max-height: 350px;width: 90%;overflow-y: auto;">
                            <!-- <div style="font-size: 20px;margin-top: 10px;">
                                <div style="display: flex;">
                                    <span style="font-weight: bold;width: 100px;">用户名:</span>
                                    <el-input v-model="user.username" placeholder="请输入内容" :disabled="isDisabled"></el-input>
                                </div>
                            </div>
                            <div style="font-size: 20px;margin-top: 10px;">
                                <div style="display: flex;">
                                    <span style="font-weight: bold;width: 100px;">用户名:</span>
                                    <el-input v-model="user.username" placeholder="请输入内容" :disabled="isDisabled"></el-input>
                                </div>
                            </div>
                            <div style="font-size: 20px;margin-top: 10px;">
                                <div style="display: flex;">
                                    <span style="font-weight: bold;width: 100px;">用户名:</span>
                                    <el-input v-model="user.username" placeholder="请输入内容" :disabled="isDisabled"></el-input>
                                </div>
                            </div> -->
                            <el-form>

                                <el-form-item label="用户名" label-width="20%" aria-required="true">
                                    <el-input v-model="form.username" autocomplete="off" style="width: 90%"
                                        :disabled="isDisabled"></el-input>
                                </el-form-item>
                                <el-form-item label="角色" label-width="20%">
                                    <el-select v-model="form.singerType" placeholder="请选择" style="width: 90%" clearable
                                        disabled>
                                        <el-option label="普通用户" value="0"></el-option>
                                        <el-option label="歌手" value="1"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="性别" label-width="20%">
                                    <el-select v-model="form.sex" placeholder="请选择" style="width: 90%" clearable
                                        :disabled="isDisabled">
                                        <el-option label="男" value="1"></el-option>
                                        <el-option label="女" value="0"></el-option>
                                    </el-select>
                                </el-form-item>
                                <el-form-item label="手机号" label-width="20%" aria-required="true">
                                    <el-input v-model="form.phone" autocomplete="off" style="width: 90%"
                                        :disabled="isDisabled"></el-input>
                                </el-form-item>
                                <el-form-item label="邮箱" label-width="20%" aria-required="true">
                                    <el-input v-model="form.email" autocomplete="off" style="width: 90%"
                                        :disabled="isDisabled"></el-input>
                                </el-form-item>
                                <el-form-item label="出生日期" label-width="20%" aria-required="true">
                                    <el-date-picker v-model="form.birth" type="date" format="yyyy-MM-dd"
                                        value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 90%"
                                        :disabled="isDisabled"></el-date-picker>
                                </el-form-item>
                                <el-form-item label="介绍" label-width="20%" aria-required="true">
                                    <el-input type="textarea" rows="3" v-model="form.introduction" autocomplete="off"
                                        style="width: 90%" :disabled="isDisabled"></el-input>
                                </el-form-item>
                                <el-form-item label="地址" label-width="20%" aria-required="true">
                                    <el-tag autocomplete="off" style="width: 90%" size="medium">{{ form.address
                                        }}</el-tag>
                                    <el-cascader v-model="value" :options="cascaderData"
                                        :props="{ expandTrigger: 'hover' }" @change="handleChange"
                                        style="width: 90%;margin-top: 5px;" :disabled="isDisabled"></el-cascader>
                                </el-form-item>

                                <el-form-item label="积分" label-width="20%" aria-required="true">
                                    <el-input v-model="form.point" autocomplete="off" style="width: 90%"
                                        disabled></el-input>
                                </el-form-item>

                            </el-form>
                            <div style="margin-top: 30px;margin-left: 30%;">
                                <el-button v-if="isDisabled" type="primary" @click="edit"
                                    style="width: 100px;">修改</el-button>
                                <el-button v-if="!isDisabled" type="success" @click="submit"
                                    style="width: 100px;">提交</el-button>
                                <el-button v-if="!isDisabled" type="warning" @click="cancel"
                                    style="width: 100px;">取消</el-button>
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="安全设置" name="second">
                        <div>
                            <el-form style="margin-left: -35%;">
                                <el-form-item label="请输入原密码" label-width="40%" aria-required="true">
                                    <el-input v-model="form1.oldPw" autocomplete="off" style="width: 90%"></el-input>
                                </el-form-item>
                                <el-form-item label="请输入新密码" label-width="40%" aria-required="true">
                                    <el-input v-model="form1.newPw" autocomplete="off" style="width: 90%"></el-input>
                                </el-form-item>
                                <el-form-item label="请确认新密码" label-width="40%" aria-required="true">
                                    <el-input v-model="form1.confirmPw" autocomplete="off"
                                        style="width: 90%"></el-input>
                                </el-form-item>
                            </el-form>
                            <div slot="footer" style="text-align: center;">
                                <el-button type="primary" @click="submit1()">确 定 修 改</el-button>
                            </div>
                        </div>
                    </el-tab-pane>
                    <el-tab-pane label="创作者中心" name="third">
                        <div style="max-height: 350px;width: 90%;overflow-y: auto;height: 350px;">
                            <div class="container">
                                <h1>音乐网站用户守则须知</h1>
                                <div class="section">
                                    <h2>引言</h2>
                                    <p>在数字化时代，音乐网站成为了音乐爱好者和创作者的重要聚集地。它们不仅为听众提供了一个便捷的音乐获取渠道，也为音乐创作者提供了展示才华的平台。然而，为了维护一个健康、有序的网络环境，确保所有用户的权益得到保护，音乐网站制定了一系列的用户守则。本守则旨在为那些希望从普通用户转变为歌手歌曲上传者的音乐爱好者提供详尽的指导和须知。
                                    </p>
                                </div>
                                <div class="section">
                                    <h2>转变成歌手歌曲上传者之前的须知</h2>
                                    <ol>
                                        <li><strong>了解平台政策：</strong>在决定成为歌曲上传者之前，用户首先需要对音乐网站的服务条款、版权政策、社区指南和上传指南有一个全面的了解。
                                        </li>
                                        <li><strong>注册和验证：</strong>用户需要在音乐网站上注册一个账号，并根据网站的要求提供必要的个人信息。</li>
                                        <!-- 其他须知项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>成为歌曲上传者之后的守则</h2>
                                    <ol>
                                        <li><strong>持续遵守平台政策：</strong>成为上传者后，用户应持续关注并遵守音乐网站的政策更新。</li>
                                        <li><strong>版权声明：</strong>用户在上传音乐作品时，应明确声明作品的版权归属。</li>
                                        <!-- 其他守则项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>版权和法律问题</h2>
                                    <ol>
                                        <li><strong>版权教育：</strong>音乐网站应提供版权教育资源，帮助用户了解版权的基本概念。</li>
                                        <li><strong>版权检测技术：</strong>音乐网站应使用先进的版权检测技术，如音频指纹技术。</li>
                                        <!-- 其他法律问题项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>社区建设和用户关系</h2>
                                    <ol>
                                        <li><strong>社区活动：</strong>音乐网站应定期举办社区活动，如在线音乐会、音乐比赛等。</li>
                                        <li><strong>用户支持：</strong>音乐网站应提供用户支持服务，帮助用户解决在上传和推广过程中遇到的问题。</li>
                                        <!-- 其他社区和用户关系项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>技术和创新</h2>
                                    <ol>
                                        <li><strong>技术支持：</strong>音乐网站应提供技术支持，帮助用户解决在音乐制作和上传过程中遇到的技术问题。</li>
                                        <li><strong>创新工具：</strong>音乐网站可以开发创新工具，如在线音乐制作软件、音频编辑工具等。</li>
                                        <!-- 其他技术和创新项 -->
                                    </ol>
                                </div>
                                <div class="section">
                                    <h2>结语</h2>
                                    <p>音乐网站不仅是一个音乐分享的平台，更是一个音乐创作和交流的社区。通过遵守上述守则，用户可以确保自己的权益得到保护，同时也为整个社区的繁荣做出贡献。让我们共同努力，创造一个健康、积极、充满创意的音乐环境。
                                    </p>
                                </div>
                            </div>
                            <div style="text-align: center;">
                                <el-checkbox v-model="read">我以阅读并同意以上内容</el-checkbox>
                            </div>
                            <div style="text-align: center;">
                                <el-button type="success" @click="next" :disabled="!read">下一步</el-button>
                            </div>
                            
                        </div>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>

    </div>

</template>
<script>
import request from '@/utils/request'
export default {
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : '',
            activeName: 'first',
            isDisabled: true,
            form: '',
            cascaderData: [],
            value: '',
            form1: {
                oldPw: '',
                newPw: '',
                confirmPw: '',
                userId: ''

            },
            read:false,
        }
    },
    created() {

    },

    mounted() {
        this.initCascader();
        this.form = JSON.parse(JSON.stringify(this.user));
    },
    methods: {
        initCascader() {
            request.get("/address/map").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.cascaderData = res.data;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        async handleChange(value) {
            let address = '';
            // console.log(value);
            if (value != null) {
                for (let i = 0; i < value.length; i++) {
                    const element = value[i];
                    // console.log(element);
                    await request.get('/address/' + element).then(res => {
                        if (res.code === '0') {
                            // console.log(res.data);
                            address = address + res.data + '/'
                        } else {
                            this.$message.error(res.msg);
                        }
                    })
                }
                // console.log(address);
                this.form.address = address;
            }

        },
        edit() {
            this.isDisabled = false;
        },
        submit() {
            request.post("/consumer", this.form).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.user = this.form;
                    console.log(this.user);
                    this.isDisabled = true;
                    localStorage.setItem("user", JSON.stringify(this.user));

                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        cancel() {
            this.form = JSON.parse(JSON.stringify(this.user));
            this.value = [];
            this.isDisabled = true;
        },
        submit1() {
            // console.log(this.form1);
            if (this.form1.oldPw == '' || this.form1.newPw == '' || this.form1.confirmPw == '') {
                this.$message({
                    message: '请输入完整信息',
                    type: 'warning'
                });
                return;
            }
            if (this.form1.oldPw == this.form1.newPw) {
                this.$message({
                    message: '新密码与原密码相同',
                    type: 'warning'
                });
                return;
            }
            if (this.form1.newPw != this.form1.confirmPw) {
                this.$message({
                    message: '两次输入的密码不一致',
                    type: 'warning'
                });
                return;
            }
            this.form1.userId = this.user.id;
            request.post('/consumer/changePassword', this.form1).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '修改成功，请重新登录！！！',
                        type: 'success'
                    });
                    this.form1 = {};
                    localStorage.removeItem("user");
                    this.$router.push('/login');
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })

        },
        uploadAvatar(res) {
            // console.log(res);
            this.user.avatar = res.data;
            localStorage.setItem("user", JSON.stringify(this.user));
            request.post('/consumer', this.user).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '上传成功',
                        type: 'success'
                    });
                    window.location.reload();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })


        },
        next(){
            // this.$message({
            //     message: '功能尚未开放，敬请期待',
            //     type: 'warning'
            // })
            this.$router.push('/agreement');
        },
    }


}
</script>
<style scoped>
.container {
    max-width: 800px;
    margin: auto;
    padding: 20px;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1,
h2,
h3 {
    color: #333;
}

h1 {
    text-align: center;
}

.section {
    margin-bottom: 20px;
}

.section:last-child {
    margin-bottom: 0;
}

p {
    margin-bottom: 15px;
}

ul {
    padding-left: 20px;
}

ul li {
    margin-bottom: 10px;
}

ul li:last-child {
    margin-bottom: 0;
}
</style>