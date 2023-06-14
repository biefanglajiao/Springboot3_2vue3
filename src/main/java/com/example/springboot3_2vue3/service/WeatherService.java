package com.example.springboot3_2vue3.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/14 13:00
 **/
@Service
public class WeatherService {

    RestTemplate restTemplate = new RestTemplate();
    String urlnow="https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=f55562896643a8c0373ad564feee1e86";
    String urlyubao="https://restapi.amap.com/v3/weather/weatherInfo?city=110101&key=f55562896643a8c0373ad564feee1e86&extensions=all";

    public JSONObject getWeatherNow(){
        JSONObject weather= restTemplate.getForObject(urlnow, JSONObject.class);
       return weather;
    }

    public JSONObject getWeatherYubao(){
        JSONObject weather= restTemplate.getForObject(urlyubao, JSONObject.class);
        return weather;
    }
}
