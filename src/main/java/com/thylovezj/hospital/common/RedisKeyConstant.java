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
    //长谷川问题的key
    public static final String PROBLEM_CGC_KEY = "cache:problem:cgc";
    //普通主观题的key
    public static final String PROBLEM_SUB_NUM = "cache:problem:sub";
    //普通客观题的key
    public static final String PROBLEM_OBJ_NUM = "cache:problem:obj";
    //普通图片题的key
    public static final String PROBLEM_PIC_NUM = "cache:problem:pic";
    //数据过期时间
    public static final long PROBLEM_EXPIRE_TIME = 24*60L;
}
