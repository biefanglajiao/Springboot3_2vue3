package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.EquipmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/14 11:59
 **/

@RestController
@RequestMapping("/equipment")
public class EquipmentController {
    @Resource
    private EquipmentService equipmentService;


    @RequestMapping("/all")
    public CommonResp findOpenAndAll(){
      List list=new ArrayList();
      list.add(equipmentService.finopensumnumber());
      list.add(equipmentService.finsumnumber());
        System.out.println(list);
      CommonResp commonResp=new CommonResp();
     commonResp.setContent(list);
        System.out.println(commonResp);
     return commonResp;
    }
}
