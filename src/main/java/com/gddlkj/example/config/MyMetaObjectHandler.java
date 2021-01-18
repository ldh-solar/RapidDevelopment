package com.gddlkj.example.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.gddlkj.example.util.SecurityUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Resource
    @Lazy
    SecurityUtil securityUtil;

    @Override
    public void insertFill(MetaObject metaObject) {
        if (null != securityUtil.getAuthentication()) {
            this.setFieldValByName("createUser", securityUtil.getAuthentication().getUsername(), metaObject);
        }
        this.setFieldValByName("createDate", LocalDateTime.now(), metaObject);
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        if (null != securityUtil.getAuthentication()) {
            this.setFieldValByName("updateUser", securityUtil.getAuthentication().getUsername(), metaObject);
        }
        this.setFieldValByName("updateDate", LocalDateTime.now(), metaObject);
    }
}
