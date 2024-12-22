<template>
    <div>
        <div style="width: 100%;">

            <el-tag style="margin: 0 40% 10px;" size="medium" type="success" effect="dark">当前歌单：{{ songListInfo.title
                }}</el-tag>


        </div>
        <div style="display: flex; flex-wrap: nowrap;">
            <el-input v-model="params.name" style="width: 200px; margin-right: 10px" placeholder="请输入歌曲名"
                @input="findBySearch()" clearable></el-input>

            <el-input v-model="params.other" style="width: 200px; margin-right: 10px" placeholder="模糊查询"
                @input="findBySearch()" clearable></el-input>
            <el-button type="warning" @click="findBySearch()">搜索</el-button>
            <el-button type="warning" @click="reset()">清空</el-button>
            <el-button type="primary" @click="add()">添加歌曲</el-button>
            <!-- <el-popconfirm title="确定删除这些数据吗？" @confirm="delBatch()">
                <el-button slot="reference" type="danger" style="margin-left: 5px">批量删除</el-button>
            </el-popconfirm>
            <el-button type="primary" @click="toggleSelection()" style="margin-left: 5px">取消选择</el-button> -->
            <el-button type="success" style="margin-left: 10px" @click="exp()" v-if="user.role === '0'">导出报表</el-button>
            <!-- <el-upload action="http://localhost:8080/api/song/upload" style="display: inline-block; margin-left: 10px"
                :show-file-list="false" :on-success="successUpload3">
                <el-button size="small" type="primary">批量导入</el-button>
            </el-upload> -->
        </div>
        <div style="max-height: 60vh;overflow: auto;">
            <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="54vh" stripe highlight-current-row
                lazy @selection-change="handleSelectionChange" :row-key="getRowKeys" ref="multipleTable">
                <el-table-column type="selection" :reserve-selection="true" v-if="user.role === '0'"></el-table-column>
                <el-table-column prop="id" label="序号" fixed width="70" sortable></el-table-column>
                <el-table-column prop="singerName" label="歌手" fixed width="80"></el-table-column>
                <el-table-column prop="name" label="歌曲名" fixed width="80"></el-table-column>

                <el-table-column prop="introductions" label="介绍" width="100"></el-table-column>
                <el-table-column prop="pic" label="歌曲封面" width="100">
                    <template v-slot="scope">
                        <el-image style="width: 70px; height: 70px; border-radius: 50%"
                            :src="'http://localhost:8080/api/files/' + scope.row.pic"
                            :preview-src-list="['http://localhost:8080/api/files/' + scope.row.pic]">
                        </el-image>
                        <!-- <span>{{ scope.row.img }}</span> -->
                    </template>
                </el-table-column>
                <el-table-column prop="url" label="歌曲" width="350">
                    <template v-slot="scope">
                        <div v-if="scope.row.url != null">
                            <audio v-if="scope.row.url.includes('song|')"
                                :src="'http://localhost:8080/api/files/' + scope.row.url" controls="controls">
                            </audio>
                            <audio v-else :src="scope.row.url" controls="controls">
                                <source :src="scope.row.url">
                                您的浏览器不支持 audio 标签。
                            </audio>
                        </div>
                        <div v-else>暂无歌曲</div>
                    </template>
                </el-table-column>
                <el-table-column prop="lyrics" label="歌词" width="150"></el-table-column>
                <el-table-column prop="clicks" label="点击次数" width="100" sortable></el-table-column>
                <el-table-column prop="createTime" label="创建日期" width="150"></el-table-column>
                <el-table-column prop="updateTime" label="更新日期" width="150"></el-table-column>
                <el-table-column prop="sts" label="状态" fixed="right">
                    <template slot-scope="scope">
                        <!-- {{ scope.row.sts }} -->
                        <el-switch v-model="scope.row.sts" active-color="#13ce66" inactive-color="#ff4949"
                            @change="updateStatus(scope.row)">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200" fixed="right">
                    <template slot-scope="scope">
                        <div>
                            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                            <!-- <el-button :disabled="!scope.row.pic" type="warning" @click="down(scope.row.pic)"
                                style="margin-left: 5px">下载</el-button>-->
                            <el-dropdown style="margin-left: 5px"
                                :disabled="!(scope.row.pic != '' && scope.row.url != '')">
                                <el-button type="warning">
                                    下载<i class="el-icon-arrow-down el-icon--right"></i>
                                </el-button>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item :disabled="!scope.row.pic">
                                        <div @click="downPic(scope.row.pic)">图片</div>
                                    </el-dropdown-item>
                                    <el-dropdown-item :disabled="!scope.row.url">
                                        <div @click="downUrl(scope.row.url)">音频</div>
                                    </el-dropdown-item>

                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                        <div style="margin-top: 5px">
                            <el-button type="info" @click="details(scope.row)">详情</el-button>

                        </div>



                    </template>

                </el-table-column>
            </el-table>
        </div>
        <div class="block">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="params.pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="params.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <div>
            <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%" top="1vh">
                <el-form :model="form">
                    <el-form-item label="歌曲名" label-width="20%" aria-required="true">
                        <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>

                    <el-form-item label="歌手" label-width="20%">
                        <el-select v-model="form.singerId" placeholder="请选择" style="width: 90%" clearable filterable>

                            <el-option v-for="item in typeObjs" :key="item.id" :label="item.name"
                                :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="歌曲封面" label-width="20%" aria-required="true">
                        <el-upload action="http://localhost:8080/api/files/upload?fileDir=songPic"
                            :on-success="successUpload" list-type="picture-card" :file-list="fileList">
                            <i slot="default" class="el-icon-plus"></i>
                            <!-- <el-button size="small" type="primary">点击上传</el-button> -->
                        </el-upload>
                        <!-- <el-upload action="http://localhost:8080/api/files/upload" v-model="form.img">

                        </el-upload> -->


                    </el-form-item>
                    <el-form-item label="歌曲" label-width="20%" aria-required="true">
                        <el-upload action="http://localhost:8080/api/files/upload?fileDir=song"
                            :on-success="successUpload2" list-type="stringtext" :file-list="songList">
                            <el-button size="small" type="primary">点击上传</el-button>

                        </el-upload>
                        <el-button type="warning" @click="dialogWeb = true" style="margin-top: 10px;">上传网络地址</el-button>
                        <!-- <el-upload action="http://localhost:8080/api/files/upload" v-model="form.img">

                        </el-upload> -->

                    </el-form-item>

                    <el-form-item label="介绍" label-width="20%" aria-required="true">
                        <el-input type="textarea" rows="3" v-model="form.introduction" autocomplete="off"
                            style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="歌词" label-width="20%" aria-required="true">
                        <el-input type="textarea" rows="5" v-model="form.lyric" autocomplete="off"
                            style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="点击次数" label-width="20%" aria-required="true">
                        <el-input v-model="form.clicks" autocomplete="off" style="width: 90%" clearable></el-input>
                    </el-form-item>

                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="submit()">确 定</el-button>
                </div>
            </el-dialog>
        </div>
        <div>
            <el-dialog title="输入歌曲的网络地址" :visible.sync="dialogWeb" width="30%">
                <el-input v-model="songWebUrl" autocomplete="off" style="width: 90%"></el-input>
                <span slot="footer" class="dialog-footer">
                    <el-button @click="songWebUrl = '', dialogWeb = false">取 消</el-button>
                    <el-button type="primary"
                        @click="form.url = songWebUrl, songList = [{ name: songWebUrl }], dialogWeb = false">确定</el-button>
                </span>
            </el-dialog>
        </div>
        <div>
            <el-dialog title="选择添加的歌曲" :visible.sync="dialogSongs" width="40%">
                <el-form :model="songsForm">
                    <el-form-item label="搜索歌曲" label-width="20%" aria-required="true">
                        <el-input v-model="params2.name" style="width: 80%; margin-right: 10px;" placeholder="请输入歌曲名"
                            @input="findBySearch2()" clearable></el-input>
                        
                        <el-input v-model="params2.other" style="width:80%; margin-right: 10px;margin-top: 20px;"
                            placeholder="模糊查询" @input="findBySearch2()" clearable></el-input>
                    </el-form-item>
                    <el-form-item label="歌曲" label-width="20%">
                        <el-select v-model="songsForm.songId" placeholder="请选择" style="width: 50%" clearable filterable>

                            <el-option v-for="item in songsForm.songsData" :key="item.id" :label="item.name"
                                :value="item.id"></el-option>
                        </el-select>
                        <span style="margin-left: 10px;width: 30%;text-align: center;">
                            检索到： {{ songsForm.songsData.length }}首歌曲</span>

                    </el-form-item>
                    <el-form-item label="" label-width="20%">
                        <el-button @click="addAll()">添加全部检索内容</el-button>

                    </el-form-item>

                </el-form>
                <span slot="footer" class="dialog-footer">
                    <el-button @click=" dialogSongs = false">取 消</el-button>
                    <el-button type="primary" @click="songsSubmit()">确定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>


