<template>
    <div>
        <div style="display: flex; flex-wrap: nowrap;">




            <el-radio-group v-model="params.radio" style=" margin-right: 10px"
                @input="params.radioId = '', findBySearch()">
                <el-radio-button label="歌曲"></el-radio-button>
                <el-radio-button label="歌单"></el-radio-button>
            </el-radio-group>
            <el-select v-model="params.radioId" :placeholder="params.radio" style="width: 100px; margin-right: 10px"
                @input="findBySearch()" clearable filterable default-first-option>
                <el-option v-for="item in objs" :key="item.id" :label="item.option" :value="item.id"></el-option>
            </el-select>
            <el-button type="warning" @click="findBySearch()">搜索</el-button>
            <el-button type="warning" @click="reset()">清空</el-button>
            <!-- <el-button type="primary" @click="add()">新增</el-button> -->
            <div class="play-btn" @click="playAll()" v-if="params.radio == '歌曲'"
                style="display:flex;height: 30px;width: 120px;padding-top: 5px;padding-left: 10px;overflow: hidden;margin-left: 20px;">
                <div>
                    <svg t="1731298449347" class="icon" viewBox="0 0 1024 1024" version="1.1"
                        xmlns="http://www.w3.org/2000/svg" p-id="1457" width="20" height="20">
                        <path
                            d="M783.74 401.86L372.23 155.28c-85.88-51.46-195.08 10.41-195.08 110.53v493.16c0 100.12 109.2 161.99 195.08 110.53l411.51-246.58c83.5-50.04 83.5-171.03 0-221.06z"
                            p-id="1458"></path>
                    </svg>
                </div>
                <span style="margin-left: 10px;">播放全部</span>
            </div>
        </div>
        <div style="max-height: 66vh;overflow: auto;">
            <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="60vh" stripe highlight-current-row
                @row-dblclick="rowClick($event)">
                <!-- <el-table-column prop="id" label="序号" width="70" sortable></el-table-column> -->
                <el-table-column prop="username" label="收藏人" width="200"></el-table-column>
                <el-table-column prop="object" label="收藏对象"></el-table-column>


                <!-- <el-table-column prop="status" label="账号状态">
                    <template slot-scope="scope">

                        <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
                        <el-tag v-else-if="scope.row.status === '1'" type="danger">封禁</el-tag>
                        <el-tag v-else type="danger">状态未知</el-tag>
                    </template>
</el-table-column> -->
                <!-- <el-table-column prop="sts" label="收藏状态" width="100" fixed="right">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.sts" active-color="#13ce66" inactive-color="#ff4949"
                            @change="updateStatus(scope.row)">
                        </el-switch>
                    </template>
                </el-table-column> -->

                <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
                <el-table-column label="操作" width="100" fixed="right">
                    <template slot-scope="scope">
                        <div>
                            <!-- <el-button type="primary" @click="edit(scope.row)">编辑</el-button> -->
                            <template>
                                <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
                                    <el-button type="danger" slot="reference" style="margin-left: 5px">删除</el-button>
                                </el-popconfirm>
                            </template>
                        </div>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="block">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="params.pageNum" :page-sizes="[10, 15, 20, 50]" :page-size="params.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>

    </div>
</template>


