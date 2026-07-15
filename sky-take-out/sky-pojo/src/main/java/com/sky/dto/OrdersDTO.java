package com.sky.dto;

import com.sky.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单信息DTO
 */
@Data
@ApiModel(description = "订单信息DTO")
public class OrdersDTO implements Serializable {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("订单号")
    private String number;

    @ApiModelProperty("订单状态 1待付款 2待接单 3已接单 4派送中 5已完成 6已取消 7退款")
    private Integer status;

    @ApiModelProperty("下单用户id")
    private Long userId;

    @ApiModelProperty("地址id")
    private Long addressBookId;

    @ApiModelProperty("下单时间")
    private LocalDateTime orderTime;

    @ApiModelProperty("结账时间")
    private LocalDateTime checkoutTime;

    @ApiModelProperty("支付方式 1微信,2支付宝")
    private Integer payMethod;

    @ApiModelProperty("实收金额")
    private BigDecimal amount;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("收货人")
    private String consignee;

    @ApiModelProperty("订单菜品")
    private List<OrderDetail> orderDetails;

}
