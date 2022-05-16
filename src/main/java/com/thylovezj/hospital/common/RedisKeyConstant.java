package com.thylovezj.hospital.common;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 保存redis的key常量
 */
public class RedisKeyConstant {
    public static final String LOGIN_USER_TOKEN = "login:token:";
    public static final Long LOGIN_CACHE_TIME  = 30L;
    //JSON序列化工具
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final String SIGN_USER_KEY = "sign:";
    public static final String PROBLEM_CGC_KEY = "cache:problem:cgc";
}
