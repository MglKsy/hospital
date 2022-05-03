package com.thylovezj.hospital.config;



import com.thylovezj.hospital.interceptor.LoginInterceptor;
import com.thylovezj.hospital.interceptor.RefreshLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;


//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Resource
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new RefreshLoginInterceptor(stringRedisTemplate))
//                .order(0);
//        registry.addInterceptor(new LoginInterceptor())
//                .excludePathPatterns("/user/login/**")
//                .order(1);
//    }
//
//
//}
