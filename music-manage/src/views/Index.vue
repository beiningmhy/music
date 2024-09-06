<template>
    <div style="overflow: auto;height: 90vh">
        <div>
            这是首页
            <span>欢迎用户{{ user.name }}</span>

        </div>
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
// import * as echarts from "echarts";
export default {
    name: "INDEX",
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
            activeName: '1',
            tableData: [],
            // provinces :require('province-city-china/dist/data.json'),
            

        }
    },
    created() {
        // console.log(this.provinces);
        
    },
    mounted() {
        // this.findNotice();
        // this.initEcharts();
    },
    methods: {
        // findNotice() {
        //     request.get("/notice").then(res => {
        //         if (res.code === '0') {
        //             // console.log(res.data);

        //             this.tableData = res.data;
        //             this.activeName = res.data[0].id;
        //         } else {
        //             this.$message({
        //                 message: res.msg,
        //                 type: 'error'
        //             });
        //         }
        //     })
        // },
        // initEcharts() {
        //     request.get("/book/echarts/bie2").then(res => {
        //         if (res.code === '0') {
        //             // console.log(res.data);
        //             this.initBie(res.data);
        //         } else {
        //             this.$message({
        //                 message: res.msg,
        //                 type: 'error'
        //             });
        //         }
        //     })
        //     request.get("/book/echarts/bar2").then(res => {
        //         if (res.code === '0') {
        //             // console.log(res.data);
        //             this.initBar(res.data.xAxis, res.data.yAxis);
        //             this.initLine(res.data.xAxis, res.data.yAxis)
        //         } else {
        //             this.$message({
        //                 message: res.msg,
        //                 type: 'error'
        //             });
        //         }
        //     })
        // },
        // initBie(data) {
        //     let chartDom = document.getElementById('bie');
        //     let myChart = echarts.init(chartDom);
        //     let option;

        //     option = {
        //         title: {
        //             text: '图书统计（折线图）',
        //             subtext: '统计维度：图书分类',
        //             left: 'center'
        //         },
        //         tooltip: {
        //             trigger: 'item'
        //         },
        //         legend: {
        //             orient: 'vertical',
        //             left: 'left'
        //         },
        //         series: [
        //             {
        //                 type: 'pie',
        //                 radius: ['20%', '70%'],
        //                 avoidLabelOverlap: false,
        //                 itemStyle: {
        //                     borderRadius: 10,
        //                     borderColor: '#fff',
        //                     borderWidth: 2
        //                 },
        //                 label: {
        //                     show: false,
        //                     position: 'center'
        //                 },
        //                 emphasis: {
        //                     label: {
        //                         show: true,
        //                         fontSize: 10,
        //                         fontWeight: 'bold'
        //                     }
        //                 },
        //                 labelLine: {
        //                     show: false
        //                 },
        //                 data: data,
        //             }
        //         ]
        //     };

        //     option && myChart.setOption(option);

        // },
        // initBar(xAxis, yAxis) {
        //     let chartDom = document.getElementById('bar');
        //     let myChart = echarts.init(chartDom);
        //     let option;

        //     option = {
        //         title: {
        //             text: '图书统计（柱状图）',
        //             subtext: '统计维度：图书分类',
        //             left: 'center'
        //         },
        //         xAxis: {
        //             type: 'category',
        //             data: xAxis
        //         },
        //         yAxis: {
        //             type: 'value'
        //         },
        //         series: [
        //             {
        //                 data: yAxis,
        //                 type: 'bar',
        //                 showBackground: true,
        //                 backgroundStyle: {
        //                     color: 'rgba(180, 180, 180, 0.2)'
        //                 }
        //             }
        //         ]
        //     };

        //     option && myChart.setOption(option);
        // },
        // initLine(xAxis, yAxis) {
        //     let chartDom = document.getElementById('line');
        //     let myChart = echarts.init(chartDom);
        //     let option;

        //     option = {
        //         title: {
        //             text: '图书统计（折线图）',
        //             subtext: '统计维度：图书分类',
        //             left: 'center'
        //         },
        //         xAxis: {
        //             type: 'category',
        //             data: xAxis
        //         },
        //         yAxis: {
        //             type: 'value'
        //         },
        //         series: [
        //             {
        //                 data: yAxis,
        //                 type: 'line'
        //             }
        //         ]
        //     };

        //     option && myChart.setOption(option);
        // },
    }
}
</script>