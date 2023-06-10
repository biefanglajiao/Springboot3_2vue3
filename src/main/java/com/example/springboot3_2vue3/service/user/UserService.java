package com.example.springboot3_2vue3.service.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot3_2vue3.Utils.CopyUtils;
import com.example.springboot3_2vue3.domain.user.User;
import com.example.springboot3_2vue3.exception.BusinessException;
import com.example.springboot3_2vue3.exception.BusinessExceptionCode;
import com.example.springboot3_2vue3.mapper.usermapper.Usermapper;
import com.example.springboot3_2vue3.req.UserLoginReq;
import com.example.springboot3_2vue3.resp.user.UserLoginResp;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Author: 常兆海
 * @Description:
 * @DateTime: 2023/6/10 19:04
 **/
@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Resource
    private Usermapper usermapper;;
    /***
     * 登录
     * @param req
     * @return
     */
    public UserLoginResp login(UserLoginReq req) {
        User user = selectByLoginName(req.getUsername());
        if (ObjectUtils.isEmpty(user)) {
            LOG.info("用户名不存在, {}", req.getUsername());
            //用户不存在
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (user.getPassword().equals(req.getPassword())) {
                //登录成功
                UserLoginResp userLoginResp = CopyUtils.copy(user, UserLoginResp.class);
                return userLoginResp;
            } else {
                //密码不对
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", req.getPassword(), user.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            }
        }
    }
    /***
     * 根据登录名查询用户
     * @param username
     * @return
     */
    public User selectByLoginName(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("username",username);//相当于where id=1

        List<User> userList = usermapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            return userList.get(0);
        }
    }


}
