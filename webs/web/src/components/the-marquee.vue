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

    <div style="width: 100%;height:248px;"  v-for="a in aa" :key="a.id"><h3>{{a.name}}</h3></div>

  </a-carousel>
</template>
<script lang="ts">
import { LeftCircleOutlined, RightCircleOutlined } from '@ant-design/icons-vue';
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
export default defineComponent({
  components: {
    LeftCircleOutlined,
    RightCircleOutlined,
  },
  setup (){
    const aa=ref();
    const a = () => {
      axios.get("/a/a").then((res) => {
        console.log(res.data.content,"aaaaa");
        aa.value=res.data.content;
      });

    };
    onMounted(() => {
      a();
    });
    return {
      aa,
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
