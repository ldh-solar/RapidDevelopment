package com.gddlkj.example.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 设置redis数据例子
     */
    @Test
    public void set() {
        redisTemplate.opsForValue().set("test:set", "bbbb");
    }

    /**
     * 获取redis数据例子
     */
    @Test
    public void get() {
        System.out.println( redisTemplate.opsForValue().get("bbbb"));
    }
}
