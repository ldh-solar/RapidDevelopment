package com.gddlkj.example.service.impl;

import com.gddlkj.example.model.domain.UserLog;
import com.gddlkj.example.mapper.UserLogMapper;
import com.gddlkj.example.service.IUserLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户日志表 服务实现类
 * </p>
 *
 * @author blank
 * @since 2020-08-10
 */
@Service
@Slf4j
@Transactional
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog>  implements IUserLogService{

}
