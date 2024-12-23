<template>
    <div>
        <div v-if="user.role === '0'">
            <div style="display: flex; flex-wrap: nowrap;">
                <el-input v-model="params.name" style="width: 200px; margin-right: 10px" placeholder="请输入名称"
                    @input="findBySearch()" clearable></el-input>
                <el-input v-model="params.content" style="width: 200px; margin-right: 10px" placeholder="请输入内容"
                    @input="findBySearch()" clearable></el-input>
                <el-select v-model="params.sellStatus" placeholder="可售状态" style="width: 100px; margin-right: 10px"
                    @input="findBySearch()" clearable>
                    <el-option label="正常" value="0"></el-option>
                    <el-option label="封禁" value="1"></el-option>
                </el-select>
                <el-select v-model="params.status" placeholder="商品状态" style="width: 100px; margin-right: 10px"
                    @input="findBySearch()" clearable>
                    <el-option label="正常" value="0"></el-option>
                    <el-option label="封禁" value="1"></el-option>
                </el-select>
                <el-input v-model="params.other" style="width: 200px; margin-right: 10px" placeholder="模糊查询"
                    @input="findBySearch()" clearable></el-input>
                <el-button type="warning" @click="findBySearch()">搜索</el-button>
                <el-button type="warning" @click="reset()">清空</el-button>
                <el-button type="primary" @click="add()">新增</el-button>
            </div>
            <div style="max-height: 80vh;overflow: auto;">
                <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="70vh" stripe
                    highlight-current-row>
                    <el-table-column prop="id" label="序号" fixed width="70" sortable></el-table-column>
                    <el-table-column prop="name" label="商品名称" fixed width="150"></el-table-column>
                    <el-table-column prop="content" label="商品内容" fixed width="150">
                        <template slot-scope="scope">
                            <el-button type="success" @click="viewEditor(scope.row.content)">点击查看</el-button>
                        </template>
                    </el-table-column>

                    <el-table-column prop="url" label="商品封面" width="150">
                        <template v-slot="scope">
                            <el-image style="width: 70px; height: 70px; border-radius: 50%"
                                :src="'http://localhost:8080/api/files/' + scope.row.url"
                                :preview-src-list="['http://localhost:8080/api/files/' + scope.row.url]">
                            </el-image>
                            <!-- <span>{{ scope.row.img }}</span> -->
                        </template>
                    </el-table-column>
                    <el-table-column prop="price" label="价格" width="150"></el-table-column>
                    <el-table-column prop="amount" label="库存数量" width="150"></el-table-column>
                    <el-table-column prop="max" label="最大购买数量" width="150"></el-table-column>
                    <el-table-column prop="sellTime" label="出售日期" width="150"></el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="150"></el-table-column>
                    <el-table-column prop="sellSts" label="可售状态" width="100" fixed="right">
                        <template slot-scope="scope">
                            <el-switch v-model="scope.row.sellSts" active-color="#13ce66" inactive-color="#ff4949"
                                @change="updateSellStatus(scope.row)">
                            </el-switch>
                        </template>
                    </el-table-column>
                    <el-table-column prop="sts" label="商品状态" width="100" fixed="right">
                        <template slot-scope="scope">
                            <el-switch v-model="scope.row.sts" active-color="#13ce66" inactive-color="#ff4949"
                                @change="updateStatus(scope.row)">
                            </el-switch>
                        </template>
                    </el-table-column>

                    <el-table-column label="操作" width="200" fixed="right">
                        <template slot-scope="scope">
                            <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
                            <el-button :disabled="!scope.row.url" type="warning" @click="down(scope.row.url)"
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
                <el-dialog title="请填写信息" :visible.sync="dialogFormVisible" width="70%" top="1vh">
                    <el-form :model="form">

                        <el-form-item label="商品名称" label-width="20%" aria-required="true">
                            <el-input v-model="form.name" autocomplete="off" style="width: 90%"></el-input>
                        </el-form-item>
                        <!-- <el-form-item label="商品内容" label-width="20%" aria-required="true">
                            <el-input v-model="form.content" autocomplete="off" style="width: 90%"></el-input>
                        </el-form-item> -->
                        <el-form-item label="商品内容" label-width="20%">
                            <div id="editor" style="width: 90%"></div>
                        </el-form-item>
                        <el-form-item label="商品封面" label-width="20%" aria-required="true">
                            <el-upload action="http://localhost:8080/api/files/upload" :on-success="successUpload"
                                list-type="picture-card" :file-list="fileList">
                                <i slot="default" class="el-icon-plus"></i>
                                <!-- <el-button size="small" type="primary">点击上传</el-button> -->
                            </el-upload>
                            <!-- <el-upload action="http://localhost:8080/api/files/upload" v-model="form.img">

                        </el-upload> -->

                        </el-form-item>
                        <el-form-item label="商品价格" label-width="20%" aria-required="true">
                            <el-input v-model="form.price" autocomplete="off" style="width: 90%"></el-input>
                        </el-form-item>
                        <el-form-item label="商品数量" label-width="20%" aria-required="true">
                            <el-input-number v-model="form.amount" :min="0" :max="1000000"
                                style="width: 90%"></el-input-number>
                        </el-form-item>
                        <el-form-item label="商品最大购买数量" label-width="20%" aria-required="true">
                            <el-input-number v-model="form.max" :min="0" :max="1000000"
                                style="width: 90%"></el-input-number>
                        </el-form-item>
                        <el-form-item label="出售时间" label-width="20%" aria-required="true">
                            <el-date-picker v-model="form.sellTime" type="datetime" format="yyyy-MM-dd HH:mm:ss"
                                value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期"
                                style="width: 90%"></el-date-picker>

                            <!-- <el-date-picker v-model="value1" type="datetime" placeholder="选择日期时间">
                            </el-date-picker> -->
                        </el-form-item>
                        <el-form-item label="可售状态" label-width="20%">
                            <!-- <el-select v-model="form.sellStatus" placeholder="请选择" style="width: 90%" clearable>
                                <el-option label="正常" value="0"></el-option>
                                <el-option label="封禁" value="1"></el-option>
                            </el-select> -->
                            <template>
                                <el-radio v-model="form.sellStatus" label="0">正常</el-radio>
                                <el-radio v-model="form.sellStatus" label="1">封禁</el-radio>
                            </template>
                        </el-form-item>
                        <el-form-item label="商品状态" label-width="20%">
                            <!-- <el-select v-model="form.status" placeholder="请选择" style="width: 90%" clearable>
                                <el-option label="正常" value="0"></el-option>
                                <el-option label="封禁" value="1"></el-option>
                            </el-select> -->
                            <template>
                                <el-radio v-model="form.status" label="0">正常</el-radio>
                                <el-radio v-model="form.status" label="1">封禁</el-radio>
                            </template>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="dialogFormVisible = false">取 消</el-button>
                        <el-button type="primary" @click="submit()">确 定</el-button>
                    </div>
                </el-dialog>
            </div>
            <div>
                <el-dialog title="商品内容" :visible.sync="editorVisible" width="70%" top="1vh">
                    <div v-html="this.viewData" class="w-e-text"></div>
                </el-dialog>
            </div>
        </div>

    </div>

