package com.gddlkj.example.model.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.gddlkj.example.model.domain.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色中间表
 * </p>
 *
 * @author blank
 * @since 2019-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user_role")
@ApiModel(value="UserRole对象", description="用户角色中间表")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long userId;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDel;

    @ApiModelProperty(value = "版本")
    private Integer version;


}
