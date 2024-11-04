<template>
    <div style="max-height: 85vh;overflow: auto;" class="detail">
        <el-backtop target=".detail" :visibility-height="10"></el-backtop>
        <!-- 头部歌手详情介绍 -->
        <div style="height: 250px;width: 80%;margin:0 auto ;">
            <el-skeleton style="width: 100%" :loading="loading" animated>
                <template slot="template">

                    <div style="display: flex;height: 250px;width: 100%;">
                        <!-- 歌手照片 -->
                        <div style="width:250px; height: 250px;">
                            <el-skeleton-item variant="image" style="width:250px; height: 250px;" />

                        </div>
                        <!-- 歌手详细介绍 -->
                        <div
                            style=" height: 250px;width: 100px;flex: 1;display: flex;flex-direction: column;margin-left: 20px;">
                            <div style="height: 60px;padding-top: 10px;padding-left: 100px;">
                                <el-skeleton-item variant="h1" style="width: 200px;height: 40px;" />
                            </div>
                            <div style="height: 120px;overflow: hidden;flex: 1;">
                                <el-skeleton :rows="5" />
                            </div>
                            <div style="height: 50px;">
                                <el-skeleton-item variant="button" style="margin-right: 16px;width: 100px;" />
                            </div>
                        </div>
                    </div>
                </template>
                <template>
                    <div style="display: flex;height: 250px;width: 100%;">
                        <!-- 歌手照片 -->
                        <div style="width:250px; height: 250px;">
                            <el-image style="width:250px; height: 250px;border-radius: 10% ;"
                                :src="'http://localhost:8080/api/files/' + singer.pic"
                                :preview-src-list="['http://localhost:8080/api/files/' + singer.pic]">
                            </el-image>

                        </div>
                        <!-- 歌手详细介绍 -->
                        <div
                            style=" height: 250px;width: 100px;flex: 1;display: flex;flex-direction: column;margin-left: 20px;">
                            <div style="height: 60px;padding-top: 10px;padding-left: 100px;">
                                <span style="font-size: 30px;">{{ singer.name }}</span>
                            </div>
                            <div style="height: 120px;overflow: hidden;flex: 1;" @click="introductionDialog = true">
                                <span style="font-size: 16px;">{{ singer.introductions }}</span>
                            </div>
                            <div style="height: 50px;">
                                <el-button type="primary" style="margin: 0 auto;">播放全部</el-button>
                            </div>
                        </div>
                    </div>

                </template>
            </el-skeleton>
        </div>
        <!-- 歌手歌曲 -->
        <div>
            <div>
                <h1 style="font-size:25px;">歌曲 ></h1>
            </div>
            <div>
                <el-pagination @current-change="handleCurrentChange2" background layout="prev, pager, next"
                    :total="total2" :page-size="params2.pageSize" :current-page="params2.pageNum">
                </el-pagination>
                <el-table v-loading="songLoading" :data="tableData" style="width: 100%;background-color: transparent ;">
                    <el-table-column prop="name" label="歌曲" width="400">
                        <template v-slot="scope">
                            <div style="display: flex;">
                                <div>
                                    <el-image style="width: 30px; height: 30px; border-radius: 10%"
                                        :src="'http://localhost:8080/api/files/' + scope.row.pic"
                                        :preview-src-list="['http://localhost:8080/api/files/' + scope.row.pic]">
                                    </el-image>
                                </div>
                                <div style="text-align: center;display: inline-block;height: 30px;line-height: 30px;">
                                    <span style="margin-left: 10px;font-weight: 600;font-size: 15px;color: black;">
                                        {{ scope.row.name }}</span>
                                </div>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="singerName" label="歌手">
                    </el-table-column>
                    <el-table-column prop="introductions" label="专辑">
                    </el-table-column>
                    <el-table-column prop="audioDuration" label="时长">
                    </el-table-column>
                </el-table>
                <el-pagination @current-change="handleCurrentChange2" background layout="prev, pager, next"
                    :total="total2" :page-size="params2.pageSize" :current-page="params2.pageNum">
                </el-pagination>
            </div>
        </div>
        <div>
            <div>
                <h1 style="font-size:25px;">评论区 ></h1>
            </div>
            <div>这里是发表评论的地方，尚未开发</div>
            <!-- 所有评论 -->
            <div>
                <div v-if="commentList.length == 0">
                    <div style="width: 60%;height: 80px;margin: 0 auto;text-align: center;">
                        <h1>暂无评论</h1>
                    </div>

                </div>
                <div v-else>
                    <div v-for="item in commentList" :key="item.id">
                        <div style="display: flex;width: 60%;height: 80px;margin: 0 auto;">
                            <div style="width: 100px;">
                                <!-- 头像 -->
                                <el-image style="width: 60px; height: 60px; border-radius: 10%;margin-top: 10px;"
                                    :src="'http://localhost:8080/api/files/' + item.userAvatar"
                                    :preview-src-list="['http://localhost:8080/api/files/' + item.userAvatar]">
                                </el-image>
                            </div>
                            <div style="flex: 1;display: flex;flex-direction: column;">
                                <div style="flex: 1;overflow: hidden;padding-top: 5px;padding-left: 5px;">
                                    <span style="font-size: 20px;color: blue;">{{ item.username }}</span>
                                    <span>
                                        :{{ item.content }}
                                    </span>
                                </div>
                                <div style="height: 30%;">
                                    <div style="float: left;">
                                        {{ item.createTime }}
                                    </div>
                                    <div style="float: right;display: flex;">
                                        <div @click="up(item)" style="margin-right: 10px;">
                                            <svg t="1728798081711" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                                xmlns="http://www.w3.org/2000/svg" p-id="1477" width="20" height="20">
                                                <path
                                                    d="M896 341.333333a42.666667 42.666667 0 0 1 41.642667 51.925334l-85.333334 469.333333A42.666667 42.666667 0 0 1 810.666667 896h-298.666667c-8.448 0-16.682667-2.517333-23.68-7.168L328.405333 810.666667H128a42.666667 42.666667 0 0 1-42.666667-42.666667V384a42.666667 42.666667 0 0 1 42.666667-42.666667h189.141333l158.250667-235.306666A42.666667 42.666667 0 0 1 512 85.333333h21.333333C592.170667 85.333333 640 170.666667 640 192L682.666667 341.333333h213.333333z m-371.114667 469.333334h251.562667l66.346667-384H640a42.666667 42.666667 0 0 1-42.666667-42.666667l-42.666666-192a21.418667 21.418667 0 0 0-18.645334-21.162667L384 395.818667v349.354666L524.885333 810.666667zM298.666667 426.666667H170.666667v298.666666h128v-298.666666z"
                                                    fill="#6CCAFF" p-id="1478"></path>
                                            </svg>
                                            <el-badge :value="item.up" class="item">
                                            </el-badge>
                                        </div>
                                        <div @click="down(item)">
                                            <svg t="1728798866603" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                                xmlns="http://www.w3.org/2000/svg" p-id="1641" width="20" height="20">
                                                <path
                                                    d="M128 682.666667a42.666667 42.666667 0 0 1-41.642667-51.925334l85.333334-469.333333A42.666667 42.666667 0 0 1 213.333333 128h341.333334c8.448 0 16.64 2.517333 23.68 7.168L695.594667 213.333333H896a42.666667 42.666667 0 0 1 42.666667 42.666667v384a42.666667 42.666667 0 0 1-42.666667 42.666667h-189.141333l-158.293334 235.306666A42.666667 42.666667 0 0 1 512 938.666667h-21.333333C431.786667 938.666667 384 853.333333 384 832L341.333333 682.666667H128z m413.781333-469.333334H247.552l-66.346667 384H384a42.666667 42.666667 0 0 1 42.666667 42.666667l42.666666 192c0 10.837333 8.106667 19.797333 18.645334 21.162667L640 628.181333V278.826667L541.781333 213.333333zM725.333333 597.333333h128V298.666667h-128v298.666666z"
                                                    fill="#d81e06" p-id="1642"></path>
                                            </svg>
                                            <el-badge :value="item.down" class="item">
                                            </el-badge>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <el-pagination @current-change="handleCurrentChange3" background layout="prev, pager, next"
                        :total="total3" :page-size="params3.pageSize" :current-page="params3.pageNum">
                    </el-pagination>
                </div>


            </div>
        </div>

        <el-dialog :title="singer.name" :visible.sync="introductionDialog" width="30%"
            style="text-align: center;border-radius: 30% !important;">
            <div style="text-align: left;">
                <span>{{ singer.introduction }}</span>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button @click="introductionDialog = false">取 消</el-button>
                <el-button type="primary" @click="introductionDialog = false">确 定</el-button>
            </span>
        </el-dialog>

    </div>

