package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.service.ClassificationOptionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ClassificationOptionController
 * Package: com.example.springboot3_2vue3.controller
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/18 19:16
 * @Version 1.0
 */@RestController
@RequestMapping("/classificationOption")
public class ClassificationOptionController {

     @Resource
    private ClassificationOptionService classificationOptionService;



}
