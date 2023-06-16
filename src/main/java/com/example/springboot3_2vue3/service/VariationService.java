package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.domain.equipment.Variation;
import com.example.springboot3_2vue3.mapper.equipmapper.VariationMapper;
import com.example.springboot3_2vue3.resp.DeviceusePower;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 22:50
 **/
@Service
public class VariationService {
    @Resource
    private VariationMapper variationMapper;


    public List<Variation> getAllVariation() {
        QueryWrapper<Variation> queryWrapper = new QueryWrapper<>();

        //获取今天的日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());

        queryWrapper.like("date", formatter.format(date));
        return variationMapper.selectList(queryWrapper);
    }

    //获取所有的设备开启信息 包含power字段
    public List<DeviceusePower> findall() {
        List<DeviceusePower> lists = variationMapper.findall();
        return lists;
    }
    //获取一个的设备开启信息 包含power字段
    public DeviceusePower finone(Long equipmentid) {
       DeviceusePower  deviceusePower = variationMapper.findone(equipmentid);
        return deviceusePower;
    }
//插入新数据
    public boolean insertonedata(Variation variation) {
        if (variationMapper.insert(variation) == 1) {
            return true;
        } else
            return false;
    }
}
