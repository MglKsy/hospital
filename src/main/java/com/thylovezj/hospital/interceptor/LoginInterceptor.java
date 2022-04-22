package com.thylovezj.hospital.interceptor;

import cn.hutool.core.util.StrUtil;

import com.alibaba.fastjson.JSON;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.exception.ThylovezjHospitalException;
import com.thylovezj.hospital.exception.ThylovezjHospitalExceptionEnum;
import com.thylovezj.hospital.mapper.UserMapper;
import com.thylovezj.hospital.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.concurrent.TimeUnit;

import static com.thylovezj.hospital.common.RedisKeyConstant.LOGIN_CACHE_TIME;
import static com.thylovezj.hospital.common.RedisKeyConstant.LOGIN_PREFIX;

/**
 * 登录拦截器
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("authorization");

        if (StrUtil.isBlank(token)){
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.NOT_LOGIN);
        }
        String openid = stringRedisTemplate.opsForValue().get(LOGIN_PREFIX + token);

        if (StrUtil.isBlank(openid)) {
            throw new ThylovezjHospitalException(ThylovezjHospitalExceptionEnum.LOGIN_STATUS_ERROR);
        }
        //刷新token有效时间
        stringRedisTemplate.expire(LOGIN_PREFIX + token,LOGIN_CACHE_TIME, TimeUnit.MINUTES);
        //更新用户最后操作时间
        userMapper.updateLastVisitTime(openid);
        return true;
    }
}
