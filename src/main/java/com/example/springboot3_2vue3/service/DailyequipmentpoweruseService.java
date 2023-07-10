package com.example.springboot3_2vue3.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.Utils.SnowFlake;
import com.example.springboot3_2vue3.domain.equipment.Deviceuse2;
import com.example.springboot3_2vue3.domain.scheduled.Dailyequipmentpoweruse;
import com.example.springboot3_2vue3.mapper.scheduled.DailyequipmentpoweruseMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/10 16:34
 **/
@Service
public class DailyequipmentpoweruseService {
    @Resource
    private SnowFlake snowFlake;

    @Resource
    private DailyequipmentpoweruseMapper dailyequipmentpoweruseMapper;



    public Float selectAlltodayByIdd(Long equipmentid){
        //获取今天的日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datems = new Date(System.currentTimeMillis());
        String date = formatter.format(datems);
        List<Dailyequipmentpoweruse> variations= dailyequipmentpoweruseMapper.selectAlltodayById(equipmentid,date);

        if (variations.size()==0){
            return 0.0f;
        }
        else {
            return variations.get(0).getPoweruse();
        }


    }

    //插入或者更新数据
    //如果存在则更新，如果不存在则插入
    public  boolean InsertOrUpdatePoweruseTodayByEquipment(Long equipmentid,Float poweruse){
        //查询今日时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datems = new Date(System.currentTimeMillis());
        String date = formatter.format(datems);
        //查询是否存在今天的数据
        QueryWrapper<Dailyequipmentpoweruse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("equipmentid",equipmentid);
        queryWrapper.like("date",date);
        boolean exists = dailyequipmentpoweruseMapper.exists(queryWrapper);
        if (exists){
            //如果存在则更新
            Dailyequipmentpoweruse dailyequipmentpoweruse = new Dailyequipmentpoweruse();
            dailyequipmentpoweruse.setEquipmentid(equipmentid);
            dailyequipmentpoweruse.setPoweruse(poweruse);
            dailyequipmentpoweruseMapper.updatePoweruse(dailyequipmentpoweruse);
            return true;
        }
        else {
            //如果不存在则插入
          Dailyequipmentpoweruse dailyequipmentpoweruse = new Dailyequipmentpoweruse();
          dailyequipmentpoweruse.setEquipmentid(equipmentid);
            dailyequipmentpoweruse.setPoweruse(poweruse);
            dailyequipmentpoweruse.setId(snowFlake.nextId());
            dailyequipmentpoweruseMapper.InsertPoweruse(dailyequipmentpoweruse);
            return true;
        }

    }
}
