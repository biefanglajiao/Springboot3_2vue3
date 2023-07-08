package com.example.springboot3_2vue3.service.scheduled;

import com.example.springboot3_2vue3.Utils.SpringUtils;
import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;
import com.example.springboot3_2vue3.mapper.scheduled.ScheduledcronMapper;
import jakarta.annotation.Resource;


/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/8 14:16
 **/
public interface ScheduledOfTask extends Runnable {


    /**
     * 定时任务方法
     */
    void execute();
    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {
        ScheduledcronService repository= SpringUtils.getBean(ScheduledcronService.class);
        Scheduledcron scheduledCron = repository.findByCronKey(this.getClass().getName());
        if (!scheduledCron.isStatus()) {  //1是启用 0是禁用  默认是1为false
            System.out.println(scheduledCron.isStatus());
            // 任务是禁用状态
            return;
        }
        execute();
    }

}
