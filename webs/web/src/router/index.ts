import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/login.vue'
import store from "@/store";
import {Tool} from "@/utils/tool";

//非惰性 全局加载、

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Login',
    component: Home
  },
  {
    path: '/home',
    name: 'Home',
    /****
     *@解释: route level code-splitting 路由级代码分解  this generates a separate chunk (about.[hash].js) for this route 这将为该路由生成一个单独的块(约.[hash].js)  which is lazy-loaded when the route is visited. 它在访问路由时是惰性加载的。
     */
    component: () => import(/* webpackChunkName: "about" */ '../views/home.vue')
  },
  {
    path: '/about',
    name: 'About',
    /****
     *@解释: route level code-splitting 路由级代码分解  this generates a separate chunk (about.[hash].js) for this route 这将为该路由生成一个单独的块(约.[hash].js)  which is lazy-loaded when the route is visited. 它在访问路由时是惰性加载的。
     */
        component: () => import(/* webpackChunkName: "about" */ '../views/about.vue')
  },
  {
    path: '/admin/ebook',
    name: 'AdminEbook',
    meta:{loginRequire:true},
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/admin_ebook.vue')
  } ,
  {
    path: '/admin/category',
    name: 'AdminCategory',
    meta:{loginRequire:true},
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/admin_category.vue')
  },
// 非惰性 全局加载方式实现
// import AdminCategory from "@/views/admin/admin_category.vue";
//   {
//     path: '/admin/category',
//     name: 'AdminCategory',
//     component: AdminCategory,
//   }
  {
    path: '/doc',
    name: 'doc',
    component: () => import(/* webpackChunkName: "about" */ '../views/doc.vue')
  },
  {
    path: '/admin/user',
    name: 'user',
    meta:{loginRequire:true},
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/admin_user.vue')
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 路由登录拦截  在前端登录校验 如果不登陆不能通过地址栏输入路由访问
router.beforeEach((to,from,next)=>{
  //对meta.loginRequire进行检测
  if(to.matched.some(function (item){
    console.log("路由拦截，是否需登录校验",item,item.meta.loginRequire);
    return item.meta.loginRequire;
  })){
    const loginUser=store.state.user;
console.log("路由拦截，是否需登录校验",loginUser);
    if (Tool.isEmpty(loginUser)) {

      next('/');
      console.log("未登录");
    } else {
      next();
      console.log("已登录");
    }
  }
else next();

});
export default router
