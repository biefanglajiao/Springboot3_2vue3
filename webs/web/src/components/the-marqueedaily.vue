<template style="width: 100%;height:248px;">

  <a-carousel autoplay>
    <div class="card1" style="width: 100%;height:248px;" v-for="item in templists" :key="item.id">

      <a-card>

        <h1>当前设备为：{{ item.name }}</h1>
        <h3>位置：{{ item.location }}</h3>
        <h2 class="aaa" v-if="item.classes&&item.temperatures>28 ">当前温度为：{{ item.temperatures }}℃</h2>
        <h2 v-else-if="item.classes&&item.temperatures<=28">当前温度为：{{ item.temperatures }}℃</h2>


        <h2 class="aaa" v-else-if="!item.classes&&item.temperatures>70 ">当前湿度为：{{ item.temperatures }}%</h2>
        <h2 v-else>当前湿度为：{{ item.temperatures }}%</h2>


      </a-card>

    </div>

  </a-carousel>
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
import {LeftCircleOutlined, RightCircleOutlined} from '@ant-design/icons-vue';
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
    LeftCircleOutlined,
    RightCircleOutlined,
  },
  setup() {

    //============================================================================================================================================================
    const aa = ref();
    const a = () => {
      axios.get("/a/a").then((res) => {
        // console.log(res.data.content,"aaaaa");
        aa.value = res.data.content;
      });

    };
    const templists = ref([
      {
        id: "404",
        name: '无检测器启动',
        location: "无",
        classes: "",
        temperatures: "",
      },
    ])//给他赋初值


    const tempall = () => {
      axios.get("/equipment/tempall").then((res) => {
        // console.log(Tool.isEmpty(templists.value), "              对比")
        const data = res.data;
        if (data.success && !Tool.isEmpty(templists.value)) {
          // console.log(data.content,"dataaaaa")
          templists.value = res.data.content;
        }
        // console.log(templists, "11111111````````````````````````````````````")
      })
    }
    onMounted(() => {
      tempall()
      a();
    });
    return {
      aa,
      templists,
    }

  }

});
</script>
<style scoped>
/* For demo */
.ant-carousel :deep(.slick-slide) {
  text-align: center;
  height: 200px;
  line-height: 160px;
  background: #364D790D;
  overflow: hidden;
}

.ant-carousel :deep(.slick-slide div) {
  color: #1185cc;
}

.ant-carousel :deep(.slick-slide .aaa) {
  color: #dc0000;
}

</style>

<style lang="less" scoped>
.card1 {
  :deep( .ant-card ) {
    background: rgba(255, 255, 255, 0.08);
  }
}
</style>