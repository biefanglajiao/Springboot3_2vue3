package com.example.springboot3_2vue3.mapper.equipmapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.equipment.Yearpoweruse;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/17 10:56
 **/
public interface YearpoweruseMapper extends BaseMapper<Yearpoweruse> {
   int InsertOrUpdatePoweruse(float poweruse);
}
