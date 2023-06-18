package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.domain.equipment.Classification;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.AlarmService;
import com.example.springboot3_2vue3.service.ClassificationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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



    @GetMapping("/selectAll")
    public CommonResp selectAll(){
        List<Classification> lists= classificationService.findAll();
        CommonResp resp = new CommonResp<>();
        resp.setContent(lists);

        return resp;
    }

    @GetMapping("/selectById")
    public CommonResp selectById(Long id){
        List<Classification> lists= classificationService.findById(id);
        CommonResp resp = new CommonResp<>();
        resp.setContent(lists);

        return resp;
    }
    @GetMapping("/selectallById")
    public CommonResp selectAllById(Long id){
        CommonResp resp = new CommonResp<>();
        List<Classification> lists = classificationService.findAllById(id);
        resp.setContent(lists);


        return resp;
    }

}
