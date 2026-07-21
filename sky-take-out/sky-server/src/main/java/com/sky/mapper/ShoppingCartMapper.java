package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 基于 MySQL 的购物车数据访问
 */
@Mapper
public interface ShoppingCartMapper {

    /**
     * 查询某用户购物车全部数据
     * @param shoppingCart 查询条件（userId 必填）
     * @return
     */
    @Select("select * from shopping_cart where user_id = #{userId} order by create_time desc")
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 查询某用户购物车中是否存在指定条件的商品
     * 按 userId + dishId + dishFlavor 或 userId + setmealId 精确匹配
     * @param shoppingCart 查询条件
     * @return
     */
    @Select("select * from shopping_cart " +
            "where user_id = #{userId} " +
            "and (#{dishId} is not null and dish_id = #{dishId} and dish_flavor = #{dishFlavor} " +
            "     or #{setmealId} is not null and setmeal_id = #{setmealId})")
    ShoppingCart getByCondition(ShoppingCart shoppingCart);

    /**
     * 更新购物车商品数量
     * @param shoppingCart
     */
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumber(ShoppingCart shoppingCart);

    /**
     * 插入购物车商品
     * @param shoppingCart
     */
    @Insert("insert into shopping_cart (name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time) " +
            "values (#{name}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{image}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(ShoppingCart shoppingCart);

    /**
     * 删除某个购物车商品
     * @param id
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

    /**
     * 清空该用户购物车
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void clean(Long userId);

    /**
     * 根据用户id清空购物车（deleteByUserId 供 Service 层调用）
     * @param userId
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);

}
