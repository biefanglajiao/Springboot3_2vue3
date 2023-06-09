<template>
  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">
      <a-layout-sider width="200" style="background: #fff">
        <div>
          <a-menu
              mode="inline"
              @click="handleClick"
              style="height: 100%"
          >
            <a-menu-item key="welcome" v-show="isshowwelcome">
              <HomeOutlined/>
              <span>欢迎</span>
            </a-menu-item>
            <a-sub-menu v-for="c in categoryslevel" :key="c.id">
              <template v-slot:title>
                <span>
                  <user-outlined/>
                  {{ c.name }}
                </span>
              </template>
              <a-menu-item v-for="child in c.children" :key="child.id">{{ child.name }}</a-menu-item>
            </a-sub-menu>
          </a-menu>
        </div>
      </a-layout-sider>
      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <div class="welcome" v-show="isshowwelcome">
         <TheWelcome></TheWelcome>
        </div>
        <a-list item-layout="vertical" size="middle" :data-source="ebooks"
                :grid="{ gutter:20 , column : 3}"
                v-show="!isshowwelcome"
        >
          <!--          :pagination="pagination"   分页属性  暂时不用先-->
          <template #footer>
            <div>
              <b>电子书籍</b>
              ---常兆海zzz
            </div>
          </template>
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
          <span>
            <component :is="'LikeOutlined'" style="margin-right: 8px"/>
            <!--            点赞数-->
            {{ item.voteCount }}
          </span>
                <span>
<!--                阅读数-->
            <component :is="'FireOutlined'" style="margin-right: 8px"/>
            {{ item.viewCount }}
          </span>
                <span>
<!--               文档数-->
            <component :is="'FileTextOutlined'" style="margin-right: 8px"/>
            {{ item.docCount }}
          </span>


              </template>
              <template #extra>

              </template>
              <a-list-item-meta :description="item.description">
                <template #title>
                  <router-link :to="'/doc?ebookId='+item.id">
                    {{ item.name }}
                  </router-link>
                </template>
                <template #avatar>
                  <a-avatar :src="item.cover"/>
                </template>
              </a-list-item-meta>

            </a-list-item>
          </template>
        </a-list>

      </a-layout-content>
    </a-layout>
  </a-layout-content>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {UserOutlined, HomeOutlined,FireOutlined,FileTextOutlined} from '@ant-design/icons-vue';
import {Tool} from "@/utils/tool";
import {message} from "ant-design-vue";
import TheWelcome from "@/components/the-welcome.vue";
//测试假数据
// const listData: Record<string, string>[] = [];
//
// for (let i = 0; i < 23; i++) {
//   listData.push({
//     href: 'https://www.antdv.com/',
//     title: `ant design vue part ${i}`,
//     avatar: 'https://joeschmoe.io/api/v1/random',
//     description:
//         'Ant Design, a design language for background applications, is refined by Ant UED Team.',
//     content:
//         'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
//   });
// }
export default defineComponent({
  name: 'Home',

  components: {
    HomeOutlined,
    FireOutlined,
    UserOutlined,
    FileTextOutlined,
    TheWelcome,
  },
  setup() {
    console.log("setup")
    const ebooks = ref();

    const isshowwelcome = ref(true);

    let categoryId2 = 0;
    /***
     * @分页：暂时不用
     * todo 分页
     */
    // const pagination = {//分页
    //   onChange: (page: number) => {
    //     console.log(page);
    //   },
    //   current: 1,
    //   total:0,
    //   pageSize: 100,
    // };
    console.log("onMounted")
    /***
     * @方法描述: 分类的书籍查询方法
     */
    const handleQuertEbook = () => {
      axios.get("/ebook/list", {
        params: {
          page: 1,
          size: 100,
          categoryId2: categoryId2,
        }
      }).then((response) => {//初始化方法
        const data = response.data;
        ebooks.value = data.content.list;
        // console.log(response)
      });
    }


    onMounted(() => {//生命周期函数
      handleQueryCategory();
      // handleQuertEbook();

    });
    /***
     * @方法描述: 点击事件
     * @param params
     */
    const handleClick = (value: any) => {
      //Q：这个函数value的含义是什么？
      //A：value是一个对象，包含了点击的菜单项的信息，包括key，keyPath，item，domEvent
      // console.log("vaule!!!!!!!",value);
      if (value.key === "welcome") {
        isshowwelcome.value = true;
      } else {
        categoryId2 = value.key;
        isshowwelcome.value = false;
        handleQuertEbook();
      }
    };


    const categoryslevel = ref();
    let categorys: any;
    /***
     * @方法描述: 数据查询方法
     * @param params
     */
    const handleQueryCategory = () => {

      axios.get("/category/list",).then((response) => {

        const data = response.data;
        if (data.success) {
          categorys = data.content.list;
          console.log("categorys 值为：", categorys);
          categoryslevel.value = [];
          console.log()
          categoryslevel.value = Tool.array2Tree(categorys, 0);
          console.log(" categoryslevel 的树形结构", categoryslevel.value);
        } else {
          message.error(data.message);
        }
      });
    };
    return {
      ebooks,
      // pagination,


      categoryslevel,
      isshowwelcome,  //互斥方法显示是否显示欢迎页面
      handleClick,//菜单按钮点击事件
    }
  }
});
</script>
<style scoped>
/*scoped 作用  限制样式只在当前组件生效*/
.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;


}</style>
<style>

</style>