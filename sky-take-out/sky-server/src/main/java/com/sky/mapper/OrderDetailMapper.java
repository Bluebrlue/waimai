package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 批量插入订单明细
     * @param orderDetails
     */
    @Insert("<script>" +
            "insert into order_detail (name, image, order_id, dish_id, setmeal_id, dish_flavor, number, amount) " +
            "values " +
            "<foreach collection='orderDetails' item='item' separator=','>" +
            "(#{item.name}, #{item.image}, #{item.orderId}, #{item.dishId}, #{item.setmealId}, #{item.dishFlavor}, #{item.number}, #{item.amount})" +
            "</foreach>" +
            "</script>")
    void insertBatch(List<OrderDetail> orderDetails);
}
