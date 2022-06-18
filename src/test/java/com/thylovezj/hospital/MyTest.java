package com.thylovezj.hospital;



import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;
import java.nio.file.Path;
import java.sql.PseudoColumnUsage;


public class MyTest {

    @Test
    public void test(){
        Long id = 3L;
        for (int i = 0; i < 100; i++) {
            int k = RandomUtil.randomInt(0, Integer.parseInt(id.toString()));
            System.out.print(k);
        }

    }

}

