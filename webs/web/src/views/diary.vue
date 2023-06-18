<template>
  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-sider width="200" style="background: #fff">
        <div>
          <a-menu
              mode="inline"
              style="height: 100%"
          >
            <a-menu-item key="2" >
              <HomeOutlined/>
              <span>日志</span>
            </a-menu-item>
          </a-menu>
        </div>
      </a-layout-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '400px' }">


          <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="diary">
            <template #footer>
              <div>
                <b>设备使用日志</b>

              </div>
            </template>
            <template #renderItem="{ item }">
              <a-list-item key="item.title">
                <template #actions>
           <span>
           <PicLeftOutlined/>
            开启时间： {{ item.begindate }}
          </span> <span>
           <PicRightOutlined/>
            关闭时间：  {{ item.enddate }}
          </span>

                </template>

                <a-list-item-meta :description="item.description">
                  <template #title>
                    <h1>设备名：  {{ item.name }}</h1>
                  </template>
                  <template #avatar>
                   <clock-circle-outlined/>
                  </template>
                </a-list-item-meta>
                本次使用耗电量为： {{ item.powerconsumption }}  <text color="#11231">wh</text>
              </a-list-item>
            </template>
          </a-list>


      </a-layout-content>
    </a-layout>
  </a-layout-content>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {
  StarOutlined,
  LikeOutlined,
  MessageOutlined,
  UserOutlined,
  HomeOutlined,
  FireOutlined,
  FileTextOutlined,
  ClockCircleOutlined,PicLeftOutlined,PicRightOutlined,
} from '@ant-design/icons-vue';
import {Tool} from "@/utils/tool";
import {message} from "ant-design-vue";

const listData: Record<string, string>[] = [];

// for (let i = 0; i < 23; i++) {
//   listData.push({
//     href: 'https://www.antdv.com/',
//     title: `ant design vue part ${i}`,
//     avatar: 'https://joeschmoe.io/api/v1/random',
//     description:
//         'Ant Design, a design language for background applications, is refined by Ant UED Team.',
//     content:
//         'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
//   });
// }


export default defineComponent({
  name: 'Home',

  components: {
    HomeOutlined,
    FireOutlined,
    UserOutlined,
    FileTextOutlined,
    ClockCircleOutlined,
PicLeftOutlined,
    PicRightOutlined,
    StarOutlined,
    LikeOutlined,
    MessageOutlined,

  },
  setup() {

    const pagination = {
      onChange: (page: number) => {
        console.log(page);
      },
      pageSize: 5,
    };


    const diary = ref();

    const isshow = ref(true);

    /***
     * @方法描述: 全部日志信息查询方法
     */
    const handleQuertInfo = () => {
      axios.get("/deviceuse2/selectAllinfo").then((response) => {//初始化方法
        const data = response.data;
        if (data.success) {

          diary.value = data.content;
          // console.log(response)
          console.log("diaryssssssssssssssssssssss", diary.value)
        } else {
          message.error(data.message);
        }
      });
    }


    onMounted(() => {//生命周期函数

      handleQuertInfo();
    });

    return {
      diary,
      // pagination,

      handleQuertInfo,

      isshow,  //互斥方法显示是否显示欢迎页面

      listData,
      pagination,


    }
  }
});
</script>

<style>

</style>