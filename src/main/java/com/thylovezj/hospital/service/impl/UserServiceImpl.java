package com.thylovezj.hospital.service.impl;



import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.LoginResult;

import com.thylovezj.hospital.pojo.User;
import com.thylovezj.hospital.service.UserService;
import com.thylovezj.hospital.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.thylovezj.hospital.common.RedisKeyConstant.*;


/**
* @author mlovek
* @description 针对表【xdu_hospital_user(微信用户信息)】的数据库操作Service实现
* @createDate 2022-04-21 16:31:19
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserMapper userMapper;
    @Override
    public ApiRestResponse<LoginResult> login(String code, String appid, String secret) {

        //用code appid secret向微信服务器请求用户信息
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        //s是请求得到的字符串
        String s = HttpUtil.get(replaceUrl);
        //将s转为 jsonObject并得到openid 微信接口服务返回session_key和open_id
        JSONObject jsonObject = JSON.parseObject(s);
        String openid = (String) jsonObject.get("openid");


        //检查openid是否存在于数据库
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenId,openid);
        long count = this.count(wrapper);

        //如果该用户不存在于数据库,先存到数据库中
        if (count == 0){
            User user = new User();
            user.setOpenId(openid);
            this.save(user);
        }
        //如果存在要刷新一下最近登录时间

        //使用uuid作为token保存在redis中
        String sessionId = UUID.randomUUID().toString();
        //失效日期:
        stringRedisTemplate.opsForValue().set(LOGIN_USER_TOKEN +sessionId,openid,LOGIN_CACHE_TIME, TimeUnit.MINUTES);
        log.info("uuid====>{}",sessionId);
        LoginResult loginResult = new LoginResult(openid, sessionId);
        return ApiRestResponse.success(loginResult);
    }
}




