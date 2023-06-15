<!--将可视化写成自定义组件-->
<template>
  <div id="login">

  </div>

  <div>
    <a-row>
      <a-col :span="24">
        <!--                <a-card>-->
        <a-row>
          <a-col :span="3">
            <div class="schedule_card2">
            <a-card  >
              <div id="XiaoHaoDianNeng" style="width: 100%;height:200px;"></div>
            </a-card>
            </div>
          </a-col>
          <a-col :span="13">
            <a-card>
              <div  id="RiLi" style="width: 100%;height:200px;"></div>
            </a-card>
          </a-col>
          <a-col :span="8">
            <a-card class="schedule_card2">
              <div id="XJXH" style="width: 100%;height:200px;"></div>

            </a-card>
          </a-col>
        </a-row>

        <!--                </a-card>-->
      </a-col>
      <br>

    </a-row>
    <a-row>
      <a-col :span="24">

        <a-row>
          <a-col :span="7">

            <a-card>
              <div style="width: 100%;height:647px;">
                <a-row>
                  <a-col :span="24">
                    <a-card style="width: 100%;height:400px;">
                      <h1>提醒列表 分两个轮播 一个提醒 一个警告 提醒有一键已读</h1>
                    </a-card>
                  </a-col>
                </a-row>
                <a-row>
                  <a-col :span="9">
                    <a-card color="red">
                      <div id="ZSBSZYXS" style="width: 100%;height:200%;"></div>

                    </a-card>
                  </a-col>
                  <a-col :span="15" style="width: 100%;height:248px;">
                    <a-card>

                      <themarquee></themarquee>

                    </a-card>
                  </a-col>
                </a-row>
              </div>
            </a-card>
          </a-col>
          <a-col :span="10">
            <a-card>
              <div style="width: 100%;height:647px;">设备状态图 按xxxx分类</div>
            </a-card>
          </a-col>
          <a-col :span="7">
            <!--            天气接口-->
            <a-card>
              <div style="width: 100%;height:647px;">
                <a-card style="width: 100%;height:400px;">


                  <div class="container">
                    <div class="nav">
                      <div class="time">上次更新时间：{{ listnow.reporttime }}</div>
                    </div>
                    <div>
                      <div class="city-info"><p class="city">{{ listnow.province }}省 {{ listnow.city }}</p>
                        <p class="weather">天气： {{ listnow.weather }}</p>
                        <h2 class="temp"><em></em>温度： {{ listnow.temperature }}℃ </h2>
                        <div class="detail"><span>风力：{{ listnow.windpower }}级</span>|
                          <span>风向：{{ listnow.winddirection }}</span>| <span>空气湿度：{{ listnow.humidity }}</span>
                        </div>
                      </div>
                      <div class="future">
                        <div class="time">上次更新时间：{{ listyubao.reporttime }}</div>
                        <div class="group"> 明天： <span
                            class="tm">白天: {{ listyubao.dayweather0 }} {{
                            listyubao.daytemp0
                          }}℃ {{ listyubao.daypower0 }}级 {{ listyubao.daywind0 }}风  </span> <span
                            class="tm"> 夜间: {{ listyubao.nightweather0 }} {{
                            listyubao.nighttemp0
                          }} ℃ {{ listyubao.nightpower0 }}级 {{ listyubao.nightwind0 }}风             </span>
                        </div>
                        <div class="group"> 后天： <span
                            class="tm">白天: {{ listyubao.dayweather1 }} {{
                            listyubao.daytemp1
                          }}℃ {{ listyubao.daypower1 }}级 {{ listyubao.daywind1 }}风  </span> <span
                            class="tm"> 夜间: {{ listyubao.nightweather1 }} {{
                            listyubao.nighttemp1
                          }} ℃ {{ listyubao.nightpower1 }}级 {{ listyubao.nightwind1 }}风             </span>
                        </div>
                      </div>
                    </div>
                  </div>


                </a-card>
                <a-card style="width: 100%;height:248px;">
                  <div>
                    <h1>当前为xxxxx模式 全部都展示（轮播图） 按钮确定形式</h1>
                  </div>

                </a-card>

              </div>
            </a-card>


          </a-col>
        </a-row>

      </a-col>

    </a-row>

  </div>


</template>
<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";

import {UserOutlined, ArrowUpOutlined, ArrowDownOutlined, LikeOutlined} from '@ant-design/icons-vue';
import Themarquee from "@/components/the-marquee.vue";

