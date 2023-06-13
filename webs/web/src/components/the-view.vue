<!--将可视化写成自定义组件-->
<template>
  <div id="login">

  </div>

    <div >
        <a-row >
            <a-col :span="24">
<!--                <a-card>-->
                    <a-row >
                        <a-col :span="3">
                          <a-card>
                          <div id="XiaoHaoDianNeng" style="width: 100%;height:200px;"></div>
                          </a-card>
                        </a-col>
                        <a-col :span="13">
                          <a-card>
   <div id="main" style="width: 100%;height:200px;"></div>
                          </a-card>
                        </a-col>
                        <a-col :span="8">
 <div id="main" style="width: 100%;height:200px;"></div>
                        </a-col>
                    </a-row>

<!--                </a-card>-->
            </a-col>
          <br>
          <a-row >
            <a-col :span="24">
              <a-card>
                <a-row>
                  <a-col :span="12">

                  </a-col>
                  <a-col :span="12">

                  </a-col>
                </a-row>
              </a-card>
            </a-col>
            <a-col :span="12">
              <a-card>
                <a-row>
                  <a-col :span="12">

                  </a-col>
                  <a-col :span="12">

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
          <br>
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
        const testEcharts = () => {


           const chartDom = document.getElementById('main');
            const  myChart = echarts.init(chartDom);


         const   option = {
                xAxis: {
                    type: 'category',
                    data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        data: [150, 230, 224, 218, 135, 147, 260],
                        type: 'line'
                    }
                ]
            };

           myChart.setOption(option);
        };
        // const init30DayEchaerts=(list:any)=>{
        //     const mychart = echarts.init(document.getElementById('main'));
        //
        //     const xAxis=[];//横坐标
        //     const seriesView=[];//纵坐标
        //     const seriesVote=[];//纵坐标
        //     for (let i=0;i<list.length;i++){
        //         const record=list[i];
        //         xAxis.push(record.date);
        //         seriesView.push(record.viewIncrease);
        //         seriesVote.push(record.voteIncrease);
        //     }
        //     const option={
        //         title:{
        //             text:'最近30天阅读量和点赞量'
        //         },
        //         tooltip:{
        //             trigger:'axis'
        //         },
        //         legend: {
        //             data: ['点赞量', '阅读量', ]
        //         },
        //         grid: {
        //             left: '3%',
        //             right: '4%',
        //             bottom: '3%',
        //             containLabel: true
        //         },
        //         toolbox: {
        //             feature: {
        //                 saveAsImage: {}
        //             }
        //         },
        //         xAxis: {
        //             type: 'category',
        //             boundaryGap: false,
        //             data: xAxis,
        //         },
        //         yAxis: {
        //             type: 'value'
        //         },
        //         series: [
        //             {
        //                 name: '阅读量',
        //                 type: 'line',
        //                 stack: 'Total',
        //                 smooth: true,
        //                 data: seriesView,
        //             },
        //             {
        //                 name: '点赞量',
        //                 type: 'line',
        //                 stack: 'Total',
        //                 smooth: true,
        //                 data: seriesVote
        //             },]
        //     };
        //     mychart.setOption(option);
        // };

        // const get30DayStatistic=()=>{
        //     axios.get("/ebook-Snapshot/get-30statistic").then((res)=>{
        //         const data=res.data;
        //         if (data.success){
        //             const list=data.content;
        //             init30DayEchaerts(list);
        //         }
        //     });
        // };
        onMounted(() => {
            getStatistic();
           testEcharts();
          mychartsXHDN();
            // get30DayStatistic();
        });
      /****
       * @description: 消耗电能表（仪表盘形式）
       */
      const mychartsXHDN=()=>{
          const mychartXiaoHaoDianNeng = echarts.init(document.getElementById('XiaoHaoDianNeng'));
          const colorStyle = [[1,new echarts.graphic.LinearGradient(
              0, 0, 1, 0, [
                {
                  offset: 0,
                  color: 'rgba(22, 148, 255, 0.1)',
                },
                {
                  offset: 1,
                  color:'rgba(63, 250, 250, 0.9)',
                }
              ]
          )]]
          const option = {
            backgroundColor: "#0b3c66",
            series: [
              //最外的圆圈（外层刻度）
              {
                type: 'gauge',
                center: [
                  '50%',
                  '55%'
                ],
                radius: '90%',
                startAngle: 220,
                endAngle: -40,
                min: 0,
                max: 100,
                axisLine: {
                  show: true,
                  lineStyle: {
                    width: 3,
                    color:colorStyle
                  }
                },
                axisLabel: {
                  show: 0
                },
                axisTick: {
                  lineStyle: {
                    color:'rgba(63,250,250,0.7)',
                    width: 1
                  },
                  length: 5
                },
                splitLine: {
                  length: 8,
                  lineStyle: {
                    color:'rgba(63,250,250,0.8)',
                    width: 3
                  }
                },
              },
              // 外围刻度（第二层）
              {
                type: 'gauge',
                center: [
                  '50%',
                  '55%'
                ],
                radius: '82%', // 1行3个
                min: 0,
                max: 100,
                startAngle: 220,
                endAngle: -40,
                axisLine: { // 坐标轴线
                  lineStyle: { // 属性lineStyle控制线条样式
                    color: colorStyle,
                    fontSize: 20,
                    width: 2,
                    opacity: 1, //刻度背景宽度
                  }
                },
                splitLine: {
                  show: false
                },
                axisLabel: {
                  show: false
                },
                axisTick: {
                  show: false
                },
              },
              // 外围刻度（中间有背景颜色那块，包括里面的刻度）
              {
                type: 'gauge',
                center: [
                  '50%',
                  '55%'
                ],
                radius: '82%', // 1行3个
                splitNumber: 10,
                min: 0,
                max: 100,
                startAngle: 220,
                endAngle: -40,
                //分隔线样式
                axisTick: {
                  lineStyle: {
                    color:'rgba(63,250,250,0.8)',
                    width: 1
                  },
                  length: 5
                },
                //刻度样式
                axisLine: {
                  show: true,
                  lineStyle: {
                    width: 100,
                    color: colorStyle
                  }
                },
                //整数分隔线
                splitLine: {
                  show: true,
                  length: 7,
                  lineStyle: {
                    color:'rgba(63, 250, 250, 0.8)',
                    width: 2
                  }
                },
                //刻度数字
                axisLabel: {
                  show: true,
                  distance: 1,
                  textStyle: {
                    color:'rgba(63, 250, 250, 0.8)',
                    fontSize: '18',
                    fontWeight: 'bold'
                  }
                },
              },
              //从外数第三条线
              {
                type: 'gauge',
                center: [
                  '50%',
                  '55%'
                ],
                radius: '65%', // 1行3个
                splitNumber: 10,
                min: 0,
                max: 100,
                startAngle: 220,
                endAngle: -40,
                axisLine: { // 坐标轴线
                  lineStyle: { // 属性lineStyle控制线条样式
                    color: colorStyle,
                    fontSize: 20,
                    width: 2,
                    opacity: 1, //刻度背景宽度
                  }
                },
                splitLine: {
                  show: false
                },
                axisLabel: {
                  show: false
                },
                pointer: {
                  show: false
                },
                axisTick: {
                  show: false
                },
                detail: {
                  show: 0
                }
              },
              // 内侧指针、数值显示
              {
                name: '',
                center: [
                  '50%',
                  '50%'
                ],
                type: 'gauge',
                radius: '74%', // 1行3个
                splitNumber: 10,
                min: 0,
                max: 100,
                startAngle: 220,
                endAngle: -40,
                axisLine: {
                  show: true,
                  lineStyle: {
                    width: 50,
                    color: [
                      [
                        1,
                        new echarts.graphic.LinearGradient(
                            0, 0, 1, 0, [
                              {
                                offset: 0,
                                color: 'rgba(0, 199, 187, 0)',
                              },
                              {
                                offset: 1,
                                color: 'rgba(0, 199, 187, 0)',
                              }
                            ]
                        )
                      ],
                    ]
                  }
                },
                axisTick: {
                  show: 0,
                },
                splitLine: {
                  show: 0,
                },
                axisLabel: {
                  show: 0
                },
                pointer: {
                  show: true,
                  length: '102%',
                  width: 8,
                  itemStyle: {
                    color:colorStyle
                  }
                },
                data: [
                  {
                    value: 22.8,
                    name: '消耗电能\nKWh',
                    title: {
                      offsetCenter: ['0%', '50%'],
                      fontSize: 30,
                      color:'#4fe8d6'
                    },
                    detail: {
                      offsetCenter: ['0%', '20%'],
                      valueAnimation: true,
                      fontSize: 40,
                      color:'#4fe8d6'
                    }
                  }
                ]
              }
            ]
          };
          mychartXiaoHaoDianNeng.setOption(option);
        }

        return {
            statistic,


        }


    }
});


</script>
