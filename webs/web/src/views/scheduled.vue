<template>

  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-sider width="200" style="background: #fff">
        <div>
          <a-menu
              mode="inline"
              style="height: 100%"
          >
            <a-menu-item key="one">
              <HomeOutlined/>
              <span @click="one">单个管理</span>
            </a-menu-item>
            <a-menu-item key="list">
              <HomeOutlined/>
              <span @click="list">批量管理</span>
            </a-menu-item>
          </a-menu>
        </div>
      </a-layout-sider>

        <the-one v-if="oneorlist">{{oneorlist}}</the-one>
        <the-list v-if="!oneorlist">{{oneorlist}}</the-list>


    </a-layout>
  </a-layout-content>

</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import {HomeOutlined} from "@ant-design/icons-vue";
import TheOne from "@/components/the-one.vue";
import TheList from "@/components/the-list.vue";
export default defineComponent({
  name: 'AdminUser',
  components: {HomeOutlined,
    TheOne,
    TheList,
  },
  setup() {
    const oneorlist = ref(true);
    const one = () => { //单个管理
      oneorlist.value = true;
      console.log(oneorlist.value)

      
    }
    const list = () => { //批量管理

      oneorlist.value = false;
      console.log(oneorlist.value)
    }
    
    /**
     * @方法描述: 初始进入页面就查一次数据
     */
    onMounted(() => {
     one();

    });
    return {

      //菜单
      one,
      list,
      //标志
      oneorlist,
    }
  }
});
</script>
