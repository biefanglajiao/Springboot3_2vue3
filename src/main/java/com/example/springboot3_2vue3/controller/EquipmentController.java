package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.domain.equipment.Temperature;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.resp.TemperatureResp;
import com.example.springboot3_2vue3.service.EquipmentService;
import com.example.springboot3_2vue3.service.TemperaturesService;
import jakarta.annotation.Resource;
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
@Resource
private TemperaturesService temperaturesService;

    @RequestMapping("/all")
    public CommonResp findOpenAndAll(){
      List list=new ArrayList();
      list.add(equipmentService.finopensumnumber());//查询已经启动的设备总数
      list.add(equipmentService.finsumnumber());//查询所有的设备总数
        System.out.println(list);
      CommonResp commonResp=new CommonResp();
     commonResp.setContent(list);
        System.out.println(commonResp);
     return commonResp;
    }

    @RequestMapping("tempall")
    public CommonResp findTempAll(){
        CommonResp<List<TemperatureResp>> listCommonResp=new CommonResp<>();
        listCommonResp.setContent(temperaturesService.findAllTemp());
        return listCommonResp;


    }
    //查寻所有已经打开的设备信息
    @RequestMapping("equipopen")
    public CommonResp findOpenInfo(){
        CommonResp<List<Equipment>> listCommonResp=new CommonResp<>();
        listCommonResp.setContent(equipmentService.findAllOpenInfo());
        return listCommonResp;
    }
}
