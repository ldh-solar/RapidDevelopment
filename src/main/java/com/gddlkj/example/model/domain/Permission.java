package com.gddlkj.example.model.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.gddlkj.example.model.domain.common.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_permission")
@ApiModel(value="Permission对象", description="权限表")
public class Permission extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "权限名不能为空")
    @ApiModelProperty(value = "权限名")
    private String name;

    @NotBlank(message = "权限编码不能为空")
    @ApiModelProperty(value = "权限编码")
    private String authority;

    @ApiModelProperty(value = "权限类型")
    private String type;

    @ApiModelProperty(value = "父节点id")
    private Long pid;

    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Boolean isDel;

    @Version
    @ApiModelProperty(value = "版本")
    private Integer version;


}
