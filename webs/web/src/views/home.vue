<template>

  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">
        <div :style="{ background: '#fff', padding: '24px', minHeight: '280px' }">
            
            
        <div class="welcome" v-show="isshowwelcome">
         <TheView></TheView>
        </div>
        </div>
    </a-layout>
  </a-layout-content>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {UserOutlined, HomeOutlined,FireOutlined,FileTextOutlined} from '@ant-design/icons-vue';
import {Tool} from "@/utils/tool";
import {message} from "ant-design-vue";
import TheView from "@/components/the-view.vue";

export default defineComponent({
  name: 'Home',

  components: {
    HomeOutlined,
    FireOutlined,
    UserOutlined,
    FileTextOutlined,
    TheView,
  },
  setup() {
    console.log("setup")

    const isshowwelcome = ref(true);




    onMounted(() => {//生命周期函数

    });
    return {
      // pagination,



      isshowwelcome,  //互斥方法显示是否显示欢迎页面

    }
  }
});
</script>
<style scoped>
/*scoped 作用  限制样式只在当前组件生效*/
.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;


}</style>
<style>

</style>