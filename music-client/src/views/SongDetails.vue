<template>
    <div style="max-height:89vh;overflow: auto;" class="detail">
        <el-backtop target=".detail" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>
        <!-- {{ this.songId }} -->
        <div style="height: 250px;width: 80%;margin:0 auto ;">
            <el-skeleton style="width: 100%;" :loading="loading" animated>
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
                                :src="'http://localhost:8080/api/files/' + song.pic"
                                :preview-src-list="['http://localhost:8080/api/files/' + song.pic]">
                            </el-image>

                        </div>
                        <!-- 歌手详细介绍 -->
                        <div
                            style=" height: 250px;width: 100px;flex: 1;display: flex;flex-direction: column;margin-left: 20px;">
                            <div style="height: 60px;padding-top: 10px;padding-left: 15%;overflow: hidden;">
                                <span style="font-size: 30px;height: 60px;line-height: 60px;">歌曲：{{ song.name }}</span>
                            </div>
                            <div @click="goSinger(singer)"
                                style="height: 40px;padding-top: 10px;padding-left: 15%;overflow: hidden;line-height: 40px;">
                                <span style="font-size: 20px;">歌手：{{ singer.name }}</span>
                            </div>
                            <div style="height: 60px;overflow: hidden;flex: 1;padding-left: 15%"
                                @click="introductionDialog = true">
                                <span style="font-size: 16px;height: 60px;line-height: 60px;">专辑：{{ song.introductions
                                    }}</span>
                            </div>
                            <div
                                style="height: 60px;padding-top: 10px;padding-left: 15%;overflow: hidden;line-height: 40px;">
                                <span style="font-size: 20px;height: 60px;line-height: 60px;">时长：{{ song.audioDuration
                                    }}</span>
                            </div>

                            <div style="display: flex;">
                                <div style="height: 50px;padding-left: 15%">
                                    <el-button type="primary" style="margin: 0 auto;"
                                        @click="play(song)">点击播放</el-button>
                                </div>
                                <div style="height: 50px;">
                                    <el-button type="primary" style="margin: 0 auto;margin-left: 30px;"
                                        @click="addSong2Collect()">添加至收藏</el-button>
                                </div>
                            </div>
                        </div>
                    </div>

                </template>
            </el-skeleton>
        </div>
        <!-- 歌词列表 -->
        <div>
            <div>
                <h1 style="font-size:25px;">歌词 ></h1>
            </div>
            <div v-if="lyricList.length == 0">
                <div style="width: 60%;height: 80px;margin: 0 auto;text-align: center;">
                    <h1>暂无歌词</h1>
                </div>
            </div>
            <div v-else>

                <div style="width: 60%;max-height: 300px;margin: 0 auto;;
                    border-radius: 20px;">
                    <div style="width: 100%;max-height: 300px;margin: 0 auto;overflow-y: auto;">
                        <div v-for="item in lyricList" :key="item.id" class="lyricDiv">
                            <div style="display: flex;width: 60%;margin: 0 auto;">
                                <div
                                    style="width: 80px;height: 40px;line-height: 40px;text-align: center;overflow: hidden;">
                                    {{ item.time }}
                                </div>
                                <div style="flex: 1;height: 40px;line-height: 40px;margin-left: 20px;overflow: hidden;">
                                    {{ item.lyric }}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div>
            <div>
                <h1 style="font-size:25px;">评论区 ></h1>
            </div>
            <div>
                <!-- 这里是发表评论的地方，尚未开发 -->
                <div class="container">
                    <!-- <label for="styled-textarea">请输入您的文本:</label> -->
                    <textarea id="styled-textarea" name="styled-textarea" rows="10" cols="50" placeholder="在这里输入您的评论..."
                        v-model="comment"></textarea>
                    <div>
                        <el-button type="primary" style="margin: 0 auto; margin-top: 10px;"
                            @click="publishComment()">发表评论</el-button>
                    </div>
                </div>

            </div>
            <!-- 所有评论 -->
            <div style="margin-top: 10px;">
                <div v-if="commentList.length == 0">
                    <div style="width: 60%;height: 80px;margin: 0 auto;text-align: center;">
                        <h1>暂无评论</h1>
                    </div>

                </div>
                <div v-else>
                    <div v-for="item in commentList" :key="item.id">
                        <div
                            style="display: flex;width: 60%;height: 80px;margin: 0 auto;border-top: 1px solid rgb(162 162 162);">
                            <div style="width: 100px;">
                                <!-- 头像 -->
                                <el-image style="width: 60px; height: 60px; border-radius: 10%;margin-top: 10px;"
                                    :src="'http://localhost:8080/api/files/' + item.userAvatar"
                                    :preview-src-list="['http://localhost:8080/api/files/' + item.userAvatar]">
                                </el-image>
                            </div>
                            <div style="flex: 1;display: flex;flex-direction: column;">
                                <div style="flex: 1;overflow: hidden;padding-top: 5px;padding-left: 5px;"
                                    @click="commentForm = item; commentDialog = true;">
                                    <span style="font-size: 20px;color: blue;">{{ item.username }}:</span>
                                    <span style="white-space:pre-wrap;word-wrap: break-word;">
                                        {{ item.content }}
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
                    <el-pagination @current-change="handleCurrentChange2" background layout="prev, pager, next"
                        :total="total2" :page-size="params2.pageSize" :current-page="params2.pageNum">
                    </el-pagination>
                </div>


            </div>
        </div>

        <el-dialog :title="song.name" :visible.sync="introductionDialog" width="30%"
            style="text-align: center;border-radius: 30% !important;">
            <div style="text-align: left;">
                <span>{{ song.introduction }}</span>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button @click="introductionDialog = false">取 消</el-button>
                <el-button type="primary" @click="introductionDialog = false">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog :title="commentForm.username" :visible.sync="commentDialog" width="40%"
            style="text-align: center;border-radius: 30% !important;">

            <div style="text-align: left;">
                <div style="height: 100px;max-height: 200px">
                    评论内容：<br>
                    <div style="padding-left: 50px;">
                        <span style="white-space:pre-wrap;word-wrap: break-word;font-size: 20px;">
                            {{ commentForm.content }}</span>
                    </div>

                </div>

                <div style="height: 30%;">
                    <div style="float: left;">
                        {{ commentForm.createTime }}
                    </div>
                    <div style="float: right;display: flex;">
                        <div @click="up(commentForm)" style="margin-right: 10px;">
                            <svg t="1728798081711" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="1477" width="20" height="20">
                                <path
                                    d="M896 341.333333a42.666667 42.666667 0 0 1 41.642667 51.925334l-85.333334 469.333333A42.666667 42.666667 0 0 1 810.666667 896h-298.666667c-8.448 0-16.682667-2.517333-23.68-7.168L328.405333 810.666667H128a42.666667 42.666667 0 0 1-42.666667-42.666667V384a42.666667 42.666667 0 0 1 42.666667-42.666667h189.141333l158.250667-235.306666A42.666667 42.666667 0 0 1 512 85.333333h21.333333C592.170667 85.333333 640 170.666667 640 192L682.666667 341.333333h213.333333z m-371.114667 469.333334h251.562667l66.346667-384H640a42.666667 42.666667 0 0 1-42.666667-42.666667l-42.666666-192a21.418667 21.418667 0 0 0-18.645334-21.162667L384 395.818667v349.354666L524.885333 810.666667zM298.666667 426.666667H170.666667v298.666666h128v-298.666666z"
                                    fill="#6CCAFF" p-id="1478"></path>
                            </svg>
                            <el-badge :value="commentForm.up" class="item">
                            </el-badge>
                        </div>
                        <div @click="down(commentForm)">
                            <svg t="1728798866603" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="1641" width="20" height="20">
                                <path
                                    d="M128 682.666667a42.666667 42.666667 0 0 1-41.642667-51.925334l85.333334-469.333333A42.666667 42.666667 0 0 1 213.333333 128h341.333334c8.448 0 16.64 2.517333 23.68 7.168L695.594667 213.333333H896a42.666667 42.666667 0 0 1 42.666667 42.666667v384a42.666667 42.666667 0 0 1-42.666667 42.666667h-189.141333l-158.293334 235.306666A42.666667 42.666667 0 0 1 512 938.666667h-21.333333C431.786667 938.666667 384 853.333333 384 832L341.333333 682.666667H128z m413.781333-469.333334H247.552l-66.346667 384H384a42.666667 42.666667 0 0 1 42.666667 42.666667l42.666666 192c0 10.837333 8.106667 19.797333 18.645334 21.162667L640 628.181333V278.826667L541.781333 213.333333zM725.333333 597.333333h128V298.666667h-128v298.666666z"
                                    fill="#d81e06" p-id="1642"></path>
                            </svg>
                            <el-badge :value="commentForm.down" class="item">
                            </el-badge>
                        </div>
                    </div>
                </div>
            </div>

            <span slot="footer" class="dialog-footer">
                <!-- <el-button @click="commentDialog = false">取 消</el-button> -->
                <el-button type="primary" @click="commentDialog = false">关闭</el-button>
            </span>
        </el-dialog>
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
            songId: this.$route.query.songId ? this.$route.query.songId : '',
            song: {},
            params: {
                pageNum: 1,
                pageSize: 1,
            },
            params2: {
                pageNum: 1,
                pageSize: 5,
            },
            total2: 0,
            loading: true,
            commentList: [],
            introductionDialog: false,
            singer: {},
            lyricList: [],
            comment: '',
            commentDialog: false,
            commentForm: {},
        }
    },
    created() {


    },
    async mounted() {
        await this.initSongDetail();
        await this.initComments();
        await this.initLyric();
    },
    methods: {
        async initSongDetail() {
            // console.log(songId);
            this.params.singerId = '';
            this.params.songId = this.songId;
            try {
                const res = await request.get("/song/search", {
                    params: this.params
                });

                if (res.code === '0') {
                    // console.log(res.data);


                    // 获取数据列表
                    const data = res.data.list;
                    let temp = [];
                    // 异步处理每个项目的音频时长
                    temp = await Promise.all(
                        data.map(async item => {
                            let audioUrl = '';
                            if (item.url != null) {
                                if (item.url.includes('|')) {
                                    audioUrl = 'http://localhost:8080/api/files/' + item.url;
                                } else {
                                    audioUrl = item.url;
                                }
                            } else {
                                audioUrl = '';

                            }

                            try {
                                const audioDuration = await this.getAudioDuration(audioUrl);
                                return {
                                    ...item,
                                    sts: item.status === '0',
                                    introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                                    audioDuration: this.formatDuration(audioDuration),
                                    audioDurationSeconds: audioDuration,
                                    audioUrl: audioUrl,
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

                    // this.total = res.data.total;
                    // this.songLoading = false;

                    this.song = temp[0];
                    // console.log(this.song);
                    await this.initSinger(this.song.singerId);
                    this.loading = false;
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
        initLyric() {
            // console.log(this.songId);

            request.get("/song/lyric/" + this.songId).then(res => {
                if (res.code === '0') {
                    this.lyricList = res.data;
                    // console.log(res.data);

                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }

            })
        },
        async initSinger(singerId) {
            this.params.songId = '';
            this.params.singerId = singerId;
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
                    // console.log(this.singer);

                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        async initComments() {
            this.params2.radioId = this.songId;
            this.params2.radio = '歌曲';
            await request.get("/comment/search", {
                params: this.params2
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
                    this.total2 = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        handleCurrentChange2(pageNum) {
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
        publishComment() {
            // console.log(this.comment);
            if (this.comment == '') {
                this.$message({
                    message: '评论不能为空',
                    type: 'error'
                });
                return;
            }
            let c = {
                songId: this.songId,
                content: this.comment,
            }
            if (this.user == '') {
                c.userId = 0;
            } else {
                c.userId = this.user.id;
            }
            request.post("/comment/save", c).then(res => {
                if (res.code == '0') {
                    this.$message({
                        message: '评论成功',
                        type: 'success'
                    });
                    this.comment = '';
                    this.initComments();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        play(item) {
            this.$store.commit('updateIsplay', false);
            localStorage.setItem("playingMusic", JSON.stringify(item));
            // 首先从 localStorage 获取现有的 musicList
            let musicList = localStorage.getItem('musicList');

            // 将获取到的字符串转换为数组
            if (musicList) {
                musicList = JSON.parse(musicList);
            } else {
                // 如果 musicList 不存在，则初始化为空数组
                musicList = [];
            }

            // 检查是否存在相同的 item 并删除
            const index = musicList.findIndex(existingItem => existingItem.id === item.id);
            if (index !== -1) {
                // 删除已存在的 item
                musicList.splice(index, 1);
            }

            // 将新的 item 放在数组的最前面
            musicList.unshift(item);

            // 将更新后的数组转换回 JSON 字符串并保存到 localStorage
            localStorage.setItem('musicList', JSON.stringify(musicList));
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
        addSong2Collect(){
            let c = {
                songId: this.songId,
                userId: this.user.id
            }
            request.post("/collect", c).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '收藏成功',
                        type: 'success'
                    });
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    })
                }
            })
        },

    },
    watch: {
        // 监听$route对象的变化
        async '$route'(to, from) {
            // 当路由变化时，更新songId
            // console.log(to);

            this.songId = to.query.songId ? to.query.songId : '';
            // console.log(this.songId);

            await this.initSongDetail();
            await this.initComments();
            await this.initLyric();
        },


    }
}
</script>
<style scoped>
.lyricDiv :hover {
    background-color: rgba(231, 230, 230, 0.734);
    border-radius: 10px;
}

/* 容器样式 */
.container {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 200px;
    background-color: rgb(255, 255, 255);
    font-family: Arial, sans-serif;
}

/* 标签样式 */
label {
    font-size: 1.2em;
    color: #333;
    margin-bottom: 10px;
}

/* 文本域样式 */
textarea {
    width: 100%;
    max-width: 800px;
    height: 100px;
    max-height: 150px;
    padding: 15px;
    font-size: 1em;
    color: #333;
    border: 2px solid #ccc;
    border-radius: 8px;
    background-color: #fff;
    transition: border-color 0.3s ease, box-shadow 0.3s ease;
    resize: vertical;
    /* 允许垂直调整大小 */
}

/* 文本域聚焦时的样式 */
textarea:focus {
    border-color: #007BFF;
    box-shadow: 0 0 10px rgba(0, 123, 255, 0.5);
    outline: none;
}

/* 占位符样式 */
::placeholder {
    color: #aaa;
    opacity: 1;
}

/* 占位符聚焦时的样式 */
textarea:focus::placeholder {
    color: #ccc;
}
</style>