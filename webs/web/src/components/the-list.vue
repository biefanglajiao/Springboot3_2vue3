<template>

  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">

      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-space direction="vertical">
              <a-input-search
                  v-model:value="param.name"
                  placeholder="设备名"
                  enter-button
                  @search="handleQueryserch({page:1,size:pagination.pageSize },param.name)"
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
                 :data-source="equips"
                 :row-key="record => record.id"
                 :pagination="pagination"
                 :loading="loading"
                 @change="handleTableChange"

        >

          <template v-slot:action="{text,record}">

            <a-space size="small">
              <a-button type="primary" @click="edit(record)">
                编辑
              </a-button>
              <a-button type="dashed" v-show="record.state" @click="closequip(record.id)">关闭
              </a-button>
              <a-button type="ghost" v-show="!record.state"  @click="openequip(record.id)">
                开启
              </a-button>
              <a-button type="primary" @click="editcagory(record.id)">
                分类编辑
              </a-button>

              <!--              原有的click方法到confirm里  cacel是放弃 这里不做操作  @cancel="cancel"-->
              <a-popconfirm
                  title="删除后不可恢复，是否删除?"
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
  <a-modal title="用户表单" v-model:visible="modalVisible" :confirm-loading="modalLoading" @ok="handleModalOk">
    <a-form :model="equip" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :layout="formLayout">
      <a-form-item label="设备名">
        <a-input v-model:value="equip.name" :disabled="!!equip.id"/>
        <!--        :disabled="equip.id" id是主键，新增时id为空，修改时id不为空，所以新增时可以输入，修改时不可以输入-->
        <!--        !!可以绕过类型校验（前端f12的报错提示）-->
      </a-form-item>
      <a-form-item label="功率">
        <a-input v-model:value="equip.power"/>
      </a-form-item>
      <a-form-item label="位置" >
        <a-input v-model:value="equip.location"/>
        <!--        v-show  是否展示、、此时新增显示，编辑不显示-->
      </a-form-item>
    </a-form>
  </a-modal>

  <a-modal title="分类编辑" v-model:visible="cagoryModalVisible" :confirm-loading="resetModalLoading"
           @ok="handleresetModalOk">
    <a-form :model="categoryIds" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :layout="formLayout">
      <a-form-item :label=eqid>
        <br>
        <div v-for="cagoryid in categoryIds" :key="cagoryid.id">{{cagoryid.name}}
          <a-select
              ref="select"
              v-model:value="cagoryid.classificationOptions.ids"
              style="width: 120px"
              @focus="focus"
          >
            <a-select-option v-for="cagory in cagoryid.classificationOptions" :key="cagory.id" :value="cagory.id">{{cagory.name}}</a-select-option>
          </a-select>
        </div>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {CascaderProps, message} from "ant-design-vue";
import {Tool} from '@/utils/tool';
// 对外部引用爆红的解决方法
declare let hexMd5: any;
declare let KEY: any;

