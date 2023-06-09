package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.domain.Demo;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/9 15:53
 **/
@RestController
@RequestMapping("/a")
public class TextController {
    @Autowired
    private TextService textService;

    @GetMapping("/a")
    public CommonResp findall(){
        CommonResp<List<Demo>> commonResp=new CommonResp<>();
    commonResp.setContent(textService.findall());
    return commonResp;

    }
}
