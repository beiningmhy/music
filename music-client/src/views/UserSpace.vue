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
            this.user.avatar=res.data;
            localStorage.setItem("user", JSON.stringify(this.user));
            request.post('/consumer',this.user).then(res=>{
                if(res.code==='0'){
                    this.$message({
                        message: '上传成功',
                        type: 'success'
                    });
                    window.location.reload();
                }else{
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