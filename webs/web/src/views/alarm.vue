<template>
  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-sider width="200" style="background: #fff">
        <div>
          <a-menu
              mode="inline"
              style="height: 100%"
          >
            <a-menu-item key="2">
              <HomeOutlined/>
              <span>告警</span>
            </a-menu-item>
          </a-menu>
        </div>
      </a-layout-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '400px' }">

        <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="diary">
          <template #header>
            <div>
              <b>设备使用未读告警</b>

            </div>
          </template>
          <template #renderItem="{ item }">
            <div v-if="item.read==false">
              <a-list-item key="item.title">
                <template #actions>
           <span>
           <ExceptionOutlined/>
            告警等级：
    <a-button v-if="item.level==1" type="primary" danger>{{ item.level }}级</a-button>
    <a-button v-if="item.level==2" danger>{{ item.level }}级</a-button>
    <a-button v-if="item.level==3" type="dashed" danger>{{ item.level }}级</a-button>


          </span> <span>
           <PicRightOutlined/>
            处理：  <a-button type="primary" @click="readstats(item.id)">我已知晓</a-button>
          </span>

                </template>
                <div class="des">
                  <a-list-item-meta :description="item.description" style="color: #1c1c1c">
                    <template #title>
                      <h1>告警设备名： {{ item.name }}</h1>
                    </template>
                    <template #avatar>
                      <clock-circle-outlined/>
                    </template>
                  </a-list-item-meta>
                </div>
                告警时间： {{ item.date }}
              </a-list-item>
            </div>
          </template>
        </a-list>
        <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="diary">
          <template #header>
            <div>
              <b>设备使用已读告警</b>

            </div>
          </template>
          <template #renderItem="{ item }">
            <div v-if="item.read==true">
              <a-list-item key="item.title">
                <template #actions>
           <span>
           <ExceptionOutlined/>
            告警等级：
    <a-button v-if="item.level==1" type="primary" danger>{{ item.level }}级</a-button>
    <a-button v-if="item.level==2" danger>{{ item.level }}级</a-button>
    <a-button v-if="item.level==3" type="dashed" danger>{{ item.level }}级</a-button>


          </span> <span>
           <PicRightOutlined/>
            处理：  <a-button type="primary" disabled>我已知晓</a-button>
          </span>

                </template>
                <div class="des">
                  <a-list-item-meta :description="item.description" style="color: #1c1c1c">
                    <template #title>
                      <h1>告警设备名： {{ item.name }}</h1>
                    </template>
                    <template #avatar>
                      <clock-circle-outlined/>
                    </template>
                  </a-list-item-meta>
                </div>
                告警时间： {{ item.date }}
              </a-list-item>
            </div>
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
  ClockCircleOutlined, PicLeftOutlined, PicRightOutlined,
  ExceptionOutlined,
} from '@ant-design/icons-vue';
import {Tool} from "@/utils/tool";
import {message} from "ant-design-vue";

const listData: Record<string, string>[] = [];


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
    ExceptionOutlined,


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
      axios.get("/alarm/selectAllinfo").then((response) => {//初始化方法
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


    //已读功能开发
    const readstats = (id:any) => {
      axios.get("/alarm/read/"+id).then((response) => {//初始化方法
        const data = response.data;
        if (data.success) {

          message.success(data.message);
          handleQuertInfo();

        }
      });
    }

    onMounted(() => {//生命周期函数

      handleQuertInfo();
    });

    return {
      diary,
      // pagination,
      readstats,
      handleQuertInfo,

      isshow,  //互斥方法显示是否显示欢迎页面

      listData,
      pagination,


    }
  }
});
</script>

<style lang="less" scoped>
.des {
  :deep( .ant-list-item-meta-description ) {
    font-size: 24px;
    font-family: "Lucida Calligraphy", cursive, serif, sans-serif;
    color: #c01616;

  }

}
</style>