<template>
    <div>
        <div v-if="user.role === '0'">
            <div style="display: flex; flex-wrap: nowrap;">

                <el-select v-model="params.status" placeholder="轮播图状态" style="width: 100px; margin-right: 10px"
                    @input="findBySearch()" clearable>
                    <el-option label="正常" value="0"></el-option>
                    <el-option label="封禁" value="1"></el-option>
                </el-select>

                <el-button type="warning" @click="findBySearch()">搜索</el-button>
                <el-button type="warning" @click="reset()">清空</el-button>
                <el-button type="primary" @click="add()">新增</el-button>
            </div>
            <div style="max-height: 80vh;overflow: auto;">
                <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="70vh" stripe
                    highlight-current-row>
                    <el-table-column prop="id" label="序号" fixed sortable></el-table-column>


                    <el-table-column prop="pic" label="轮播图">
                        <template v-slot="scope">
                            <el-image style="width: 70px; height: 70px; border-radius: 50%"
                                :src="'http://localhost:8080/api/files/' + scope.row.pic"
                                :preview-src-list="['http://localhost:8080/api/files/' + scope.row.pic]">
                            </el-image>
                            <!-- <span>{{ scope.row.img }}</span> -->
                        </template>
                    </el-table-column>

                    <el-table-column prop="sts" label="轮播图状态">

                        <template slot-scope="scope">
                            <el-switch v-model="scope.row.sts" active-color="#13ce66" inactive-color="#ff4949"
                                @change="updateStatus(scope.row)">
                            </el-switch>
                        </template>
                    </el-table-column>

                    <el-table-column label="操作" width="200px">
                        <template slot-scope="scope">
                            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                            <el-button :disabled="!scope.row.pic" type="warning" @click="down(scope.row.pic)"
                                style="margin-left: 5px">下载</el-button>
                            <template>

                                <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
                                    <el-button type="danger" slot="reference" style="margin-left: 5px">删除</el-button>
                                </el-popconfirm>
                            </template>

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
                <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%" >
                    <el-form :model="form">

                        <el-form-item label="轮播图" label-width="20%" aria-required="true">
                            <el-upload action="http://localhost:8080/api/files/upload?fileDir=swiper" :on-success="successUpload"
                                list-type="picture-card" :file-list="fileList">
                                <i slot="default" class="el-icon-plus"></i>
                                <!-- <el-button size="small" type="primary">点击上传</el-button> -->
                            </el-upload>
                            <!-- <el-upload action="http://localhost:8080/api/files/upload" v-model="form.img">

                        </el-upload> -->

                        </el-form-item>

                        <el-form-item label="轮播图状态" label-width="20%">
                            <el-select v-model="form.status" placeholder="请选择" style="width: 90%" clearable>
                                <el-option label="正常" value="0"></el-option>
                                <el-option label="封禁" value="1"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="submit()">确 定</el-button>
                    </div>
                </el-dialog>
            </div>
        </div>

    </div>

</template>


<script>
import request from "@/utils/request";
export default {
    // 定义一些页面上控件触的事件调用的方法
    methods: {

        load() {
            request.get("/banner").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch() {
            request.get("/banner/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.tableData = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',


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
            this.form = {};
            this.dialogFormVisible = true;
        },
        edit(obj) {
            this.fileList = [{ name: obj.pic, url: "http://localhost:8080/api/files/" + obj.pic }] ? [{ name: obj.pic, url: "http://localhost:8080/api/files/" + obj.pic }] : [];
            this.form = JSON.parse(JSON.stringify(obj));
            this.dialogFormVisible = true;
        },
        del(id) {
            request.delete("/banner/" + id).then(res => {
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

            request.post("/banner", this.form).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.dialogFormVisible = false;
                    this.findBySearch();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        updateStatus(row) {


            row.status = row.status == '0' ? '1' : '0';
            this.form = row;
            this.submit();

        },
        successUpload(res) {
            // console.log(res);

            this.form.pic = res.data;

        },
        down(pic) {
            if (pic.includes('|')) {
                location.href = 'http://localhost:8080/api/files/' + pic;

            } else {
                this.$message({
                    message: '下载失败',
                    type: 'error'
                });
            }

        },


    }
    ,
    // 页面加载的时候做一些事情
    created() {
        this.findBySearch();
        // this.goback();
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
            form: {
                address: '',
            },
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            fileList: [],
            value: [],
            time: '5',
        }
    },
    computed: {


    },
    watch: {
        dialogFormVisible(newValue, oldValue) {
            // console.log(newValue);
            if (newValue == false) {
                this.fileList = [];
                this.form = { address: '1' };
                this.value = [];
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
    white-space: nowrap;
    text-overflow: ellipsis;
}
</style>
