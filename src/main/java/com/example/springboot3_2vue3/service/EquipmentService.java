package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.mapper.equipmapper.Equipmapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/14 11:49
 **/
@Service
public class EquipmentService {
    @Resource
    private Equipmapper equipmapper;

    public Long finsumnumber(){

      return equipmapper.selectCount(null);
    }
    public Long finopensumnumber(){
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);
       return  equipmapper.selectCount(queryWrapper);
    }


}
