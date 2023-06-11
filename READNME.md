# 关于系统：

> 这是一个基于SpringBoot3+Vue3.0++Ant Design Vue+vant+MybatisPuls+MySQL的智能家居系统:

## 详细：

- 本系统采用了一后端+二前端的架构形式

- 后端

- > - 以Springboot3为基础，
  > - 使用Mysql作为数据库，
  > - 使用MybatisPuls进行数据库操作，
  > - 使用Redis作为缓存（用户登录token），
  > - <font color = red>使用RabbitMQ进行消息队列管理（推送提醒），</font>
  > - <font color = red>使用SpringMail进行邮件发送（推送提醒），</font>
  > - 使用SpringAOP进行自定义日志管理，
  > - 自定义拦截器进行登录校验（token验证；打印接口耗时；接口过滤（那些路径可以不登录就访问））
  > - 使用雪花算法赋值id
  > - 使用MD5算法进行后端加密
  > - 对请求和返回做封装统一管理

- 前端

- > - 使用vue3作为前端
  > - 分别使用vant和Ant Design Vue搭建前端移动端和web端操作
  > - 使用node.js初始化项目（npm）
  > - 使用axious进行请求通讯管理
  > - 使用Echarts进行可视化界面的搭建

- 使用websocket进行前后端实时通信管理

- 使用nginx实现服务器前端的部署

- 使用宝塔linux界面进行项目的部署

## 开发进程：

