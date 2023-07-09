package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.AlarmService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    //查询所有的已读数据
    @GetMapping("/selectAllinfoRead")
    public CommonResp selectAllinforead (){
        CommonResp commonResp=new CommonResp();
        commonResp.setContent(alarmService.selectAllinfoRead());
        return commonResp;
    }
    //查询所有的未读数据
    @GetMapping("/selectAllinfoNoread")
    public CommonResp selectAllinfonoread (){
        CommonResp commonResp=new CommonResp();
        commonResp.setContent(alarmService.selectAllNoreadinfo());
        return commonResp;
    }

    @GetMapping("/read/{id}")
    public CommonResp selectAllinfo (@PathVariable  Long id){
        CommonResp commonResp=new CommonResp();
        commonResp.setContent(alarmService.read(id));
        if (commonResp.getContent().equals(1)) {
            commonResp.setMessage("修改成功");
        }else {
            commonResp.setMessage("修改失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }
}
