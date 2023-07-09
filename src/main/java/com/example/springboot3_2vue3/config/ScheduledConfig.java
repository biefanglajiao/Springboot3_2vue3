package com.example.springboot3_2vue3.config;

import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;
import com.example.springboot3_2vue3.mapper.scheduled.ScheduledcronMapper;
import com.example.springboot3_2vue3.service.scheduled.ScheduledOfTask;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/8 14:04
 **/
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {
    @Autowired
    private ApplicationContext context;
    @Resource
    private ScheduledcronMapper cronRepository;
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        for (Scheduledcron springScheduledCron : cronRepository.selectList(null)) {
            Class<?> clazz;
            Object task;
            try {
                clazz = Class.forName(springScheduledCron.getCronkey());
                task = context.getBean(clazz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("spring_scheduled_cron表数据" + springScheduledCron.getCronkey() + "有误", e);
            } catch (BeansException e) {
                throw new IllegalArgumentException(springScheduledCron.getCronkey() + "未纳入到spring管理", e);
            }
            Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
            // 可以通过改变数据库数据进而实现动态改变执行周期
            for (Scheduledcron cron : cronRepository.findByCronKey(springScheduledCron.getCronkey())) {
                String cronexpression = cron.getCronexpression();
                taskRegistrar.addTriggerTask(((Runnable) task),
                        triggerContext -> {

//                        String cronExpression = cronRepository.findByCronKey(springScheduledCron.getCronkey()).getCronexpression();
//                        return new CronTrigger(cronExpression).nextExecutionTime(triggerContext).toInstant();
                            return new CronTrigger(cronexpression).nextExecutionTime(triggerContext).toInstant();
                        }
                );

            }

        }
    }
    @Bean
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }
}