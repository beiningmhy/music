<template>
    <div>
        <div>
            <div style="display: flex; flex-wrap: nowrap;">
                <el-select v-model="params.type" placeholder="请选择类型" style="width: 100px; margin-right: 10px"
                    @input="findBySearch()" clearable filterable default-first-option>
                    <el-option v-for="item in typeList" :key="item.id" :label="item" :value="item"></el-option>
                </el-select>
                <el-input v-model="params.content" style="width: 200px; margin-right: 10px" placeholder="请输入内容"
                    @input="findBySearch()" clearable></el-input>

                <el-select v-model="params.status" placeholder="请选择状态" style="width: 200px; margin-right: 10px"
                    @input="findBySearch()" clearable>
                    <el-option label="正常" value="0"></el-option>
                    <el-option label="封禁" value="1"></el-option>
                </el-select>

                <el-button type="warning" @click="findBySearch()">搜索</el-button>
                <el-button type="warning" @click="reset()">清空</el-button>
                <!-- <el-button type="primary" @click="add()">新增</el-button> -->
            </div>
            <div style="max-height: 76vh;overflow: auto;">
                <el-table :data="tableData" style="width: 100%; margin: 15px 0px" stripe highlight-current-row>
                    <el-table-column prop="id" label="序号" sortable></el-table-column>
                    <el-table-column prop="type" label="类型"></el-table-column>
                    <el-table-column prop="content" label="内容"></el-table-column>

                    <el-table-column prop="createTime" label="创建时间"></el-table-column>
                    <!-- <el-table-column prop="status" label="账号状态">
                    <template slot-scope="scope">

                        <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
                        <el-tag v-else-if="scope.row.status === '1'" type="danger">封禁</el-tag>
                        <el-tag v-else type="danger">状态未知</el-tag>
                    </template>
</el-table-column> -->
                    <el-table-column prop="sts" label="状态">
                        <template slot-scope="scope">
                            <el-switch v-model="scope.row.sts" active-color="#13ce66" inactive-color="#ff4949"
                                @change="updateStatus(scope.row)">
                            </el-switch>
                        </template>
                    </el-table-column>

                    <el-table-column label="操作" width="160">
                        <template slot-scope="scope">
                            <div>
                                <!-- <el-button type="primary" @click="edit(scope.row)">编辑</el-button> -->
                                <template>
                                    <el-popconfirm title="确定删除吗？" @confirm="del(scope.row.id)">
                                        <el-button type="danger" slot="reference"
                                            style="margin-left: 5px">删除</el-button>
                                    </el-popconfirm>
                                </template>
                            </div>


                        </template>

                    </el-table-column>
                </el-table>
            </div>
            <div class="block">
                <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                    :current-page="params.pageNum" :page-sizes="[1, 2, 5, 10]" :page-size="params.pageSize"
                    layout="total, sizes, prev, pager, next, jumper" :total="total">
                </el-pagination>
            </div>
            <div>
                <!-- <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
                    <el-form :model="form">
                        <el-form-item label="姓名" label-width="20%" aria-required="true">
                            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
                        </el-form-item>
                        <el-form-item label="角色" label-width="20%">
                            <el-select v-model="form.role" placeholder="请选择" style="width: 90%" clearable>
                                <el-option label="超级管理员" value="0"></el-option>
                                <el-option label="歌曲管理员" value="1"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="账号状态" label-width="20%">
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
                </el-dialog> -->
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
            request.get("/feedback").then(res => {
                if (res.code === '0') {
                    console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch() {
            request.get("/feedback/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.tableData = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0'
                    }));
                    // console.log(this.tableData);

                    this.total = res.data.total;
                    this.initType();
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        initType() {
            request.get("/feedback/type").then(res => {
                if (res.code === '0') {
                    // console.log(res);
                    
                    this.typeList = res.data;
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
                pageSize: 5,
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
            this.form = JSON.parse(JSON.stringify(obj));
            this.dialogFormVisible = true;
        },
        del(id) {
            request.delete("/feedback/" + id).then(res => {
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
            request.post("/feedback", this.form).then(res => {
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
        goback() {
            if (this.user.role !== '0') {
                this.intervalId = setInterval(() => {
                    this.time = this.time - 1;
                    if (this.time === 0) {
                        clearInterval(this.intervalId);
                        this.$router.push('/');
                    }
                }, 1000);
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
                pageSize: 5,
            },
            total: 0,
            tableData: [],
            dialogFormVisible: false,
            form: {},
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            typeList: [],

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
}
</script>
<style>
.el-table .warning-row {
    background: oldlace;
}

.el-table .success-row {
    background: #f0f9eb;
}
</style>
