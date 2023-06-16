package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.domain.equipment.Variation;
import com.example.springboot3_2vue3.mapper.equipmapper.Equipmapper;
import com.example.springboot3_2vue3.mapper.equipmapper.VariationMapper;
import com.example.springboot3_2vue3.resp.DeviceusePower;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/14 11:49
 **/
@Service
public class EquipmentService {
    @Resource
    private Equipmapper equipmapper;
    @Resource
    private Equipment equipment;
    @Resource
    private VariationService variationService;
    @Resource
    private Variation variation;

    public Long finsumnumber(){

      return equipmapper.selectCount(null);
    }
    public Long finopensumnumber(){
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);
       return  equipmapper.selectCount(queryWrapper);
    }


    public List<Equipment> findAllOpenInfo(){
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);
        return equipmapper.selectList(queryWrapper);
    }
    //开启设备
    public int openequip(Long id){
        equipment.setId(id);
        equipment.setState(true);
return equipmapper.updateById(equipment);
    }
    //关闭设备   关闭的同时计算一次耗电量
    @Transactional
    public boolean closeequip(Long id){
        equipment.setId(id);
        equipment.setState(false);
        equipmapper.updateById(equipment);
        long startTime = System.currentTimeMillis();
      DeviceusePower deviceusePower = variationService.finone(id);//找到记录
        System.out.println(deviceusePower.getDate());
        long opendeTime = startTime - deviceusePower.getDate();
        float opendeTimemin = opendeTime / 1000 / 60 ;
        float opendeTimeHour=opendeTimemin/60;
        System.out.println(opendeTimeHour);
        //给小时耗电量统计表赋值
        variation.setEquipmentid(deviceusePower.getEquipmentid());

        variation.setData(deviceusePower.getPower() * opendeTimeHour);
     return  variationService.insertonedata(variation);

    }



}
