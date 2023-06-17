package com.example.springboot3_2vue3.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.domain.equipment.Variation;
import com.example.springboot3_2vue3.domain.equipment.Yearpoweruse;
import com.example.springboot3_2vue3.mapper.equipmapper.YearpoweruseMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/17 11:01
 **/
@Service
public class YearpoweruseService {
    @Resource
    private YearpoweruseMapper yearpoweruseMapper;

    public int InsertOrUpdatePoweruse(float poweruse){
        return yearpoweruseMapper.InsertOrUpdatePoweruse(poweruse);
    }

    public  Float getPoweruse(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datems = new Date(System.currentTimeMillis());
        String date = formatter.format(datems);
        QueryWrapper<Yearpoweruse> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("date", date);
        return yearpoweruseMapper.selectOne(queryWrapper).getPoweruse();
    }
    public List<Yearpoweruse> getAllPoweruse(){
        return yearpoweruseMapper.selectList(null);
    }
}
