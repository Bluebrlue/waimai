package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 套餐分页查询DTO
 */
@Data
@ApiModel(description = "套餐分页查询DTO")
public class SetmealPageQueryDTO implements Serializable {

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页显示记录数")
    private int pageSize;

    @ApiModelProperty("套餐名称")
    private String name;

    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("套餐状态 0表示禁用 1表示启用")
    private Integer status;

}
