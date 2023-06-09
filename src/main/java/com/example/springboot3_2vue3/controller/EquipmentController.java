package com.example.springboot3_2vue3.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.Utils.SnowFlake;
import com.example.springboot3_2vue3.domain.equipment.*;
import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;
import com.example.springboot3_2vue3.mapper.equipmapper.Equipment_Classification_OptionMapper;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.resp.DeviceusePowerResp;
import com.example.springboot3_2vue3.resp.TemperatureResp;
import com.example.springboot3_2vue3.service.*;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

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
    private DailyequipmentpoweruseService dailyequipmentpoweruseService;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private TemperaturesService temperaturesService;
    @Resource
    private VariationService variationService;

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private DeviceuseService deviceuseService;
    @Resource
    private Deviceuse2Service deviceuse2Service;
    @Resource
    private Equipment_Classification_OptionMapper equipmentClassificationOptionMapper;

    @RequestMapping("/all")
    public CommonResp findOpenAndAll() {
        List list = new ArrayList();
        list.add(equipmentService.finopensumnumber());//查询已经启动的设备总数
        list.add(equipmentService.finsumnumber());//查询所有的设备总数
        System.out.println(list);
        CommonResp commonResp = new CommonResp();
        commonResp.setContent(list);
        System.out.println(commonResp);
        return commonResp;
    }

    @RequestMapping("tempall")
    public CommonResp findTempAll() {
        CommonResp<List<TemperatureResp>> listCommonResp = new CommonResp<>();
        listCommonResp.setContent(temperaturesService.findAllTemp());
        return listCommonResp;


    }

    @RequestMapping("findallinfo")
    public CommonResp findAllinfo() {
        CommonResp<List<Equipment>> listCommonResp = new CommonResp<>();
        listCommonResp.setContent(equipmentService.findAllInfo());
        return listCommonResp;


    }

    @GetMapping("/selectbystatus/{status}")
    public CommonResp selectbystatus(@PathVariable int status) {

        CommonResp<List<Equipment>> listCommonResp = new CommonResp<>();
        listCommonResp.setContent(equipmentService.selectbystatus(status));
        return listCommonResp;
    }

    @GetMapping("/selectbypower/{power}")
    public CommonResp selectbypower(@PathVariable int power) {

        CommonResp<List<Equipment>> listCommonResp = new CommonResp<>();
        listCommonResp.setContent(equipmentService.selectbypower(power));
        return listCommonResp;
    }


    //查寻所有已经打开的设备信息
    @RequestMapping("findequipopen")
    public CommonResp findOpenInfo() {
        CommonResp<List<Equipment>> listCommonResp = new CommonResp<>();
        listCommonResp.setContent(equipmentService.findAllOpenInfo());
        return listCommonResp;
    }

    /****
     * @Description: 开启摸个设备 同时在记录表露记录开启的时间  Long型时间方便定时任务计算开启时间
     * @param id
     * @return
     */
    @GetMapping("/openequip/{id}")
    @Transactional
    public CommonResp Openequip(@PathVariable long id) {

        CommonResp<Equipment> commonResp = new CommonResp<>();
        if (equipmentService.openequip(id) == 1) {//开启设备
            commonResp.setMessage("设备开启成功");
            long ids = snowFlake.nextId();
            Long date = System.currentTimeMillis();
            Deviceuse deviceuse = new Deviceuse();
            deviceuse.setEquipmentid(id);
            deviceuse.setId(ids);
            deviceuse.setDate(date);
            commonResp = deviceuseService.opendevice(deviceuse);//将设备开启时间放到表中


            Deviceuse2 deviceuse2 = new Deviceuse2();
            deviceuse2.setId(ids);
            deviceuse2.setEquipmentid(id);
            deviceuse2Service.addbegin(deviceuse2);//将开启时间 设备id等信息传入日志表

        } else commonResp.setMessage("开启失败，请稍后刷新重试");
        return commonResp;
    }


    @GetMapping("/closeequip/{id}")
    @Transactional
    public CommonResp Closeequip(@PathVariable long id) {
//        System.out.println("_______________________________________设备关闭逻辑开始");
        CommonResp<Equipment> commonResp = new CommonResp<>();
        if (equipmentService.closeequip(id)) {//关闭设备
            commonResp.setMessage("设备关闭成功");

//            System.out.println("_______________________________________");
            long startTime = System.currentTimeMillis();
            DeviceusePowerResp deviceusePowerResp = deviceuseService.finone(id);//找到记录
//            System.out.println(deviceusePowerResp.getPower() + "            power");
//            System.out.println(deviceusePowerResp.getDate() + "                 date");
            long opendeTime = startTime - deviceusePowerResp.getDate();
            float opendeTimemin = opendeTime / 1000 / 60;
            float opendeTimeHour = opendeTimemin / 60;
//            System.out.println(opendeTimeHour);
            //给小时耗电量统计表赋值

//______________________________________________日各种设备使用电量
            Variation variation = new Variation();
            variation.setEquipmentid(deviceusePowerResp.getEquipmentid());
            float poweruse = deviceusePowerResp.getPower() * opendeTimeHour;

//            Float beforepower= variationService.selectAlltodayByIdd(id);
            //查询今天开启过的现在关闭了的耗电量
            Float beforepower = dailyequipmentpoweruseService.selectAlltodayByIdd(id);
//            System.out.println(beforepower+"       beforepower 查询今天开启过的现在关闭了的耗电量");
            variation.setData(poweruse + beforepower);//要加上上一次开过的数据

            variationService.insertonedata(variation);//记录一下关闭时间的耗电量
//            System.out.println("_______________________________________设备关闭时间耗电量："+poweruse+beforepower);

            Deviceuse deviceuse = new Deviceuse();
            deviceuse.setEquipmentid(id);
            long ids = deviceuseService.selectByequipmentidGetID(deviceuse.getEquipmentid());
            commonResp = deviceuseService.deletdevice(deviceuse);//将设备开启时间放到设备开启表中的对应数据删除


            System.out.println(beforepower + "       beforepower");
            System.out.println(poweruse + "       poweruse  本次使用耗电量");
            Deviceuse2 deviceuse2 = new Deviceuse2();
            deviceuse2.setId(ids);
            deviceuse2.setEquipmentid(id);
            deviceuse2.setEnddate(System.currentTimeMillis());
            deviceuse2.setPowerconsumption(poweruse);
            deviceuse2Service.addend(deviceuse2);//在日志表中加入日志信息--本次使用耗电量

            //给日设备累计耗电量统计表赋值
            float powerusetoday = deviceusePowerResp.getPower() * (opendeTimeHour % 24);//设备的今日使用量
//            System.out.println("_______________________________________设备的今日使用量deviceusePowerResp.getPower() ："+deviceusePowerResp.getPower());
//                    System.out.println("_______________________________________设备的今日使用量："+powerusetoday);
            Float aFloat = dailyequipmentpoweruseService.selectAlltodayByIdd(deviceuse2.getEquipmentid());//查询日设备累计耗电量统计表的今天开启过的现在关闭了设备的耗电量
//            System.out.println("_______________________________________查询日设备累计耗电量统计表的今天开启过的现在关闭了设备的耗电量："+aFloat);
            dailyequipmentpoweruseService.InsertOrUpdatePoweruseTodayByEquipment(deviceuse2.getEquipmentid(), powerusetoday + aFloat);//更新今日使用量

        } else commonResp.setMessage("关闭失败，请稍后刷新重试");
//        System.out.println("_______________________________________设备关闭逻辑结束");
        return commonResp;
    }


    @GetMapping("test")
    public CommonResp<Variation> aa(Long equipmentid) {
        CommonResp commonResp = new CommonResp<>();
        commonResp.setContent(variationService.findsuminfo());

        return commonResp;
    }

    @PostMapping("/save")//保存书籍  一般保存类用post
    public CommonResp save(@RequestBody @Valid Equipment equipment) {//json格式的数据要用@RequestBody 注解  from表单格式 就可以直接提交


        CommonResp commonResp = new CommonResp();
        if (equipment.getId() == null) {
            commonResp.setContent(equipmentService.insert(equipment));
        }
        commonResp.setContent(equipmentService.update(equipment));
        commonResp.setMessage("保存成功");
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")//删除书籍  一般保存类用post
    public CommonResp delete(@PathVariable long id) {//@PathVariable 用于获取url中的数据
        CommonResp commonResp = new CommonResp();
        if (equipmentService.delete(id)) {
            equipmentClassificationOptionMapper.deleteByeid(id);//删除这个设备对应的分类表的数据
            commonResp.setSuccess(true);
            commonResp.setMessage("删除成功");
        } else {
            commonResp.setSuccess(false);
            commonResp.setMessage("删除失败");
        }
        return commonResp;
    }

    @GetMapping("/selectbyname/{name}")
    public CommonResp selectbyname(@PathVariable String name) {
        CommonResp<List<Equipment>> listCommonResp = new CommonResp<>();
        listCommonResp.setContent(equipmentService.selectByname(name));
        return listCommonResp;
    }

    //根据设备位置查询设备信息
    @GetMapping("/selectbylocation/{location}")
    public CommonResp selectbylocation(@PathVariable String location) {
        CommonResp<List<Equipment>> listCommonResp = new CommonResp<>();
        listCommonResp.setContent(equipmentService.selectBylocation(location));
        return listCommonResp;
    }


}
