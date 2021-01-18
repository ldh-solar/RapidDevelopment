package com.gddlkj.example.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gddlkj.example.mapper.PermissionMapper;
import com.gddlkj.example.mapper.RolePermissionMapper;
import com.gddlkj.example.model.domain.Permission;
import com.gddlkj.example.model.domain.RolePermission;
import com.gddlkj.example.model.vo.PermissionTreeVo;
import com.gddlkj.example.service.IPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author blank
 * @since 2019-01-03
 */
@Service
@Slf4j
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>  implements IPermissionService{

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Permission> findByUserId(Long userId) {
        return baseMapper.findByUserId(userId);
    }

    @Override
    public List<PermissionTreeVo> listTree() {
        return baseMapper.listTree();
    }

    @Override
    public List<Permission> findByRoleId(Long id) {
        return baseMapper.findByRoleId(id);
    }

    /**
     * 更新角色权限信息
     * @param id 用户id
     * @param permission 权限数组
     */
    @Override
    public void updateRolePermission(Long id, List<Permission> permission) {
        // 先删除权限信息
        rolePermissionMapper.delete(Wrappers.<RolePermission>lambdaQuery().eq(RolePermission::getRoleId,id));
        RolePermission rolePermission;
        for(Permission p:permission)
        {
            rolePermission=new RolePermission();
            rolePermission.setPermissionId(p.getId());
            rolePermission.setRoleId(id);
            rolePermissionMapper.insert(rolePermission);
        }

    }
}
