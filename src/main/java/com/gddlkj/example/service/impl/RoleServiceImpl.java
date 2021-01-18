package com.gddlkj.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gddlkj.example.mapper.RoleMapper;
import com.gddlkj.example.mapper.UserRoleMapper;
import com.gddlkj.example.model.domain.Role;
import com.gddlkj.example.model.domain.UserRole;
import com.gddlkj.example.service.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
@Service
@Slf4j
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>  implements IRoleService{

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> listRoleByUser(Long id) {
        return baseMapper.listRoleByUser(id);
    }

    @Override
    public void updateUserRole(Long id, List<Long> roles) {
        // 先删除所有角色
        userRoleMapper.delete(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId,id));
        UserRole userRole;
        for(Long r:roles)
        {
            userRole=new UserRole();
            userRole.setRoleId(r);
            userRole.setUserId(id);
            userRoleMapper.insert(userRole);
        }
    }
}
