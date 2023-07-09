package com.example.springboot3_2vue3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.Alarm;
import com.example.springboot3_2vue3.domain.equipment.Deviceuse2;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/18 11:41
 **/
public interface AlarmMapper extends BaseMapper<Alarm> {

    List<Alarm> selectAllinfo();

    List<Alarm> selectAllNoreadinfo();
    List<Alarm> selectAllinfoRead();

    boolean  insertAlarm(Alarm alarm);
}
