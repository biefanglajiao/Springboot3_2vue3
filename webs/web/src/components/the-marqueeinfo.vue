<template style="width: 100%;height:248px;">
<!--  <a-row>-->
<!--    <a-dropdown>-->
<!--      <a class="ant-dropdown-link" @click.prevent>-->
<!--        请选择你要选择分类形式-->
<!--        <DownOutlined/>-->
<!--      </a>-->
<!--      <template #overlay>-->
<!--        <a-menu>-->
<!--          <a-menu-item v-for="list in listdad" :key="list.id" @click="findallbyid(list.id)">-->
<!--            {{ list.name }}-->
<!--          </a-menu-item>-->

<!--        </a-menu>-->
<!--      </template>-->
<!--    </a-dropdown>-->
<!--  </a-row>-->
<h1>按分类显示</h1>
  <a-radio-group >
    <a-radio-button v-for="list in listdad" :key="list.id"   :value=list.name @Click="findallbyid(list.id)" >
{{list.name}}
    </a-radio-button>
    <a-carousel autoplay dot-position="left">
      <div class="card1" style="width: 100%;height:248px;" v-for="item in templists" :key="item.id">

        <a-card size="small" title="设备未读告警">

          <h1>告警设备为：{{ item.name }}</h1>
          <h3>描述：{{ item.description }}</h3>
          <h2 class="aaa">等级：{{ item.level }}级</h2>


        </a-card>

      </div>

    </a-carousel>

  </a-radio-group>









  <!--  </template>-->

  <!--  <a-carousel arrows>-->
  <!--    <template #prevArrow>-->
  <!--      <div class="custom-slick-arrow" style="left: 10px; z-index: 1">-->
  <!--        <left-circle-outlined />-->
  <!--      </div>-->
  <!--    </template>-->
  <!--    <template #nextArrow>-->
  <!--      <div class="custom-slick-arrow" style="right: 10px" >-->
  <!--        <right-circle-outlined />-->
  <!--      </div>-->
  <!--    </template>-->

  <!-- -->
  <!--  </a-carousel>-->
</template>
<script lang="ts">
import {DownOutlined, LeftCircleOutlined, RightCircleOutlined} from '@ant-design/icons-vue';
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";

declare let echarts: any;
import {Tool} from "@/utils/tool";

export default defineComponent({
  computed: {
    Tool() {
      return Tool
    }
  },
  components: {
    DownOutlined,
    LeftCircleOutlined,
    RightCircleOutlined,
  },
  setup() {

    //============================================================================================================================================================

    const templists = ref([
      {
        id: "404",
        name: '无设备',
        description: "无",
        date: "2023-XX-XX X:X:X",
        equipmentid: 404,
        read: false,
        level: 0,
      },
    ])//给他赋初值


    const tempall = () => {
      axios.get("/alarm/selectAllNoreadinfo").then((res) => {
        // console.log(Tool.isEmpty(templists.value), "              对比")
        const data = res.data;
        if (data.success && !Tool.isEmpty(templists.value)) {
          // console.log(data.content,"dataaaaa")
          templists.value = res.data.content;
        }
        // console.log(templists, "11111111````````````````````````````````````")
      })
    }
    // ______________________________________________________________________获取分类显示板块开始__________________________________________
    const listdad = ref();
    const getdad = () => {
      axios.get("/classification/selectAll").then((res) => {
        const data = res.data;
        if (data.success) {
          listdad.value = data.content;


        }

      });
    }


    //获取主类下所有的子分类设备信息
    const findallbyid = (id: any) => {
      axios.get("/classification/selectallById/" + id).then((res) => {
        const data = res.data;
        if (data.success) {
          const list = data.content;
          console.log("分类数据 ：                                  ：", list)
        } else {
          console.log("分类数据 ：                                  ：", data)
        }
      })
    }
    onMounted(() => {
      tempall(),
          getdad(),
          findallbyid(1)

    });
    return {

      templists,

      listdad,//父分类列表
      findallbyid,
    }

  }

});
</script>
<style scoped>
/* For demo */
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 180px;
  line-height: 160px;
  background: #364D790D;
  overflow: hidden;
}

.ant-carousel :deep(.slick-slide div) {
  color: #f80000;
  font-size: 15px;
  /*颜色*/
}

.ant-carousel :deep(.slick-slide .aaa) {
  color: #dc0000;
}

</style>

