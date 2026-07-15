package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单拒绝DTO
 */
@Data
@ApiModel(description = "订单拒绝DTO")
public class OrdersRejectionDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单拒绝原因")
    private String rejectionReason;

}
