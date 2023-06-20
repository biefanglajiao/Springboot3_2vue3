package com.example.springboot3_2vue3.mapper.equipmapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.equipment.Equipment_Classification_Option;

import java.util.List;


/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/20 12:27
 **/
public interface Equipment_Classification_OptionMapper extends BaseMapper<Equipment_Classification_Option> {

    List<Equipment_Classification_Option> selectByeid(Long eid);

int deleteByeid(Long eid);

  int  insertinfo(Equipment_Classification_Option equipment_classification_option);

}
