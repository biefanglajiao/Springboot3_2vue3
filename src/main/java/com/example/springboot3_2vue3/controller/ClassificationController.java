package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.service.AlarmService;
import com.example.springboot3_2vue3.service.ClassificationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: ClassificationController
 * Package: com.example.springboot3_2vue3.controller
 * Description:
 *
 * @Author SoyaMilk-豆浆
 * @Create 2023/6/18 19:16
 * @Version 1.0
 */
@RestController
@RequestMapping("/classification")
public class ClassificationController {

    @Resource
    private ClassificationService classificationService;


}
