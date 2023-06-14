package com.example.springboot3_2vue3.controller;

import com.alibaba.fastjson.JSONObject;

import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.service.WeatherService;
import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/14 13:13
 **/
@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Resource
    private WeatherService weatherService;

    @GetMapping("/now")
//    public CommonResp getWeatherNow(){
////      weatherService.getWeatherNow();

//        return commonResp;
//    }
    public CommonResp getWeatherNow(){
        JSONObject weather= new JSONObject();
        weather.fluentPutAll(weatherService.getWeatherNow());
        CommonResp<JSONObject> commonResp=new CommonResp<>();
commonResp.setContent(weather);
        return commonResp;
    }
    @GetMapping("/yubao")
    public CommonResp getWeatherYubao(){
        JSONObject weather= new JSONObject();
        weather.fluentPutAll(weatherService.getWeatherYubao());
        CommonResp<JSONObject> commonResp=new CommonResp<>();
       commonResp.setContent(weather);
        return commonResp;
    }
}
