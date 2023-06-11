<!--将header写成自定义组件-->
<template>

    <div>
        <a-row>
            <a-col :span="24">
                <a-card>
                    <a-row>
                        <a-col :span="8">
                            <a-statistic title="总阅读量" :value="statistic.viewCount">
                                <template #suffix>
                                    <UserOutlined/>
                                </template>
                            </a-statistic>
                        </a-col>
                        <a-col :span="8">
                            <a-statistic title="总点赞量" :value="statistic.voteCount">
                                <template #suffix>
                                    <Like-outlined/>
                                </template>
                            </a-statistic>
                        </a-col>
                        <a-col :span="8">
                            <a-statistic title="点赞率" :value="statistic.voteCount/statistic.viewCount*100"
                                         :precision="2"
                                         suffix="%"
                                         :value-style="{color: '#cf1322'}">
                                <template #suffix>
                                    <Like-outlined/>
                                </template>
                            </a-statistic>
                        </a-col>
                    </a-row>
                </a-card>
            </a-col>
        </a-row>
        <br>
        <a-row :gutter="16">
            <a-col :span="12">
                <a-card>
                    <a-row>
                        <a-col :span="12">
                            <a-statistic title="昨日阅读量" :value="statistic.todayViewCount">
                                <template #suffix>
                                    <UserOutlined/>
                                </template>
                            </a-statistic>
                        </a-col>
                        <a-col :span="12">
                            <a-statistic title="昨日点赞量" :value="statistic.todayVoteCount">
                                <template #suffix>
                                    <like-outlined/>
                                </template>
                            </a-statistic>
                        </a-col>
                    </a-row>
                </a-card>
            </a-col>
            <a-col :span="12">
                <a-card>
                    <a-row>
                        <a-col :span="12">
                            <a-statistic title="预计今日阅读量" :value="statistic.todayViewIncrease">
                                <template #suffix>
                                    <UserOutlined/>
                                </template>
                            </a-statistic>
                        </a-col>
                        <a-col :span="12">
                            <a-statistic title="预计今日阅读增长" :value="statistic.todayViewIncreaseRateAbs"
                                         :precision="2"
                                         suffix="%"
                                         :value-style="statistic.todayViewIncreaseRate <0 ?{color: '#3f8600'} : {color: '#cf1322'}"
                            >
                                <template #prefix>
                                    <arrow-down-outlined v-if="statistic.todayViewIncreaseRate<0"/>
                                    <arrow-up-outlined v-else/>
                                </template>
                            </a-statistic>
                        </a-col>
                    </a-row>
                </a-card>
            </a-col>
        </a-row>
        <br>
        <a-row>
            <a-col :span="24">
                <div id="main" style="width: 100%;height:300px;"></div>
            </a-col>
        </a-row>
    </div>
</template>
<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {UserOutlined, ArrowUpOutlined, ArrowDownOutlined, LikeOutlined} from '@ant-design/icons-vue';

declare  let echarts: any;
export default defineComponent({
    name: 'the-welcome',
    components: {
        UserOutlined,
        LikeOutlined,
        ArrowDownOutlined,
        ArrowUpOutlined
    },

    setup() {
        const statistic = ref();
        statistic.value = {};
        const getStatistic = () => {
            axios.get("/ebook-Snapshot/get-statistic").then((res) => {
                const data = res.data;
                if (data.success) {
                    const ststisticResp = data.content;
                    if (ststisticResp.length < 2) {
                        //对昨天没有数据的情况做处理
                        statistic.value.viewCount = ststisticResp[0].viewCount;
                        statistic.value.voteCount = ststisticResp[0].voteCount;
                        statistic.value.todayViewCount = statistic.value.viewCount;
                        statistic.value.todayVoteCount = statistic.value.voteCount;
                    } else {
                        statistic.value.viewCount = ststisticResp[1].viewCount;
                        statistic.value.voteCount = ststisticResp[1].voteCount;
                        statistic.value.todayViewCount = ststisticResp[0].viewIncrease;
                        statistic.value.todayVoteCount = ststisticResp[0].voteIncrease;
                    }

                    //按分钟计算当前时间点站一天的百分比
                    const now = new Date();
                    const nowRate = (now.getHours() * 60 + now.getMinutes()) / (24 * 60);
                    statistic.value.todayViewIncrease = parseInt(String(ststisticResp[1].viewIncrease / nowRate));
                    statistic.value.todayViewIncreaseRate = (statistic.value.todayViewIncrease - ststisticResp[0].viewIncrease) / ststisticResp[0].viewIncrease * 100;
                    statistic.value.todayViewIncreaseRateAbs = Math.abs(statistic.value.todayViewIncreaseRate);
                }

            });
        };
        // const testEcharts = () => {
        //
        //
        //    const chartDom = document.getElementById('main');
        //     const  myChart = echarts.init(chartDom);
        //
        //
        //  const   option = {
        //         xAxis: {
        //             type: 'category',
        //             data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        //         },
        //         yAxis: {
        //             type: 'value'
        //         },
        //         series: [
        //             {
        //                 data: [150, 230, 224, 218, 135, 147, 260],
        //                 type: 'line'
        //             }
        //         ]
        //     };
        //
        //    myChart.setOption(option);
        // };
        const init30DayEchaerts=(list:any)=>{
            const mychart = echarts.init(document.getElementById('main'));
            const xAxis=[];//横坐标
            const seriesView=[];//纵坐标
            const seriesVote=[];//纵坐标
            for (let i=0;i<list.length;i++){
                const record=list[i];
                xAxis.push(record.date);
                seriesView.push(record.viewIncrease);
                seriesVote.push(record.voteIncrease);
            }
            const option={
                title:{
                    text:'最近30天阅读量和点赞量'
                },
                tooltip:{
                    trigger:'axis'
                },
                legend: {
                    data: ['点赞量', '阅读量', ]
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: xAxis,
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '阅读量',
                        type: 'line',
                        stack: 'Total',
                        smooth: true,
                        data: seriesView,
                    },
                    {
                        name: '点赞量',
                        type: 'line',
                        stack: 'Total',
                        smooth: true,
                        data: seriesVote
                    },]
            };
            mychart.setOption(option);
        };

        const get30DayStatistic=()=>{
            axios.get("/ebook-Snapshot/get-30statistic").then((res)=>{
                const data=res.data;
                if (data.success){
                    const list=data.content;
                    init30DayEchaerts(list);
                }
            });
        };
        onMounted(() => {
            getStatistic();
            // testEcharts();
            get30DayStatistic();
        });


        return {
            statistic,


        }


    }
});


</script>
