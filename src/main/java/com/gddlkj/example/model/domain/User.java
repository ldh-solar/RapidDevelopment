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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user")
@ApiModel(value="User对象", description="用户表")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    @Size(min=3, max=20, message="用户名长度只能在3-10之间")
    private String username;

    @NotBlank(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    @Size(min=6, max=20, message="密码长度只能在6-20之间")
    @ApiModelProperty(value = "密码")
    private String password;

    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Boolean isDel;

    @Version
    @ApiModelProperty(value = "版本")
    private Integer version;


}
