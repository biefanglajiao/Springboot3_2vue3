package com.example.springboot3_2vue3.service.scheduled;

import org.springframework.stereotype.Component;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/9 9:19
 **/
@Component
public class Openequip implements ScheduledOfTask{
    @Override
    public void execute() {
        System.out.println("执行了");
    }
}
