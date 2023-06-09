<template>

  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">

      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-space direction="vertical">
              <a-input-search
                  v-model:value="param.name"
                  placeholder="名称"
                  enter-button
                  @search="handleQuery({page:1,size:pagination.pageSize})"
              />
            </a-space>
          </a-form-item>
          <a-form-item>
            <p>
              <a-button type="primary" @click="add()">
                新增
              </a-button>
            </p>
          </a-form-item>
        </a-form>
        <a-table :columns="columns"
                 :data-source="ebooks"
                 :row-key="record => record.id"
                 :pagination="pagination"
                 :loading="loading"
                 @change="handleTableChange"
        >
          <!--        //Q::row-key="record => record.id这个代码的含义是什么-->
          <!--        //A:row-key是一个属性，用来指定数据的主键，这里指定的是id，这样在表格中就可以通过id来唯一标识一行数据-->
          <template #cover="{ text:cover }">
            <img class="img_xhz" v-if="cover" :src="cover" alt="avatar">
            <!--            //todo 图片的处理-->
          </template>
          <template v-slot:category="{text,record}">

            <span>{{getCategoryName(record.category1Id)}}/{{getCategoryName(record.category2Id)}}</span>
          </template>
          <template v-slot:action="{text,record}">

            <a-space size="small">
              <router-link  :to="'/admin/doc?ebookId='+record.id">
              <a-button type="primary">
              文档管理
              </a-button>
              </router-link>
              <a-button type="primary" @click="edit(record)">
                编辑
              </a-button>
              <!--              原有的click方法到confirm里  cacel是放弃 这里不做操作  @cancel="cancel"-->
              <a-popconfirm
                  title="删除后不可回复，是否删除?"
                  ok-text="是"
                  cancel-text="否"
                  @confirm="delet(record.id)"
              >
                <a-button type="danger">
                  <!--                delete是关键字  @click="delet(record.id)-->
                  删除
                </a-button>
              </a-popconfirm>

            </a-space>
          </template>
        </a-table>


      </a-layout-content>
    </a-layout>
  </a-layout-content>
  <!--  //Q::confirm-loading的含义-->
  <!--  //A:confirm-loading是一个属性，当点击确定按钮时，会调用handleModalOk方法，handleModalOk方法会调用axios的post方法，保存数据，然后重新加载列表-->
  <a-modal title="电子书表单" v-model:visible="modalVisible" :confirm-loading="modalLoading" @ok="handleModalOk">
    <a-form :model="ebook" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :layout="formLayout">
      <a-form-item label="名称">
        <a-input v-model:value="ebook.name"/>
      </a-form-item>
      <a-form-item label="封面">
        <a-input v-model:value="ebook.cover"/>
      </a-form-item>
      <a-form-item label="分类">
        <a-cascader v-model:value="categoryIds"
                    :field-names="{label: 'name',value: 'id',children: 'children'}"
                    :options="categoryslevel"

        />
      </a-form-item>
      <a-form-item label="描述">
        <a-input v-model:value="ebook.description" type="text"/>
      </a-form-item>


    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import {Tool} from '@/utils/tool';

