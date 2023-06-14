package com.example.springboot3_2vue3.websocket;

import com.alibaba.fastjson.JSONObject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


import java.util.HashMap;

/**
 * @Author: 常兆海
 * @Description:  类似controller  用来处理websocket请求
 * @DateTime: 2023/5/15 14:39
 **/

@Component
@ServerEndpoint("/websocket/{token}")
public class WebSocketServer {
    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(WebSocketServer.class);
    /**
     * 每一个客户端一个token
     */
    private String token="";

    private  static HashMap<String , Session> map=new HashMap<>();//放置所有的连接


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {

        map.put(token,session);
        this.token=token;
        LOG.info("有新窗口开始监听:"+token+",当前在线人数为" + map.size()+"  session.id:"+session.getId());
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        map.remove(this.token);  //从set中删除
      LOG.info("连接关闭，token：{}，session.id：{} 当前连接数：{} ", this.token,session.getId(),map.size());
    }
    /**
     * 收到消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        LOG.info("收到来自窗口"+token+"的信息:"+message);
    }
    /**
     * 发生错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
      LOG.error("发生错误",error);

    }
    /**
     * 群发自定义消息
     * */
    public  void sendInfo(String message) {
        for (String token :map.keySet()) {
            Session session=map.get(token);
     try {
         session.getBasicRemote().sendText(message);
     } catch (Exception e) {
         LOG.error("发送消息出错：{},内容:{}",token,message);
     }
     LOG.info("发送消息：{},内容:{}",token,message);
        }
     }
     /**
     * 群发天气信息
     * */
    public  void sendWeatherInfo(JSONObject message) {
        for (String token :map.keySet()) {
            Session session=map.get(token);
     try {
         session.getBasicRemote().sendObject(message);
     } catch (Exception e) {
         LOG.error("发送消息出错：{},内容:{}",token,message);
     }
     LOG.info("发送消息：{},内容:{}",token,message);
        }
     }
}