<script>
import request from "@/utils/request";
export default {
    // 定义一些页面上控件触的事件调用的方法
    methods: {
        load() {
            if (this.songListId == '') {
                this.$message.error("请先选择歌单");
                this.$router.push({ path: '/songList' });
            } else {
                request.post("/songList/" + this.songListId).then(res => {
                    if (res.code === '0') {
                        this.songListInfo = res.data;
                    }
                })
            }
        },
        async findBySearch() {

            // console.log(this.id);
            this.params.songListId = this.songListId;
            this.params.singerId = this.user.singerId;
            await request.get("/song/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.tableData = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        lyrics: item.lyric != null && item.lyric != '' && item.lyric.length > 10 ? item.lyric.substring(0, 50) + '...' : item.lyric,
                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
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
                username: '',
                pageNum: 1,
                pageSize: 20,
            }
            this.findBySearch();
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
            this.findBySearch2();
            this.songsForm.songListId = this.songListId;
            this.dialogSongs = true;
        },
        async findBySearch2() {
            this.params2.singerId = this.user.singerId;
            await request.get("/song", {
                params: this.params2

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.songsForm.songsData = res.data.map(item => ({
                        ...item,
                        lyrics: item.lyric != null && item.lyric != '' && item.lyric.length > 10 ? item.lyric.substring(0, 50) + '...' : item.lyric,
                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                    }));


                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        songsSubmit() {
            // console.log(this.songsForm);

            request.post("/listSong", this.songsForm).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    // this.songsForm = {};
                    // this.dialogSongs = false;
                    this.findBySearch();
                } else {
                    // this.$message({
                    //     message: res.msg,
                    //     type: 'error'

                    // })
                    this.$message.error(res.msg);

                }
            })
        },
        addAll() {
            for (let i = 0; i < this.songsForm.songsData.length; i++) {
                let s = {
                    songId: this.songsForm.songsData[i].id,
                    songListId: this.songsForm.songListId
                };
                request.post("/listSong", s).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        // this.songsForm = {};
                        // this.dialogSongs = false;
                        this.findBySearch();
                    } else {

                        this.$message.error(res.msg + "来自歌曲：" + this.songsForm.songsData[i].name);

                    }
                })
            }

        },
        edit(obj) {
            this.fileList = obj.pic ? [{ name: obj.pic, url: "http://localhost:8080/api/files/" + obj.pic }] : [];
            this.songList = obj.url ? [{ name: obj.url, url: "http://localhost:8080/api/files/" + obj.url }] : [];
            this.form = JSON.parse(JSON.stringify(obj));
            this.dialogFormVisible = true;
        },
        del(id) {
            request.delete("/listSong/" + id + "/" + this.songListId).then(res => {
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
        submit() {
            request.post("/song?cont=" + this.continue2add, this.form).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.dialogFormVisible = false;
                    this.songWebUrl = '';
                    this.findBySearch();
                    this.continue2add = 0;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                    if (res.msg === '该歌曲已存在，请勿重复添加') {

                        this.$confirm('此操作将强制添加, 是否继续?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            this.$message({
                                type: 'success',
                                message: '操作成功继续提交可强制添加!'
                            });
                            this.continue2add = 1;
                        }).catch(() => {
                            this.$message({
                                type: 'info',
                                message: '已取消操作'
                            });
                        });
                    }

                }
            })
        },
        updateStatus(row) {
            // console.log(this.tableData);

            if (row.name === this.user.name) {
                this.$message({
                    message: '不能操作自己',
                    type: 'error'
                });
                row.sts = !row.sts;
            } else {
                row.status = row.status === '0' ? '1' : '0';
                this.form = row;
                this.submit();
            }
        },
        successUpload(res) {
            if (res.code === '0') {
                this.$message({
                    message: '上传成功',
                    type: 'success'
                });
                this.form.pic = res.data;
            } else {
                this.$message({
                    message: res.msg,
                    type: 'error'
                });
            }
        },
        successUpload2(res) {
            if (res.code === '0') {
                this.$message({
                    message: '上传成功',
                    type: 'success'
                });
                this.form.url = res.data;
            } else {
                this.$message({
                    message: res.msg,
                    type: 'error'
                });
            }

        },
        down(avatar) {
            if (avatar.includes('|')) {
                location.href = 'http://localhost:8080/api/files/' + avatar;

            } else {
                this.$message({
                    message: '下载失败',
                    type: 'error'
                });
            }

        },
        downPic(pic) {

            if (pic.includes('|')) {
                location.href = 'http://localhost:8080/api/files/' + pic;

            } else {
                this.$message({
                    message: '下载失败',
                    type: 'error'
                });
            }
        },
        downUrl(url) {
            if (url.includes('|')) {
                location.href = 'http://localhost:8080/api/files/' + url;

            } else if (url.includes('http')) {
                location.href = url;
            } else {
                this.$message({
                    message: '下载失败',
                    type: 'error'
                });
            }
        },
        details(row) {
            window.open('https://cn.bing.com/search?q=' + row.name, '_blank');
        },
        delBatch() {
            // console.log(this.multipleSelection);

            if (this.multipleSelection.length === 0) {
                this.$message.warning("请勾选您要删除的项")
                return
            }
            request.put("/song/delBatch", this.multipleSelection).then(res => {
                if (res.code === '0') {
                    this.$message.success("批量删除成功")
                    this.findBySearch()
                    this.multipleSelection = [];
                    this.toggleSelection();
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        handleSelectionChange(val) {
            // console.log(val);
            this.multipleSelection = val;
        },
        getRowKeys(row) {
            return row.id;
        },
        toggleSelection() {

            this.$refs.multipleTable.clearSelection();

        },
        exp() {
            let ids = [];
            ids = this.multipleSelection.map(item => item.id);
            // console.log(ids);
            if (ids.length == 0) {
                this.$message({
                    message: '请选择要导出的数据',
                    type: 'error'
                });
                return;
            }
            window.open('http://localhost:8080/api/song/export/' + ids + '?token=' + this.token, '_self');

        },
        successUpload3(res) {
            if (res.code === '0') {
                this.$message.success("批量导入成功")
                this.findBySearch()
            } else {
                this.$message.error(res.msg)
            }
        },

    }
    ,
    // 页面加载的时候做一些事情
    created() {
        this.load();
        this.findBySearch();

    },
    mounted() {
        if(!localStorage.getItem("user")){
            this.$router.push("/");
        }
    },
    data() {
        return {
            params: {
                name: '',
                phone: '',
                pageNum: 1,
                pageSize: 20,
            }, params2: {
                name: '',
                phone: ''
            },
            total: 0,
            tableData: [],
            dialogFormVisible: false,
            dialogWeb: false,
            continue2add: 0,
            form: {},
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            fileList: [],
            songList: [],
            typeObjs: [],
            songWebUrl: '',
            multipleSelection: [],
            token: JSON.parse(localStorage.getItem("user")).token,
            songListId: this.$route.query.songListId ? this.$route.query.songListId : '',
            dialogSongs: false,
            songsForm: { songsData: [], },
            songListInfo: {},

        }
    },
    computed: {


    },
    watch: {
        dialogFormVisible(newValue, oldValue) {
            // console.log(newValue);
            if (newValue == false) {
                this.fileList = [];
                this.songList = [];
                this.form = {};
                this.songWebUrl = '';
            }
        },
        dialogSongs(newValue, oldValue) {
            if (newValue == false) {
                this.songsForm = { songsData: [] };
                this.params2 = {};
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

/* CSS */
.cell {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
</style>
