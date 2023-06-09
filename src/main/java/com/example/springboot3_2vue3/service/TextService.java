package com.example.springboot3_2vue3.service;

import com.example.springboot3_2vue3.domain.Demo;
import com.example.springboot3_2vue3.mapper.TextMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/9 15:52
 **/
@Service
public class TextService {
    @Resource
    private TextMapper textMapper;


    public List<Demo> findall(){
        return textMapper.selectList(null);
    }
}
