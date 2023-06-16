package com.example.springboot3_2vue3.mapper.equipmapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.equipment.Temperature;
import com.example.springboot3_2vue3.domain.equipment.Variation;

import java.util.ArrayList;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 22:50
 **/
public interface VariationMapper extends BaseMapper<Variation> {
    ArrayList<Variation> findAllInfo(String date);
}
