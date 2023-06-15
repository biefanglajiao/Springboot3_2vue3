<template style="width: 100%;height:248px;">
  <a-carousel arrows>
    <template #prevArrow>
      <div class="custom-slick-arrow" style="left: 10px; z-index: 1">
        <left-circle-outlined />
      </div>
    </template>
    <template #nextArrow>
      <div class="custom-slick-arrow" style="right: 10px" >
        <right-circle-outlined />
      </div>
    </template>

    <div style="width: 100%;height:248px;"  v-for="item in templists" :key="item.id">
      <h3>当前设备为：{{item.description}}</h3>

    </div>

  </a-carousel>
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
          // console.log(data,"dataaaaa")
          templists.value=res.data.content;
        }
      })
    }
    onMounted(() => {
      // tempall()
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
  height: 210px;
  line-height: 210px;
  background: #364d79;
  overflow: hidden;
}

.ant-carousel :deep(.slick-arrow.custom-slick-arrow) {
  width: 25px;
  height: 25px;
  font-size: 25px;
  color: #fff;
  background-color: rgba(31, 45, 61, 0.11);
  opacity: 0.3;
  z-index: 1;
}
.ant-carousel :deep(.custom-slick-arrow:before) {
  display: none;
}
.ant-carousel :deep(.custom-slick-arrow:hover) {
  opacity: 0.5;
}

.ant-carousel :deep(.slick-slide h3) {
  color: #fff;
}

</style>
