<template>
    <div>
        <div style="overflow: auto;height: 85vh">
            <div style="width: 50%;height: 400px;display: flex;float: left;" id="div1">

            </div>
            <div style="width: 50%;height: 400px;display: flex;float: left;" id="div2">

            </div>
        </div>
    </div>
</template>
<script>
import request from "@/utils/request";
import * as echarts from "echarts";
export default {
    name: "AdminSpace",
    data() {
        return {

        }
    },
    mounted() {
        this.initEcharts();
        this.intervalId = setInterval(() => {
            this.initEcharts();
        }, 2000);
    },
    methods: {
        initEcharts() {
            request.get("/system/cpu").then(res => {
                if (res.code === '0') {
                    this.initCpu(res.data);
                }
            })
            request.get("/system/memory").then(res => {
                if (res.code === '0') {
                    this.initMemory(res.data);
                }
            })
        },
        initCpu(data) {
            var chartDom = document.getElementById('div1');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                tooltip: {
                    formatter: '{a} <br/>{b} : {c}%'
                },
                title: {
                    text: 'CPU信息',
                    left: 'center'
                },
                series: [
                    {
                        name: 'CPU使用率',
                        type: 'gauge',
                        progress: {
                            show: true
                        },
                        detail: {
                            valueAnimation: true,
                            formatter: '{value}'
                        },
                        data: [
                            {
                                value: data.toFixed(3)
                            }
                        ]
                    }
                ]
            };

            option && myChart.setOption(option);

        },
        initMemory(data) {
            var chartDom = document.getElementById('div2');
            var myChart = echarts.init(chartDom);
            var option;

            option = {
                tooltip: {
                    formatter: '{a} <br/>{b} : {c}%'
                },
                title: {
                    text: '内存信息',
                    left: 'center'
                },
                series: [
                    {
                        name: '内存使用率',
                        type: 'gauge',
                        progress: {
                            show: true
                        },
                        detail: {
                            valueAnimation: true,
                            formatter: '{value}'
                        },
                        data: [
                            {
                                value: data.toFixed(3)
                            }
                        ]
                    }
                ]
            };

            option && myChart.setOption(option);
        },

    },
    created() {

    },
    beforeDestroy() {
        // 清除定时器，防止内存泄漏
        clearInterval(this.intervalId);
    }

}
</script>