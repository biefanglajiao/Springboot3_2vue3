<template>
  <div class="loginin">

      <div class="loginin-title">
          <van-nav-bar title="智能家居后台控制系统" />
<!--          <van-icon name="user-circle-o" size="1.5rem" />-->
          <van-row>
              <van-col span="9"></van-col>
              <van-col span="10">请先登录</van-col>
              <van-col span="5"></van-col>
          </van-row><span></span>
      </div>

      <van-form @submit="onSubmit">

          <van-cell-group inset>

              <van-field
                      v-model="username"
                      name="用户名"
                      label="用户名"
                      placeholder="用户名"
                      :rules="[{ required: true, message: '请填写用户名' }]"

              />
              <van-field
                      v-model="password"
                      type="password"
                      name="密码"
                      label="密码"
                      placeholder="密码"
                      :rules="[{ required: true, message: '请填写密码' }]"
              />
          </van-cell-group>
          <div style="margin: 16px;">
              <van-button round block type="primary" native-type="submit">
                 登录
              </van-button>
          </div>
      </van-form>

  </div>
</template>

<script>
import {defineComponent, onMounted, ref} from 'vue';
import { Col, Row } from 'vant';
import { showToast } from 'vant';
import 'vant/es/toast/style';
import { Icon } from 'vant';
import { NavBar } from 'vant';
export default defineComponent({
    name: 'login',
    components: {
        [Icon.name]: Icon,
        [NavBar.name]: NavBar,
        [Col.name]: Col,
        [Row.name]: Row,
    },
    return:{
        Icon,
        NavBar,
        Col,
        Row,
    }

})

</script>

<style lang="less">
.goods {
  padding-bottom: 50px;

  &-swipe {
    img {
      width: 100%;
      display: block;
    }
  }

  &-title {
    font-size: 16px;
  }

  &-price {
    color: #f44;
  }

  &-express {
    color: #999;
    font-size: 12px;
    padding: 5px 15px;
  }

  &-cell-group {
    margin: 15px 0;

    .van-cell__value {
      color: #999;
    }
  }

  &-tag {
    margin-left: 5px;
  }
}
</style>
