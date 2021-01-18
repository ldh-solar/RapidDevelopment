package com.gddlkj.example.model.domain;

import com.gddlkj.example.model.domain.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户日志表
 * </p>
 *
 * @author blank
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_user_log")
@ApiModel(value="UserLog对象", description="用户日志表")
public class UserLog extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "ip地址")
    private String loginIp;

    @ApiModelProperty(value = "描述")
    private String logDesc;

    @ApiModelProperty(value = "类型")
    private String logType;

    @ApiModelProperty(value = "参数")
    private String args;

    @ApiModelProperty(value = "方法名")
    private String methodName;

    @ApiModelProperty(value = "运行时间")
    @TableField("runTime")
    private Long runTime;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    private Boolean isDel;

    @ApiModelProperty(value = "版本")
    private Integer version;


}
