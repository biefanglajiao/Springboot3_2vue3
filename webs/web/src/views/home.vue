<template>

    <a-layout-content style="padding: 0 50px">

        <a-layout style="padding: 24px 0; background: #fff">

          <div>

              <a-button @click="fullScreen" v-show="flag">全屏</a-button>
            <a-button @click="unfullScreen" v-show="!flag">关闭全屏</a-button>

            <div  id="view"></div>



                <TheView></TheView>
          </div>



        </a-layout>
    </a-layout-content>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, onUnmounted, reactive, computed} from 'vue';
import axios from "axios";
import {UserOutlined, HomeOutlined, FireOutlined, FileTextOutlined} from '@ant-design/icons-vue';
import {Tool} from "@/utils/tool";
import {message} from "ant-design-vue";
import TheView from "@/components/the-view.vue";
import screenfull from "screenfull";
import store from "@/store";
export default defineComponent({
  name: 'home',
  components: {
    TheView
  },
  setup() {
    const  flag = computed(() => store.state.flag); //从store中获取header是否显示标志
    const fullScreen = () => {
    message.info('开启全屏');
         if (screenfull.isEnabled && !screenfull.isFullscreen) {
           screenfull.request();
          store.commit('setFlag', false);//将header隐藏
         }
  }
  const unfullScreen = () => {
    message.info('关闭全屏');
         if (screenfull.isEnabled && screenfull.isFullscreen) {
           screenfull.exit();
          store.commit('setFlag', true);//将header隐藏
         }
  }


  return {
    fullScreen,
    unfullScreen,
    flag,
  }
  }
}) ;



</script>
<style>


</style>