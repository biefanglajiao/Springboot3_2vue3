<template>

    <div class="body">

      <div class="box">
        <span class="borderLine"></span>

            <a-form
                    :model="loginUser"
                    name="basic"
                    :label-col="{ span: 8 }"
                    :wrapper-col="{ span: 16 }"
                    autocomplete="off"
            >
              <a-form-item>   <div style="font-size:23px; width: 292px">欢迎使用智能家居管理系统</div>
                <div style=font-size:35px>请登录：</div>
              </a-form-item>

                <a-form-item
                        label="Username"
                        name="username"
                        :rules="[{ required: true, message: 'Please input your username!' }]"
                >
                    <a-input v-model:value="loginUser.username"/>

                </a-form-item>

                <a-form-item
                        label="Password"
                        name="password"
                        :rules="[{ required: true, message: 'Please input your password!' }]"
                >

                    <a-input-password v-model:value="loginUser.password"/>

                </a-form-item>


                <a-form-item :wrapper-col="{ offset: 8, span: 16 }">
                    <a-button type="primary" html-type="登录" @click="login">Submit</a-button>
                </a-form-item>
            </a-form>

        </div>
    </div>
</template>
<style scoped src="../styles/index.css"></style>
<script lang="ts">
import {computed, defineComponent, ref} from 'vue';
import axios from "axios";
import {message, notification} from "ant-design-vue";
import store from "@/store";
import router from '@/router';
import {Tool} from "@/utils/tool";

declare let hexMd5: any;
declare let KEY: any;

export default defineComponent({
    setup() {
        const loginUser = ref({
            username: '',
            password: ''
        });
        //用于回显
        const user = computed(() => store.state.user); //从store中获取用户信息
        const checkPassword = (password: any) => {
            //密码长度不能小于8位且要包含英文和数字
            const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/;
            return reg.test(password);
//默认密码test   tset1234
        }
        //登录
        const login = () => {
            console.log("login");

            if (!checkPassword(loginUser.value.password)) {
                message.error("密码长度不能小于8位且要包含英文和数字");
                return false;
            }
            loginUser.value.password = hexMd5(loginUser.value.password + KEY);
            axios.post('/user/login', loginUser.value).then((response) => {

                const data = response.data;
                if (data.success) {
                    message.success("登录成功");
                    store.commit('setUser', data.content);//将用户信息存入store

                   router.push({path: '/home'});//跳转到首页
                } else {
                    message.error(data.message);

                }

            });
        }


        return {
            loginUser,
            login,
            user,
        };
    },
});
</script>

<style scoped>
*, *::after, *::before {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    user-select: none;
}

.body {
    width: 100%;
    height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    font-family: "Montserrat", sans-serif;
    font-size: 12px;
  background-image: url('../assets/001.png');
}

</style>