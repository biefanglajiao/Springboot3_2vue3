import { createRouter, createWebHashHistory } from 'vue-router';

const routes = [
  {
    name: 'notFound',
    path: '/:path(.*)+',
    redirect: {
      name: 'login',
    },
  },
  {
    name: 'user',
    path: '/user',
    component: () => import('./view/user'),
    meta: {
      title: '会员中心',
    },
  },
  {
    name: 'cart',
    path: '/cart',
    component: () => import('./view/cart'),
    meta: {
      title: '购物车',
    },
  },
  {
    name: 'login',
    path: '/login',
    component: () => import('./view/goods/login.vue'),
    meta: {
      title: '登录',
    },
  },
];

const router = createRouter({
  routes,
  history: createWebHashHistory(),
});

router.beforeEach((to, from, next) => {
  const title = to.meta && to.meta.title;
  if (title) {
    document.title = title;
  }
  next();
});

export { router };
