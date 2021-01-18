package com.gddlkj.example.util;

import com.gddlkj.example.model.domain.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SecurityUtil {

    @Resource
    private RedisTemplate redisTemplate;

    public User getAuthentication(){
        return (User)redisTemplate.opsForValue().get("system:user:"+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }


}