</template>
<script>
import request from '@/utils/request';

export default {
    data() {
        return {
            singerId: this.$route.query.singerId ? this.$route.query.singerId : '',
            params: {
                pageNum: 1,
                pageSize: 1,
            },
            params2: {
                pageNum: 1,
                pageSize: 10,
            },
            params3: {
                pageNum: 1,
                pageSize: 10,
            },
            loading: true,
            singer: [],
            introductionDialog: false,
            total2: 0,
            tableData: [],
            commentList: [],
            total3: 0,
            songLoading: true,

        }
    },
    // props: {
    //     singer: {
    //         type: Object,
    //         required: true
    //     }
    // },
    created() {
        this.initSingerDetail();
        this.initSongs();
        this.initComments();
    },

    methods: {
        async initSingerDetail() {
            this.params.singerId = this.singerId;
            await request.get("/singer/search", {
                params: this.params
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data)
                    this.loading = false;
                    let temp = [];
                    temp = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        introductions: item.introduction != null && item.introduction.length > 200 ? item.introduction.substring(0, 200) + '...' : item.introduction,
                        // birth: new Date(item.birth), 
                    }));

                    this.singer = temp[0];
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        async initSongs() {
            this.params2.singerId = this.singerId;
            try {
                const res = await request.get("/song/search", {
                    params: this.params2
                });

                if (res.code === '0') {
                    // console.log(res.data);
                    this.loading = false;

                    // 获取数据列表
                    const data = res.data.list;

                    // 异步处理每个项目的音频时长
                    this.tableData = await Promise.all(
                        data.map(async item => {
                            let audioUrl = '';
                            if (item.url.includes('|')) {
                                audioUrl = 'http://localhost:8080/api/files/' + item.url;
                            } else {
                                audioUrl = item.url;
                            }

                            try {
                                const audioDuration = await this.getAudioDuration(audioUrl);
                                return {
                                    ...item,
                                    sts: item.status === '0',
                                    introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                                    audioDuration: this.formatDuration(audioDuration)
                                };
                            } catch (error) {
                                // console.error(`Failed to load audio file for item ${item.id}:`, error);
                                return {
                                    ...item,
                                    sts: item.status === '0',
                                    introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                                    audioDuration: null // 或者设置为其他默认值
                                };
                            }
                        })
                    );

                    this.total2 = res.data.total;
                    this.songLoading = false;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            } catch (error) {
                console.error('Error fetching data:', error);
                this.$message({
                    message: '请求失败',
                    type: 'error'
                });
            }
        },
        async initComments() {
            this.params3.radioId = this.singerId;
            this.params3.radio = '歌手';
            await request.get("/comment/search", {
                params: this.params3
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data)
                    this.loading = false;
                    let temp = [];
                    temp = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                    }))
                    this.commentList = temp;
                    this.total3 = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        getAudioDuration(url) {
            return new Promise((resolve, reject) => {
                const audio = new Audio(url);
                audio.addEventListener('loadedmetadata', () => {
                    resolve(Math.floor(audio.duration));
                });
                audio.addEventListener('error', (event) => {
                    // console.error('Audio error event:', event);
                    reject(new Error('Failed to load audio file'));
                });
            });
        },
        formatDuration(seconds) {
            if (seconds === null || seconds === undefined) {
                return '未知';
            }
            const minutes = Math.floor(seconds / 60);
            const remainingSeconds = seconds % 60;
            return `${minutes}:${remainingSeconds < 10 ? '0' : ''}${remainingSeconds}`;
        },

        handleCurrentChange2(pageNum) {
            this.params2.pageNum = pageNum;
            this.initSongs();
        },
        handleCurrentChange3(pageNum) {
            this.params2.pageNum = pageNum;
            this.initComments();
        },
        up(item) {
            // console.log(item);
            request.post("/comment/up", item).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '点赞成功',
                        type: 'success',
                        duration: 500
                    });
                    this.initComments();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        down(item) {
            request.post("/comment/down", item).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '反对成功',
                        type: 'warning',
                        duration: 500
                    });
                    this.initComments();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
    },

}
</script>
<style scoped>
.detail {
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