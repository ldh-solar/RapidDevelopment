package com.gddlkj.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gddlkj.example.mapper.RolePermissionMapper;
import com.gddlkj.example.model.domain.RolePermission;
import com.gddlkj.example.service.IRolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 角色权限中间表 服务实现类
 * </p>
 *
 * @author blank
 * @since 2019-01-07
 */
@Service
@Slf4j
@Transactional
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>  implements IRolePermissionService{

}
