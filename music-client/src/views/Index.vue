<template>

    <div style="max-height: 89vh;overflow: auto;" class="main">
        <div ref="draggableButton" class="draggable-button" @mousedown="startDrag">
            <el-backtop target=".main" :visibility-height="10" :bottom="120"></el-backtop>
        </div>

        <!-- 轮播图 -->
        <div>
            <el-carousel :interval="4000" type="card" height="200px">
                <el-carousel-item v-for="item in banner" :key="item.id" :value="item.id"
                    style="background-color: black;background-repeat: repeat; ">
                    <img :src="'http://localhost:8080/api/files/' + item.pic" style="width: 100%;height:100%;" />

                </el-carousel-item>
            </el-carousel>
        </div>
        <!-- 歌手 -->
        <hr>
        <div>
            <h1 style="font-size:25px;">歌手 ></h1>
            <div style="display: flex;flex-wrap: wrap;">
                <div v-for="item in singer" :key="item.id" :label="item.pic" :value="item.id" @click="goSinger(item)"
                    style="flex-direction: row; margin-left: 20px;margin-bottom: 20px;overflow: hidden;width: 100px;height: 150px;">
                    <el-image style="width: 100px; height: 100px; border-radius: 50% ;"
                        :src="'http://localhost:8080/api/files/' + item.pic">
                    </el-image>
                    <div style="text-align: center;">
                        <span style="font-size: small;">{{ item.name }}</span>
                    </div>
                </div>

            </div>
            <el-pagination @current-change="handleCurrentChange1" background layout="prev, pager, next" :total="total1"
                :page-size="params1.pageSize" :current-page="params1.pageNum">
            </el-pagination>

        </div>
        <!-- 歌单 -->
        <hr>
        <div>
            <h1 style="font-size:25px;">歌单 ></h1>
            <div style="display: flex;flex-wrap: wrap;">
                <div v-for="item in songList" :key="item.id" :label="item.pic" :value="item.id" @click="goSongList(item)"
                    style="flex-direction: row; margin-left: 20px;margin-bottom: 20px;">
                    <el-image style="width: 100px; height: 100px; border-radius: 20% ;"
                        :src="'http://localhost:8080/api/files/' + item.pic">
                    </el-image>
                    <div style="text-align: center;">
                        <span style="font-size: small;">{{ item.titles }}</span>
                    </div>
                </div>

            </div>
            <el-pagination @current-change="handleCurrentChange2" background layout="prev, pager, next" :total="total2"
                :page-size="params2.pageSize" :current-page="params2.pageNum">
            </el-pagination>

        </div>
        <div style="height: 15vh;">

        </div>
    </div>
</template>
<script>
import request from '@/utils/request';

export default {
    data() {
        return {
            singer: [],
            songList: [],
            banner: [],
            params1: {
                pageSize: 10,
                pageNum: 1,
            },
            params2: {
                pageSize: 10,
                pageNum: 1,
            },
            total1: 0,
            total2: 0,
            isDragging: false,
            initialX: 0,
            initialY: 0,
            currentX: 300,
            currentY: 300,
            windowWidth: 0,
            windowHeight: 0,


        }
    },
    mounted() {
        this.setInitialPosition();
        window.addEventListener('resize', this.handleResize);
        // this.handleResize();
    },

    methods: {
        async loadSwiper() {
            await request.get("/banner").then(res => {
                if (res.code === '0') {
                    // console.log(res.data);

                    this.banner = JSON.parse(JSON.stringify(res.data));
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        async loadSinger() {
            await request.get("/singer/search", {
                params: this.params1

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.singer = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                        // birth: new Date(item.birth), 
                    }));
                    // console.log(this.tableData);

                    this.total1 = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        async loadSongList() {
            await request.get("/songList/search", {
                params: this.params2

            }).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.songList = res.data.list.map(item => ({
                        ...item,
                        sts: item.status === '0',
                        introductions: item.introduction != null && item.introduction.length > 10 ? item.introduction.substring(0, 10) + '...' : item.introduction,
                        titles: item.title != null && item.title.length > 5 ? item.title.substring(0, 5) + '...' : item.title,
                        // birth: new Date(item.birth), 
                    }));
                    // console.log(this.tableData);

                    this.total2 = res.data.total;
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        handleCurrentChange1(pageNum) {
            this.params1.pageNum = pageNum;
            this.loadSinger();
        },
        handleCurrentChange2(pageNum) {
            this.params2.pageNum = pageNum;
            this.loadSongList();
        },
        goSinger(item) {
            // console.log(item);
            this.$router.push({
                path: '/singerDetails',
                query: {
                    singerId: item.id
                }
            })

        },
        goSongList(item) {
            // console.log(item);
            this.$router.push({
                path: '/songListDetails',
                query: {
                    songListId: item.id
                }
            })

        },
        setInitialPosition() {
            const button = this.$refs.draggableButton;
            const rect = button.getBoundingClientRect();
            const windowWidth = window.innerWidth;
            const windowHeight = window.innerHeight;
            const buttonWidth = rect.width;
            const buttonHeight = rect.height;
            // console.log(windowWidth);
            // console.log(windowHeight);
            // console.log(rect);


            this.currentX = windowWidth - 300;
            this.currentY = windowHeight - 100;
            // button.style.transform = `translate(${this.currentX}px, ${this.currentY}px)`;
        },
        startDrag(event) {
            this.isDragging = true;
            this.initialX = event.clientX - this.currentX;
            this.initialY = event.clientY - this.currentY;
            document.addEventListener('mousemove', this.drag);
            document.addEventListener('mouseup', this.stopDrag);
        },
        drag(event) {
            if (this.isDragging) {
                const newX = event.clientX - this.initialX;
                const newY = event.clientY - this.initialY;
                this.currentX = newX;
                this.currentY = newY;
                this.$refs.draggableButton.style.transform = `translate(${newX}px, ${newY}px)`;
            }
        },
        stopDrag() {
            this.isDragging = false;
            document.removeEventListener('mousemove', this.drag);
            document.removeEventListener('mouseup', this.stopDrag);
        },
        handleResize() {
            this.windowWidth = window.innerWidth;
            this.windowHeight = window.innerHeight;
            // console.log(this.windowWidth);
            let button = this.$refs.draggableButton;
            this.currentX = this.windowWidth - 300;
            this.currentY = this.windowHeight - 100;
            // button.style.transform = `translate(${this.currentX}px, ${this.currentY}px)`;

        },

    },
    created() {
        this.loadSinger();
        this.loadSongList();
        this.loadSwiper();
        // this.handleResize();
    }
}
</script>
<style>
.el-carousel__item h3 {
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}

.main {
    max-height: 90vh;
    overflow: auto;
    scrollbar-width: none;
    /* 隐藏滚动条 */
    -ms-overflow-style: none;
    /* 隐藏 IE 和 Edge 的滚动条 */
    overflow: -moz-scrollbars-none;
    /* 隐藏 Firefox 的滚动条 */
}

hr {
    border: 1px solid #c9c4c4;
    margin: 20px 0;
}

.draggable-button {
    position: absolute;
    cursor: move;
    padding: 10px 20px;
    /* background-color: #4CAF50; */
    color: white;
    border: none;
    border-radius: 5px;
    user-select: none;
    z-index: 10000;
}
</style>