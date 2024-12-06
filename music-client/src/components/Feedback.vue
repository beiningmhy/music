<template>
    <div>
        <div style="" class="feedback" @click="feedback()">
            点击向我们反馈
        </div>
        <div>
            <el-dialog title="请填写信息" :visible.sync="feedbackDialog" width="500px">
                <el-form :model="params">

                    <el-form-item label="类型" label-width="20%">
                        <el-radio-group v-model="params.type" size="medium">
                            <el-tooltip class="item" effect="dark" content="包括页面显示问题，页面内容错误问题" placement="top">
                                <el-radio label="页面问题" value="页面问题" size="medium">页面问题</el-radio>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="包括页面内容长时间加载失败，图片歌曲等资源加载问题"
                                placement="top">
                                <el-radio label="网络问题" value="网络问题" size="medium">网络问题</el-radio>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="包括商店物品购买问题，没有想要的商品等" placement="top">
                                <el-radio label="商店问题" value="商店问题" size="medium">商店问题</el-radio>
                            </el-tooltip>
                            <div style="height: 20px;"></div>
                            <el-tooltip class="item" effect="dark" content="包括歌曲歌词加载失败或错误，没有想要的歌曲等" placement="bottom">
                                <el-radio label="歌曲问题" value="歌曲问题" size="medium">歌曲问题</el-radio>
                            </el-tooltip>
                            <el-tooltip class="item" effect="dark" content="其他" placement="bottom">
                                <el-radio label="其他" value="其他" size="medium">其他</el-radio>
                            </el-tooltip>
                            <!-- <el-tooltip class="item" effect="dark" content="包括商店物品购买问题，没有想要的商品等" placement="right">
                                <el-radio :label="3" value="商店问题" size="medium">商店问题</el-radio>
                            </el-tooltip> -->
                        </el-radio-group>
                        <div style="height: 50px;"></div>
                    </el-form-item>
                    <el-form-item label="内容" label-width="20%" aria-required="true">
                        <el-input v-model="params.content" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="feedbackDialog = false">取 消</el-button>
                    <el-button type="primary" @click="submit()">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>
<script>
import request from "@/utils/request";
export default {
    data() {
        return {
            feedbackDialog: false,
            params: {
                content: "",
                type: ""
            }
        }
    },
    methods: {
        feedback() {
            this.params = {
                content: "",
                type: ""
            };
            this.feedbackDialog = true;
        },
        submit() {
            request.post("/feedback", this.params).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.feedbackDialog = false;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
    }
}
</script>
<style scoped>
.feedback {
    height: 30px;
    line-height: 30px;
    /* width: 20%; */
    margin: 0 auto;
    margin-top: 30px;
}

.feedback:hover {
    color: #c41fe4;
}
</style>