</template>


<script>
import request from "@/utils/request";
import E from 'wangeditor';
let editor
function initWangEditor(content) {
    setTimeout(() => {
        if (!editor) {
            editor = new E('#editor')
            editor.config.placeholder = '请输入内容'
            editor.config.uploadFileName = 'file'
            editor.config.uploadImgServer = 'http://localhost:8080/api/files/wang/upload'
            editor.create()
        }
        editor.txt.html(content)
    }, 10)
}
export default {
    // 定义一些页面上控件触的事件调用的方法
    methods: {

        load() {
            request.get("/product").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch() {
            request.get("/product/search", {
                params: this.params

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.tableData = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        sellSts: item.sellStatus === '0',
                        // introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
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
            initWangEditor("");
            this.form = {};
            this.dialogFormVisible = true;
        },
        edit(obj) {
            initWangEditor(obj.content ? obj.content : "");
            this.fileList = [{ name: obj.url, url: "http://localhost:8080/api/files/" + obj.url }] ? [{ name: obj.url, url: "http://localhost:8080/api/files/" + obj.url }] : [];
            this.form = JSON.parse(JSON.stringify(obj));
            this.dialogFormVisible = true;
        },
        del(id) {
            request.delete("/product/" + id).then(res => {
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


            this.form.content = editor.txt.html();
            request.post("/product", this.form).then(res => {
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
            request.post("/product", this.form).then(res => {
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
        updateSellStatus(row) {
            // console.log(this.tableData);


            row.sellStatus = row.sellStatus === '0' ? '1' : '0';
            this.form = row;
            request.post("/product", this.form).then(res => {
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
        successUpload(res) {
            // console.log(res);

            this.form.url = res.data;

        },
        down(url) {
            if (url.includes('|')) {
                location.href = 'http://localhost:8080/api/files/' + url;

            } else {
                this.$message({
                    message: '下载失败',
                    type: 'error'
                });
            }

        },
        viewEditor(content) {
            this.editorVisible = true;
            this.viewData = content;
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
            viewData: '',
            editorVisible: false,

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
