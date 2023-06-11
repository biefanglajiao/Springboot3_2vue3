<template>

    <a-layout-content style="padding: 0 50px">

        <a-layout style="padding: 24px 0; background: #fff">

          <div>

              <a-button @click="fullScreen">全屏</a-button>

            <div  id="view"></div>



                <TheView></TheView>
          </div>



        </a-layout>
    </a-layout-content>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, onUnmounted, reactive} from 'vue';
import axios from "axios";
import {UserOutlined, HomeOutlined, FireOutlined, FileTextOutlined} from '@ant-design/icons-vue';
import {Tool} from "@/utils/tool";
import {message} from "ant-design-vue";
import TheView from "@/components/the-view.vue";
import screenfull from "screenfull";

export default defineComponent({
  name: 'home',
  components: {
    TheView
  },
  setup() {
    const fullScreen = () => {
    message.info('全屏');
         if (screenfull.isEnabled && !screenfull.isFullscreen) {
           screenfull.request();
         }
  }
  return {
    fullScreen,
  }
  }
}) ;



</script>
<style>


</style>