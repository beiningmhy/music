<template>
    <div>
        <div style="display: flex; flex-wrap: nowrap;">
            <el-input v-model="params.name" style="width: 200px; margin-right: 10px" placeholder="请输入歌曲名"
                @input="findBySearch()" clearable></el-input>
            <el-select v-model="params.singerId" placeholder="歌手" style="width: 100px; margin-right: 10px"
                @input="findBySearch()" clearable filterable default-first-option>
                <el-option v-for="item in typeObjs" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
            <el-input v-model="params.other" style="width: 200px; margin-right: 10px" placeholder="模糊查询"
                @input="findBySearch()" clearable></el-input>
            <el-select v-model="params.status" placeholder="请选择状态" style="width: 100px; margin-right: 10px"
                @input="findBySearch()" clearable>
                <el-option label="正常" value="0"></el-option>
                <el-option label="封禁" value="1"></el-option>
            </el-select>
            <el-button type="warning" @click="findBySearch()">搜索</el-button>
            <el-button type="warning" @click="reset()">清空</el-button>
            <el-button type="primary" @click="add()">新增</el-button>
            <el-popconfirm title="确定删除这些数据吗？" @confirm="delBatch()">
                <el-button slot="reference" type="danger" style="margin-left: 5px">批量删除</el-button>
            </el-popconfirm>
            <el-button type="primary" @click="toggleSelection()" style="margin-left: 5px">取消选择</el-button>
            <el-button type="success" style="margin-left: 10px" @click="exp()" v-if="user.role === '0'">导出报表</el-button>
            <el-upload action="http://localhost:8080/api/song/upload" style="display: inline-block; margin-left: 10px"
                :show-file-list="false" :on-success="successUpload3">
                <el-button size="small" type="primary">批量导入</el-button>
            </el-upload>
        </div>
        <div style="max-height: 80vh;overflow: auto;">
            <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="70vh" stripe highlight-current-row
                lazy @selection-change="handleSelectionChange" :row-key="getRowKeys" ref="multipleTable"
                v-loading="loading">
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
                <el-table-column label="歌单操作" width="80">
                    <template v-slot="scope">
                        <el-button type="primary" @click="editSongList(scope.row)">编辑</el-button>
                    </template>
                </el-table-column>
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
                <el-table-column label="操作" width="160" fixed="right">
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

                            <template>

                                <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
                                    <el-button type="danger" slot="reference">删除</el-button>
                                </el-popconfirm>
                            </template>
                            <!-- <el-button type="info" @click="details(scope.row)" style="margin-left: 5px">详情</el-button> -->
                            <el-dropdown style="margin-left: 5px">
                                <el-button type="info">
                                    详情<i class="el-icon-arrow-down el-icon--right"></i>
                                </el-button>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item>
                                        <div @click="detailsBing(scope.row)">Bing</div>
                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <div @click="detailsMusic(scope.row)">Music</div>
                                    </el-dropdown-item>
                                    <el-dropdown-item>
                                        <div @click="detailsMusic2(scope.row)">Music2</div>
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>



                    </template>

                </el-table-column>
            </el-table>
        </div>
        <div class="block">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="params.pageNum" :page-sizes="[10, 20, 50, 100, total]" :page-size="params.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <div>
            <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="50%" top="1vh">
                <el-form :model="form">
                    <el-form-item label="歌曲名" label-width="20%" aria-required="true">
                        <el-input v-model="form.name" autocomplete="off" style="width: 90%" clearable></el-input>
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
                        <el-button type="warning" @click="form.url = null" style="margin-top: 10px;">填null</el-button>
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
                        <el-button type="warning" @click="lrc(form)" style="margin-top: 10px;">自动检索歌词</el-button>
                        <el-button type="warning" @click="lrc2a(form)" style="margin-top: 10px;">手动检索歌词</el-button>
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
            <el-dialog title="选择添加的歌单" :visible.sync="dialogSongList" width="30%">
                <el-form :model="songListForm">
                    <el-form-item label="歌曲名" label-width="20%" aria-required="true">
                        <el-tag type="success">{{ songListForm.songName }}</el-tag>
                    </el-form-item>
                    <el-form-item label="现有歌单" label-width="20%">
                        <div v-if="songListForm.songListNames.length == 0">
                            <el-tag type="danger">暂无歌单</el-tag>
                        </div>
                        <div v-else>
                            <el-tag v-for="item in songListForm.songListNames" :key="item.id" :label="item.title"
                                closable @close="removeSongList(item, songListForm)" @click="tagClick(item)">{{
                                    item.title }}</el-tag>
                        </div>

                    </el-form-item>
                    <el-form-item label="歌单" label-width="20%">
                        <el-select v-model="songListForm.songListId" placeholder="请选择" style="width: 90%" clearable
                            filterable>

                            <el-option v-for="item in songListObjs" :key="item.id" :label="item.titles"
                                :value="item.id"></el-option>
                        </el-select>
                    </el-form-item>

                </el-form>

                <span slot="footer" class="dialog-footer">
                    <el-button @click=" dialogSongList = false, songListForm = {}">关闭</el-button>
                    <el-button type="primary" @click="songListSubmit()">添加</el-button>
                </span>
            </el-dialog>

            <el-dialog title="确认歌词" :visible.sync="lrcDialog" width="40%" top="1vh">
                <div style="text-align: center;color: blueviolet;font-size: large;">
                    歌曲ID：{{ selectSong.id }}<br>
                    歌曲名：{{ selectSong.name }}<br>
                    歌手：{{ selectSong.singer }}<br>
                    专辑：{{ selectSong.introduction }}<br>

                </div>
                <hr>
                <pre>{{ lrcText }}</pre>

                <span slot="footer" class="dialog-footer">
                    <el-button @click=" lrcDialog = false, lrcText = ''">关闭</el-button>
                    <el-button type="primary" @click="form.lyric = lrcText, lrcDialog = false">添加</el-button>
                </span>
            </el-dialog>
            <el-dialog title="请选择歌曲" :visible.sync="selectSongDialog" width="50%" top="10vh">

                <div style="max-height: 70vh;overflow: auto;">
                    <el-table :data="songObjs" style="width: 100%; margin: 15px 0px" height="50vh" stripe
                        highlight-current-row lazy>
                        <el-table-column prop="id" label="序号" fixed width="100" sortable></el-table-column>
                        <el-table-column prop="name" label="歌曲名" width="150"></el-table-column>
                        <el-table-column prop="singer" label="歌手" width="150"></el-table-column>
                        <el-table-column prop="introduction" label="专辑"></el-table-column>
                        <el-table-column label="操作" width="80">
                            <template slot-scope="scope">
                                <div>
                                    <el-button type="primary" @click="lrc2b(scope.row)">选择</el-button>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>

                <span slot="footer" class="dialog-footer">
                    <el-button @click=" selectSongDialog = false">关闭</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>


