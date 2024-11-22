<template>
    <div style="max-height: 89vh;overflow: auto;" class="product">
        <el-backtop target=".product" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>

        <div>
            <div class="product-title" style="width: 100%;text-align: center;">

                <el-tooltip class="item" effect="dark" content="本商城所有物品均由积分兑换" placement="bottom">
                    <h1 style="font-size: 25px;">MUSIC商城</h1>
                </el-tooltip>
            </div>
            <hr>
            <div>
                <div class="product-main" style="width: 90%;margin: 0 auto;height: 55vh;overflow: auto;">
                    <div class="product-content"
                        style="width: 100%;height: 55vh;max-height:660px;display: flex;flex-wrap: wrap;">
                        <div v-for="item in productList" :key="item.id" style="width:25%;height: 300px;padding: 20px;"
                            @click="goToDetail(item.id)">
                            <div
                                style="width: 100%;height: 100%;border-radius: 20px;box-shadow: 5px 0px 10px rgba(0, 0, 0, 0.3);position: relative;">
                                <el-image style="width: 100%; height: 100%; border-radius: 20px ;"
                                    :src="'http://localhost:8080/api/files/' + item.url">
                                </el-image>
                                <div style="position: absolute;bottom: 0;left: 0;right: 0;
                                    height: 80px;border-radius: 10px ;backdrop-filter: blur(30px);">
                                    <div style="display: flex;">
                                        <div
                                            style="width: 35%;height:80px;padding-top: 10px;display: flex;flex-direction: column;">
                                            <div
                                                style="width: 100%;height: 30px;text-align: center;font-size: large;line-height: 30px;overflow: hidden;">
                                                {{ item.name }}
                                            </div>
                                            <div
                                                style="width: 100%;height: 30px;text-align: center;color:white;overflow: hidden;line-height: 30px;">
                                                库存：{{ item.amount }}
                                            </div>
                                        </div>
                                        <div
                                            style="height: 80px;width: 65%;display: flex;flex-direction: column;padding-top: 10px;">

                                            <div
                                                style="width: 100%;height: 30px;text-align: center;font-size: large;line-height: 30px;overflow: hidden;">
                                                ￥<span style="color: red;">{{ item.price }}</span>
                                            </div>
                                            <div
                                                style="width: 100%;height: 30px;text-align: center;color:white;overflow: hidden;line-height: 30px;font-size: 10px;">
                                                售卖时间：{{ item.sellTime }}
                                            </div>


                                        </div>
                                    </div>
                                </div>
                                <div v-if="item.sellStatus === '1'" style="position: absolute;top: 0;left: 0;right: 0;height: 30px;
                                    border-radius: 10px ;backdrop-filter: blur(30px);">
                                    <div style="text-align: center;color: red;font-size: 20px;">
                                        不 可 售
                                    </div>
                                </div>
                                <div v-else-if="item.amount<=0" style="position: absolute;top: 0;left: 0;right: 0;height: 30px;
                                    border-radius: 10px ;backdrop-filter: blur(30px);">
                                    <div style="text-align: center;color: red;font-size: 20px;">
                                        已 售 罄
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="width: 90%;margin: 0 auto;margin-top: 10px;">
                    <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next"
                        :total="total" :page-size="params.pageSize" :current-page="params.pageNum">
                    </el-pagination>
                </div>

            </div>
        </div>


        <div style="height: 12vh;">

        </div>
    </div>
</template>
<script>
import request from '@/utils/request';
import E from 'wangeditor';
export default {
    data() {
        return {
            productList: [],
            params: {
                pageSize: 8,
                pageNum: 1,
                status: 0,
            },
            total: 0,
        }
    },
    methods: {
        initProduct() {
            request.get('/product/search', {
                params: this.params,
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res);
                    this.productList = res.data.list;
                    this.total = res.data.total;
                } else {
                    this.$message.error(res.msg);
                }
            })
        },
        handleCurrentChange(pageNum) {
            this.params.pageNum = pageNum;
            this.initProduct();
        },
        goToDetail(id) {
            this.$router.push({
                path: '/productDetails',
                query: {
                    productId: id
                }
            })
        }
    },
    async mounted() {
        await this.initProduct();
    },
    created() {

    }

}
</script>
<style lang="less"></style>