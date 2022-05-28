package com.thylovezj.hospital;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.thylovezj.hospital.dto.GestureVo;
import com.thylovezj.hospital.mapper.GestureMapper;
import com.thylovezj.hospital.mapper.UserMapper;
import com.thylovezj.hospital.pojo.Gesture;
import com.thylovezj.hospital.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private GestureMapper gestureMapper;
    @Test
    void contextLoads() {
        LambdaQueryWrapper<Gesture> gestureVoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        gestureVoLambdaQueryWrapper.gt(Gesture::getId,46);

        gestureMapper.delete(gestureVoLambdaQueryWrapper);

    }

}
