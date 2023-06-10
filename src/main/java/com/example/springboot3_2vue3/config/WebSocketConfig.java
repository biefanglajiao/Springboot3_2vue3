package com.example.springboot3_2vue3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/5/15 14:37
 **/
@Configuration
public class WebSocketConfig {
    //声明我这个springboot应用要暴露、使用websocket了
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
