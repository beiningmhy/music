<template>
    <div>
        <div>
            <div style="display: flex; flex-wrap: nowrap;">

                <el-select v-model="params.productId" placeholder="请选择商品" style="width: 200px; margin-right: 10px"
                    @input="findBySearch()" clearable filterable default-first-option>
                    <el-option v-for="item in productObjs" :key="item.id" :label="item.productName"
                        :value="item.productId"></el-option>
                </el-select>

                <!-- <el-input v-model="params.other" style="width: 200px; margin-right: 10px" placeholder="模糊查询"
                    @input="findBySearch()" clearable></el-input> -->
                <el-button type="warning" @click="findBySearch()">搜索</el-button>
                <el-button type="warning" @click="reset()">清空</el-button>

            </div>
            <div style="max-height: 66vh;overflow: auto;">
                <el-table :data="tableData" style="width: 100%; margin: 15px 0px" height="60vh" stripe
                    highlight-current-row>
                    <el-table-column prop="id" label="序号" fixed width="70" sortable></el-table-column>
                    <el-table-column prop="username" label="用户名称" fixed width="150"></el-table-column>
                    <el-table-column prop="productName" label="商品名称" fixed width="150"></el-table-column>
                    <el-table-column prop="url" label="商品封面" width="150">
                        <template v-slot="scope">
                            <el-image style="width: 70px; height: 70px; border-radius: 50%"
                                :src="'http://localhost:8080/api/files/' + scope.row.url"
                                :preview-src-list="['http://localhost:8080/api/files/' + scope.row.url]">
                            </el-image>
                            <!-- <span>{{ scope.row.img }}</span> -->
                        </template>
                    </el-table-column>
                    <el-table-column prop="price" label="价格" width="100"></el-table-column>
                    <el-table-column prop="num" label="购买数量" width="80"></el-table-column>
                    <el-table-column label="总价" width="150">
                        <template v-slot="scope">
                            <span>{{ scope.row.price * scope.row.num }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column prop="createTime" label="创建时间" width="150" sortable></el-table-column>


                    <el-table-column label="操作" width="200" fixed="right">
                        <template slot-scope="scope">

                            <el-button :disabled="!scope.row.url" type="warning" @click="detail(scope.row)"
                                style="margin-left: 5px">详情</el-button>
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



        </div>

    </div>

</template>


<script>
import request from "@/utils/request";

export default {
    // 定义一些页面上控件触的事件调用的方法
    methods: {

        load() {
            request.get("/order").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);

                    this.tableData = res.data;
                }
            })
        },
        findBySearch() {
            this.params.userId = this.user.id;
            this.params.status = '0';
            request.get("/order/search", {
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

        initProduct() {
            let p = this.user.id;
            // console.log(p);

            request.get("/order/product/" + p).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.productObjs = res.data.map(item => ({
                        ...item,
                    }));


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
        submit() {
            request.post("/order", this.form).then(res => {
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

        del(id) {
            request.post("/order/status/" + id).then(res => {
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

        updateStatus(row) {
            // console.log(this.tableData);


            row.status = row.status === '0' ? '1' : '0';
            this.form = row;
            this.submit();

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
        detail(row) {
            console.log(row);

            this.$router.push({
                path: '/productDetails',
                query: {
                    productId: row.productId
                }
            });
        },


    }
    ,
    // 页面加载的时候做一些事情
    created() {


    },
    async mounted() {
        if (!localStorage.getItem("user")) {
            this.$router.push("/");
        }
        await this.findBySearch();
        await this.initProduct();
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
            userObjs: [],
            productObjs: [],

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
