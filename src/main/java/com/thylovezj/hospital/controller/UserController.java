package com.thylovezj.hospital.controller;


import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.common.Constant;
import com.thylovezj.hospital.customInterface.NotIntercept;
import com.thylovezj.hospital.dto.AvatorUriVo;
import com.thylovezj.hospital.dto.LoginResult;

import com.thylovezj.hospital.service.OssService;
import com.thylovezj.hospital.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    OssService ossService;
    @Value("${weixin.appid}")
    private String appid;

    @Value("${weixin.secret}")
    private String secret;

    @NotIntercept
    @GetMapping("/login/{code}")
    public ApiRestResponse<LoginResult> handlerCode(@PathVariable("code") String code) {

        return userService.login(code, appid, secret);
    }

    @PostMapping("/login/upload")
    public ApiRestResponse uploadPic(@RequestBody MultipartFile file) throws IOException {
        //MultipartFile为上传文件,返回值是图片存储的uri
        String AvatorFile = ossService.upload(file, Constant.AVATOR_URI);
        AvatorUriVo avatorUriVo = new AvatorUriVo();
        avatorUriVo.setUri(AvatorFile);
        return ApiRestResponse.success(avatorUriVo);
    }
}
