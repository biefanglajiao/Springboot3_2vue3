package com.example.springboot3_2vue3.mapper.equipmapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.equipment.Deviceuse2;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/16 11:37
 **/
public interface Deviceuse2Mapper extends BaseMapper<Deviceuse2> {

    List<Deviceuse2> selectAlltodayById(int equipmentid,String date);
}
