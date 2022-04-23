package com.thylovezj.hospital.config;


import com.thylovezj.hospital.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //不拦截以下路径
        String[] urls = new String[]{
                "/v1/user/login/**"
        };
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns(urls);
    }

}
