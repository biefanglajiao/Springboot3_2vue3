package com.example.springboot3_2vue3.service.scheduled;

import com.example.springboot3_2vue3.aspect.LogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/8 14:39
 **/
@Component
public class Test implements ScheduledOfTask{
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
    @Override
    public void execute() {
        System.out.println("执行了");
        LOG.info("执行了yici !!!~~~~~~~~~");
    }
}
