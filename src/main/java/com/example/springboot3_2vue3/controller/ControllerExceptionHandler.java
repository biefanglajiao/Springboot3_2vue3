package com.example.springboot3_2vue3.controller;

import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: 常兆海
 * @Description: 统一异常处理、数据预处理等
 * @DateTime: 2023/4/12 22:32
 **/
@ControllerAdvice
public class ControllerExceptionHandler {
private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

/****
 * 校验异常统一处理
 */
@ExceptionHandler(value = BindException.class)
    @ResponseBody
    public CommonResp validExceptionHandler(BindException e) {
        CommonResp comminResp = new CommonResp<>();
        comminResp.setSuccess(false);
        comminResp.setMessage("参数校验异常：" + e.getAllErrors().get(0).getDefaultMessage());
        LOG.warn(e.getAllErrors().get(0).getDefaultMessage());
        return comminResp;
    }

/****
 * 登录异常统一处理
 */
@ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    public CommonResp validExceptionHandler(BusinessException e) {
        CommonResp commonResp = new CommonResp<>();
        commonResp.setSuccess(false);
        commonResp.setMessage("业务异常：" + e.getCode().getDesc());
        LOG.warn(e.getCode().getDesc());
        return commonResp;
    }

    /****
 * 普通异常统一处理
 */
@ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResp validExceptionHandler(Exception e) {
        CommonResp comminResp = new CommonResp<>();
        comminResp.setSuccess(false);
        comminResp.setMessage("业务异常：我也不会看异常");
        LOG.error("系统异常",e);
        return comminResp;
    }
}