<script>
import request from "@/utils/request";
import axios from 'axios';
export default {
    // 定义一些页面上控件触的事件调用的方法
    methods: {
        load() {
            request.get("/song").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        async findBySearch() {
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
                    this.loading = false;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        findSinger() {
            request.get("/singer").then(res => {
                if (res.code === '0') {
                    this.typeObjs = res.data;
                } else {
                    this.$message.error(res.msg)
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
            this.form = {
                name: '',
                singerName: '',
            };
            this.dialogFormVisible = true;
        },
        edit(obj) {
            this.fileList = obj.pic ? [{ name: obj.pic, url: "http://localhost:8080/api/files/" + obj.pic }] : [];
            this.songList = obj.url ? [{ name: obj.url, url: "http://localhost:8080/api/files/" + obj.url }] : [];
            this.form = JSON.parse(JSON.stringify(obj));
            this.dialogFormVisible = true;
        },
        del(id) {
            request.delete("/song/" + id).then(res => {
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
        detailsBing(row) {
            window.open('https://cn.bing.com/search?q=' + row.name, '_blank');
        },
        detailsMusic(row) {
            // console.log(row);

            window.open('http://127.0.0.1:163/?name=' + row.name + '&type=netease', '_blank');
        },
        detailsMusic2(row) {
            // console.log(row);

            window.open('https://tool.liumingye.cn/music/#/search/M/song/' + row.singerName + row.name, '_blank');
        },
        delBatch() {
            console.log(this.multipleSelection);

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
            console.log(val);
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
            console.log(ids);
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
        editSongList(row) {
            // console.log(row);

            request.get("/songList").then(res => {
                if (res.code === '0') {
                    this.songListObjs = res.data.map(item => ({
                        ...item,
                        titles: item.title != null && item.title.length > 10 ? item.title.substring(0, 10) + '...' : item.title,

                    }));
                    // console.log(this.songListObjs);
                    this.songListForm.songId = row.id;
                    this.songListForm.songName = row.name;
                    request.post("/songList/songList/" + row.id).then(res => {
                        if (res.code === '0') {
                            this.songListForm.songListNames = res.data;
                            // console.log(this.songListForm.songListNames.length);

                            this.dialogSongList = true;
                        } else {
                            this.$message.error(res.msg)
                        }
                    })


                } else {
                    this.$message.error(res.msg)
                }
            })


        },
        songListSubmit() {
            request.post("/listSong", this.songListForm).then(res => {
                if (res.code === '0') {
                    this.$message.success("添加歌曲到歌单成功")
                    // this.dialogSongList = false;
                    let row = {
                        id: this.songListForm.songId,
                        name: this.songListForm.songName
                    }
                    // console.log(row);

                    this.editSongList(row);
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        removeSongList(item, songListForm) {
            this.$confirm('此操作将此歌曲从歌单中移除, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                request.delete("/listSong/" + songListForm.songId + "/" + item.id).then(res => {
                    if (res.code === '0') {
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        });
                        this.findBySearch();
                        let row = {
                            id: songListForm.songId,
                            name: songListForm.songName
                        }
                        // console.log(row);

                        this.editSongList(row);
                    } else {
                        this.$message({
                            message: res.msg,
                            type: 'error'
                        });
                    }
                })

            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消操作'
                });
            });



        },
        tagClick(item) {
            this.$router.push({
                path: '/songList2Song',
                query: {
                    songListId: item.id,
                }
            })
        },
        updateStatus(row) {
            // console.log(this.tableData);
            row.status = row.status === '0' ? '1' : '0';
            this.form = row;
            this.submit();
        },
        lrc(form) {
            if (form.singerId == '' || form.name == '') {
                this.$message.error("请先完善歌曲信息");
                return;
            }

            let resultObj = this.typeObjs.find(obj => obj.id === form.singerId);

            form.singerName = resultObj.name;
            // console.log(form);
            if (form.singerName == '' || form.name == '') {
                this.$message.error("请先完善歌曲信息");
                return;
            }


            let name = form.name;
            let songid = 0;
            let song = {};
            let id = '';
            axios.post('/163id/?s=' + name + '&type=1&limit=50&offset=0')
                .then(response => {
                    // 处理响应数据
                    //console.log(response.data.result.songs);
                    let songs = response.data.result.songs;
                    for (let i = 0; i < songs.length; i++) {
                        // console.log(songs[i].ar[0].name);
                        if (songs[i].ar[0].name == form.singerName && (songs[i].name.includes(form.name) || form.name.includes(songs[i].name))) {
                            // console.log(i);
                            id = i;
                            song = songs[i];
                            songid = songs[i].id;
                            break;
                        }
                    }
                    if (songid == 0) {
                        for (let i = 0; i < songs.length; i++) {
                            if ((songs[i].ar[0].name.includes(form.singerName) || form.singerName.includes(songs[i].ar[0].name)) && (songs[i].name.includes(form.name) || form.name.includes(songs[i].name))) {
                                // console.log(i);
                                id = i;
                                song = songs[i];
                                songid = songs[i].id;
                                break;
                            }
                        }
                    }
                    if (songid == 0) {
                        id = 0;
                        song = songs[0];
                        songid = songs[0].id;
                    }
                    // console.log(song);
                    // console.log(id);
                    this.selectSong = {
                        id: song.id,
                        name: song.name,
                        singer: song.ar[0].name,
                        introduction: song.al.name
                    };
                    axios.post('/lrc/?id=' + songid + '&lv=1')
                        .then(response => {
                            // 处理响应数据
                            if (response.status == 200) {
                                // console.log(response.data);
                                this.$message({
                                    message: '歌词获取成功',
                                    type: 'success'
                                });
                                this.lrcDialog = true;
                                this.lrcText = response.data.lrc.lyric;
                            }
                        })
                        .catch(error => {
                            // 处理错误情况
                            console.error('Error fetching data:', error);
                        });
                })
                .catch(error => {
                    // 处理错误情况
                    console.error('Error fetching data:', error);
                });
        },
        lrc2a(form) {
            if (form.singerId == '' || form.name == '') {
                this.$message.error("请先完善歌曲信息");
                return;
            }

            let resultObj = this.typeObjs.find(obj => obj.id === form.singerId);

            form.singerName = resultObj.name;
            // console.log(form);
            if (form.singerName == '' || form.name == '') {
                this.$message.error("请先完善歌曲信息");
                return;
            }
            let name = form.name;
            let songid = 0;
            axios.post('/163id/?s=' + name + '&type=1&limit=50&offset=0')
                .then(response => {
                    // 处理响应数据
                    // console.log(response.data.result.songs);
                    let songs = response.data.result.songs;
                    let songObjs = [];
                    for (let i = 0; i < songs.length; i++) {
                        songObjs.push({
                            id: songs[i].id,
                            name: songs[i].name,
                            singer: songs[i].ar[0].name,
                            introduction: songs[i].al.name
                        })
                    }
                    // console.log(songObjs);
                    this.songObjs = songObjs;
                    this.selectSongDialog = true;
                })
                .catch(error => {
                    // 处理错误情况
                    console.error('Error fetching data:', error);
                });
        },
        lrc2b(item) {
            
            this.selectSong =item;
            // console.log(item);
            this.selectSongDialog = false;
            axios.post('/lrc/?id=' + item.id + '&lv=1')
                .then(response => {
                    // 处理响应数据
                    if (response.status == 200) {
                        // console.log(response.data);
                        this.$message({
                            message: '歌词获取成功',
                            type: 'success'
                        });
                        this.lrcDialog = true;
                        this.lrcText = response.data.lrc.lyric;
                    }
                })
                .catch(error => {
                    // 处理错误情况
                    console.error('Error fetching data:', error);
                });
        },



    }
    ,
    // 页面加载的时候做一些事情
    created() {
        // this.findBySearch();
        this.findSinger();
    },
    async mounted() {
        await this.findBySearch();
    },

    data() {
        return {
            params: {
                name: '',
                phone: '',
                pageNum: 1,
                pageSize: 20,
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
            dialogSongList: false,
            songListObjs: [],
            songListForm: {
                songListNames: []
            },
            loading: true,
            lrcDialog: false,
            lrcText: '',
            songObjs: [],
            selectSongDialog: false,
            selectSong: {},
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
        dialogSongList(newValue, oldValue) {
            // console.log(newValue);
            if (newValue == false) {
                this.songListForm = { songListNames: [] };
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
