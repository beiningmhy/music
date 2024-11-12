<template>
    <div style="max-height: 86vh;overflow: auto;">
        <div style="width: 30%;margin: 20vh auto;margin-left: 30%;">
            <el-form :model="form" style="margin-left: -25%;">
                <el-form-item label="请输入原密码" label-width="40%" aria-required="true">
                    <el-input v-model="form.oldPw" autocomplete="off" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item label="请输入新密码" label-width="40%" aria-required="true">
                    <el-input v-model="form.newPw" autocomplete="off" style="width: 90%"></el-input>
                </el-form-item>
                <el-form-item label="请确认新密码" label-width="40%" aria-required="true">
                    <el-input v-model="form.confirmPw" autocomplete="off" style="width: 90%"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer"  style="text-align: center;margin-top: 30%;">
                <el-button type="primary" @click="submit()" >确 定 修 改</el-button>
            </div>
        </div>

    </div> 
</template>
<script>
import request from '@/utils/request';

export default {
    name: "ChangePassword",
    data() {
        return {
            form: {
                oldPw: '',
                newPw: '',
                confirmPw: '',
                userId: ''
            
            },
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        }
    },
    methods: {
        submit(){
            console.log(this.form);
            if(this.form.oldPw == '' || this.form.newPw == '' || this.form.confirmPw == ''){
                this.$message({
                    message: '请输入完整信息',
                    type: 'warning'
                });
                return;
            }
            if(this.form.oldPw==this.form.newPw){
                this.$message({
                    message: '新密码与原密码相同',
                    type: 'warning'
                });
                return;
            }
            if(this.form.newPw != this.form.confirmPw){
                this.$message({
                    message: '两次输入的密码不一致',
                    type: 'warning'
                });
                return;
            }
            this.form.userId=this.user.id;
            request.post('/admin/changePassword',this.form).then(res => {
                if(res.code ==='0'){
                    this.$message({
                        message: '修改成功，请重新登录！！！',
                        type: 'success'
                    });
                    this.form={};
                    localStorage.removeItem("user");
                    this.$router.push('/login');
                }else{
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
            
        }
    },
    created() {

    },

}
</script>