<template>

  <a-layout-content style="padding: 0 50px">

    <a-layout style="padding: 24px 0; background: #fff">

      <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-space direction="vertical">
              <a-input-search
                  v-model:value="param.name"
                  placeholder="根据描述查询"
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
          <a-form-item>
            <a-dropdown>
              <template #overlay>
                <a-menu >
                  <a-menu-item  key="1 ">
                    <a-button @click="statussearch(1)">
                    <tag-outlined/>
                    根据状态查询-开
                    </a-button>
                  </a-menu-item>
                  <a-menu-item  key="2"     >
                    <a-button @click="statussearch(0)">
                    <tag-outlined/>
                    根据状态查询-关
                    </a-button>
                  </a-menu-item>
                  <a-menu-item  key="3"  >
                    <a-button @click="operationsearch(1)">
                    <UserOutlined/>
                    根据操作查询-开启设备
                    </a-button>
                  </a-menu-item>
                  <a-menu-item  key="4" >
                    <a-button @click="operationsearch(0)">
                    <UserOutlined/>
                    根据操作查询-关闭设备
                    </a-button>
                  </a-menu-item>
                </a-menu>
              </template>
              <a-button @click="handleQuery">
                选择展现形式（默认）
                <DownOutlined/>
              </a-button>
            </a-dropdown>
          </a-form-item>
        </a-form>
        <a-table :columns="columns"
                 :data-source="scheduleds"
                 :row-key="record => record.id"
                 :pagination="pagination"
                 :loading="loading"
                 @change="handleTableChange"

        >
          <template v-slot:status="text">
            <a-tag v-if="text.state" color="green">开启</a-tag>
            <a-tag v-else color="red">关闭</a-tag>
          </template>
          <template v-slot:action="{text,record}">

            <a-space size="small">
              <a-button type="primary" @click="edit(record)">
                编辑
              </a-button>
              <a-button type="dashed" v-show="record.state" @click="closscheduled(record.id)">关闭
              </a-button>
              <a-button type="ghost" v-show="!record.state" @click="openscheduled(record.id)">
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
  <a-modal title="定时任务表单" v-model:visible="modalVisible" :confirm-loading="modalLoading" @ok="handleModalOk">
    <a-form :model="scheduled" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }">
      <a-form-item label="描述">
        <a-input v-model:value="scheduled.explan" :disabled="!!scheduled.id"/>
        <!--        :disabled="scheduled.id" id是主键，新增时id为空，修改时id不为空，所以新增时可以输入，修改时不可以输入-->
        <!--        !!可以绕过类型校验（前端f12的报错提示）-->
      </a-form-item>
      <a-form-item label="表达式">
        <a-input v-model:value="scheduled.cronexpression"/>
      </a-form-item>
      <a-form-item label="任务操作">
        <a-select
            ref="select"
            v-model:value="scheduled.cronkey"
            style="width: 120px"
            @focus="focus"
        >
          <a-select-option value="START_PACKAGE">开启设备</a-select-option>
          <a-select-option value="STOP_PACKAGE">关闭设备</a-select-option>
        </a-select>
      </a-form-item>
    </a-form>
  </a-modal>

  <a-modal title="分类编辑" v-model:visible="cagoryModalVisible" :confirm-loading="resetModalLoading"
           @ok="handleresetModalOk">
    <a-form :model="categoryIds" :label-col="{ span: 4 }" :wrapper-col="{ span: 14 }" :layout="formLayout">
      <a-form-item :label=eqid>
        <br>
        <div v-for="cagoryid in categoryIds" :key="cagoryid.id">{{ cagoryid.name }}
          <a-select
              ref="select"
              v-model:value="cagoryid.classificationOptions.ids"
              style="width: 120px"
              @focus="focus"
          >
            <a-select-option v-for="cagory in cagoryid.classificationOptions" :key="cagory.id" :value="cagory.id">
              {{ cagory.name }}
            </a-select-option>
          </a-select>
        </div>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import {Tool} from '@/utils/tool';
import {UserOutlined, DownOutlined, TagOutlined} from '@ant-design/icons-vue';

