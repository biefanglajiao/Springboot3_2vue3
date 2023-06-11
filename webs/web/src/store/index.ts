import { createStore } from 'vuex'

declare let  SessionStorage: any;
const USER='USER';


const store= createStore({
  state: {
    user:SessionStorage.get(USER)||{},//获取用户信息 如果没有就是空对象 有就是用户信息
    flag:SessionStorage.get('flag')||true,
  },
  mutations: {//同步
    setUser(state,user){
      state.user = user;
      SessionStorage.set(USER,user);//存储用户信息
    },
    setFlag(state,flag){
      state.flag = flag;
    }
  },
  actions: {//异步
  },
  modules: {
  }
})


export default store;
