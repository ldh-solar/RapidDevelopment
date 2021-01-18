package com.gddlkj.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gddlkj.example.mapper.DictionaryMapper;
import com.gddlkj.example.model.domain.Dictionary;
import com.gddlkj.example.service.IDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author blank
 * @since 2019-01-24
 */
@Service
@Slf4j
@Transactional
public class DictionaryServiceImpl extends ServiceImpl<DictionaryMapper, Dictionary>  implements IDictionaryService{


    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Dictionary infoByValue(String type, String value) {
        Dictionary dictionary = (Dictionary) redisTemplate.opsForValue().get("dictionary:"+type+":value:"+value);
        if(null==dictionary)
        {
            Dictionary dType = baseMapper.selectOne(Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getName,type));
            dictionary = baseMapper.selectOne(Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getPid,dType.getId()).eq(Dictionary::getValue,value));
            if(null!=dictionary)
                redisTemplate.opsForValue().set("dictionary:" + type + ":value:" + value, dictionary, 1, TimeUnit.HOURS);
        }
        return dictionary;
    }

    @Override
    public Dictionary infoByName(String type, String name) {
        Dictionary dictionary = (Dictionary) redisTemplate.opsForValue().get("dictionary:"+type+":name:"+name);
        if(null==dictionary)
        {
            Dictionary dType = baseMapper.selectOne(Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getName,type));
            dictionary = baseMapper.selectOne(Wrappers.<Dictionary>lambdaQuery().eq(Dictionary::getPid,dType.getId()).eq(Dictionary::getName,name));
            if(null!=dictionary)
                redisTemplate.opsForValue().set("dictionary:"+type+":name:"+name,dictionary,1, TimeUnit.HOURS);
        }
        return dictionary;
    }
}