export default defineComponent({
  components: {
    UserOutlined,
    DownOutlined,
    TagOutlined,
  },
  name: 'AdminUser',
  setup() {
    const param = ref();
    param.value = {};
    const scheduleds = ref();
    const pagination = ref({
      current: 1,
      pageSize: 5,
      total: 0
    });
    const loading = ref(false);
    const columns = [
      {
        title: '描述',
        dataIndex: 'explan',
      },
      {
        title: '表达式',
        dataIndex: 'cronexpression',
      },
      {
        title: '任务对应操作',
        dataIndex: 'cronkey',
      },
      {
        title: '状态',
        key: 'status',
        slots: {customRender: 'status'}
      },
      {
        title: '操作',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    const modalLoading = ref<boolean>(false);
    const modalVisible = ref<boolean>(false);
    const cagoryModalVisible = ref<boolean>(false);
    const resetModalLoading = ref<boolean>(false);
    const resetModalVisible = ref<boolean>(false);
    const scheduled = ref();

    /*****
     * @方法描述: 编辑表单的提交
     */
    const handleModalOk = () => {//保存
      modalLoading.value = true;

      axios.post("/scheduled/addorupdate", scheduled.value).then((response) => {
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
          modalVisible.value = false;//关闭视图
        }

      });

    };
    /***
     *@方法描述: 单击编辑按钮方法
     */
    const edit = (record: any) => {
      modalVisible.value = true;
      // scheduled.value = record;
      scheduled.value = Tool.copy(record);
    }


    const categoryIds = ref();
    const eqid = ref();
    //单击分类编辑
    const editcagory = (record: any) => {
      scheduled.value = Tool.copy(record);
      console.log("equp!!!!!!!!!!!!!!!!!!!!!!!!!!!!", scheduled)
      cagoryModalVisible.value = true;
      eqid.value = scheduled.value;
      console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!", eqid)
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
        if (data.success) {
          categoryIds.value = data.content;
          console.log("!!!!!!!!!!!!!!!!!!!!!!!!!!!!", categoryIds)
        }
      });


    }
//定义一个list数组

    const handleresetModalOk = () => {//分类编辑的保存
      const list: any[] = [];
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
    const handleQueryserch = (params: any, searchValue: string) => {
      loading.value = true;

      axios.get("/scheduled/selectbyname/" + searchValue).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          scheduleds.value = data.content;
          console.log("scheduleds.value", scheduleds.value);
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
      scheduled.value = {};


    }
    //todo 根据描述查询    根据操作分类  根据状态分类
    /***
     * @方法描述: 根据状态分类
     */
    const  statussearch =(key:any)=>{
      axios.get("/scheduled/selectbystatus/"+key).then((response) => {
        const data = response.data;
        if (data.success) {
          scheduleds.value = data.content;
          //重置分页按钮
          pagination.value.current = 1;
          pagination.value.total = data.content.total;
        } else {
          message.error(data.message);
        }
      });
    }
    /***
     * @方法描述: 根据操作分类
     */
    const  operationsearch =(key:any)=>{
      axios.get("/scheduled/selectbyoperation/"+key).then((response) => {
        const data = response.data;
        if (data.success) {
          scheduleds.value = data.content;
          //重置分页按钮
          pagination.value.current = 1;
          pagination.value.total = data.content.total;
        } else {
          message.error(data.message);
        }
      });
    }

    /***
     * @方法描述: 删除按钮方法
     */


    const delet = (id: number) => {
      axios.delete("/scheduled/delete/" + id).then((response) => {

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

      axios.get("/scheduled/findall").then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          scheduleds.value = data.content;
          console.log("scheduleds.value", scheduleds.value);
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
    const openscheduled = (id: any) => {

      axios.get("/scheduledment/openscheduled/" + id).then((response) => {//初始化方法
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
    const closscheduled = (id: any) => {

      axios.get("/scheduledment/closescheduled/" + id).then((response) => {//初始化方法
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
      scheduleds,
      pagination,
      columns,
      loading,
      handleTableChange,
      handleQuery,

      //   编辑表格相关
      modalLoading,
      modalVisible,
      scheduled,
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
      openscheduled,
      closscheduled,
      //分类展示
      statussearch,
      operationsearch,

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