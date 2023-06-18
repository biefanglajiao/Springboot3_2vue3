package com.example.springboot3_2vue3.service;

import com.example.springboot3_2vue3.domain.equipment.ClassificationOption;
import com.example.springboot3_2vue3.mapper.equipmapper.ClassificationOptionMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * ClassName: ClassificationOptionServiceeTest
 * Package: com.example.springboot3_2vue3.service
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/17 16:54
 * @Version 1.0
 */
@SpringBootTest
public class ClassificationOptionServiceeTest {

    @Resource
    private ClassificationOptionService classificationOptionService;

}
