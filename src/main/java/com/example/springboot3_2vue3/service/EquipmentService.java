package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.mapper.equipmapper.Equipmapper;
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
        UpdateWrapper<Equipment> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("state",  1);
      return equipmapper.update(null, updateWrapper);

//        Equipment equipment=new Equipment();
//        equipment.setId(id);
//        equipment.setState(true);
//return equipmapper.updateById(equipment);
    }
    //关闭设备   关闭的同时计算一次耗电量
    @Transactional
    public boolean closeequip(Long id){
        UpdateWrapper<Equipment> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("state",  0);

     if (equipmapper.update(null, updateWrapper)==1){
         return true;
     }else
         return false;


    }



}
