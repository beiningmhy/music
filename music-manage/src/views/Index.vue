<template>
    <div style="overflow: auto;height: 85vh">
        

        <div>
            <div style="height: 600px;width: 100%;display: flex;">
                <div  style="display: flex;height: 100%;width: 25%;flex-direction: column;">
                    <div style="height: 50%;width: 100%;flex: 1;" id="div1">
                        <!-- 系统用户性别统计 -->
                    </div>
                    <div style="height: 50%;width: 100%;flex: 1;" id="div3">
                        <!-- 歌手性别统计 -->
                    </div>
                </div>
                <div style="display: flex;height: 100%;width: 50%;">
                    <div style="display: flex; float: left;height: 100%;width: 100%;" id="div5">
                    </div>
                </div>
                <div style="display: flex;height: 100%;width: 25%;flex-direction: column;">
                    <div style="display: flex; float: left;height: 50%;width: 100%;" id="div2">
                        <!-- 系统用户状态统计 -->
                    </div>

                    <div style="display: flex; float: left;height: 50%;width: 100%;" id="div4">
                        <!-- 管理员状态统计 -->
                    </div>
                </div>

            </div>


        </div>
        <!-- <div> -->
            <!-- 这是首页
            <span>欢迎用户{{ user.name }}</span> -->
            <!-- <div>
                <iframe width="100%" height="100%" class="embed-show"
                    src="http://127.0.0.1:1234/#fileView&path=http%3A%2F%2F127.0.0.1%3A1234%2F%3Fexplorer%2Fshare%2Ffile%26hash%3Da2d2CbtYDFp8bw2nsF7opqWHbcxsmQSCi3786YKV42IyUROZ-wdjONTZG-eig99Ny_w%26name%3D%2F%25E6%2584%25BF%25E4%25B8%258E%25E6%2584%2581-%25E6%259E%2597%25E4%25BF%258A%25E6%259D%25B0.flac%26_etag%3D7f7f1%26size%3D22363583"
                    allowtransparency="true" allowfullscreen="true" webkitallowfullscreen="true"
                    mozallowfullscreen="true" frameborder="0" scrolling="no"></iframe>
                <iframe width="100%" height="100%" class="embed-show"
                    src="http://127.0.0.1:1234/#fileView&path=http%3A%2F%2F127.0.0.1%3A1234%2F%3Fexplorer%2Fshare%2Ffile%26hash%3D7172UJoYOBUe6hxpgNLf3MkpTArlBAukeRUid1egpcoz8eXOXV0fT_Mz4uH9GvXRQvc%26name%3D%2F%25E5%258F%25A5%25E5%258F%25B7-G.E.M.%25E9%2582%2593%25E7%25B4%25AB%25E6%25A3%258B.flac%26_etag%3D53aa2%26size%3D26775816"
                    allowtransparency="true" allowfullscreen="true" webkitallowfullscreen="true"
                    mozallowfullscreen="true" frameborder="0" scrolling="no"></iframe>
            </div>

        </div> -->
        <!-- <div>
            <div style="display: flex;height: 40vh;">
                <div style="flex: 1;">
                    <div style="margin-bottom: 15px; font-weight: bold; font-size: 18px">系统公告</div>
                    <el-collapse v-model="activeName" accordion>
                        <el-collapse-item v-for="item in tableData" :title="item.name" :name="item.id" :key="item.id">
                            <div style="padding: 0 20px">{{ item.content }}</div>
                        </el-collapse-item>
                    </el-collapse>
                </div>
                <div style="width: 50px"></div>
                <div style="flex: 1; margin-top: 50px;">
                    <div id="bie" style="width: 100%; height: 40vh"></div>
                </div>
            </div>
            <div style="display: flex;height: 40vh;">
                <div style="flex: 1; margin-top: 20px">
                    <div id="bar" style="width:100%; height: 40vh"></div>
                </div>
                <div style="flex: 1; margin-top:20px">
                    <div id="line" style="width: 100%; height: 40vh"></div>
                </div>
            </div>
        </div> -->
    </div>

