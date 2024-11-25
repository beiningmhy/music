<template>
    <div style="max-height: 89vh;overflow: auto;" class="agreement">
        <el-backtop target=".agreement" :visibility-height="10" :bottom="120"></el-backtop>
        <div>
            <div style="text-align: center;">
                <h1>创作者认证中心</h1>
            </div>
            <el-divider></el-divider>

            <div style="width: 90%;margin: 0 auto;">
                <el-steps :active="active" finish-status="success">
                    <el-step title="步骤 1"> </el-step>
                    <el-step title="步骤 2"> </el-step>
                    <el-step title="步骤 3"> </el-step>
                </el-steps>
                <el-button style="margin-top: 12px;" @click="next">下一步</el-button>

                <div>
                    <div v-if="active == 0" style="text-align: center;">
                        <h2>认证成为音乐创作者，需要支付10000用户积分</h2>
                        <h3>当前用户积分为：{{ user.point }}</h3>
                        <h3 v-if="user.point >= 10000">满足当前要求可以进行下一步</h3>
                        <h3 v-else>不满足当前要求，请充值后再进行下一步</h3>
                        <el-button style="margin-top: 12px;" @click="next"
                            :disabled="user.point < 10000">下一步</el-button>

                    </div>
                    <div v-else-if="active == 1" style="text-align: center;">
                        <div>
                            歌手名：<el-tag type="success">{{user.username}}</el-tag>
                        </div>

                        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
                    </div>
                    <div v-else-if="active == 2">
                        312312
                        <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
                    </div>
                </div>
            </div>

        </div>

        <div style="height: 15vh;">

        </div>
    </div>
</template>
<script>
export default {
    data() {
        return {
            active: 0,
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : '',
        }
    },

    methods: {
        next() {
            if (this.active++ > 2) this.active = 0;
        }
    },
    mounted() {

    },
    created() {

    }


}
</script>
<style scoped></style>