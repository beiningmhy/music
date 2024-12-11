<template>
    <div>
        <div style="display: flex;">
            <div id="div1" style="width: 40vw;height: 40vw;">

            </div>
        </div>

    </div>
</template>
<script>
import request from '@/utils/request';
import * as echarts from "echarts";
export default {
    data() {
        return {
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        }
    },
    mounted() {
        this.initEcharts();
    },
    methods: {
        initEcharts() {
            request.get('/song/songClicks/' + this.user.singerId).then(res => {
                if (res.code === '0') {
                    // console.log(res.data);
                    this.initSongClicks(res.data);
                } else {
                    this.$message({
                        message: res.msg,
                        type: 'error'
                    });
                }
            })
        },
        initSongClicks(data) {
            let chartDom = document.getElementById('div1');
            let myChart = echarts.init(chartDom);
            let option;
            option = {
                tooltip: {
                    trigger: 'item'
                },
                title: {
                    text: '歌曲播放量统计',
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
                        radius: [50, 140],
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
    }
}
</script>
<style></style>