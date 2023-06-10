package com.example.springboot3_2vue3.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.example.springboot3_2vue3.Utils.RequestContext;
import com.example.springboot3_2vue3.Utils.SnowFlake;


import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;



//执行顺序  ： 过滤器 》 拦截器 》 切面
@Aspect
@Component
public class LogAspect {//aop 模式 接口耗时
private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
@Resource
private SnowFlake snowFlake;

//定义一个切点
   @Pointcut("execution(public * com.example.springboot3_2vue3.controller.*.*(..))")//切点表达式 所有的controller方法
    public void contorllerPointcut() {
    }
    @Before("contorllerPointcut()")//在切点之前执行
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));//使用雪花算法自定义日志的流水号 可以在配置文件（logback-spring.xml）中读取使用

       //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        //打印请求信息
        LOG.info("------------- LogAspect 开始 -------------");
        LOG.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
        LOG.info("远程地址: {}", request.getRemoteAddr());
        LOG.info("请求方法: {}.{}", signature.getDeclaringTypeName(), name);

        RequestContext.setRemoteAddr(getRemoteIp(request));//获取远程ip并放入线程变量中


        //打印请求参数
        Object[] args = joinPoint.getArgs();
//        LOG.info("请求参数: {}", joinPoint.getArgs());

        Object[] arguments = new Object[args.length];
        for (int i=0;i<args.length;i++){
            if (args[i] instanceof HttpServletRequest||args[i] instanceof HttpServletResponse ||args[i] instanceof MultipartFile){
               continue;
        }arguments[i]=args[i];
        }

        //排除字段；敏感字段或者 太长的不显示
        String[] excludeFields = {"password","newPassword","oldPassword","file"};
        PropertyPreFilters filter = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludeFilter = filter.addFilter();
        excludeFilter.addExcludes(excludeFields);
        LOG.info("请求参数: {}", com.alibaba.fastjson.JSON.toJSONString(arguments,excludeFilter, SerializerFeature.IgnoreNonFieldGetter));
        /****\
         * @坑描述：  在这个方法 toJSONString 如果没有SerializerFeature.IgnoreNonFieldGetter这个参数，会在参数校验的捕获BindEXception中 出现bug
         * @参数含义： SerializerFeature.IgnoreNonFieldGetter 这个参数的作用是：如果一个对象中有一个属性，但是这个属性没有对应的get方法，那么在转换成json的时候，这个属性就会被忽略掉
         * @解绝参考文章： https://blog.csdn.net/qq_30546099/article/details/129287020?spm=1001.2101.3001.6650.4&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EYuanLiJiHua%7EPosition-4-129287020-blog-122999083.235%5Ev28%5Epc_relevant_recovery_v2&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EYuanLiJiHua%7EPosition-4-129287020-blog-122999083.235%5Ev28%5Epc_relevant_recovery_v2&utm_relevant_index=5
          */

    }

    @Around("contorllerPointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();//前置
        Object result = proceedingJoinPoint.proceed();//执行方法
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password", "file"};//后置
        PropertyPreFilters filters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter excludefilter = filters.addFilter();
        excludefilter.addExcludes(excludeProperties);
        LOG.info("返回结果: {}", JSONObject.toJSONString(result, excludefilter));
        LOG.info("------------- 结束 耗时：{} ms -------------", System.currentTimeMillis() - startTime);
        return result;
    }

    /**
     * 使用nginx做反向代理，需要用该方法才能取到真实的远程IP
     * @param request
     * @return
     */
    public String getRemoteIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
