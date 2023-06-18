package com.example.springboot3_2vue3.mapper.equipmapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot3_2vue3.domain.equipment.ClassificationOption;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ClassName: ClassificationOptionMapper
 * Package: com.example.springboot3_2vue3.mapper.equipmapper
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/17 16:27
 * @Version 1.0
 */
public interface ClassificationOptionMapper extends BaseMapper<ClassificationOption> {

    //根据id查询分类项
    List<ClassificationOption> findAll(Long id);

}
