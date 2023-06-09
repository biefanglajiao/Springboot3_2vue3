package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.springboot3_2vue3.Utils.CopyUtils;
import com.example.springboot3_2vue3.Utils.SnowFlake;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;
import com.example.springboot3_2vue3.exception.BusinessException;
import com.example.springboot3_2vue3.exception.BusinessExceptionCode;
import com.example.springboot3_2vue3.mapper.equipmapper.EquipMapper;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
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
    private EquipMapper equipmapper;
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private SnowFlake snowFlake;


    public Long finsumnumber() {

        return equipmapper.selectCount(null);
    }

    public Long finopensumnumber() {
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        return equipmapper.selectCount(queryWrapper);
    }


    public List<Equipment> findAllOpenInfo() {
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        return equipmapper.selectList(queryWrapper);
    }

    public List<Equipment> findAllInfo() {

        return equipmapper.selectList(null);
    }

    //开启设备
    public int openequip(Long id) {
        UpdateWrapper<Equipment> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("state", 1);
        rocketMQTemplate.convertAndSend("VOTE_TOPIC", "您有一台设备已开启");
        return equipmapper.update(null, updateWrapper);

//        Equipment equipment=new Equipment();
//        equipment.setId(id);
//        equipment.setState(true);
//return equipmapper.updateById(equipment);
    }

    //关闭设备   关闭的同时计算一次耗电量
    @Transactional
    public boolean closeequip(Long id) {
        UpdateWrapper<Equipment> updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        updateWrapper.set("state", 0);
        rocketMQTemplate.convertAndSend("VOTE_TOPIC", "您有一台设备已关闭");
        if (equipmapper.update(null, updateWrapper) == 1) {
            return true;
        } else
            return false;


    }

    public int insert(Equipment equipment) {
        Equipment equipment1 = CopyUtils.copy(equipment, Equipment.class);//将请求参数更新为实体
        System.out.println(equipment1);
        equipment1.setId(snowFlake.nextId());
        return equipmapper.insert(equipment1);
//        if (equipmapper.insertinfo(equipment1)==1){
//            return true;
//        }else
//            return false;

    }

    public boolean delete(long id) {
        if (equipmapper.deleteById(id) == 1) {
            return true;
        } else {
            return false;
        }

    }

    public List<Equipment> selectByname(String name) {
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", name);
        return equipmapper.selectList(queryWrapper);
    }
    public List<Equipment> selectBylocation(String location) {
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("location", location);
        return equipmapper.selectList(queryWrapper);
    }

    public int update(Equipment equipment) {
        return equipmapper.updateById(equipment);
    }

    public List<Equipment> selectbystatus(int status) {
        QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", status);
        return equipmapper.selectList(queryWrapper);
    }

    public List<Equipment> selectbypower(int power) {
        if (power == 1) {
            //根据power字段升序
            QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByAsc("power");
            List<Equipment> list = equipmapper.selectList(queryWrapper);
            return list;
        } else if (power == 0) {
            //根据power字段降序
            QueryWrapper<Equipment> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("power");
            List<Equipment> list = equipmapper.selectList(queryWrapper);
            return list;
        } else throw new BusinessException(BusinessExceptionCode.PARAMETER_VALIDATION_ERROR);


    }


}
