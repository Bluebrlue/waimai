package com.sky.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单分页查询DTO
 */
@Data
@ApiModel(description = "订单分页查询DTO")
public class OrdersPageQueryDTO implements Serializable {

    @ApiModelProperty("页码")
    private int page;

    @ApiModelProperty("每页显示记录数")
    private int pageSize;

    @ApiModelProperty("订单号")
    private String number;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款")
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始时间")
    private LocalDateTime beginTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("下单用户id")
    private Long userId;

}