</template>
<script>
import request from "@/utils/request";
import * as echarts from "echarts";
export default {
    name: "INDEX",
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            activeName: '1',
            tableData: [],
            // provinces :require('province-city-china/dist/data.json'),
            windowWidth: window.innerWidth,

        }
    },
    created() {
        // this.handleResize();
    },
    mounted() {
        this.initEcharts();
        window.addEventListener('resize', this.handleResize);
    },
    methods: {
        handleResize() {
            this.windowWidth = window.innerWidth;
            // console.log(this.windowWidth);

        },
        initEcharts() {
            request.get("/consumer/sexCount").then(res => {
                if (res.code === '0') {
                    this.initConsumerSexCount(res.data);
                    // console.log(res.data);
                }
            })
            request.get("/consumer/statusCount").then(res => {
                if (res.code === '0') {
                    this.initConsumerStatusCount(res.data);
                    // console.log(res.data);
                }
            })
            request.get("/singer/sexCount").then(res => {
                if (res.code === '0') {
                    this.initSingerSexCount(res.data);
                    // console.log(res.data);
                }
            })
            request.get("/admin/statusCount").then(res => {
                if (res.code === '0') {
                    this.initAdminStatusCount(res.data);
                    // console.log(res.data);
                }
            })
            request.get("/song/songCount").then(res => {
                if (res.code === '0') {
                    this.initSongCount(res.data);
                    // console.log(res.data);
                }
            })


        },
        initConsumerSexCount(data) {
            let chartDom = document.getElementById('div1');
            let myChart = echarts.init(chartDom);
            let option;
            option = {
                title: {
                    text: '系统用户性别统计',
                    left: 'center'
                },
                legend: {
                    top: 'bottom'
                },
                tooltip: {
                    trigger: 'item'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },

                series: [
                    {
                        name: '',
                        type: 'pie',
                        radius: [10, 50],
                        center: ['50%', '50%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 8
                        },
                        data: data,
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        initConsumerStatusCount(data) {
            let chartDom = document.getElementById('div2');
            let myChart = echarts.init(chartDom);
            let option;
            option = {
                title: {
                    text: '系统用户状态统计',
                    left: 'center'
                },
                legend: {
                    top: 'bottom'
                },
                tooltip: {
                    trigger: 'item'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },

                series: [
                    {
                        name: '',
                        type: 'pie',
                        radius: [10, 50],
                        center: ['50%', '50%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 8
                        },
                        data: data,
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        initSingerSexCount(data) {
            let chartDom = document.getElementById('div3');
            let myChart = echarts.init(chartDom);
            let option;
            option = {
                title: {
                    text: '歌手性别统计',
                    left: 'center'
                },
                legend: {
                    top: 'bottom'
                },
                tooltip: {
                    trigger: 'item'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },

                series: [
                    {
                        name: '',
                        type: 'pie',
                        radius: [10, 50],
                        center: ['50%', '50%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 8
                        },
                        data: data,
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        initAdminStatusCount(data) {
            let chartDom = document.getElementById('div4');
            let myChart = echarts.init(chartDom);
            let option;
            option = {
                title: {
                    text: '管理员状态统计',
                    left: 'center'
                },
                legend: {
                    top: 'bottom'
                },
                tooltip: {
                    trigger: 'item'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },

                series: [
                    {
                        name: '',
                        type: 'pie',
                        radius: [10, 50],
                        center: ['50%', '50%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 8
                        },
                        data: data,
                    }
                ]
            };
            option && myChart.setOption(option);
        },
        initSongCount(data) {
            let chartDom = document.getElementById('div5');
            let myChart = echarts.init(chartDom);
            let option;
            option = {
                tooltip: {
                    trigger: 'item'
                },
                title: {
                    text: '歌曲统计',
                    left: 'center'
                },
                // legend: {
                //     orient: 'vertical',
                //     left: 'left',
                // },
                toolbox: {
                    show: true,
                    feature: {
                        mark: { show: true },
                        dataView: { show: true, readOnly: false },
                        restore: { show: true },
                        saveAsImage: { show: true }
                    }
                },

                series: [
                    {
                        name: '',
                        type: 'pie',
                        radius: [50, 180],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10
                        },
                        // label: {
                        //     show: false,
                        //     position: 'center'
                        // },
                        // roseType: 'area',
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 40,
                                fontWeight: 'bold'
                            }
                        },
                        // labelLine: {
                        //     show: false
                        // },
                        data: data,
                    }
                ]
            };
            option && myChart.setOption(option);
        },
    },

}
</script>
<style scoped></style>