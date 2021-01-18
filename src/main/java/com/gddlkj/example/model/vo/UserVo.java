package com.gddlkj.example.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author blank
 * @since 2019-01-06
 */
@Data
@ApiModel(value = "UserVo对象", description = "映射")
public class UserVo {

    @ApiModelProperty(value = "主键ID")
    private Long Id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createDate;


}
