<template>
    <div style="max-height: 89vh;overflow: auto;" class="product">
        <el-backtop target=".product" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>

        <div>
            <div class="product-title" style="width: 100%;text-align: center;position: relative;">

                <el-tooltip class="item" effect="dark" content="本商城所有物品均由积分兑换" placement="bottom">
                    <h1 style="font-size: 25px;">MUSIC商城</h1>
                </el-tooltip>
                <div style="position: absolute;right: 20px;top: 10px;" @click="initProduct">
                    <svg t="1732342346761" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="2352" width="20" height="20">
                        <path
                            d="M896 198.4 896 198.4l0 179.2 0 0c0 19.2-6.4 32-19.2 44.8-12.8 12.8-32 19.2-44.8 19.2l0 0-179.2 0 0 0c-19.2 0-32-6.4-44.8-19.2-25.6-25.6-25.6-64 0-89.6C620.8 320 633.6 313.6 652.8 313.6l0 0 25.6 0C627.2 275.2 576 256 518.4 256 441.6 256 377.6 281.6 332.8 332.8l0 0c-25.6 25.6-64 25.6-89.6 0-25.6-25.6-25.6-64 0-89.6l0 0C313.6 172.8 409.6 128 518.4 128c96 0 185.6 38.4 249.6 96L768 198.4l0 0c0-19.2 6.4-32 19.2-44.8 25.6-25.6 64-25.6 89.6 0C889.6 160 896 179.2 896 198.4zM416 691.2c-12.8 12.8-32 19.2-44.8 19.2l0 0L352 710.4C396.8 748.8 448 768 505.6 768c70.4 0 134.4-25.6 179.2-76.8l0 0c25.6-25.6 64-25.6 89.6 0 25.6 25.6 25.6 64 0 89.6l0 0C710.4 851.2 614.4 896 505.6 896c-96 0-185.6-38.4-249.6-96l0 32 0 0c0 19.2-6.4 32-19.2 44.8-25.6 25.6-64 25.6-89.6 0C134.4 864 128 844.8 128 825.6l0 0 0-179.2 0 0c0-19.2 6.4-32 19.2-44.8C160 588.8 172.8 582.4 192 582.4l0 0 179.2 0 0 0c19.2 0 32 6.4 44.8 19.2C441.6 627.2 441.6 665.6 416 691.2z"
                            p-id="2353"></path>
                    </svg>
                </div>
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
                                <div v-else-if="item.amount <= 0" style="position: absolute;top: 0;left: 0;right: 0;height: 30px;
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
        if(!localStorage.getItem("user")){
            this.$router.push("/");
        }
        await this.initProduct();
    },
    created() {

    }

}
</script>
<style lang="less"></style>