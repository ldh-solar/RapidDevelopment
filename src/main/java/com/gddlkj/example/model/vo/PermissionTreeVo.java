package com.gddlkj.example.model.vo;

import com.gddlkj.example.model.domain.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "PermissionTreeVo对象", description = "PermissionTreeVo对象")
public class PermissionTreeVo extends Permission {

    @ApiModelProperty(value = "子级")
    private List<PermissionTreeVo> children;


}
