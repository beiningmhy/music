<template>
    <div>
        <div style="display: flex; flex-wrap: nowrap;">
            <el-input v-model="params.title" style="width: 200px; margin-right: 10px" placeholder="请输入歌单名"
                @input="findBySearch()" clearable></el-input>
            <!-- <el-select v-model="params.style" placeholder="曲风" style="width: 100px; margin-right: 10px"
                @input="findBySearch()" clearable filterable default-first-option>
                <el-option v-for="item in styleObjs" :key="item" :label="item" :value="item"></el-option>
            </el-select> -->
            <el-input v-model="params.other" style="width: 200px; margin-right: 10px" placeholder="模糊查询"
                @input="findBySearch()" clearable></el-input>
            <el-button type="warning" @click="findBySearch()">搜索</el-button>
            <el-button type="warning" @click="reset()">清空</el-button>
            <el-button type="primary" @click="add()">新增</el-button>
        </div>
        <div style="max-height: 66vh;overflow: auto;">
            <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="60vh" stripe highlight-current-row
                v-loading="loading">
                <el-table-column prop="id" label="序号" fixed width="70" sortable></el-table-column>
                <el-table-column prop="title" label="歌单名" fixed width="200"></el-table-column>


                <el-table-column prop="avatar" label="歌单封面" width="150">
                    <template v-slot="scope">
                        <el-image style="width: 70px; height: 70px; border-radius: 50%"
                            :src="'http://localhost:8080/api/files/' + scope.row.pic"
                            :preview-src-list="['http://localhost:8080/api/files/' + scope.row.pic]">
                        </el-image>
                        <!-- <span>{{ scope.row.img }}</span> -->
                    </template>
                </el-table-column>

                <el-table-column prop="introductions" label="介绍" width="400"></el-table-column>
                <el-table-column prop="style" label="曲风" width="150"></el-table-column>
                <el-table-column prop="clicks" label="点击次数" width="100" sortable></el-table-column>

                <el-table-column prop="createTime" label="创建时间" width="150">
                    <template v-slot="scope">
                        <el-button type="text" icon="el-icon-search" @click="toSong(scope.row)">点击查看歌单内容</el-button>
                    </template>
                </el-table-column>


                <el-table-column label="操作" width="160" fixed="right">
                    <template slot-scope="scope">
                        <div>
                            <!-- <el-button type="primary" @click="edit(scope.row)">编辑</el-button> -->
                            <el-button :disabled="!scope.row.pic" type="warning" @click="down(scope.row.pic)"
                                style="">下载</el-button>
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
                :current-page="params.pageNum" :page-sizes="[5, 10, 15, 20]" :page-size="params.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <div>
            <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%" top="5vh">
                <el-form :model="form">
                    <el-form-item label="歌单名" label-width="20%" aria-required="true">
                        <el-input v-model="form.title" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="头像" label-width="20%" aria-required="true">
                        <el-upload action="http://localhost:8080/api/files/upload?fileDir=songListPic"
                            :on-success="successUpload" list-type="picture-card" :file-list="fileList">
                            <i slot="default" class="el-icon-plus"></i>
                            <!-- <el-button size="small" type="primary">点击上传</el-button> -->
                        </el-upload>
                        <!-- <el-upload action="http://localhost:8080/api/files/upload" v-model="form.img">

                        </el-upload> -->

                    </el-form-item>

                    <el-form-item label="介绍" label-width="20%" aria-required="true">
                        <el-input type="textarea" rows="3" v-model="form.introduction" autocomplete="off"
                            style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="曲风" label-width="20%" aria-required="true" hidden>
                        <el-input v-model="form.style" autocomplete="off" style="width: 90%" clearable disabled hidden></el-input>
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
    </div>
</template>


<script>
import request from "@/utils/request";
export default {
    // 定义一些页面上控件触的事件调用的方法
    methods: {
        load() {
            request.get("/songList").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        async findBySearch() {
            this.params.style=this.user.username;
            await request.get("/songList/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.tableData = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 100) + '...' : item.introduction,
                        // birth: new Date(item.birth), 
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
        findStyle() {
            request.get("/songList/style").then(res => {
                if (res.code === '0') {
                    this.styleObjs = res.data;
                    // console.log(this.styleObjs);
                } else {
                    this.$message.error(res.msg)
                }
            })
        },
        reset() {
            this.params = {
                username: '',
                pageNum: 1,
                pageSize: 10,
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
                style:this.user.username,
            };
            this.dialogFormVisible = true;
        },
        edit(obj) {
            this.fileList = [{ name: obj.pic, url: "http://localhost:8080/api/files/" + obj.pic }] ? [{ name: obj.pic, url: "http://localhost:8080/api/files/" + obj.pic }] : [];
            this.form = JSON.parse(JSON.stringify(obj));
            this.dialogFormVisible = true;
        },
        del(id) {
            request.delete("/songList/" + id).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.findBySearch();
                    this.findStyle();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })

        },
        submit() {
            request.post("/songList", this.form).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.dialogFormVisible = false;
                    this.findBySearch();
                    this.findStyle();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
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
            // console.log(res);

            this.form.pic = res.data;

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
        details(row) {
            window.open('https://cn.bing.com/search?q=' + row.title, '_blank');
        },
        toSong(row) {
            this.$router.push({
                path: '/songList2Song',
                query: {
                    songListId: row.id
                }
            })
        }
    }
    ,
    // 页面加载的时候做一些事情
    created() {
        // this.findBySearch();
        // this.findStyle();
    },
    async mounted() {
        if(!localStorage.getItem("user")){
            this.$router.push("/");
        }
        await this.findBySearch();
    },

    data() {
        return {
            params: {
                name: '',
                phone: '',
                pageNum: 1,
                pageSize: 10,
            },
            total: 0,
            tableData: [],
            dialogFormVisible: false,
            form: {},
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            fileList: [],
            styleObjs: [],
            loading: true,
        }
    },
    computed: {


    },
    watch: {
        dialogFormVisible(newValue, oldValue) {
            // console.log(newValue);
            if (newValue == false) {
                this.fileList = [];
                this.form = {};
            }
        },
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
