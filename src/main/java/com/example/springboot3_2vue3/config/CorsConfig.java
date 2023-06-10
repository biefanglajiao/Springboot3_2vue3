package com.example.springboot3_2vue3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")//配置可以被跨域的路径，可以任意配置，可以具体到直接请求路径。
                .allowedOriginPatterns("*")//配置可以被跨域的域名，如果要携带cookie，不能写*，必须指定具体的域名
                .allowedHeaders(CorsConfiguration.ALL)//允许任何头
                .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")//允许任何方法（post、get等）
                .allowCredentials(true) //是否允许证书 不再默认开启
                .maxAge(3600);//跨域允许时间 1小时内不再需要预检验，（发options请求）--检验请求

    }
}
