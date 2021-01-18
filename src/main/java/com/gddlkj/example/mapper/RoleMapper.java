package com.gddlkj.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gddlkj.example.model.domain.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select * from t_role inner join t_user_role on t_role.id = t_user_role.role_id and t_user_role.user_id = #{id} and t_role.is_del=0  ")
    List<Role> listRoleByUser(Long id);
}
