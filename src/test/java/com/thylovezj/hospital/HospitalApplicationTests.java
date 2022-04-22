package com.thylovezj.hospital;

import com.thylovezj.hospital.mapper.UserMapper;
import com.thylovezj.hospital.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class HospitalApplicationTests {
    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    void contextLoads() {
        System.out.println(LocalDateTime.now());
        System.out.println(stringRedisTemplate.opsForValue().get("name"));
    }

}
