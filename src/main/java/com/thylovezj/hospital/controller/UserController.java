package com.thylovezj.hospital.controller;


import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.LoginResult;

import com.thylovezj.hospital.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    @GetMapping("/login/{code}")
    public ApiRestResponse<LoginResult> handlerCode(@PathVariable("code")String code){

      return userService.login(code,appid,secret);
    }



}
