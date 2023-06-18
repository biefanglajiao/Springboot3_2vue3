package com.example.springboot3_2vue3.mapper.equipmapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.equipment.Classification;
import com.example.springboot3_2vue3.domain.equipment.ClassificationOption;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ClassificationMapper
 * Package: com.example.springboot3_2vue3.mapper.equipmapper
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/17 16:27
 * @Version 1.0
 */
public interface ClassificationMapper extends BaseMapper<Classification> {

    //根据id查询所有分类
    List<Classification> findAll(Long id);

}
