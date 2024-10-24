<template>
    <div style="overflow: auto;height: 85vh">
        <div>
            这是首页
            <span>欢迎用户{{ user.name }}</span>
            <iframe width="90%" height="90%" class="embed-show"
                src="http://127.0.0.1:1234/#fileView&path=http%3A%2F%2F127.0.0.1%3A1234%2F%3Fexplorer%2Fshare%2Ffile%26hash%3Da2d2CbtYDFp8bw2nsF7opqWHbcxsmQSCi3786YKV42IyUROZ-wdjONTZG-eig99Ny_w%26name%3D%2F%25E6%2584%25BF%25E4%25B8%258E%25E6%2584%2581-%25E6%259E%2597%25E4%25BF%258A%25E6%259D%25B0.flac%26_etag%3D7f7f1%26size%3D22363583"
                allowtransparency="true" allowfullscreen="true" webkitallowfullscreen="true" mozallowfullscreen="true"
                frameborder="0" scrolling="no"></iframe>
            <iframe width="90%" height="90%" class="embed-show"
                src="http://127.0.0.1:1234/#fileView&path=http%3A%2F%2F127.0.0.1%3A1234%2F%3Fexplorer%2Fshare%2Ffile%26hash%3D7172UJoYOBUe6hxpgNLf3MkpTArlBAukeRUid1egpcoz8eXOXV0fT_Mz4uH9GvXRQvc%26name%3D%2F%25E5%258F%25A5%25E5%258F%25B7-G.E.M.%25E9%2582%2593%25E7%25B4%25AB%25E6%25A3%258B.flac%26_etag%3D53aa2%26size%3D26775816"
                allowtransparency="true" allowfullscreen="true" webkitallowfullscreen="true" mozallowfullscreen="true"
                frameborder="0" scrolling="no"></iframe>
        </div>
        
        <div>
            <div style="width: 100%;height: 300px;">
                <div style="display: flex; float: left;width: 50%;height: 100%;" id="div1">

                </div>
            </div>

            <div style="background-color: blue;width: 100%;height: 300px;">

            </div>
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
import * as echarts from "echarts";
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
    },
    mounted() {
        this.initEcharts();
    },
    methods: {
        initEcharts() {
            let chartDom = document.getElementById('div1');
            let myChart = echarts.init(chartDom);
            let option;
            option = {
                legend: {
                    top: 'bottom'
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
                        name: 'Nightingale Chart',
                        type: 'pie',
                        radius: [20, 100],
                        center: ['50%', '50%'],
                        roseType: 'area',
                        itemStyle: {
                            borderRadius: 8
                        },
                        data: [
                            { value: 400, name: 'rose 1' },
                            { value: 38, name: 'rose 2' },
                            
                        ]
                    }
                ]
            };
            option && myChart.setOption(option);

        },
    }
}
</script>