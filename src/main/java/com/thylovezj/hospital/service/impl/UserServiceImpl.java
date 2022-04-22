package com.thylovezj.hospital.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thylovezj.hospital.common.ApiRestResponse;
import com.thylovezj.hospital.dto.LoginResult;
import com.thylovezj.hospital.dto.WxLogin;
import com.thylovezj.hospital.pojo.User;
import com.thylovezj.hospital.service.UserService;
import com.thylovezj.hospital.mapper.UserMapper;
import com.thylovezj.hospital.util.WeChatUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;



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

    @Autowired
    private UserMapper userMapper;
    @Override
    public ApiRestResponse<LoginResult> login(WxLogin wxLogin, String appid, String secret) {
        //获取三个信息， code ency iv
        String code = wxLogin.getCode();
        String encryptedData = wxLogin.getEncryptedData();
        String iv = wxLogin.getIv();
        //用code appid secret向微信服务器请求用户信息
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid={0}&secret={1}&js_code={2}&grant_type=authorization_code";
        String replaceUrl = url.replace("{0}", appid).replace("{1}", secret).replace("{2}", code);
        //s是请求得到的字符串
        String s = HttpUtil.get(replaceUrl);
        //将s转为 jsonObject并得到 session_Key和openid
        JSONObject jsonObject = JSON.parseObject(s);
        String session_key = (String) jsonObject.get("session_key");
        String openid = (String) jsonObject.get("openid");

        //解密获得用户信息
        String resultJson = WeChatUtil.decryptData(encryptedData, session_key, iv);
        User user = JSON.parseObject(resultJson, User.class);
        user.setOpenId(openid);

        //检查openid是否存在于数据库
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getOpenId,openid);
        long count = this.count(wrapper);

        //如果该用户不存在于数据库,先存到数据库中
        if (count == 0){
            this.save(user);
        }
        //如果存在要刷新一下最近登录时间
        userMapper.updateUpdateTime(openid);
        //使用uuid作为token保存在redis中
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(uuid,openid);
        log.info("uuid====>{}",uuid);
        LoginResult loginResult = new LoginResult(openid, uuid);
        return ApiRestResponse.success(loginResult);
    }
}




