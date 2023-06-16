package com.example.springboot3_2vue3.mapper.equipmapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.equipment.Deviceuse;
import com.example.springboot3_2vue3.resp.DeviceusePowerResp;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/16 8:23
 **/
public interface DeviceuseMapper extends BaseMapper<Deviceuse> {
    List<DeviceusePowerResp> findall();
    DeviceusePowerResp findone(Long id);
}
