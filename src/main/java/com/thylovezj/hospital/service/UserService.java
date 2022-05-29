package com.thylovezj.hospital.service;

import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.LoginResult;

import com.thylovezj.hospital.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author mlovek
* @description 针对表【xdu_hospital_user(微信用户信息)】的数据库操作Service
* @createDate 2022-04-21 16:31:19
*/
public interface UserService extends IService<User> {
    ApiRestResponse<LoginResult> login(String code, String appid, String secret);

    ApiRestResponse<String> confirm();
}
