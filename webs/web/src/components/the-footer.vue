<!--将header写成自定义组件-->
<template>
<a-layout-footer style="text-align: center" v-show=flag>
  Ant Design ©2018 Created by CZH <span v-show="user.id">，当前登录用户为：{{user.username}}</span>
</a-layout-footer>
</template>
<script lang="ts">
import { defineComponent ,computed, onMounted} from 'vue';
import store from "@/store";
import {notification} from "ant-design-vue";
import {Tool} from "@/utils/tool";


export default defineComponent({
  name: 'the-footer',
setup(){
    const user=computed(()=>store.state.user);
  const flag = computed(() => store.state.flag); //从store中获取header是否显示标志
  /*************
   * websocket相关
   */
  let websocket: any;
  let token: any;
  const onOpen = () => {
    console.log('WebSocket连接成功，状态码：', websocket.readyState)
  };
  const onMessage = (event: any) => {
    console.log('WebSocket收到消息：', event.data);
    notification['success']({
      message: '收到新消息',
      description: event.data
    });

  };
  const onError = () => {
    console.log('WebSocket连接错误，状态码：', websocket.readyState)
  };
  const onClose = () => {
    console.log('WebSocket连接关闭，状态码：', websocket.readyState)
  };
  // 初始化WebSocket
  const initWebSocket = () => {
    // 连接成功
    websocket.onopen = onOpen;
    // 收到消息的回调
    websocket.onmessage = onMessage;
    // 连接错误
    websocket.onerror = onError;
    // 连接关闭的回调
    websocket.onclose = onClose;
  };

  const open = () => {
    // WebSocket
    if ('WebSocket' in window) {
      token = Tool.uuid(10);
      // 连接地址：ws://127.0.0.1:8080/websocket/xxx
      websocket = new WebSocket(process.env.VUE_APP_WS_SERVER + '/websocket/' + token);
      initWebSocket()

      // 关闭
      // websocket.close();
    } else {
      alert('当前浏览器 不支持')
    }
  }

  onMounted(() => {
    open();
   });
    return{
        user,
      flag
    }
}
});
</script>