export default defineComponent({
  name: 'AdminEbook',
  setup() {
    const param = ref();
    param.value = {};
    const ebooks = ref();
    const pagination = ref({
      current: 1,
      pageSize: 5,
      total: 0
    });
    const loading = ref(false);
    const columns = [
        {
      title: '封面',
      dataIndex: 'cover',
      //   渲染
      slots: {customRender: 'cover'}
    },
      {
        title: '名称',
        dataIndex: 'name',
      },
      {
        title: '分类',
      slots: {customRender: 'category'}

      },

      {
        title: '文档数',
        dataIndex: 'docCount',
      },
      {
        title: '阅读数',
        dataIndex: 'docCount',
      },
      {
        title: '点赞数',
        dataIndex: 'docCount',
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];
    /*****
     * @方法描述: 编辑表单的提交
     */
    const modalLoading = ref<boolean>(false);
    const modalVisible = ref<boolean>(false);
    const ebook = ref();
    const categoryIds = ref();
    const handleModalOk = () => {//保存
      modalLoading.value = true;
      if (categoryIds.value ==null) {
        categoryIds.value=[500,1];//啥也不选默认为默认分类(其他-未分类)

      }
        console.log("     “啊阿达啊大大" , categoryIds.value)
        ebook.value.category1Id = categoryIds.value[0];
        ebook.value.category2Id = categoryIds.value[1];
        axios.post("/ebook/save", ebook.value).then((response) => {
          modalLoading.value = false;//有返回就关闭加载
          const data = response.data;//data==common,resp
          if (data.success) {
            modalVisible.value = false;//关闭视图
            //重新加载列表
            handleQuery({
              page: pagination.value.current,
              size: pagination.value.pageSize
            });
          } else {
            message.error(data.message);
          }

        });

    };
    /***
     *@方法描述: 单击编辑按钮方法
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      // ebook.value = record;
      ebook.value = Tool.copy(record);
      // Q:这句代码的含义
      // A:这句代码的含义是将record的值赋值给ebook.value，但是这样做会导致修改ebook.value的值的时候，record的值也会跟着改变，所以这里需要使用深拷贝的方式，将record的值赋值给ebook.value
      categoryIds.value = [ebook.value.category1Id, ebook.value.category2Id];
    }
    const categoryslevel = ref();
    let categorys :any;
    /***
     * @方法描述: 分类数据查询方法
     * @param params
     */
    const handleQueryCategory = () => {
      loading.value = true;
      axios.get("/category/list",).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
           categorys = data.content.list;
           console.log("categorys 值为：",categorys);
          categoryslevel.value = [];
          console.log()
          categoryslevel.value = Tool.array2Tree(categorys, 0);
          console.log(" categoryslevel 的树形结构",categoryslevel.value);


//加载完分类以后，在加载电子书，否则分类树加载很慢，电子数会报错
          handleQuery({
            page: 1,
            size: pagination.value.pageSize,
          });
        } else {
          message.error(data.message);
        }
      });
    };
    /***
     * @方法描述: 查询id对应name
     */
    const getCategoryName = (id: number) => {
      let reslut="";
      for (let i = 0; i < categorys.length; i++) {
        if (categorys[i].id == id) {
         reslut=categorys[i].name;
        }
      }
      return reslut;
    }
    /***
     *@方法描述: 单击新增按钮方法
     */
    const add = () => {
      modalVisible.value = true;
      ebook.value = {};
      ebook.value.cover = "url地址";

    }
    /***
     * @方法描述: 删除按钮方法
     */


    const delet = (id: number) => {
      axios.delete("/ebook/delete/" + id).then((response) => {

        const data = response.data;


        //重新加载列表
        handleQuery({
          page: pagination.value.current,
          size: pagination.value.pageSize
        });
      });
    }

    /***
     * @方法描述: 数据查询方法
     */
    const handleQuery = (params: any) => {
      loading.value = true;
      axios.get("/ebook/list", {
        params: {
          page: params.page,
          size: params.size,
          name: param.value.name,
        }
      }).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          ebooks.value = data.content.list;

          //重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        } else {
          message.error(data.message);
        }
      });
    };


    /***
     * @方法描述: 表格点击页面触发
     */
    const handleTableChange = (pagination: any) => {
      handleQuery({
        page: pagination.current,
        size: pagination.pageSize
      });

    };


    /**
     * @方法描述: 初始进入页面就查一次数据
     */
    onMounted(() => {
      /***
       *
       *    handleQueryCategory();
       *       handleQuery({
       *         page: 1,
       *         size: pagination.value.pageSize,
       *       });
       * 这两个方法是异步执行的  如果获取分类在获取电子数后面 ，电子书中给分类渲染的方法会报错误（为空--因为没有取到 ）
       * 改进 把获取电子数的方法 放到获取分类的方法中
       */
   handleQueryCategory();
    });


    return {
      //列表
      param,
      ebooks,
      pagination,
      columns,
      loading,
      handleTableChange,
      handleQuery,

      //   编辑表格相关
      modalLoading,
      modalVisible,
      ebook,
      handleModalOk,
      edit,
      add,
      delet,

      /**
       * 分类相关
       */
      categoryIds,

      categoryslevel,
      getCategoryName,
    }
  }
});
</script>
<style scoped>
/*scoped 作用  限制样式只在当前组件生效*/
.img_xhz {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px 0;


}</style>