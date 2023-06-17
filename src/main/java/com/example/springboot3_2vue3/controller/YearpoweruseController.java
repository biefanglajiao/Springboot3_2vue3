package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.YearpoweruseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/17 11:01
 **/
@RestController
@RequestMapping("/yearpoweruse")
public class YearpoweruseController {
    @Resource
    private YearpoweruseService yearpoweruseService;

    @GetMapping("/insertorupdate")
    public CommonResp InsertOrUpdatePoweruse(int poweruse) {
        CommonResp commonResp = new CommonResp<>();
        int a = yearpoweruseService.InsertOrUpdatePoweruse(poweruse);

        if (a == 1) {
            commonResp.setSuccess(true);
            commonResp.setMessage("成功插入数据");
        } else if (a == 2) {
            commonResp.setSuccess(true);
            commonResp.setMessage("成功更新数据");
        } else {
            commonResp.setSuccess(false);
            commonResp.setMessage("插入或更新数据失败");
        }
        return commonResp;

    }

    //获取今日的用电总量信息
    @GetMapping("/getpoweruse")
    public CommonResp getPoweruse(){
        CommonResp commonResp = new CommonResp<>();
        commonResp.setContent(yearpoweruseService.getPoweruse());
        commonResp.setSuccess(true);
        commonResp.setMessage("成功获取数据");
        return commonResp;
    }
    //获取所有的用电总量信息
    @GetMapping("/getallpoweruse")
    public CommonResp getallPoweruse(){
        CommonResp commonResp = new CommonResp<>();
        commonResp.setContent(yearpoweruseService.getAllPoweruse());
        commonResp.setSuccess(true);
        commonResp.setMessage("成功获取数据");
        return commonResp;
    }
}
