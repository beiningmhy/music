<template>
    <div>
        <div style="display: flex; flex-wrap: nowrap;">
            <el-input v-model="params.username" style="width: 200px; margin-right: 10px" placeholder="请输入姓名"
                @change="findBySearch()" clearable></el-input>
            <el-select v-model="params.singerType" placeholder="角色" style="width: 100px; margin-right: 10px"
                @change="findBySearch()" clearable>
                <el-option label="普通用户" value="0"></el-option>
                <el-option label="歌手" value="1"></el-option>
            </el-select>
            <el-select v-model="params.status" placeholder="用户状态" style="width: 100px; margin-right: 10px"
                @change="findBySearch()" clearable>
                <el-option label="正常" value="0"></el-option>
                <el-option label="封禁" value="1"></el-option>
            </el-select>
            <el-input v-model="params.other" style="width: 200px; margin-right: 10px" placeholder="模糊查询"
                @change="findBySearch()" clearable></el-input>
            <el-button type="warning" @click="findBySearch()">搜索</el-button>
            <el-button type="warning" @click="reset()">清空</el-button>
            <el-button type="primary" @click="add()">新增</el-button>
        </div>
        <div style="max-height: 80vh;overflow: auto;">
            <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="70vh" stripe
                highlight-current-row> 
                <el-table-column prop="id" label="序号" fixed width="70" sortable></el-table-column>
                <el-table-column prop="username" label="姓名" fixed width="150"></el-table-column>
                <el-table-column prop="uid" label="UID" width="150">
                    <template slot-scope="scope">
                        <el-tag>
                            {{ scope.row.uid }}
                        </el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="singerType" label="角色" width="100">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.singerType === '0'" type="success">普通用户</el-tag>
                        <el-tag v-else-if="scope.row.singerType === '1'" type="primary">歌手</el-tag>
                        <el-tag v-else type="danger">角色未知</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="sex" label="性别" width="50">
                    <template slot-scope="scope">
                        <el-tag v-if="scope.row.sex === '0'" type="danger">女</el-tag>
                        <el-tag v-else-if="scope.row.sex === '1'" type="success">男</el-tag>
                        <el-tag v-else type="danger">未知</el-tag>
                    </template>
                </el-table-column>

                <el-table-column prop="avatar" label="头像" width="150">
                    <template v-slot="scope">
                        <el-image style="width: 70px; height: 70px; border-radius: 50%"
                            :src="'http://localhost:8080/api/files/' + scope.row.avatar"
                            :preview-src-list="['http://localhost:8080/api/files/' + scope.row.avatar]">
                        </el-image>
                        <!-- <span>{{ scope.row.img }}</span> -->
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="手机号" width="150"></el-table-column>
                <el-table-column prop="email" label="邮箱" width="150"></el-table-column>
                <el-table-column prop="birth" label="出生日期" width="150"></el-table-column>
                <el-table-column prop="introductions" label="介绍" width="150"></el-table-column>
                <el-table-column prop="address" label="地址" width="150"></el-table-column>

                <el-table-column prop="point" label="积分" width="150"></el-table-column>
                <el-table-column prop="loginTime" label="登录时间" width="150"></el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
                <!-- <el-table-column prop="status" label="账号状态">
                    <template slot-scope="scope">

                        <el-tag v-if="scope.row.status === '0'" type="success">正常</el-tag>
                        <el-tag v-else-if="scope.row.status === '1'" type="danger">封禁</el-tag>
                        <el-tag v-else type="danger">状态未知</el-tag>
                    </template>
                </el-table-column> -->
                <el-table-column prop="sts" label="账号状态" width="100" fixed="right">
                    <template slot-scope="scope">
                        <el-switch v-model="scope.row.sts" active-color="#13ce66" inactive-color="#ff4949"
                            @change="updateStatus(scope.row)">
                        </el-switch>
                    </template>
                </el-table-column>

                <el-table-column label="操作" width="200" fixed="right">
                    <template slot-scope="scope">
                        <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                        <el-button :disabled="!scope.row.avatar" type="warning" @click="down(scope.row.avatar)"
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
            <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="30%" top="1vh">
                <el-form :model="form">
                    <el-form-item label="UID" label-width="20%" aria-required="true">
                        <el-tag>{{ form.uid }}</el-tag>
                    </el-form-item>
                    <el-form-item label="用户名" label-width="20%" aria-required="true">
                        <el-input v-model="form.username" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="角色" label-width="20%">
                        <el-select v-model="form.singerType" placeholder="请选择" style="width: 90%" clearable>
                            <el-option label="普通用户" value="0"></el-option>
                            <el-option label="歌手" value="1"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="性别" label-width="20%">
                        <el-select v-model="form.sex" placeholder="请选择" style="width: 90%" clearable>
                            <el-option label="男" value="1"></el-option>
                            <el-option label="女" value="0"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="手机号" label-width="20%" aria-required="true">
                        <el-input v-model="form.phone" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" label-width="20%" aria-required="true">
                        <el-input v-model="form.email" autocomplete="off" style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="出生日期" label-width="20%" aria-required="true">
                        <el-date-picker v-model="form.birth" type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                            placeholder="选择日期" style="width: 90%"></el-date-picker>
                    </el-form-item>
                    <el-form-item label="介绍" label-width="20%" aria-required="true">
                        <el-input type="textarea" rows="3" v-model="form.introduction" autocomplete="off"
                            style="width: 90%"></el-input>
                    </el-form-item>
                    <el-form-item label="地址" label-width="20%" aria-required="true">
                        <el-tag autocomplete="off" style="width: 90%" size="medium">{{ form.address }}</el-tag>
                        <el-cascader v-model="value" :options="cascaderData" :props="{ expandTrigger: 'hover' }"
                            @change="handleChange" style="width: 90%;margin-top: 5px;"></el-cascader>
                    </el-form-item>
                    <el-form-item label="头像" label-width="20%" aria-required="true">
                        <el-upload action="http://localhost:8080/api/files/upload" :on-success="successUpload"
                            list-type="picture-card" :file-list="fileList">
                            <i slot="default" class="el-icon-plus"></i>
                            <!-- <el-button size="small" type="primary">点击上传</el-button> -->
                        </el-upload>
                        <!-- <el-upload action="http://localhost:8080/api/files/upload" v-model="form.img">

                        </el-upload> -->

                    </el-form-item>
                    <el-form-item label="积分" label-width="20%" aria-required="true">
                        <el-input v-model="form.point" autocomplete="off" style="width: 90%"></el-input>
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
        initCascader() {
            request.get("/address/map").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.cascaderData = res.data;
                }
            })
        },
        load() {
            request.get("/consumer").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch() {
            request.get("/consumer/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.tableData = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',

                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                        // introduction: item.introduction.substring(0, 10),
                        // birth: new Date(item.birth), 
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
            this.form = {address:''};
            this.dialogFormVisible = true;
        },
        edit(obj) {
            this.fileList = [{ name: obj.avatar, url: "http://localhost:8080/api/files/" + obj.avatar }] ? [{ name: obj.avatar, url: "http://localhost:8080/api/files/" + obj.avatar }] : [];
            this.form = JSON.parse(JSON.stringify(obj));
            this.dialogFormVisible = true;
        },
        del(id) {
            request.delete("/consumer/" + id).then(res => {
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

            request.post("/consumer", this.form).then(res => {
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
        successUpload(res) {
            // console.log(res);

            this.form.avatar = res.data;

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
        async handleChange(value) {
            let address = '';
            // console.log(value);
            if (value != null) {
                for (let i = 0; i < value.length; i++) {
                    const element = value[i];
                    // console.log(element);
                    await request.get('/address/' + element).then(res => {
                        if (res.code === '0') {
                            // console.log(res.data);
                            address = address + res.data + '/'
                        }
                    })
                }
                // console.log(address);
                this.form.address = address;
            }

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
            cascaderData: [],
            value: [],
        }
    },
    computed: {


    },
    watch: {
        dialogFormVisible(newValue, oldValue) {
            // console.log(newValue);
            if (newValue == false) {
                this.fileList = [];
                this.form = {address:'1'};
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