<script>
import request from "@/utils/request";
export default {
    // 定义一些页面上控件触的事件调用的方法
    methods: {
        load() {
            request.get("/collect").then(res => {
                if (res.code === '0') {
                    console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch() {
            this.params.userId = this.user.id;
            this.params.status = '0';
            request.get("/collect/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.tableData = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        object: item.songName != null && item.songName != '' ? item.songName
                            : item.singerName != null && item.singerName != '' ? item.singerName
                                : item.songListTitle != null && item.songListTitle != '' ? item.songListTitle : '未知'
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
        reset() {
            this.params = {
                name: '',
                phone: '',
                pageNum: 1,
                pageSize: 15,
            }
            this.findBySearch();
            this.objs = [];
        },
        handleSizeChange(pageSize) {
            this.params.pageSize = pageSize;
            this.findBySearch();
        },
        handleCurrentChange(pageNum) {
            this.params.pageNum = pageNum;
            this.findBySearch();
        },

        del(id) {
            // console.log(id);

            request.post("/collect/status/" + id).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.findBySearch();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })

        },


        initSong() {
            request.get("/collect/song/" + this.user.id).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.songObjs = res.data.map(item => ({
                        ...item,
                        option: item.songName != null && item.songName != '' && item.songName.length > 10 ? item.songName.substring(0, 50) + '...' : item.songName,
                    }));
                    this.objs = this.songObjs;
                    // this.songObjs = res.data;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },

        initSongList() {
            request.get("/collect/songList/" + this.user.id).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    // this.songListObjs = res.data;
                    this.songListObjs = res.data.map(item => ({
                        ...item,
                        option: item.songListTitle != null && item.songListTitle != '' && item.songListTitle.length > 10 ? item.songListTitle.substring(0, 50) + '...' : item.songListTitle,
                    }));
                    this.objs = this.songListObjs;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        rowClick(item) {
            // console.log(item);
            if (item.songId != null) {
                this.$router.push({
                    path: '/songDetails',
                    query: {
                        songId: item.songId
                    }
                })
            } else if (item.songListId != null) {
                this.$router.push({
                    path: '/songListDetails',
                    query: {
                        songListId: item.songListId
                    }
                })
            }

        },
        async playAll() {
            this.$store.commit('updateIsplay', false);
            if (this.tableData.length != 0) {
                // 首先从 localStorage 获取现有的 musicList
                let musicList = localStorage.getItem("musicList");

                // 将获取到的字符串转换为数组
                if (musicList) {
                    musicList = JSON.parse(musicList);
                } else {
                    // 如果 musicList 不存在，则初始化为空数组
                    musicList = [];
                }
                let dataList = [];
                for (let i = 0; i < this.tableData.length; i++) {
                    let songs = await this.initSongDetail(this.tableData[i].songId);
                    let data = '';
                    // songs.then((result) => {
                    //     data = JSON.parse(result);
                    //     console.log(data);
                    // }).catch((error) => {
                    //     console.error('Promise被拒绝:', error);
                    // });
                    // console.log(JSON.parse(songs));
                    
                    dataList.push(JSON.parse(songs));
                }
                // console.log(dataList);

                localStorage.setItem('playingMusic', JSON.stringify(dataList[dataList.length - 1]));
                // 遍历 tableData 中的每一首歌
                dataList.forEach(song => {
                    // 检查是否存在相同的 song 并删除
                    const index = musicList.findIndex(existingSong => existingSong.id === song.id);
                    if (index !== -1) {
                        // 删除已存在的 song
                        musicList.splice(index, 1);
                    }


                    // 将新的 song 放在数组的最前面
                    musicList.unshift(song);
                });

                // 将更新后的数组转换回 JSON 字符串并保存到 localStorage
                localStorage.setItem("musicList", JSON.stringify(musicList));
            }
        },

        async initSongDetail(songId) {
            // console.log(songId);
            this.params2.singerId = '';
            this.params2.songId = songId;
            try {
                const res = await request.get("/song/search", {
                    params: this.params2
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
                    // console.log(temp[0]);
                    return JSON.stringify(temp[0]);


                    // console.log(this.song);
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


    }
    ,
    // 页面加载的时候做一些事情
    created() {
        this.findBySearch();
        // this.initSong();
        // this.initSinger();
        // this.initSongList();

    },
    data() {
        return {
            params: {
                content: '',
                pageNum: 1,
                pageSize: 15,
            },
            total: 0,
            tableData: [],
            dialogFormVisible: false,
            form: {},
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            songObjs: [],
            songListObjs: [],
            objs: [],
            userObjs: [],
            params2: {
                pageNum: 1,
                pageSize: 1,
            }

        }
    },
    computed: {

        // tableDataWithSts() {
        //     return this.tableData.map(item => ({
        //         ...item,
        //         sts: item.status === 0?true:false
        //     }));


        // }
    },
    watch: {
        'params.radio': function (newValue, oldValue) {
            this.params.radioId = '';
            if (newValue === '歌曲') {
                this.initSong();
            } else if (newValue === '歌单') {
                this.initSongList();
            }
        }
    },
}
</script>
<style>
.el-table .warning-row {
    background: oldlace;
}

.el-table .success-row {
    background: #f0f9eb;
}

.el-badge:hover {
    cursor: pointer;
    /* 鼠标悬停时变为小手 */
    user-select: none;
    /* 防止文本被选中 */
    -webkit-user-select: none;
    /* Safari */
    -moz-user-select: none;
    /* Firefox */
    -ms-user-select: none;
    /* IE10+/Edge */
}

svg:hover {
    cursor: pointer;
    /* 鼠标悬停时变为小手 */
    user-select: none;
    /* 防止文本被选中 */
    -webkit-user-select: none;
    /* Safari */
    -moz-user-select: none;
    /* Firefox */
    -ms-user-select: none;
    /* IE10+/Edge */
}

.play-btn:hover {
    background-color: rgb(206, 206, 206);
    border-radius: 10px;
}
</style>
