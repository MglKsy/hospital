package com.thylovezj.hospital.interceptor;

import cn.hutool.core.util.StrUtil;

import com.thylovezj.hospital.util.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static com.thylovezj.hospital.common.RedisKeyConstant.LOGIN_CACHE_TIME;
import static com.thylovezj.hospital.common.RedisKeyConstant.LOGIN_USER_TOKEN;


public class RefreshLoginInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate stringRedisTemplate;

    public RefreshLoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的token
        String authorization = request.getHeader("authorization");
        //如果token为空
        if (StrUtil.isBlank(authorization)){
            //不存在，拦截
            return true;
        }
        String token = stringRedisTemplate.opsForValue().get(LOGIN_USER_TOKEN + authorization);
        if (StrUtil.isBlank(token)){
            //token为空
            return true;
        }
        //刷新有效期
        stringRedisTemplate.expire(LOGIN_USER_TOKEN + authorization,LOGIN_CACHE_TIME, TimeUnit.DAYS);
        //将用户openid保存在 ThreadLocal中
        UserHolder.setId(token);
        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeId();
    }
}
