<template style="width: 100%;height:248px;">

  <a-carousel autoplay dot-position="left">
    <div class="card1" style="width: 100%;height:248px;" v-for="item in templists" :key="item.id">

      <a-card size="small" title="设备使用日志">

        <h1>设备名：{{ item.name }}</h1>
        <h1>开启时间：{{ item.begindate }}</h1>
        <h1>关闭时间：{{ item.enddate }}</h1>
       <h2 >本次耗电：{{ item.powerconsumption }}wh</h2>


      </a-card>

    </div>

  </a-carousel>

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



    const templists = ref([
      {
        begindate: "2023-X-X XX:XX:XX",
        enddate: "2023-X-X XX:XX:XX",
        powerconsumption: 0.0,
        name: "无设备使用日志"
      },
    ])//给他赋初值


    const tempall = () => {
      axios.get("/deviceuse2/selectAllinfo").then((res) => {
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

    });
    return {
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
  color: #003b73; /*标题颜色*/
  /*color: #1185cc;*/
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