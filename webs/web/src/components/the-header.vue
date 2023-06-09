<!--将header写成自定义组件-->
<template>
    <a-layout class="layout">
        <a-layout-header>
            <div class="logo"/>
            <a-menu
                    theme="dark"
                    mode="horizontal"
                    class="menu-flex"
                    :style="{ lineHeight: '64px' }"
            >
                <a-menu-item key="home" class="menu-left">
                    <router-link to="/">首页</router-link>
                </a-menu-item>
                <a-menu-item key="admin_book" v-if="user.id">
                    <router-link to="/admin/ebook">电子书管理</router-link>
                </a-menu-item>
                <a-menu-item key="admin_user" v-if="user.id">
                    <router-link to="/admin/user">用户管理</router-link>
                </a-menu-item>

                <a-menu-item key="admin_catepory" v-if="user.id">
                    <router-link to="/admin/category">分类管理</router-link>
                </a-menu-item>
                <a-menu-item key="about" v-else>
                    <router-link to="/about">关于我们</router-link>
                </a-menu-item>
                <!--  :style="user.id? {} : {display:'none'}" 在menu上使用v-show无效，所以通过自定义css达到不登陆不显示效果-->

                <a-menu-item class="menu-right" v-if="user.id">
                    <span>您好：{{ user.name}}</span>
                </a-menu-item>
                <a-menu-item class="menu-right" @click="showLoginModal" v-else>
                    <span>登录</span>
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

            <a-modal
                    title="登录"
                    v-model:visible="loginModalVisible"
                    :confirm-loading="loginModalLoading"
                    @ok="login"
            >
                <a-form :model="loginUser" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" >
                    <a-form-item label="用户名">
                        <a-input v-model:value="loginUser.loginName"/>
                    </a-form-item>
                    <a-form-item label="密码">
                        <a-input v-model:value="loginUser.password" type="password"/>
                    </a-form-item>
                </a-form>
            </a-modal>

        </a-layout-header>
    </a-layout>
</template>
<script lang="ts">
import {defineComponent, ref, computed} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import store from "@/store";

declare let hexMd5: any;
declare let KEY: any;

export default defineComponent({
    name: 'the-header',

    setup() {
        //用于登录
        const loginUser = ref({
            loginName: '',
            password: ''
        });
        //用于回显
        const user = computed(() => store.state.user); //从store中获取用户信息

        const loginModalVisible = ref(false);
        const loginModalLoading = ref(false);
        const showLoginModal = () => {
            loginModalVisible.value = true;
        };

        const checkPassword = (password: any) => {
            //密码长度不能小于8位且要包含英文和数字
            const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,}$/;
            return reg.test(password);

        }

        //登录
        const login = () => {
            console.log("login");
            loginModalLoading.value = true;
            if (!checkPassword(loginUser.value.password)) {
                message.error("密码长度不能小于8位且要包含英文和数字");
                loginModalLoading.value = false;
                return false;
            }
            loginUser.value.password = hexMd5(loginUser.value.password + KEY);
            axios.post('/user/login', loginUser.value).then((response) => {
                loginModalLoading.value = false;
                const data = response.data;
                if (data.success) {
                    loginModalVisible.value = false;

                    message.success("登录成功");
                    store.commit('setUser', data.content);//将用户信息存入store
                } else {
                    message.error(data.message);

                }

            });
        }
        //退出登录
        const logout = () => {
            console.log("logout");
            axios.get('/user/logout/' + user.value.token).then((response) => {
                const data = response.data;
                if (data.success) {
                    message.success("退出登录成功");
                    store.commit('setUser', {});//将用户信息制空
                } else {
                    message.error(data.message);

                }

            });
        }


        return {
            loginUser,
            loginModalVisible,
            loginModalLoading,
            showLoginModal,
            login,
            logout,


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