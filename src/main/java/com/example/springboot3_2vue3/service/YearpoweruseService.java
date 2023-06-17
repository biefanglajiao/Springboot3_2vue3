package com.example.springboot3_2vue3.service;

import com.example.springboot3_2vue3.mapper.equipmapper.YearpoweruseMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/17 11:01
 **/
@Service
public class YearpoweruseService {
    @Resource
    private YearpoweruseMapper yearpoweruseMapper;

    public int InsertOrUpdatePoweruse(int poweruse){
        return yearpoweruseMapper.InsertOrUpdatePoweruse(poweruse);
    }
}
