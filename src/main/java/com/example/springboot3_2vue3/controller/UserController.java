package com.example.springboot3_2vue3.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.springboot3_2vue3.Utils.SnowFlake;
import com.example.springboot3_2vue3.aspect.LogAspect;
import com.example.springboot3_2vue3.req.UserLoginReq;
import com.example.springboot3_2vue3.resp.CommonResp;
import com.example.springboot3_2vue3.resp.user.UserLoginResp;
import com.example.springboot3_2vue3.service.user.UserService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/10 19:21
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);
@Resource
private UserService userService;
    @Resource
    private RedisTemplate redisTemplate;
@Resource
private SnowFlake snowFlake;
    @PostMapping("/login")//保存书籍  一般保存类用post
    public CommonResp login(@RequestBody @Valid UserLoginReq req) {//json格式的数据要用@RequestBody 注解  from表单格式 就可以直接提交
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));//密码加密 将前端的明文密码转为32位MD5

        CommonResp<UserLoginResp> resp = new CommonResp<>();
        UserLoginResp login = userService.login(req);

        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token： {}，并放入redis", token);
        login.setToken(token.toString());//将token存入login中
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(login), 3600 * 24, TimeUnit.SECONDS);//将token存入redis中 有效期为一天
//前后端都用tostring  才能保证数据一直可以被对比
        resp.setContent(login);
        return resp;
    }
}
