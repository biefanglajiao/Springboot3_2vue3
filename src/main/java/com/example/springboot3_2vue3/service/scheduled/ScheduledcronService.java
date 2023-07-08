package com.example.springboot3_2vue3.service.scheduled;

import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;
import com.example.springboot3_2vue3.mapper.scheduled.ScheduledcronMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/8 14:24
 **/
@Service
public class ScheduledcronService {

    @Resource
    private ScheduledcronMapper scheduledcronMapper;


    public Scheduledcron findByCronKey(String cronKey) {


        Scheduledcron byCronKey = scheduledcronMapper.findByCronKey(cronKey);
//        System.out.println(byCronKey.isStatus()+"!!!!!!!");
        return byCronKey;
    }


}
