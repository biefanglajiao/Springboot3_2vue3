package com.example.springboot3_2vue3.service;

import com.example.springboot3_2vue3.domain.equipment.ClassificationOption;
import com.example.springboot3_2vue3.mapper.equipmapper.ClassificationOptionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ClassificationOptionService
 * Package: com.example.springboot3_2vue3.service.user
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/17 16:30
 * @Version 1.0
 */
@Service
public class ClassificationOptionService {

    @Resource
    private ClassificationOptionMapper classificationOptionMapper;

    //新增分类项
    public void add(ClassificationOption classificationOption){
        classificationOptionMapper.insert(classificationOption);
    }

    //删除分类项
    public void deletd(long id){
        classificationOptionMapper.deleteById(id);
    }

    //修改分类项
    public void update(ClassificationOption classificationOption){
        classificationOptionMapper.updateById(classificationOption);
    }

    //根据id查询分类项
    public List<ClassificationOption> findAll(Long id){
        return classificationOptionMapper.findAll(id);
    }


}
