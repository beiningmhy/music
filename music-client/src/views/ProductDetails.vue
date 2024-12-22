<template>
    <div style="max-height: 89vh;overflow: auto;" class="productDetails">
        <el-backtop target=".productDetails" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>

        <div style="height: 74vh;width: 90%;margin: 0 auto;display: flex;">
            <div style="width: 300px;height: 100%;padding: 20px;display: flex;flex-direction: column;
                border-right: 1px solid #ccc;">
                <div
                    style="width: 260px;height: 260px;background-color: bisque;border-radius: 20px ;position: relative;">
                    <el-image style="width: 100%; height: 100%; border-radius: 20px ;"
                        :src="'http://localhost:8080/api/files/' + product.url">
                    </el-image>
                    <div v-if="product.sellStatus === '1'" style="position: absolute;top: 0;left: 0;right: 0;height: 30px;
                                    border-radius: 10px ;backdrop-filter: blur(30px);">
                        <div style="text-align: center;color: red;font-size: 20px;">
                            不 可 售
                        </div>
                    </div>
                    <div v-else-if="product.amount <= 0" style="position: absolute;top: 0;left: 0;right: 0;height: 30px;
                                    border-radius: 10px ;backdrop-filter: blur(30px);">
                        <div style="text-align: center;color: red;font-size: 20px;">
                            已 售 罄
                        </div>
                    </div>
                </div>
                <div>
                    <div style="margin-top: 10px;font-size: 20px;font-weight: 600;">{{ product.name }}</div>
                    <div style="margin-top: 10px;">价格:{{ product.price }}</div>
                    <div style="margin-top: 10px;">库存:{{ product.amount }}</div>
                    <div style="margin-top: 10px;">售卖时间:{{ product.sellTime }}</div>
                </div>
                <div style="width: 100%;">
                    <div style="margin-top: 10px;">购买数量:
                        <el-input-number v-model="num" :min="1" :max="product.max" label="描述文字"></el-input-number>
                    </div>
                    <div style="margin-top: 10px;">总价:{{ num * product.price }}</div>
                </div>
                <div style="margin-left: 150px;">
                    <!-- {{ product.sellStatus=='0' }} -->
                    <el-button :disabled="product.sellStatus == '0' ? false : true" type="primary"
                        @click="buy()">立即购买</el-button>
                </div>
            </div>
            <div style="position: relative;flex: 1;">
                <div style="height: 74vh;overflow: auto;">
                    <div v-html="this.product.content" class="w-e-text"></div>
                </div>
                <div v-if="!isBuy"
                    style="position: absolute;bottom: -1px;right: 0;top: 0;left: 0;backdrop-filter: blur(10px);">
                    <div style="width: 300px;margin: 0 auto;margin-top:100px;">
                        <span style="font-size: 50px;">请购买后查看</span>
                    </div>
                </div>
            </div>

        </div>
        <div style="height: 15vh;">

        </div>
    </div>
</template>
<script>
import request from '@/utils/request';

export default {
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : '',
            productId: this.$route.query.productId ? this.$route.query.productId : '',
            params: {
                pageNum: 1,
                pageSize: 1,
            },
            product: {},
            num: 1,
            totalPrice: 0,
            isBuy: false,
        }
    },

    methods: {
        initProductDetail() {
            this.params.productId = this.productId;
            request.get('/product/search', {
                params: this.params,
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res);
                    this.product = res.data.list[0];
                    this.total = res.data.total;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        initOrder() {
            if (!this.user) {
                return;
            }
            let p = {
                userId: this.user.id,
                productId: this.productId,
                pageNum: 1,
                pageSize: 1,
                status: '0',
            };


            request.get('/order/search', {
                params: p,
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res);
                    if (res.data.total !== 0) {
                        this.isBuy = true;
                    }
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        buy() {
            if (!this.user) {
                this.$message.error('请先登录');
                return;
            }
            let order = {
                userId: this.user.id,
                productId: this.productId,
                num: this.num,
                price: this.product.price,
            }
            request.post('/order', order).then(res => {
                if (res.code === '0') {
                    console.log(res);
                    this.$message.success('购买成功');
                    setTimeout(() => {
                        window.location.reload();
                    }, 1000);

                } else {
                    this.$message.error(res.msg);
                }
            })
        },
    },
    async mounted() {
        if(!localStorage.getItem("user")){
            this.$router.push("/");
        }
        await this.initProductDetail();
        await this.initOrder();
    },
    created() {

    },
}
</script>
<style scoped></style>