package com.example.springboot3_2vue3.schedule;

import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.service.EquipmentService;
import jakarta.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 22:34
 **/
public class equip {
    @Resource
    private EquipmentService equipmentService;
    //每小时执行一次
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void  耗电量小时统计(){
        List<Equipment>  list=equipmentService.findAllOpenInfo();

        //获取当前时间
     //todo 根据设备开启状况 每小时写一次耗电量

    }
}
