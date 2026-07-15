package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分类数据模型
 */
@Data
@ApiModel(description = "分类数据模型")
public class CategoryDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("类型 1菜品分类 2套餐分类")
    private Integer type;

    @ApiModelProperty("分类名称")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

}
