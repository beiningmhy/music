<template>
    <div>
        <div style="display: flex; flex-wrap: nowrap;">
            <el-input v-model="params.content" style="width: 200px; margin-right: 10px" placeholder="请输入评论内容" clearable
                @input="findBySearch()"></el-input>
            <!-- <el-select v-model="params.userId" placeholder="请选择用户" style="width: 200px; margin-right: 10px"
                @input="findBySearch()" clearable filterable default-first-option>
                <el-option v-for="item in userObjs" :key="item.id" :label="item.username" :value="item.id"></el-option>
            </el-select> -->

            <!-- <el-select v-model="params.status" placeholder="请选择评论状态" style="width: 100px; margin-right: 10px"
                @input="findBySearch()" clearable>
                <el-option label="启用" value="0"></el-option>
                <el-option label="禁用" value="1"></el-option>
            </el-select> -->

            <el-radio-group v-model="params.radio" style=" margin-right: 10px;"
                @input="params.radioId = '', findBySearch()">
                <el-radio-button label="歌曲"></el-radio-button>
                <el-radio-button label="歌手"></el-radio-button>
                <el-radio-button label="歌单"></el-radio-button>
            </el-radio-group>
            <!-- <el-select v-model="params.songId" placeholder="歌曲" style="width: 100px; margin-right: 10px"
                @input="findBySearch(), params.singerId = ''" clearable filterable default-first-option>
                <el-option v-for="item in songObjs" :key="item.id" :label="item.option" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="params.singerId" placeholder="歌手" style="width: 100px; margin-right: 10px"
                @input="findBySearch()" clearable filterable default-first-option>
                <el-option v-for="item in singerObjs" :key="item.id" :label="item.option" :value="item.id"></el-option>
            </el-select>
            <el-select v-model="params.songListId" placeholder="歌单" style="width: 100px; margin-right: 10px"
                @input="findBySearch()" clearable filterable default-first-option>
                <el-option v-for="item in songListObjs" :key="item.id" :label="item.option"
                    :value="item.id"></el-option>
            </el-select> -->
            <el-select v-model="params.radioId" :placeholder="params.radio" style="width: 100px; margin-right: 10px"
                @input="findBySearch()" clearable filterable default-first-option>
                <el-option v-for="item in objs" :key="item.id" :label="item.option" :value="item.id"></el-option>
            </el-select>
            <el-button type="warning" @click="findBySearch()">搜索</el-button>
            <el-button type="warning" @click="reset()">清空</el-button>
            <!-- <el-button type="primary" @click="add()">新增</el-button> -->
        </div>
        <div style="max-height: 66vh;overflow: auto;">
            <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="60vh" stripe highlight-current-row
                @row-dblclick="rowClick($event)">
                <el-table-column prop="id" label="序号" fixed="left" width="70" sortable></el-table-column>
                <el-table-column prop="username" label="评论人" fixed="left" width="80"></el-table-column>
                <el-table-column prop="content" label="评论内容" width="500"></el-table-column>
                <el-table-column prop="object" label="评论对象" width="300"></el-table-column>


                <!-- <el-table-column prop="status" label="账号状态">
                    <template slot-scope="scope">

                        <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
                        <el-tag v-else-if="scope.row.status === '1'" type="danger">封禁</el-tag>
                        <el-tag v-else type="danger">状态未知</el-tag>
                    </template>
