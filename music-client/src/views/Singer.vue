<template>
    <div style="max-height: 89vh;overflow: auto;" class="singer">
        <el-backtop target=".singer" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>
        <div style="display: flex;flex-direction: column;">
            <div>
                <!-- top10 -->
                <div>
                    <h1 style="font-size:25px;">TOP10歌手 ></h1>
                </div>
                <div style="display: flex;flex-wrap: wrap;">
                    <div v-for="item in singerTop" :key="item.id" :label="item.pic" :value="item.id"
                        @click="goSinger(item)"
                        style="flex-direction: row; margin-left: 20px;margin-bottom: 20px;overflow: hidden;width: 100px;height: 150px;">
                        <el-image style="width: 100px; height: 100px; border-radius: 50% ;"
                            :src="'http://localhost:8080/api/files/' + item.pic">
                        </el-image>
                        <div style="text-align: center;">
                            <span style="font-size: small;">{{ item.name }}</span>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <!-- 所有歌手 -->
                <h1 style="font-size:25px;">歌手 ></h1>
                <div style="display: flex;flex-wrap: wrap;">
                    <div v-for="item in singer" :key="item.id" :label="item.pic" :value="item.id"
                        @click="goSinger(item)"
                        style="flex-direction: row; margin-left: 20px;margin-bottom: 20px;overflow: hidden;width: 100px;height: 150px;">
                        <el-image style="width: 100px; height: 100px; border-radius: 50% ;"
                            :src="'http://localhost:8080/api/files/' + item.pic">
                        </el-image>
                        <div style="text-align: center;">
                            <span style="font-size: small;">{{ item.name }}</span>
                        </div>
                    </div>

                </div>
                <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next"
                    :total="total" :page-size="params.pageSize" :current-page="params.pageNum">
                </el-pagination>
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
            singerTop: [],
            params: {
                pageSize: 10,
                pageNum: 1,
            },
            singer: [],
            total: 0,
        }
    },
    created() {

    },
    async mounted() {
        await this.initTopSinger();
        await this.loadSinger();
    },

    methods: {
        async initTopSinger() {
            await request.get("/singer/top/" + 10).then(res => {
                // console.log(res);
                if (res.code === '0') {
                    this.singerTop = res.data.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                        // birth: new Date(item.birth), 
                    }));
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        async loadSinger() {
            await request.get("/singer/search", {
                params: this.params

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

                    this.total = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        handleCurrentChange(pageNum) {
            this.params.pageNum = pageNum;
            this.loadSinger();
        },
        goSinger(item) {
            // console.log(item);
            request.post("/singer/clicks", item).then(res => {
                if (res.code === '0') {

                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
            this.$router.push({
                path: '/singerDetails',
                query: {
                    singerId: item.id
                }
            })

        },


    },

}
</script>