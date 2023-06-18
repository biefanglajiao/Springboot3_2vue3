package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.Deviceuse2Service;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/17 23:14
 **/
@RestController
@RequestMapping("/deviceuse2")
public class Deviceuse2Controller {

    @Resource
    private Deviceuse2Service deviceuse2Service;
    @GetMapping("/selectAllinfo")
    public CommonResp selectAllinfo (){
        CommonResp commonResp=new CommonResp();
        commonResp.setContent(deviceuse2Service.selectAllinfo());
        return commonResp;
    }
    @GetMapping("/selectAlltodayinfo")
    public CommonResp selectAlltodatinfo (){
        CommonResp commonResp=new CommonResp();
        commonResp.setContent(deviceuse2Service.selectAlltodayinfo());
        return commonResp;
    }
}
