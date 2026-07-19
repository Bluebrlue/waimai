package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 基于 Redis 的购物车数据访问
 * key: cart:user:{userId}
 * field: {type}:{dishId|setmealId}:{dishFlavor}
 * value: ShoppingCart (JSON)
 */
@Repository
public class ShoppingCartMapper {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY_PREFIX = "cart:user:";

    /**
     * 查询某用户购物车全部数据
     * @param userId
     * @return
     */
    public List<ShoppingCart> list(Long userId) {
        String key = KEY_PREFIX + userId;
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        if (entries == null || entries.isEmpty()) {
            return new ArrayList<>();
        }
        List<ShoppingCart> list = new ArrayList<>();
        for (Object value : entries.values()) {
            list.add((ShoppingCart) value);
        }
        return list;
    }

    /**
     * 判断购物车中是否已存在某个商品
     * @param key 完整的 Redis key
     * @param field 商品唯一标识
     * @return
     */
    public ShoppingCart getByField(String key, String field) {
        Object obj = redisTemplate.opsForHash().get(key, field);
        return obj == null ? null : (ShoppingCart) obj;
    }

    /**
     * 添加或修改购物车商品
     * @param key
     * @param field
     * @param shoppingCart
     */
    public void put(String key, String field, ShoppingCart shoppingCart) {
        redisTemplate.opsForHash().put(key, field, shoppingCart);
    }

    /**
     * 删除某个购物车商品
     * @param key
     * @param field
     */
    public void remove(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 清空该用户购物车
     * @param userId
     */
    public void clean(Long userId) {
        redisTemplate.delete(KEY_PREFIX + userId);
    }

}