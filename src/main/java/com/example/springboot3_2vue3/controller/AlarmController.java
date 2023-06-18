package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.AlarmService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/18 11:51
 **/
@RestController
@RequestMapping("/alarm")
public class AlarmController {
    @Resource
    private AlarmService alarmService;

    @GetMapping("/selectAllinfo")
    public CommonResp selectAllinfo (){
        CommonResp commonResp=new CommonResp();
        commonResp.setContent(alarmService.selectAllinfo());
        return commonResp;
    }
}
