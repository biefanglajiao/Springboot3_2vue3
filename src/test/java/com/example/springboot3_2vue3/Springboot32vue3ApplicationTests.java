package com.example.springboot3_2vue3;

import com.example.springboot3_2vue3.mapper.TextMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Springboot32vue3ApplicationTests {
    @Resource
    private TextMapper textMapper;

    @Test
    void contextLoads() {
        System.out.println(textMapper.selectList(null));
    }

}
