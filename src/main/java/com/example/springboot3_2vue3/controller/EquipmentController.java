package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.Utils.SnowFlake;
import com.example.springboot3_2vue3.domain.equipment.Deviceuse;
import com.example.springboot3_2vue3.domain.equipment.Equipment;
import com.example.springboot3_2vue3.domain.equipment.Temperature;
import com.example.springboot3_2vue3.mapper.equipmapper.DeviceuseMapper;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.resp.TemperatureResp;
import com.example.springboot3_2vue3.service.DeviceuseService;
import com.example.springboot3_2vue3.service.EquipmentService;
import com.example.springboot3_2vue3.service.TemperaturesService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
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

@Resource
private SnowFlake snowFlake;
@Resource
private Deviceuse deviceuse;
    @Resource
    private DeviceuseService    deviceuseService;

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
    @RequestMapping("findequipopen")
    public CommonResp findOpenInfo(){
        CommonResp<List<Equipment>> listCommonResp=new CommonResp<>();
        listCommonResp.setContent(equipmentService.findAllOpenInfo());
        return listCommonResp;
    }

    /****
     * @Description: 开启摸个设备 同时在记录表露记录开启的时间  Long型时间方便定时任务计算开启时间
     * @param id
     * @return
     */
    @RequestMapping("openequip")
    @Transactional
    public CommonResp Openequip(long id){
        CommonResp<Equipment> commonResp=new CommonResp<>();
        if (equipmentService.openequip(id)==1){//开启设备
            commonResp.setMessage("设备开启成功");
            long ids = snowFlake.nextId();
            Long  date=System.currentTimeMillis();
            deviceuse.setEquipmentid(id);
            deviceuse.setId(ids);
            deviceuse.setDate(date);
            commonResp=deviceuseService.opendevice(deviceuse);//将设备开启时间放到表中
        }
        else commonResp.setMessage("开启失败，请稍后刷新重试");
        return commonResp;
    }


    @RequestMapping("closeequip")
    @Transactional
    public CommonResp Closeequip(long id){
        CommonResp<Equipment> commonResp=new CommonResp<>();
        if (equipmentService.closeequip(id)){//关闭设备
            commonResp.setMessage("设备关闭成功");
            deviceuse.setEquipmentid(id);
            commonResp=deviceuseService.deletdevice(deviceuse);//将设备开启时间放到设备开启表中的对应数据删除
        }
        else commonResp.setMessage("关闭失败，请稍后刷新重试");
        return commonResp;
    }



}
