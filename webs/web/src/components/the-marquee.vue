<template style="width: 100%;height:248px;">

    <a-carousel autoplay>
      <div style="width: 100%;height:248px;"  v-for="item in templists" :key="item.id">
        <a-card>
          <h1>当前设备为：{{item.name}}</h1>
          <h1>位置：{{item.location}}</h1>
          <h2   v-if="item.classes&&item.temperatures>24 " >当前温度为：{{item.temperatures}}℃</h2>
          <h1 v-else-if="item.classes&&item.temperatures<=24"  >当前温度为：{{item.temperatures}}℃</h1>


          <h2   v-else-if="!item.classes&&item.temperatures>70 " >当前湿度为：{{item.temperatures}}%</h2>
          <h1 v-else>当前湿度为：{{item.temperatures}}%</h1>


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
import { LeftCircleOutlined, RightCircleOutlined } from '@ant-design/icons-vue';
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
declare let echarts: any;
export default defineComponent({
  components: {
    LeftCircleOutlined,
    RightCircleOutlined,
  },
  setup (){

    //============================================================================================================================================================
    const aa=ref();
    const a = () => {
      axios.get("/a/a").then((res) => {
        // console.log(res.data.content,"aaaaa");
        aa.value=res.data.content;
      });

    };
    const templists=ref();
    const  tempall=()=>{
      axios.get("/equipment/tempall").then((res)=>{
        const data = res.data;
        if (data.success) {
         // console.log(data.content,"dataaaaa")
          templists.value=res.data.content;
        }
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
  background: #364d79;
  overflow: hidden;
}

.ant-carousel :deep(.slick-slide div) {
  color: #1185cc;
}
.ant-carousel :deep(.slick-slide h2) {
  color: #dc0000;
}

</style>
