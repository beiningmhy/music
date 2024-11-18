<template>
    <div>
        <div>
            <el-input v-model="params.name" style="width: 200px; margin-right: 10px" placeholder="请输入公告标题"
                @input="findBySearch()"></el-input>
            <el-button type="warning" @click="findBySearch()">搜索</el-button>
            <el-button type="warning" @click="reset()">清空</el-button>
            <el-button type="primary" @click="add()">新增</el-button>
        </div>
        <div style="max-height: 76vh;overflow: auto;">
            <el-table :data="tableData" style="width: 100%; margin: 15px 0px" stripe highlight-current-row>
                <el-table-column prop="id" label="序号" sortable></el-table-column>
                <el-table-column prop="name" label="公告标题"></el-table-column>
                <el-table-column prop="content" label="公告内容"></el-table-column>
                <el-table-column prop="time" label="发布时间" sortable></el-table-column>
                <el-table-column prop="sts" label="公告状态" width="100" fixed="right">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.sts" active-color="#13ce66" inactive-color="#ff4949"
                            @change="updateStatus(scope.row)">
                        </el-switch>
                    </template>
                </el-table-column>
                <el-table-column label="操作" fixed="right">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                        <el-button type="success" @click="update(scope.row)">更新</el-button>
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
                :current-page="params.pageNum" :page-sizes="[1, 2, 5, 10]" :page-size="params.pageSize"
                layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <div>
            <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%">
                <el-form :model="form">
                    <el-form-item label="公告标题" label-width="20%" aria-required="true">
                        <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>

                    <el-form-item label="公告内容" label-width="20%">
                        <el-input type="textarea" v-model="form.content" autocomplete="off"
                            style="width: 90%"></el-input>
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
            request.get("/notice").then(res => {
                if (res.code === '0') {
                    console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch() {
            request.get("/notice/search", {
                params: this.params
                // params:{
                //     name:this.params.name,
                //     phone:this.params.phone
                // }
            }).then(res => {
                if (res.code === '0') {
                    console.log(res.data);
                    this.tableData = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',

                    }));
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
            request.delete("/notice/" + id).then(res => {
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
            request.post("/notice", this.form).then(res => {
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
        update(row) {
            request.post("/notice", row).then(res => {
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


            row.status = row.status === '0' ? '1' : '0';
            this.form = row;
            this.submit();

        },
    }
    ,
    // 页面加载的时候做一些事情
    created() {
        this.findBySearch();
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
        }
    }
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