</el-table-column> -->
                <!-- <el-table-column prop="sts" label="评论状态" width="100" fixed="right">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.sts" active-color="#13ce66" inactive-color="#ff4949"
                            @change="updateStatus(scope.row)">
                        </el-switch>
                    </template>
                </el-table-column> -->
                <el-table-column prop="up" label="点赞数" width="100" sortable>
                    <template slot-scope="scope">
                        <div>
                            <svg t="1728798081711" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="1477" width="20" height="20">
                                <path
                                    d="M896 341.333333a42.666667 42.666667 0 0 1 41.642667 51.925334l-85.333334 469.333333A42.666667 42.666667 0 0 1 810.666667 896h-298.666667c-8.448 0-16.682667-2.517333-23.68-7.168L328.405333 810.666667H128a42.666667 42.666667 0 0 1-42.666667-42.666667V384a42.666667 42.666667 0 0 1 42.666667-42.666667h189.141333l158.250667-235.306666A42.666667 42.666667 0 0 1 512 85.333333h21.333333C592.170667 85.333333 640 170.666667 640 192L682.666667 341.333333h213.333333z m-371.114667 469.333334h251.562667l66.346667-384H640a42.666667 42.666667 0 0 1-42.666667-42.666667l-42.666666-192a21.418667 21.418667 0 0 0-18.645334-21.162667L384 395.818667v349.354666L524.885333 810.666667zM298.666667 426.666667H170.666667v298.666666h128v-298.666666z"
                                    fill="#6CCAFF" p-id="1478"></path>
                            </svg>
                            <el-badge :value="scope.row.up" class="item">
                            </el-badge>
                        </div>

                    </template>
                </el-table-column>
                <el-table-column prop="down" label="反对数" width="100" sortable>
                    <template slot-scope="scope">
                        <div>
                            <svg t="1728798866603" class="icon" viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="1641" width="20" height="20">
                                <path
                                    d="M128 682.666667a42.666667 42.666667 0 0 1-41.642667-51.925334l85.333334-469.333333A42.666667 42.666667 0 0 1 213.333333 128h341.333334c8.448 0 16.64 2.517333 23.68 7.168L695.594667 213.333333H896a42.666667 42.666667 0 0 1 42.666667 42.666667v384a42.666667 42.666667 0 0 1-42.666667 42.666667h-189.141333l-158.293334 235.306666A42.666667 42.666667 0 0 1 512 938.666667h-21.333333C431.786667 938.666667 384 853.333333 384 832L341.333333 682.666667H128z m413.781333-469.333334H247.552l-66.346667 384H384a42.666667 42.666667 0 0 1 42.666667 42.666667l42.666666 192c0 10.837333 8.106667 19.797333 18.645334 21.162667L640 628.181333V278.826667L541.781333 213.333333zM725.333333 597.333333h128V298.666667h-128v298.666666z"
                                    fill="#d81e06" p-id="1642"></path>
                            </svg>
                            <el-badge :value="scope.row.down" class="item">
                            </el-badge>
                        </div>

                    </template>
                </el-table-column>
                <!-- <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column> -->
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
            request.get("/comment").then(res => {
                if (res.code === '0') {
                    console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch() {
            this.params.userId = this.user.id;
            this.params.status = '0';
            request.get("/comment/search", {
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
        add() {
            this.form = {};
            this.dialogFormVisible = true;
        },
        edit(obj) {
            this.form = JSON.parse(JSON.stringify(obj));
            this.dialogFormVisible = true;
        },
        del(id) {
            request.post("/comment/status/" + id).then(res => {
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
            request.get("/comment/song/" + this.user.id).then(res => {
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
        initSinger() {
            request.get("/comment/singer/" + this.user.id).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    // this.singerObjs = res.data;
                    this.singerObjs = res.data.map(item => ({
                        ...item,
                        option: item.singerName != null && item.singerName != '' && item.singerName.length > 10 ? item.singerName.substring(0, 50) + '...' : item.singerName,
                    }));
                    this.objs = this.singerObjs;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        initSongList() {
            request.get("/comment/songList/" + this.user.id).then(res => {
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
            console.log(item);
            if (item.singerId != null) {
                this.$router.push({
                    path: '/singerDetails',
                    query: {
                        singerId: item.singerId
                    }
                })
            } else if (item.songId != null) {
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


    },
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
            singerObjs: [],
            songListObjs: [],
            objs: [],
            userObjs: [],
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
            } else if (newValue === '歌手') {
                this.initSinger();
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
</style>
