package com.gddlkj.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gddlkj.example.mapper.UserRoleMapper;
import com.gddlkj.example.model.domain.UserRole;
import com.gddlkj.example.service.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户角色中间表 服务实现类
 * </p>
 *
 * @author blank
 * @since 2019-01-07
 */
@Service
@Slf4j
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>  implements IUserRoleService{

}
