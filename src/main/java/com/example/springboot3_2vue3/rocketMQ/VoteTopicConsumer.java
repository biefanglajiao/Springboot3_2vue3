package com.example.springboot3_2vue3.rocketMQ;


import com.example.springboot3_2vue3.websocket.WebSocketServer;
import jakarta.annotation.Resource;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/5/15 22:17
 * @consumerGroup="default": 对应配置文件的group
 * @topic="VOTE_TOPIC" 对应docservice中的topic
 **/
@Service
@RocketMQMessageListener(consumerGroup = "default", topic = "VOTE_TOPIC")
public class VoteTopicConsumer implements RocketMQListener<MessageExt> {
    private static final Logger LOG = LoggerFactory.getLogger(VoteTopicConsumer.class);

    @Resource
    private WebSocketServer webSocketServer;

    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        LOG.info("收到消息：{}", new String(body));
        webSocketServer.sendInfo(new String(body));
    }

}
