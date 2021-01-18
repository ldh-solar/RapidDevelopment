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

/**
 * <p>
 * 数据字典
 * </p>
 *
 * @author blank
 * @since 2019-01-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_dictionary")
@ApiModel(value="Dictionary对象", description="数据字典")
public class Dictionary extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典名")
    private String name;

    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Boolean isDel;

    @Version
    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "值")
    private String value;

    @ApiModelProperty(value = "上级id")
    private Long pid;


}
