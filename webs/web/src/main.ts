import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Antd from  'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import axios from 'axios'
import {Tool} from "@/utils/tool";

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import VueParticles from 'vue-particles';

createApp(App).use(store).use(router).use(Antd).use(VueParticles).mount('#app');


console.log('环境',process.env.NODE_ENV);

//改变axios的默认配置 以后所有的axios请求都会带上这个配置baseURL  和xue的不一样
axios.defaults.baseURL = process.env.VUE_APP_SERVER;

/***
 * axios拦截器
 */
axios.interceptors.request.use(function (config) {
    console.log("请求拦截器",config);
    const token=store.state.user.token;
    if(Tool.isNotEmpty(token)){
        config.headers.token=token;
        console.log("请求头新增token;"+token);
    }
    return config;
},
function (error) {
    return Promise.reject(error);
});
axios.interceptors.response.use(function (response) {
    console.log("响应拦截器",response);
    return response;
},
function (error) {
    console.log("响应错误",error);
    return Promise.reject(error);
});