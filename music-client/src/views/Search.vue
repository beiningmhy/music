<template>
    <div style="max-height: 89vh;overflow: auto;" class="search">
        <el-backtop target=".search" style="z-index: 10000;" :bottom="120" :visibility-height="10"></el-backtop>
        <div style="height: 100px;width: 500px;margin: 0 auto;line-height: 100px;">
            <el-input placeholder="请输入内容" v-model="search" class="input-with-select" clearable>
                <el-button slot="append" icon="el-icon-search" @click="initSearch()"></el-button>
            </el-input>
        </div>
        <hr>
        <!-- 搜索结果 -->
        <div>
            <div>
                <el-tooltip class="item" effect="dark" content="单击歌曲查看歌曲详情，双击添加至播放列表" placement="left">
                    <h1 style="font-size:25px;width: 150px;">搜索结果 ></h1>
                </el-tooltip>

            </div>
            <div>
                <el-pagination @current-change="handleCurrentChange" background layout="prev, pager, next"
                    :total="total" :page-size="params.pageSize" :current-page="params.pageNum">
                </el-pagination>
                <el-table v-loading="songLoading" :data="tableData" style="width: 100%;background-color: transparent ;"
                    @row-dblclick="songDbClick($event)">
                    <el-table-column prop="name" label="歌曲" width="400">
                        <template v-slot="scope">
                            <div style="display: flex;">
                                <div>
                                    <el-image style="width: 30px; height: 30px; border-radius: 10%"
                                        :src="'http://localhost:8080/api/files/' + scope.row.pic"
                                        :preview-src-list="['http://localhost:8080/api/files/' + scope.row.pic]">
                                    </el-image>
                                </div>
                                <div style="text-align: center;display: inline-block;height: 30px;line-height: 30px;"
                                    @click="songClick(scope.row)">
                                    <span style="margin-left: 10px;font-weight: 600;font-size: 15px;color: black;">
                                        {{ scope.row.name }}</span>
                                </div>
                                <!-- <div>
                                    <el-button type="text" style="margin-left: 10px;">点击</el-button>
                                </div> -->
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
    name: 'Search',
    data() {
        return {
            search: this.$route.query.search ? this.$route.query.search : '',
            params: {
                pageNum: 1,
                pageSize: 10,
            },
            total: 0,
            tableData: [],
            songLoading: false,
        }
    },
    components: {

    },
    mounted() {
        this.initSearch();
    },
    methods: {
        async initSearch() {
            if (this.search == '') {
                this.$message({
                    message: '请输入搜索内容',
                    type: 'error'
                });
                return;
            }
            this.params.search = this.search;
            try {
                const res = await request.get("/song/search", {
                    params: this.params
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
                            // console.log(item.url);

                            if (item.url != null) {
                                if (item.url.includes('|')) {
                                    audioUrl = 'http://localhost:8080/api/files/' + item.url;
                                } else {
                                    audioUrl = item.url;
                                }
                            } else {
                                audioUrl = '';
                            }
                            // console.log(audioUrl);
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

                    this.total = res.data.total;
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
        handleCurrentChange(pageNum) {
            this.params.pageNum = pageNum;
            this.initSearch();
        },
        songClick(item) {
            // console.log(item);
            this.$router.push({
                path: '/songDetails',
                query: {
                    songId: item.id
                }
            })


        },
        songDbClick(item) {
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
    }
}
</script>