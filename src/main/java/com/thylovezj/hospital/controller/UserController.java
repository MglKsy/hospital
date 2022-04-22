package com.thylovezj.hospital.controller;


import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.LoginResult;
import com.thylovezj.hospital.dto.WxLogin;
import com.thylovezj.hospital.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
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

    /**
     *
     * @param wxLogin 自己定义的实体，里面包含code 和rawData
     *
     * @return LoginResult 返回一个自定义对象，里面包含openid和 token
     */
    @PostMapping("/login")
    public ApiRestResponse<LoginResult> handlerCode(@RequestBody WxLogin wxLogin){

        return userService.login(wxLogin,appid,secret);
    }
}
