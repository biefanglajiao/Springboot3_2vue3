package com.example.springboot3_2vue3.mapper.scheduled;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/8 14:09
 **/
public interface ScheduledcronMapper extends BaseMapper<Scheduledcron> {
    List<Scheduledcron> findByCronKey(String cronKey);
}
