package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        Long userId = BaseContext.getCurrentId();

        ShoppingCart condition = new ShoppingCart();
        condition.setUserId(userId);
        condition.setDishId(shoppingCartDTO.getDishId());
        condition.setSetmealId(shoppingCartDTO.getSetmealId());
        condition.setDishFlavor(shoppingCartDTO.getDishFlavor());

        //查询该商品是否已经在购物车中
        ShoppingCart existing = shoppingCartMapper.getByCondition(condition);

        if (existing != null) {
            //已存在，份数 +1
            existing.setNumber(existing.getNumber() + 1);
            shoppingCartMapper.updateNumber(existing);
        } else {
            //不存在，填充商品信息后新增
            ShoppingCart shoppingCart = new ShoppingCart();
            BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
            shoppingCart.setUserId(userId);
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            if (shoppingCartDTO.getDishId() != null) {
                Dish dish = dishMapper.getById(shoppingCartDTO.getDishId());
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {
                Setmeal setmeal = setmealMapper.getById(shoppingCartDTO.getSetmealId());
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }

            shoppingCartMapper.insert(shoppingCart);
        }
    }

    /**
     * 查看购物车
     * @return
     */
    public List<ShoppingCart> showShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        ShoppingCart condition = new ShoppingCart();
        condition.setUserId(userId);
        return shoppingCartMapper.list(condition);
    }

    /**
     * 清空购物车
     */
    public void cleanShoppingCart() {
        Long userId = BaseContext.getCurrentId();
        shoppingCartMapper.clean(userId);
    }

    /**
     * 减少购物车中一个商品
     * @param shoppingCartDTO
     */
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        Long userId = BaseContext.getCurrentId();

        ShoppingCart condition = new ShoppingCart();
        condition.setUserId(userId);
        condition.setDishId(shoppingCartDTO.getDishId());
        condition.setSetmealId(shoppingCartDTO.getSetmealId());
        condition.setDishFlavor(shoppingCartDTO.getDishFlavor());

        ShoppingCart existing = shoppingCartMapper.getByCondition(condition);
        if (existing == null) {
            return;
        }

        if (existing.getNumber() > 1) {
            existing.setNumber(existing.getNumber() - 1);
            shoppingCartMapper.updateNumber(existing);
        } else {
            shoppingCartMapper.deleteById(existing.getId());
        }
    }

}
