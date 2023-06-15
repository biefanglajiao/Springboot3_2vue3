package com.example.springboot3_2vue3;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling//开启定时任务
@SpringBootApplication
@MapperScan("com.example.springboot3_2vue3.mapper")
public class Springboot32vue3Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot32vue3Application.class, args);
    }

}
