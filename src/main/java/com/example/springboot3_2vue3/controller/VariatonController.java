package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.domain.equipment.Variation;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.resp.VariationNameResp;
import com.example.springboot3_2vue3.service.VariationService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/15 22:57
 **/
@RestController
@RequestMapping("/variation")
public class VariatonController {
    @Resource
    private VariationService variationService;
    @GetMapping("/all")
    public CommonResp findAll(){
        CommonResp<List<Variation>> commonResp=new CommonResp<>();
        commonResp.setContent(variationService.getAllVariation());
        return  commonResp;
    }

    @GetMapping("/allandname")
    public CommonResp findAllAndName(){
        CommonResp<List<VariationNameResp>> commonRespList=new CommonResp<>();
        commonRespList.setContent(variationService.getAllVariationName());
        return commonRespList;
    }

    @GetMapping("/getallclass")
    public CommonResp getallclass(){
        CommonResp<List<Variation>> commonResp=new CommonResp<>();
        commonResp.setContent(variationService.getallclass());
        return commonResp   ;
    }
}
