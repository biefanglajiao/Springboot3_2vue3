package com.example.springboot3_2vue3.mapper.scheduled;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.scheduled.Dailyequipmentpoweruse;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/10 16:35
 **/
public interface DailyequipmentpoweruseMapper extends BaseMapper<Dailyequipmentpoweruse> {
    List<Dailyequipmentpoweruse> selectAlltodayById(Long equipmentid, String date);


    int updatePoweruse(Dailyequipmentpoweruse dailyequipmentpoweruse);

    int InsertPoweruse(Dailyequipmentpoweruse dailyequipmentpoweruse);


}
