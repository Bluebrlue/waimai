package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单取消DTO
 */
@Data
@ApiModel(description = "订单取消DTO")
public class OrdersCancelDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单取消原因")
    private String cancelReason;

}
