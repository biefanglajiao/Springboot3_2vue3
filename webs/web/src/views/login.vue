<template>
    <div class="body">
        <div class="main">

            <a-form
                    :model="loginUser"
                    name="basic"
                    :label-col="{ span: 8 }"
                    :wrapper-col="{ span: 16 }"
                    autocomplete="off"
            >
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
<script lang="ts">
import {computed, defineComponent, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";
import router from '@/router';

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
    background-color: #ecf0f3;
    color: #a0a5a8;
}

.main {
    position: relative;
    width: 1000px;
    min-width: 1000px;
    min-height: 600px;
    height: 600px;
    padding: 25px;
    background-color: #ecf0f3;
    box-shadow: 10px 10px 10px #d1d9e6, -10px -10px 10px #f9f9f9;
    border-radius: 20px;
    overflow: hidden;
}

@media (max-width: 1200px) {
    .main {
        transform: scale(0.7);
    }
}

@media (max-width: 1000px) {
    .main {
        transform: scale(0.6);
    }
}

@media (max-width: 800px) {
    .main {
        transform: scale(0.5);
    }
}

@media (max-width: 600px) {
    .main {
        transform: scale(0.4);
    }
}
</style>