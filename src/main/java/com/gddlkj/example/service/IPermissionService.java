package com.gddlkj.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gddlkj.example.model.domain.Permission;
import com.gddlkj.example.model.vo.PermissionTreeVo;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author blank
 * @since 2019-01-03
 */
public interface IPermissionService extends IService<Permission> {

    List<Permission> findByUserId(Long userId);

    List<PermissionTreeVo> listTree();

    List<Permission> findByRoleId(Long id);

    void updateRolePermission(Long id, List<Permission> permission);
}
