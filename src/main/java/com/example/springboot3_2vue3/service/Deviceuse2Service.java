package com.example.springboot3_2vue3.service;

import com.example.springboot3_2vue3.domain.equipment.Deviceuse2;
import com.example.springboot3_2vue3.mapper.equipmapper.Deviceuse2Mapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/16 11:38
 **/
@Service
public class Deviceuse2Service {
    @Resource
    private Deviceuse2Mapper deviceuse2Mapper;

    //在开启时 记录一下开启时间
public  void  addbegin(Deviceuse2 deviceuse2){
    deviceuse2Mapper.insert(deviceuse2);
}


    //在关闭时  记录一下关闭时间和      这次使用的耗电量
    public  void  addend(Deviceuse2 deviceuse2){
        deviceuse2Mapper.updateById(deviceuse2);
    }


    public Float selectAlltodayByIdd(Long equipmentid){
        //获取今天的日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datems = new Date(System.currentTimeMillis());
        String date = formatter.format(datems);
        List<Deviceuse2> deviceuse2List=  deviceuse2Mapper.selectAlltodayById(equipmentid,date);
       if (deviceuse2List.size()==0){
           return null;
       }
else {
          return deviceuse2List.get(0).getPowerconsumption();
       }
    }
}
