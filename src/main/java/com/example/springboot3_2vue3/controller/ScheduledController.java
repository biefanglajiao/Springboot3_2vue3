package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.Utils.ScheduledUtils;
import com.example.springboot3_2vue3.Utils.SnowFlake;
import com.example.springboot3_2vue3.domain.scheduled.Scheduledcron;
import com.example.springboot3_2vue3.exception.BusinessException;
import com.example.springboot3_2vue3.exception.BusinessExceptionCode;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.scheduled.ScheduledcronService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/7/9 9:01
 **/
@RestController
@RequestMapping("/scheduled")
public class ScheduledController {
    @Resource
    private SnowFlake snowFlake;

    @Resource
    private ScheduledcronService scheduledcronService;

    @GetMapping("/findall")
    public CommonResp findAll() {
        List<Scheduledcron> scheduledcrons = scheduledcronService.selectAll();
        CommonResp commonResp = new CommonResp();
        commonResp.setContent(scheduledcrons);
        commonResp.setMessage("查询成功");
        return commonResp;
    }

    @PostMapping("/addorupdate")
    public CommonResp addorupdate(@RequestBody Scheduledcron scheduledcron) {//json格式的数据要用@RequestBody 注解  from表单格式 就可以直接提交
        CommonResp commonResp = new CommonResp();
        //字段校验
        if (scheduledcron.getCronkey().equals("测试")) {
            scheduledcron.setCronkey(ScheduledUtils.TEST_PACKAGE);
        } else if (scheduledcron.getCronkey().equals("START_PACKAGE")) {
            scheduledcron.setCronkey(ScheduledUtils.START_PACKAGE);
        } else if (scheduledcron.getCronkey().equals("STOP_PACKAGE")) {
            scheduledcron.setCronkey(ScheduledUtils.STOP_PACKAGE);
        }else throw new BusinessException(BusinessExceptionCode.PARAMETER_VALIDATION_ERROR);
        //新增操作
        if (scheduledcron.getId() == null) {

            scheduledcron.setId(snowFlake.nextId());
            int insert = scheduledcronService.insert(scheduledcron);
            if (insert == 1) {
                commonResp.setMessage("添加成功");
            } else {
                commonResp.setMessage("添加失败");
            }
        } else {//修改操作
            int update = scheduledcronService.updateByPrimaryKey(scheduledcron);
            if (update == 1) {
                commonResp.setMessage("修改成功");
            } else {
                commonResp.setMessage("修改失败");
            }
        }
        return commonResp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp commonResp = new CommonResp();
        int delete = scheduledcronService.deleteByPrimaryKey(id);
        if (delete == 1) {
            commonResp.setMessage("删除成功");
        } else {
            commonResp.setMessage("删除失败");
        }
        return commonResp;
    }
    @GetMapping("/selectbyname/{name}")
    public CommonResp selectbyname(@PathVariable String name) {
        CommonResp commonResp = new CommonResp();
        List<Scheduledcron> scheduledcron = scheduledcronService.findByExplanLike(name);
        commonResp.setContent(scheduledcron);
        commonResp.setMessage("查询成功");
        return commonResp;
    }
}
