<template>
    <div style="max-height: 85vh;overflow: auto;" class="main">
        <!-- 轮播图 -->
        <div>
            <el-carousel :interval="4000" type="card" height="200px">
                <el-carousel-item v-for="item in 6" :key="item">
                    <h3 class="medium">{{ item }}</h3>
                </el-carousel-item>
            </el-carousel>
        </div>
        <!-- 歌手 -->
        <div>
            <h1 style="font-size:25px;">歌手 ></h1>
            <div style="display: flex;flex-wrap: wrap;">
                <div v-for="item in singer" :key="item.id" :label="item.pic" :value="item.id" @click="goSinger(item)"
                    style="flex-direction: row; margin-left: 20px;margin-bottom: 20px;">
                    <el-image style="width: 100px; height: 100px; border-radius: 50% ;"
                        :src="'http://localhost:8080/api/files/' + item.pic">
                    </el-image>
                </div>

            </div>
            <el-pagination @current-change="handleCurrentChange1" background layout="prev, pager, next" :total="total1"
                :page-size="params1.pageSize" :current-page="params1.pageNum">
            </el-pagination>

        </div>
        <!-- 歌单 -->
        <div>
            <h1 style="font-size:25px;">歌单 ></h1>
            <div style="display: flex;flex-wrap: wrap;">
                <div v-for="item in songList" :key="item.id" :label="item.pic" :value="item.id" @click="goSinger(item)"
                    style="flex-direction: row; margin-left: 20px;margin-bottom: 20px;">
                    <el-image style="width: 100px; height: 100px; border-radius: 50% ;"
                        :src="'http://localhost:8080/api/files/' + item.pic">
                    </el-image>
                </div>

            </div>
            <el-pagination @current-change="handleCurrentChange2" background layout="prev, pager, next" :total="total2"
                :page-size="params2.pageSize" :current-page="params2.pageNum">
            </el-pagination>

        </div>
    </div>
</template>
<script>
import request from '@/utils/request';

export default {
    data() {
        return {
            singer: [],
            songList:[],
            params1: {
                pageSize: 10,
                pageNum: 1,
            },
            params2: {
                pageSize: 10,
                pageNum: 1,
            },
            total1: 0,
            total2: 0,

        }
    },

    methods: {
        async loadSinger() {
            await request.get("/singer/search", {
                params: this.params1

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.singer = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                        // birth: new Date(item.birth), 
                    }));
                    // console.log(this.tableData);

                    this.total1 = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        async loadSongList() {
            await request.get("/songList/search", {
                params: this.params2

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.songList = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 100) + '...' : item.introduction,
                        // birth: new Date(item.birth), 
                    }));
                    // console.log(this.tableData);

                    this.total2 = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        handleCurrentChange1(pageNum) {
            this.params1.pageNum = pageNum;
            this.loadSinger();
        },
        handleCurrentChange2(pageNum) {
            this.params2.pageNum = pageNum;
            this.loadSongList();
        },
        goSinger(item) {
            console.log(item);

        },
    },
    created() {
        this.loadSinger();
        this.loadSongList();
    }
}
</script>
<style>
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}
.main{
    max-height: 90vh;
    overflow: auto;
    scrollbar-width: none;
    /* 隐藏滚动条 */
    -ms-overflow-style: none;
    /* 隐藏 IE 和 Edge 的滚动条 */
    overflow: -moz-scrollbars-none;
    /* 隐藏 Firefox 的滚动条 */
}
</style>