export default defineComponent({
  name: 'AdminUser',
  setup() {
    const param = ref();
    param.value = {};
    const equips = ref();
    const pagination = ref({
      current: 1,
      pageSize: 5,
      total: 0
    });
    const loading = ref(false);
    const columns = [
      {
        title: '设备名',
        dataIndex: 'name',
      }, {
        title: '设备id',
        dataIndex: 'id',
      },
      {
        title: '功率',
        dataIndex: 'power',
      },

      {
        title: '位置',
        dataIndex: 'location',
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    const modalLoading = ref<boolean>(false);
    const modalVisible = ref<boolean>(false);
    const cagoryModalVisible = ref<boolean>(false);
    const resetModalLoading = ref<boolean>(false);
    const resetModalVisible = ref<boolean>(false);
    const equip = ref();

    /*****
     * @方法描述: 编辑表单的提交
     */
    const handleModalOk = () => {//保存
      modalLoading.value = true;

      axios.post("/equipment/save", equip.value).then((response) => {
        modalLoading.value = false;//有返回就关闭加载
        const data = response.data;//data==common,resp
        if (data.success) {
          message.success(data.message);
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
      // equip.value = record;
      equip.value = Tool.copy(record);
    }


    const categoryIds = ref();
    const eqid = ref();
    //单击分类编辑
    const editcagory = (record: any) => {
      equip.value = Tool.copy(record);
      console.log("equp!!!!!!!!!!!!!!!!!!!!!!!!!!!!", equip)
      cagoryModalVisible.value = true;
      eqid.value = equip.value;
      console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!",eqid)
      // axios.get("/classification/selectAll").then((response) => {
      //
      //   const data = response.data;//data==common,resp
      //   if (data.success){
      //     categoryIds.value=data.content;
      //     console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!",categoryIds)
      //   }
      // });
      axios.get("/classification/selectallchild").then((response) => {

        const data = response.data;//data==common,resp
        if (data.success){
          categoryIds.value=data.content;
          console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!",categoryIds)
        }
      });


    }
//定义一个list数组

    const handleresetModalOk = () => {//分类编辑的保存
      const list : any[]= [];
      resetModalLoading.value = true;
      // console.log("!!!!!!!!adadadadada!!!!!!!!!!!!!!!!!!!!",categoryIds.value)

      list.push(eqid.value)
      categoryIds.value.forEach((item: any) => {
        //将ids放入list中
        list.push(item.classificationOptions.ids)
      })


      //取出categoryIds内的
      axios.post("/classification/save", list).then((response) => {
        resetModalLoading.value = false;//有返回就关闭加载
        const data = response.data;//data==common,resp
        if (data.success) {
          cagoryModalVisible.value = false;//关闭视图
          message.success(data.message);

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
     *@方法描述: 单击查询方法
     */
    const handleQueryserch = (params: any,searchValue: string) => {
      loading.value = true;

      axios.get("/equipment/selectbyname/"+searchValue).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          equips.value = data.content;
          console.log("equips.value", equips.value);
          //重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.content.total;
        } else {
          message.error(data.message);
        }
      });
    }


    /***
     *@方法描述: 单击新增按钮方法
     */
    const add = () => {
      modalVisible.value = true;
      equip.value = {};


    }
    /***
     * @方法描述: 删除按钮方法
     */


    const delet = (id: number) => {
      axios.delete("/equipment/delete/" + id).then((response) => {

        const data = response.data;
        if (data.success) {
          message.success(data.message);

          //重新加载列表
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize
          });
        } else {
          message.error(data.message);
        }

      });
    }

    /***
     * @方法描述: 数据查询方法
     */
    const handleQuery = (params: any) => {
      loading.value = true;

      axios.get("/equipment/findallinfo").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          equips.value = data.content;
          console.log("equips.value", equips.value);
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
      console.log(pagination);

    };
    /***
     * 开启设备
     */
    const openequip =(id: any)=>{

      axios.get("/equipment/openequip/"+id).then((response) => {//初始化方法
        const data = response.data;
        if (data.success) {

          message.success(data.message);
          handleQuery({
            page: 1,
            size: pagination.value.pageSize,
          });

        }
      });

    }
    const closequip =(id: any)=>{

      axios.get("/equipment/closeequip/"+id).then((response) => {//初始化方法
        const data = response.data;
        if (data.success) {

          message.success(data.message);
          handleQuery({
            page: 1,
            size: pagination.value.pageSize,
          });

        }
      });

    }

    /**
     * @方法描述: 初始进入页面就查一次数据
     */
    onMounted(() => {
      handleQuery({
        page: 1,
        size: pagination.value.pageSize,
      });

    });
    return {
      //列表
      param,
      equips,
      pagination,
      columns,
      loading,
      handleTableChange,
      handleQuery,

      //   编辑表格相关
      modalLoading,
      modalVisible,
      equip,
      handleModalOk,
      edit,
      add,
      delet,


      //   重置密码相关
      resetModalLoading,
      resetModalVisible,
      handleresetModalOk,
      handleQueryserch,

      /**
       * 分类相关
       */
      categoryIds,
      editcagory,
      cagoryModalVisible,
      eqid,



      //开关设备
      openequip,
      closequip,
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