package com.gddlkj.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gddlkj.example.model.domain.Role;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
public interface IRoleService extends IService<Role> {

    List<Role> listRoleByUser(Long id);

    void updateUserRole(Long id, List<Long> roles);
}
