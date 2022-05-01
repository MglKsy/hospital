package com.thylovezj.hospital.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantOssPropertiesUtils implements InitializingBean {
    //读取OSS配置文件内容
    @Value("${aliyun.oss.file.endpoint}")
    public String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    public String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    public String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    public String bucketName;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    //初始化完之后执行
    //定义公开静态常量
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }
}
