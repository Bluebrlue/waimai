package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜品分页查询DTO
 */
@Data
@ApiModel(description = "菜品分页查询DTO")
public class DishPageQueryDTO implements Serializable {

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页显示记录数")
    private int pageSize;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("状态 0表示禁用 1表示启用")
    private Integer status;

}
