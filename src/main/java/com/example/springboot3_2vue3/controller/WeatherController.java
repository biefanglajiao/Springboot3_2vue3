package com.example.springboot3_2vue3.controller;

import com.alibaba.fastjson.JSONObject;

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
    public JSONObject getWeatherNow(){
//      weatherService.getWeatherNow();

        return weatherService.getWeatherNow();
    }
    @GetMapping("/yubao")
    public JSONObject getWeatherYubao(){

        return weatherService.getWeatherYubao();
    }
}
