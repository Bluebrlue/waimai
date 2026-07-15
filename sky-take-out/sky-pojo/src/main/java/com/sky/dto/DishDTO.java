package com.sky.dto;

import com.sky.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜品数据模型
 */
@Data
@ApiModel(description = "菜品数据模型")
public class DishDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("菜品名称")
    private String name;

    @ApiModelProperty("菜品分类id")
    private Long categoryId;

    @ApiModelProperty("菜品价格")
    private BigDecimal price;

    @ApiModelProperty("图片")
    private String image;

    @ApiModelProperty("描述信息")
    private String description;

    @ApiModelProperty("状态 0停售 1起售")
    private Integer status;

    @ApiModelProperty("口味")
    private List<DishFlavor> flavors = new ArrayList<>();

}
