<template>
  <div>
  <vue-particles
      class="login__particles"
      color="#39AFFD"
      :particleOpacity="0.7"
      :particlesNumber="100"
      shapeType="circle"
      :particleSize="4"
      linesColor="#8DD1FE"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
  >
  </vue-particles>
    <a-layout-content style="padding: 0 50px" class="con">

        <a-layout style="padding: 24px 0; background: rgba(255,255,255,0%) ;">


            <a-row>
              <a-col :span="24">
                  <a-row>

                    <a-col :span="1">
                      <a-tooltip placement="leftBottom" >
                      <template #title>
                        <span>开启全屏</span>
                      </template>
                        <fullscreen-outlined @click="fullScreen" v-show="flag" style="color: rgba(255, 255, 255)" />

                    </a-tooltip>
                      <a-tooltip placement="leftBottom" >
                      <template #title>
                        <span>关闭全屏</span>
                      </template>
                        <fullscreen-exit-outlined @click="unfullScreen" v-show="!flag" style="color: rgba(255, 255, 235, 255)"/>

                    </a-tooltip>

                    </a-col>
                    <a-col :span="23">

                      <h1 class="form-title">智能家居可视化管理平台</h1>
                    </a-col>

                  </a-row>

              </a-col>
            </a-row>

            <div  id="view"></div>



                <TheView></TheView>




        </a-layout>
    </a-layout-content>
  </div>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref, onUnmounted, reactive, computed} from 'vue';
import axios from "axios";
import {FullscreenOutlined,FullscreenExitOutlined} from '@ant-design/icons-vue';
import {Tool} from "@/utils/tool";
import {message} from "ant-design-vue";
import TheView from "@/components/the-view.vue";
import screenfull from "screenfull";
import store from "@/store";
export default defineComponent({
  name: 'home',
  components: {
    TheView,
    FullscreenOutlined,
    FullscreenExitOutlined
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




.form-title{
  background-image: -webkit-linear-gradient(left, #91caff, #0157de 25%, #91caff 50%, #0157de 75%, #91caff);
  -webkit-text-fill-color: transparent;
  -webkit-background-clip: text;
  -webkit-background-size: 200% 100%;
  -webkit-animation:  maskedAnimation 4s infinite linear;
  font-size: 36px;
  font-family: system-ui !important;
  text-align: center;
  padding-bottom: 10px;
  font-weight: bold;
}

@keyframes maskedAnimation {
  0% {
    background-position: 0 0;
  }
  100% {
    background-position: -100% 0;
  }
}
.login__particles {
  height: 100%;
  width: 100%;
  background-size: cover;
  background-repeat: no-repeat;

  opacity:0.9;
  position:fixed;
  pointer-events: none;
}
.con{
  background-image: url('../assets/001.png');
}


</style>