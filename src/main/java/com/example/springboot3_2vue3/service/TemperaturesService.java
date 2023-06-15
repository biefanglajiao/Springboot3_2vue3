package com.example.springboot3_2vue3.service;

import com.example.springboot3_2vue3.domain.equipment.Temperature;
import com.example.springboot3_2vue3.mapper.equipmapper.TemperaturesMapper;
import com.example.springboot3_2vue3.resp.TemperatureResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 8:26
 **/
@Service
public class TemperaturesService {
    @Resource
    private TemperaturesMapper temperaturesMapper;

    public List<TemperatureResp> findAllTemp(){
        List<Temperature> temps = temperaturesMapper.findAllTemp();
        List<TemperatureResp> resps = new ArrayList();
        for (Temperature temp : temps) {
            TemperatureResp resp = new TemperatureResp();
            resp.setId(temp.getId());
            resp.setClasses(temp.isClasses());
            resp.setName(temp.getEquipment().getName());
            resp.setLevel(temp.isLevel());
            resp.setLocation(temp.getEquipment().getLocation());
            resp.setTemperatures(temp.getTemperatures());
            resp.setEquipid(temp.getEquipment().getId());
            resps.add(resp);
        }
        return resps;
    }
}
