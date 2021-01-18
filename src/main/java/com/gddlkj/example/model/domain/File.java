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
 * 文件表
 * </p>
 *
 * @author blank
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_file")
@ApiModel(value="File对象", description="文件表")
public class File extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名")
    private String filename;

    @ApiModelProperty(value = "地址")
    private String url;

    @TableLogic
    @ApiModelProperty(value = "是否删除")
    private Boolean isDel;

    @Version
    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "保存路径")
    private String filePath;

    @ApiModelProperty(value = "文件大小")
    private Long size;


}