declare let echarts: any;
export default defineComponent({
  name: 'the-welcome',
  components: {

    UserOutlined,
    LikeOutlined,
    ArrowDownOutlined,
    ArrowUpOutlined,
    Themarquee,
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
      const myChart = echarts.init(chartDom);


      const option = {
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
      // testEcharts();
      mychartsXHDN();
      mychartXJXH();
      mychartRL();
      getOpenAndSum();
      // mychartZSBSZYXS();
      // get30DayStatistic();
      getweathernow();
      getweather3d();

    });

    /****
     * @description: 消耗电能表（仪表盘形式）
     */
    const mychartsXHDN = () => {
      const mychartXiaoHaoDianNeng = echarts.init(document.getElementById('XiaoHaoDianNeng'));
      const colorStyle = [[1, new echarts.graphic.LinearGradient(
          0, 0, 1, 0, [
            {
              offset: 0,
              color: 'rgba(22, 148, 255, 0.1)',
            },
            {
              offset: 1,
              color: 'rgba(63, 250, 250, 0.9)',
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
                color: colorStyle
              }
            },
            axisLabel: {
              show: 0
            },
            axisTick: {
              lineStyle: {
                color: 'rgba(63,250,250,0.7)',
                width: 1
              },
              length: 5
            },
            splitLine: {
              length: 8,
              lineStyle: {
                color: 'rgba(63,250,250,0.8)',
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
                color: 'rgba(63,250,250,0.8)',
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
                color: 'rgba(63, 250, 250, 0.8)',
                width: 2
              }
            },
            //刻度数字
            axisLabel: {
              show: true,
              distance: 1,
              textStyle: {
                color: 'rgba(63, 250, 250, 0.8)',
                fontSize: '10',
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
                fontSize: 5,
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
                color: colorStyle
              }
            },
            data: [
              {
                value: 22.8,
                name: '今日消耗电能\nKWh',
                title: {
                  offsetCenter: ['0%', '50%'],
                  fontSize: 10,
                  color: '#4fe8d6'
                },
                detail: {
                  offsetCenter: ['0%', '20%'],
                  valueAnimation: true,
                  fontSize: 20,
                  color: '#4fe8d6'
                }
              }
            ]
          }
        ]
      };
      mychartXiaoHaoDianNeng.setOption(option);
    }


    /***
     * @description: 各个设备的消耗：
     */
    const mychartXJXH = () => {
      const mychartXiJieXiaoHao = echarts.init(document.getElementById('XJXH'));
      const option = {
        backgroundColor: "#03213D",
        color: ["#5090FF", "#01B3E4", "#FF7E00", "#99004c"],
        tooltip: {
          trigger: "axis",
          backgroundColor: "rgba(0,0,0,.6)",
          borderColor: "rgba(147, 235, 248, .8)",
          textStyle: {
            color: "#FFF",
          },
        },
        grid: {
          left: "2%",
          right: "5%",
          bottom: "5%",
          top: "30px",
          containLabel: true,
        },

        legend: {
          show: true,
          icon: "rect",
          orient: "horizontal",
          left: "right",
          itemWidth: 12,
          itemHeight: 12,
          formatter: ["{a|{name}}"].join("\n"),
          textStyle: {
            fontSize: 12,
            color: "#6A93B9",
            height: 8,
            rich: {
              a: {
                verticalAlign: "bottom",
              },
            },
          },
          data: ["一号窗口", "二号窗口", "三号窗口", "四号窗口"],
        },
        xAxis: {
          type: "category",
          axisLine: {
            lineStyle: {
              color: "#BDD8FB",
              fontSize: 12,
            },
          },
          axisLabel: {
            // interval:0,
            color: "#BDD8FB",
            fontSize: 12,
          },
          axisTick: {
            show: false,
          },
          data: [
            "计生",
            "劳保医保",
            "房屋租赁",
            "退役军人",
          ],
        },
        yAxis: {
          type: "value",
          min: 0,
          minInterval: 1,
          nameTextStyle: {
            fontSize: 12,
            color: "#BDD8FB",
            align: "center",
          },
          splitLine: {
            lineStyle: {
              color: "rgba(255, 255, 255, 0.15)",
            },
          },
          splitArea: {show: false},
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            fontSize: 12,
            fontFamily: "Bebas",
            color: "#BDD8FB",
          },
        },
        series: [
          {
            type: "line",
            smooth: true, // 是否曲线
            name: "一号窗口", // 图例对应类别
            data: [4000, 6000, 7000, 2000, 5000, 7000, 9000], // 纵坐标数据
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,  //右
                y: 0,  //下
                x2: 0,  //左
                y2: 1,  //上
                colorStops: [
                  {
                    offset: 0.1,
                    color: '#5090FF' // 0% 处的颜色
                  },
                  {
                    offset: 1,
                    color: '#1057E500' // 100% 处的颜色
                  }
                ]
              },
            },
          },
          {
            type: "line",
            smooth: true,
            name: "二号窗口",
            data: [1000, 4000, 5000, 6000, 3000, 8000, 7000],
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,  //右
                y: 0,  //下
                x2: 0,  //左
                y2: 1,  //上
                colorStops: [
                  {
                    offset: 0.1,
                    color: '#01B3E4' // 0% 处的颜色
                  },
                  {
                    offset: 1,
                    color: '#86DCF300' // 100% 处的颜色
                  }
                ]
              },
            },
          },
          {
            type: "line",
            smooth: true,
            name: "三号窗口",
            data: [1230, 4520, 5620, 7220, 3520, 8600, 7630],
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,  //右
                y: 0,  //下
                x2: 0,  //左
                y2: 1,  //上
                colorStops: [
                  {
                    offset: 0.1,
                    color: '#FF7E00' // 0% 处的颜色
                  },
                  {
                    offset: 1,
                    color: '#FF000000' // 100% 处的颜色
                  }
                ]
              },
            },
          },
          {
            type: "line",
            smooth: true,
            name: "四号窗口",
            data: [230, 4520, 5520, 6620, 3620, 8620, 7620],
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,  //右
                y: 0,  //下
                x2: 0,  //左
                y2: 1,  //上
                colorStops: [
                  {
                    offset: 0.1,
                    color: '#99004c' // 0% 处的颜色
                  },
                  {
                    offset: 1,
                    color: '#FF000000' // 100% 处的颜色
                  }
                ]
              },
            },
          },
        ],
      };
      mychartXiJieXiaoHao.setOption(option);
    }

    /*****
     * @description: 日历图
     */
    const mychartRL = () => {
      const mychartRiLi = echarts.init(document.getElementById("RiLi"));

      function getVirtulData(year: any) {
        year = year || '2017';
        var date = +echarts.number.parseDate(year + '-01-01');
        var end = +echarts.number.parseDate((+year + 1) + '-01-01');
        var dayTime = 3600 * 24 * 1000;
        var data = [];
        for (var time = date; time < end; time += dayTime) {
          data.push([
            echarts.format.formatTime('yyyy-MM-dd', time),
            Math.floor(Math.random() * 1000)
          ]);
        }
        return data;
      }

      var data = {
        '2015': getVirtulData(2015),
        '2016': getVirtulData(2016),
        '2017': getVirtulData(2017)
      };


      const option = {
        legend: {
          top: 250,
          selected: {
            '2015': true,

          },
          selectedMode: 'single'
        },
        tooltip: {
          position: 'top'
        },
        visualMap: {
          min: 0,
          max: 1000,
          calculable: true,
          orient: 'horizontal',
          left: 'center',
          top: 'top'
        },

        calendar: {
          range: '2015',
          cellSize: ['auto', 20]
        },

        series: [{
          type: 'heatmap',
          coordinateSystem: 'calendar',
          calendarIndex: 0,
          data: data[2015],
          name: '2015'
        }]
      };


      mychartRiLi.setOption(option);
    }
    /****
     * @description: 总设备数在运行数仪表盘
     */
    const mychartZSBSZYXS = (list: any) => {

      const mychart = echarts.init(document.getElementById("ZSBSZYXS"));
      let max = list[1];
      let value = list[0];


      const option = {
        backgroundColor: 'rgba(255,255,255,0)',
        title: [
          {
            text: '{a|设备启动比}\n\n\n\n{b|' + value + ':' + max + '}',
            show: true,
            x: 'center',
            y: 'top',
            textStyle: {
              rich: {
                a: {
                  fontSize: 20,
                  color: 'rgba(4,127,171,.4)',
                  padding: [0, 0, 25, 0],
                },
                b: {
                  fontSize: 20,
                  color: '#0479ab',
                  fontFamily: 'alibabaPuhuiM',
                },
              },
            },
          },
        ],
        polar: {
          center: ['50%', '50%'],
          radius: ['60%', '75%'],
        },
        angleAxis: {
          max: max,
          show: false,
        },
        radiusAxis: {
          type: 'category',
          show: true,
          axisLabel: {
            show: false,
          },
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
        },
        series: [
          {
            name: '',
            type: 'bar',
            roundCap: true,
            showBackground: true,
            backgroundStyle: {
              color: 'rgba(19, 84, 146, .4)',
            },
            data: [value],
            coordinateSystem: 'polar',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                  {
                    offset: 0,
                    color: '#005DCF',
                  },
                  {
                    offset: 1,
                    color: '#00CCFF',
                  },
                ]),
              },
            },
          },
          {
            name: '',
            type: 'gauge',
            radius: '54%',
            axisLine: {
              lineStyle: {
                color: [
                  [
                    1,
                    new echarts.graphic.LinearGradient(0, 1, 0, 0, [
                      {
                        offset: 0,
                        color: 'rgba(0, 182, 253, 0)',
                      },
                      {
                        offset: 0.5,
                        color: 'rgba(0, 182, 253, .2)',
                      },
                      {
                        offset: 1,
                        color: 'rgba(0, 182, 253, .4)',
                      },
                    ]),
                  ],
                ],
                width: 1,
              },
            },
            axisLabel: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            splitLine: {
              show: false,
            },
            itemStyle: {
              show: false,
            },
            detail: {
              show: false,
            },
            data: [],
            pointer: {
              show: false,
            },
          },
        ],
      };

      mychart.setOption(option);
    }
    /****
     * @description: 获取总设备数在运行数
     */
    const getOpenAndSum = () => {
      axios.get("/equipment/all").then((res) => {
        const data = res.data;
        if (data.success) {
          const list = data.content;

          mychartZSBSZYXS(list);
        }

      });
    }
    /*****
     * @description: 天气相关开始
     */
    const listnow = ref();
    listnow.value = {};
    const getweathernow = () => {
      axios.get("/weather/now").then((res) => {
        const data = res.data;
        if (data.success) {
          // console.log("now:     ",data);
          // console.log(data.content); console.log(data.content.lives);console.log(data.content.lives[0].province);
          listnow.value.province = data.content.lives[0].province;
          listnow.value.city = data.content.lives[0].city;
          listnow.value.weather = data.content.lives[0].weather;
          listnow.value.temperature = data.content.lives[0].temperature;
          listnow.value.winddirection = data.content.lives[0].winddirection;
          listnow.value.windpower = data.content.lives[0].windpower;
          listnow.value.humidity = data.content.lives[0].humidity;
          listnow.value.reporttime = data.content.lives[0].reporttime;

          // console.log("now:     ", listnow);
        } else {
          console.log(data);
        }
      })


    }
    const listyubao = ref();
    listyubao.value = {};//
    const getweather3d = () => {
      axios.get("/weather/yubao").then((res) => {
        const data = res.data;
        if (data.success) {
          listyubao.value = data.content.forecasts[0].casts;
          // console.log("yubao:     ", data);
          // console.log("yubao:     ", data.content.forecasts[0].casts);

          listyubao.value.reporttime = data.content.forecasts[0].reporttime;
          listyubao.value.daypower0 = data.content.forecasts[0].casts[1].daypower;
          listyubao.value.daywind0 = data.content.forecasts[0].casts[1].daywind;
          listyubao.value.daytemp0 = data.content.forecasts[0].casts[1].daytemp;
          listyubao.value.dayweather0 = data.content.forecasts[0].casts[1].dayweather;
          listyubao.value.nightpower0 = data.content.forecasts[0].casts[1].nightpower;
          listyubao.value.nightwind0 = data.content.forecasts[0].casts[1].nightwind;
          listyubao.value.nighttemp0 = data.content.forecasts[0].casts[1].nighttemp;
          listyubao.value.nightweather0 = data.content.forecasts[0].casts[1].nightweather;
          listyubao.value.daypower1 = data.content.forecasts[0].casts[2].daypower;
          listyubao.value.daywind1 = data.content.forecasts[0].casts[2].daywind;
          listyubao.value.daytemp1 = data.content.forecasts[0].casts[2].daytemp;
          listyubao.value.dayweather1 = data.content.forecasts[0].casts[2].dayweather;
          listyubao.value.nightpower1 = data.content.forecasts[0].casts[2].nightpower;
          listyubao.value.nightwind1 = data.content.forecasts[0].casts[2].nightwind;
          listyubao.value.nighttemp1 = data.content.forecasts[0].casts[2].nighttemp;
          listyubao.value.nightweather1 = data.content.forecasts[0].casts[2].nightweather;
          // console.log("yubao:     ", data.content.forecasts[0].casts[1].daypower);
          // console.log("yubao1:     ",listyubao.value.data1);


        } else {
          console.log(data);
        }
      })
    }

    // --------------------------------------------------------------------@description: 天气相关结束----------------------------------------------------
    return {
      statistic,
      //天气相关
      listnow,
      listyubao,

    }


  },

});


</script>
<style scoped>
.container {
  background: #000;
  opacity: 0.7;
  color: #fff;
}

.nav {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}

.city-info {
  text-align: center;

  .temp {
    font-size: 24px;
    color: #a0a5a8;

    em {
      font-size: 32px;
      font-style: normal;
    }
  }
}

.future {
  padding: 0 10px;
  margin-top: 30px;

  .group {
    height: 44px;
    line-height: 44px;
    background: rgba(255, 255, 255, 0.3);
    margin-bottom: 10px;
    padding: 0 10px;
    font-size: 13px;
    border-radius: 5px;
  }

}</style>

<style lang="less" scoped>
.schedule_card2  {
  :deep( .ant-card ) {
    background: #ffffff14;

  }
}
</style>