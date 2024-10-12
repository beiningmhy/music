<template>
    <div>
        <div>
            <el-input v-model="params.name" style="width: 200px; margin-right: 10px" placeholder="请输入地区名"
                @change="findBySearch()"></el-input>


            <el-button type="warning" @click="findBySearch()">搜索</el-button>
            <el-button type="warning" @click="reset()">清空</el-button>
            <el-button type="primary" @click="add()">新增</el-button>
        </div>
        <div style="max-height: 76vh;overflow: auto;">
            <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="70vh" stripe
                highlight-current-row>
                <el-table-column prop="id" label="序号"></el-table-column>
                <el-table-column prop="name" label="地区名"></el-table-column>
                <!-- 
                <el-table-column prop="loginTime" label="登录时间"></el-table-column>
                <el-table-column prop="createTime" label="创建时间"></el-table-column> -->
                <!-- <el-table-column prop="status" label="账号状态">
                    <template slot-scope="scope">

                        <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
                        <el-tag v-else-if="scope.row.status === '1'" type="danger">封禁</el-tag>
                        <el-tag v-else type="danger">状态未知</el-tag>
                    </template>
</el-table-column> -->


                <el-table-column label="操作" fixed="right" width="300px">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                        <el-button type="success" @click="go(scope.row)">下一级</el-button>
                        <el-button type="warning" @click="back(scope.row)">上一级</el-button>
                        <template>

                            <el-popconfirm title="确定删除吗？" @confirm="del(scope.row)">
                                <el-button type="danger" slot="reference" style="margin-left: 5px">删除</el-button>
                            </el-popconfirm>
                        </template>

                    </template>

                </el-table-column>
            </el-table>
        </div>
        <div class="block">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                :current-page="params.pageNum" :page-sizes="[10, 15, 20, 30]" :page-size="params.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <div>
            <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
                <el-form :model="form">
                    <div v-if="form.id == null">
                        <el-form-item label="添加方式" label-width="20%">
                            <template>
                                <el-radio v-model="radio" label="1">同级添加</el-radio>
                                <el-radio v-model="radio" label="2">次级添加</el-radio>
                            </template>
                        </el-form-item>
                    </div>

                    <div v-if="radio == 1">
                        <el-form-item label="地区名" label-width="20%" aria-required="true">
                            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
                        </el-form-item>

                    </div>
                    <div v-else-if="radio == 2">

                        <el-form-item label="父级地区" label-width="20%">
                            <el-select v-model="form.parentId" placeholder="请选择" style="width: 90%" clearable
                                filterable>
                                <el-option v-for="item in this.tableData" :key="item.id" :label="item.name"
                                    :value="item.id"></el-option>

                            </el-select>
                        </el-form-item>
                        <el-form-item label="地区名" label-width="20%" aria-required="true">
                            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
                        </el-form-item>
                    </div>

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
        initCascader() {
            request.get("/address/map").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.cascaderData = res.data;
                }
            })
        },
        load() {
            request.get("/address").then(res => {
                if (res.code === '0') {
                    console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch(parentId) {
            this.params.status = '1';
            request.get("/address/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    if (res.data.list.length != 0) {
                        // console.log(res.data);
                        this.tableData = res.data.list;
                        this.params.parentId = res.data.list[0].parentId;
                        this.total = res.data.total;
                    } else {
                        this.params.parentId = parentId;
                        this.findBySearch2();
                    }

                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        findBySearch2() {
            this.params.status = '0';
            request.get("/address/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    if (res.data.list.length != 0) {
                        // console.log(res.data);
                        this.tableData = res.data.list;
                        this.params.parentId = res.data.list[0].parentId;
                        this.total = res.data.total;
                    }

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
        del(row) {
            let id = row.id;
            request.delete("/address/" + id).then(res => {
                if (res.code === '0') {
                    this.$message({
                        message: '操作成功',
                        type: 'success'
                    });
                    this.findBySearch(row.parentId);
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })

        },
        submit() {
            if (this.radio == 1) {
                this.form.parentId = this.params.parentId;
            }

            request.post("/address", this.form).then(res => {
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
        go(row) {
            // console.log(id);
            this.params.name = '';
            this.params.parentId = row.id;
            this.params.status = '1';
            request.get("/address/search", {
                params: this.params
            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    if (res.data.list.length == 0) {
                        this.$message({
                            message: '没有数据',
                            type: 'error'
                        });
                        // console.log(parentId);

                        this.params.parentId = row.parentId;
                    } else {
                        // console.log(res.data);
                        this.tableData = res.data.list;
                        this.total = res.data.total;
                        this.params.parentId = res.data.list[0].parentId;
                        this.params.pageNum = res.data.pageNum;
                    }
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        back(row) {
            this.params.name = '';
            this.params.parentId = row.parentId;
            this.params.status = '0';
            request.get("/address/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    if (res.data.list.length == 0) {
                        this.$message({
                            message: '没有数据',
                            type: 'error'
                        });
                        this.params.parentId = row.parentId;
                    } else {
                        // console.log(res.data);

                        this.tableData = res.data.list;
                        this.total = res.data.total;
                        this.params.parentId = res.data.list[0].parentId;
                        this.params.pageNum = res.data.pageNum;
                    }
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
    }
    ,
    // 页面加载的时候做一些事情
    created() {
        this.findBySearch();
        this.initCascader();
    },
    data() {
        return {
            params: {
                name: '',
                parentId: '',
                pageNum: 1,
                pageSize: 15,
            },
            total: 0,
            tableData: [],
            dialogFormVisible: false,
            form: {
                id: null,
                name: '',
                parentId: '',
            },
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            radio: '1',
            cascaderData:[],
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
        dialogFormVisible(newValue, oldValue) {
            // console.log(newValue);
            if (newValue == false) {
                this.radio = '1';
            }
        },
        radio(newValue, oldValue) {

            this.form = {
                id: null,
                name: '',
                parentId: '',
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
</style>
