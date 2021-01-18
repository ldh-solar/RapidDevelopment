package com.gddlkj.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gddlkj.example.model.domain.Permission;
import com.gddlkj.example.model.vo.PermissionTreeVo;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author blank
 * @since 2019-01-03
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> findByUserId(Long userId);

    List<PermissionTreeVo> listTree();

    List<PermissionTreeVo> selectPermissionChildren();

    List<Permission> findByRoleId(Long id);
}
