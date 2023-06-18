package com.example.springboot3_2vue3.service;



import com.example.springboot3_2vue3.Utils.TimeUtils;
import com.example.springboot3_2vue3.domain.equipment.Deviceuse2;
import com.example.springboot3_2vue3.mapper.equipmapper.Deviceuse2Mapper;
import com.example.springboot3_2vue3.resp.Deviceuse2Resp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Resource
    private TimeUtils timeUtils;

    //在开启时 记录一下开启时间
public  void  addbegin(Deviceuse2 deviceuse2){
    deviceuse2Mapper.insert(deviceuse2);
}


    //在关闭时  记录一下关闭时间和      这次使用的耗电量
    public  void  addend(Deviceuse2 deviceuse2){
        deviceuse2Mapper.updateById(deviceuse2);
    }


 public List<Deviceuse2Resp> selectAllinfo(){
     List<Deviceuse2> deviceuse2List = deviceuse2Mapper.selectAllinfo();
List<Deviceuse2Resp> deviceuse2RespLists = new ArrayList<>();
     for (Deviceuse2 deviceuse2 : deviceuse2List) {
            Deviceuse2Resp deviceuse2Resp = new Deviceuse2Resp();
            deviceuse2Resp.setId(deviceuse2.getId());
            deviceuse2Resp.setEquipmentid(deviceuse2.getEquipmentid());
            deviceuse2Resp.setBegindate(deviceuse2.getBegindate());
            deviceuse2Resp.setEnddate(timeUtils.getTimeStampString(deviceuse2.getEnddate(),"yyyy-MM-dd HH:mm:ss",0));
            deviceuse2Resp.setPowerconsumption(deviceuse2.getPowerconsumption());
            deviceuse2Resp.setName(deviceuse2.getEquipment().getName());
            deviceuse2RespLists.add(deviceuse2Resp);
     }
     return  deviceuse2RespLists;
 }
}
