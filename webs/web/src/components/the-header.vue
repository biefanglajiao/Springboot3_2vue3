<!--将header写成自定义组件-->
<template>
  <a-layout class="layout" v-show=flag>
    <a-layout-header>
      <div class="logo"/>
      <a-menu
          theme="dark"
          mode="horizontal"
          class="menu-flex"
          :style="{ lineHeight: '64px' }"

      >
        <a-menu-item key="home" class="menu-left">
          <router-link to="/home">首页</router-link>
        </a-menu-item>
        <a-menu-item key="diary" v-if="user.id">
          <router-link to="/diary">日志</router-link>
        </a-menu-item>
        <a-menu-item key="alarm" v-if="user.id">
          <router-link to="/alarm">告警</router-link>
        </a-menu-item>

<!--        <a-menu-item key="category" v-if="user.id">-->
<!--          <router-link to="/category">分类管理</router-link>-->
<!--        </a-menu-item>-->
        <a-menu-item key="equipment" v-if="user.id">
          <router-link to="/equipment">设备管理</router-link>
        </a-menu-item>
        <a-menu-item key="scheduled" v-if="user.id">
          <router-link to="/scheduled">定时任务</router-link>
        </a-menu-item>
        <a-menu-item key="about" v-else>
          <router-link to="/about">关于我们</router-link>
        </a-menu-item>
        <!--  :style="user.id? {} : {display:'none'}" 在menu上使用v-show无效，所以通过自定义css达到不登陆不显示效果-->

        <a-menu-item class="menu-right" v-if="user.id">
          <span>您好：{{ user.username }}</span>
        </a-menu-item>

        <a-popconfirm
            title="确认退出登录?"
            ok-text="是"
            cancel-text="否"
            @confirm="logout()"
        >

          <a-menu-item class="menu-right" v-if="user.id">
            <span>退出登录</span>
          </a-menu-item>
        </a-popconfirm>

      </a-menu>

    </a-layout-header>
  </a-layout>
</template>
<script lang="ts">
import {defineComponent, ref, computed} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";
import router from '@/router';

declare let hexMd5: any;
declare let KEY: any;

export default defineComponent({
  name: 'the-header',

  setup() {

    //用于回显
    const user = computed(() => store.state.user); //从store中获取用户信息
    const flag = computed(() => store.state.flag); //从store中获取header是否显示标志
    const loginModalVisible = ref(false);
    const loginModalLoading = ref(false);
    const showLoginModal = () => {
      loginModalVisible.value = true;
    };


    //退出登录
    const logout = () => {
      console.log("logout");
      axios.get('/user/logout/' + user.value.token).then((response) => {
        const data = response.data;
        if (data.success) {
          message.success("退出登录成功");
          store.commit('setUser', {});//将用户信息制空
          // 跳到首页
          router.push("/");
        } else {
          message.error(data.message);

        }

      });
    }


    return {
      loginModalVisible,
      loginModalLoading,
      showLoginModal,
      logout,

      flag,
      user,
    }


  }
});


</script>
<style>
.menu-flex {
  display: flex;
  justify-content: space-between;
}

.menu-right {
  color: white;
  padding-left: 10px;
  order: 1;


}

.menu-left {
  order: 2;

}

</style>