package com.gddlkj.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gddlkj.example.model.domain.Dictionary;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author blank
 * @since 2019-01-24
 */
public interface IDictionaryService extends IService<Dictionary> {

    /**
     * @param type  字典类型
     * @param value 字典值
     * @return 返回字典实体
     */
    Dictionary infoByValue(String type, String value);

    /**
     * @param type 字典类型
     * @param name 字典名
     * @return 返回字典实体
     */
    Dictionary infoByName(String type, String name);

}
