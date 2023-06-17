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
              <a-card :bordered="false">
                <div id="XiaoHaoDianNeng" style="width: 100%;height:200px;"></div>
              </a-card>
            </div>
          </a-col>
          <a-col :span="13">
            <a-card>
              <div id="RiLi" style="width: 100%;height:200px;"></div>
            </a-card>
          </a-col>
          <a-col :span="8">
            <div class="schedule_card2">
              <a-card>
                <div id="XJXH" style="width: 100%;height:200px;"></div>

              </a-card>
            </div>
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
            <div class="schedule_card2">
              <!--              开启透明-->
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
                      <div class="schedule_card2">
                        <a-card>

                          <themarquee></themarquee>

                        </a-card>
                      </div>
                    </a-col>
                  </a-row>
                </div>
              </a-card>
            </div>
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
import {Tool} from '@/utils/tool';

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

    /****
     * @description: 消耗电能表（仪表盘形式）
     */
    const mychartsXHDN = (todaypoweruse: any) => {
      const mychartXiaoHaoDianNeng = echarts.init(document.getElementById('XiaoHaoDianNeng'));
      let value = todaypoweruse/1000;
      let int = value.toFixed(2);
      let num = 50 * value/100;

      const  option = {
        backgroundColor: 'rgba(255,255,255,0)',
        title: {
          text: '\n\n\n\n\n今日耗电总量\n{num|' + int + '}kwh',
          x: 'center',
          y: 'center',
          textStyle: {
            color:'#fff',
            fontSize:24,
            rich: {
              num: {
                fontSize: 48,
                color: '#29EEF3',
              }
            },
          },
        },
        series: [
          {
            type: 'gauge',
            radius: '85%',
            startAngle: 180,
            endAngle: 0,
            clockwise: true,
            splitNumber: 30,
            zlevel: 10,
            detail: {
              offsetCenter: [0, -20],
              formatter: ' ',
            },
            pointer: {
              show: true,
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
              lineStyle: {
                color: [[
                  1, new echarts.graphic.LinearGradient(
                      0, 0, 1, 0, [{
                        offset: 0,
                        color: '#8520DA',
                      },
                        {
                          offset: 1,
                          color: '#12D9FD',
                        }
                      ]
                  )
                ],],
                width: 20,
              },
            },
            splitLine: {
              show: true,
              length: 20,
              lineStyle: {
                color: '#012c93',
                width: 4,
              },
            },
            axisLabel: {
              show: false,
            },
            data:{
              value: 55.33
            }
          },
          {
            type: 'pie',
            name: '内层细圆环',
            radius: ['79%', '85%'],
            hoverAnimation: false,
            zlevel: 2,
            clockWise: true,
            startAngle: 180,
            itemStyle: {
              normal: {
                color: '#09dafd',
              },
            },
            label: {
              show: false,
            },
            data: [
              {
                value: num,
                itemStyle: {
                  normal: {
                    color: new echarts.graphic.LinearGradient(
                        0, 0, 1, 0, [{
                          offset: 0,
                          color: '#8520DA',
                        },
                          {
                            offset: 1,
                            color: '#12D9FD',
                          }
                        ]),
                  },
                },
              },
              {
                value: 50-num,
                itemStyle: {
                  normal: {
                    color: '#21449B',
                  },
                },
              },
              {
                value: 50,
                itemStyle: {
                  normal: {
                    label: {
                      show: false,
                    },
                    labelLine: {
                      show: false,
                    },
                    color: 'rgba(0,0,0,0)',
                    borderWidth: 0,
                  },
                  emphasis: {
                    color: 'rgba(0,0,0,0)',
                    borderWidth: 0,
                  },
                },
              },
            ],
          },
          {
            type: 'pie',
            name: '内层细圆环',
            radius: ['87%', '88%'],
            hoverAnimation: false,
            zlevel: 2,
            clockWise: true,
            startAngle: 180,
            itemStyle: {
              normal: {
                color: '#09dafd',
              },
            },
            label: {
              show: false,
            },
            data: [
              {
                value: 50,
                itemStyle: {
                  normal: {
                    color: '#09dafd',
                  },
                },
              },
              {
                value: 50,
                itemStyle: {
                  normal: {
                    label: {
                      show: false,
                    },
                    labelLine: {
                      show: false,
                    },
                    color: 'rgba(0,0,0,0)',
                    borderWidth: 0,
                  },
                  emphasis: {
                    color: 'rgba(0,0,0,0)',
                    borderWidth: 0,
                  },
                },
              },
            ],
          },
          {
            type: 'pie',
            name: '内层细圆环',
            radius: ['92%', '94%'],
            hoverAnimation: false,
            zlevel: 2,
            clockWise: true,
            startAngle: '180',
            itemStyle: {
              normal: {
                color: '#07b6e3',
              },
            },
            label: {
              show: false,
            },
            data: [
              {
                value: 50,
                itemStyle: {
                  normal: {
                    color: '#07b6e3',
                  },
                },
              },
              {
                value: 50,
                itemStyle: {
                  normal: {
                    label: {
                      show: false,
                    },
                    labelLine: {
                      show: false,
                    },
                    color: 'rgba(0,0,0,0)',
                    borderWidth: 0,
                  },
                  emphasis: {
                    color: 'rgba(0,0,0,0)',
                    borderWidth: 0,
                  },
                },
              },
            ],
          },
          {
            type: 'pie',
            name: '内层环',
            radius: ['80%', '100%'],
            startAngle: '180',
            hoverAnimation: false,
            clockWise: true,
            zlevel: 0,
            itemStyle: {
              normal: {
                color: '#012c93',
              },
            },
            label: {
              show: false,
            },
            data: [
              {
                value: 50,
                itemStyle: {
                  normal: {
                    color: '#012c93',
                  },
                },
              },
              {
                value: 50,
                itemStyle: {
                  normal: {
                    label: {
                      show: false,
                    },
                    labelLine: {
                      show: false,
                    },
                    color: 'rgba(0,0,0,0)',
                    borderWidth: 0,
                  },
                  emphasis: {
                    color: 'rgba(0,0,0,0)',
                    borderWidth: 0,
                  },
                },
              },
            ],
          },
        ],
      };


      mychartXiaoHaoDianNeng.setOption(option);
    }


    /***
     * @description: 各个设备的消耗：
     */
    const mychartXJXH = (allclassinfo: any, allinfo: any) => {
      console.log(allclassinfo, "allclassinfo")
      console.log(allinfo, "allinfo")
      const mychartXiJieXiaoHao = echarts.init(document.getElementById('XJXH'));
      const category: any[] = [];//开启的设备名的列表
      const categoryid = [];//开启的设备id的列表


      //将所有的分类信息按分类id和分类名 分别存入数组中
      for (let i = 0; i < allclassinfo.length; i++) {
        const record = allclassinfo[i];
        category.push(Tool.copy(record.equipment.name));
        categoryid.push(Tool.copy(record.equipmentid));
        // console.log(record.equipment.name,"      name")

      }

      const records = [];//开启的设备名的对应耗电记录  用来暂时存放某一类的耗电数据
      const recordssum: unknown[] = [];//开启的设备名的对应耗电统计   用来按分好的类存放的耗电数据
      for (let i = 0; i < categoryid.length; i++) {
        console.log("i", i)
        // console.log(categoryid[i],"      categoryid[i]")
        for (let j = 0; j < allinfo.length; j++) {
          // console.log("j",j)
          if (allinfo[j].equipmentid == categoryid[i]) {
            // console.log("true")
            // console.log(records,"      records前")
            // console.log(allinfo[j].date,"      allinfo.date")
            // console.log(allinfo[j].data,"      allinfo.data")
            records.push([Tool.copy(allinfo[j].date), Tool.copy(allinfo[j].data)]);

            // console.log(records,"      records后")

// console.log(j)
          }

        }

        recordssum.push(Tool.copy(records));//使用深拷贝赋值 防止下面pop操作时对recordssum发生影响
        // console.log(recordssum,"      recordssum               aaaaaaaaaaaaaaaa")
        for (let k = 0; k < records.length;) {//pop元素以后会让他的length变短 所以不能k++
          // console.log(records.length,"      records.length")
          records.pop();
          //  console.log(k,"      k")
          //  console.log(records.pop(),"      records               bbbbbbbbbbbbbbbbbbb")
        }

        // console.log(records,"      records")
        console.log(recordssum, "      recordssum")
      }
      // console.log(category,"category")
      // console.log(categoryid,"categoryid")
      const option = {


        title: {
          text: "\n日\n\n设\n\n备\n\n耗\n\n电\n\n量\nkw/h",

          left: "left",
          top: "left",
          textStyle: {
            fontSize: 13,
            color: '#4fe8d6',
          },

        },
        // 提示框组件
        tooltip: {
          trigger: 'axis', // 触发类型。坐标轴触发
          axisPointer: {
            // 坐标轴指示器配置项
            type: 'cross', // 指示器类型 十字准星指示器
            label: {
              // 坐标轴指示器的文本标签
              backgroundColor: '#e6b600' // 文本标签的背景颜色就是x轴y轴上的内容
            }
          }
        },
        // 组件离容器的距离
        grid: {
          top: '3%',
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true // grid 区域是否包含坐标轴的刻度标签
        },
        legend: {
          data: category,
          textStyle: {
            color: '#b0a7a7',
            fontSize: 12
          },
        },
        // 配置x轴
        xAxis: {
          type: 'time',
          axisTick: {
            show: false
          }
        },
        yAxis: {
          type: 'value',
          axisLabel: {
            interval: 0,
            fontSize: 12,
            color: '#fff'
          },
          axisLine: {show: true},
          splitLine: {
            // 修改背景线条样式  x、y轴都可以给添加
            show: true, // 是否展示
            lineStyle: {
              color: '#0A3C40' // 线条颜色
            }
          }
        },
        // 配置折线图数据
        series: function () {
          var serie = [];
          for (var i = 0; i < category.length; i++) {
            var item = {
              smooth: true, // 是否曲线
              name: category[i],
              type: 'line',
              data: recordssum[i],

            }
            serie.push(item);
          }
          return serie;
        }()

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
                  color: 'rgba(5,172,231,0.67)',
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
    //______________________________________________________________________________每日耗电量开始_______________________________________________________
    //获取日均用电中种类个数

    const getallclass = () => {
      axios.get("/variation/getallclass").then((res) => {
        const data = res.data;
        if (data.success) {
          const allclassinfo = data.content;
          console.log("分类数据 ：                                  ：", allclassinfo)

          allandname(allclassinfo)
        } else {
          console.log("分类数据 ：                                  ：", data)
        }
      })
    }
    //获取日均用电数据
    const allandname = (allclassinfo: any) => {
      axios.get("/variation/allandname").then((res) => {
        const data = res.data;
        if (data.success) {
          const allInfo = data.content;
          console.log("全部数据  ：                                  ：", allInfo)
          mychartXJXH(allclassinfo, allInfo)


        } else {
          console.log("全部数据                  ：", data)
        }
      })

    }
    //____________________________________________________________________获取日耗电量信息结束__________________________________________
    //___________________________________________________________________获取日总耗电量信息开始__________________________________________
    const todaypoweruse = ref();
    const getpoweruse = () => {
      axios.get("/yearpoweruse/getpoweruse").then((res) => {
        const data = res.data;
        if (data.success) {
          todaypoweruse.value = data.content;
          mychartsXHDN(todaypoweruse.value);
        } else {
          console.log("全部数据                  ：", data)
        }
      })

    }


    //__________________________________________获取日总耗电量信息结束__________________________________________
    onMounted(() => {
      getStatistic();
      // testEcharts();


      mychartRL();
      getOpenAndSum();
      // mychartZSBSZYXS();
      // get30DayStatistic();
      getweathernow();
      getweather3d();


      //----_获取日耗电量信息

      getallclass();
      //---获取日总耗电量信息
      getpoweruse();
    });

    return {
      statistic,
      //天气相关
      listnow,
      listyubao,
      //日耗电量统计图


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
.schedule_card2 {
  :deep( .ant-card ) {
    background: #ffffff14;

  }

  :deep( .ant-card-bordered ) {
    border: 1px dotted #008cff;
  }
}
</style>