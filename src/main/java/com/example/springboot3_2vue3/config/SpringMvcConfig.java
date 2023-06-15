package com.example.springboot3_2vue3.config;


import com.example.springboot3_2vue3.interceptor.LogInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

@Resource
LogInterceptor logInterceptor;

    /***
     * 添加拦截器  为什么用拦截器：因为他可以针对某些请求进行拦截，然后做一些处理
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login/**",
                        "/redis/**",
                        "/user/**",



                        "/equipment/**",
                        "/text/**",
                        "/a/a",
                        "/weather/**"
                );//拦截所有请求，除了exculdePathPatterns中的请求
    }

}
