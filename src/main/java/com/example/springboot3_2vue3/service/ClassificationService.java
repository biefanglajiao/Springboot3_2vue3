package com.example.springboot3_2vue3.service;

import com.example.springboot3_2vue3.domain.equipment.Classification;
import com.example.springboot3_2vue3.mapper.equipmapper.ClassificationMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: ClassificationService
 * Package: com.example.springboot3_2vue3.service
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/17 16:30
 * @Version 1.0
 */
@Service
public class ClassificationService {

    @Resource
    private ClassificationMapper classificationMapper;

    //新增分类
    public void add(Classification classification){
        classificationMapper.insert(classification);
    }

    //删除分类
    public void delete(Long id){

    }

    //修改分类
    public void update(Classification classification){
        classificationMapper.updateById(classification);
    }

    //查询所有分类
    public List<Classification> findAll(){
        return classificationMapper.selectList(null);
    }

    //根据id查询分类
    public List<Classification> findAll(Long id){
        return classificationMapper.findAll(id);
    }

}
