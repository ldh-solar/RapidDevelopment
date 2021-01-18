package com.gddlkj.example.util;

import com.gddlkj.example.model.domain.Permission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {
    @Autowired
    //private MongoTemplate mongoTemplate;


    /**
     * 设置mongo数据例子
     */
    @Test
    public void set() {
        Permission permission = new Permission();
        permission.setAuthority("123");
        permission.setIsDel(false);
        permission.setName("abc");
        //mongoTemplate.save(permission);
    }


}
