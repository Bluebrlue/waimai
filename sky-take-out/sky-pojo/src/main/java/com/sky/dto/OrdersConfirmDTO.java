package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单确认DTO
 */
@Data
@ApiModel(description = "订单确认DTO")
public class OrdersConfirmDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款")
    private Integer status;

}
