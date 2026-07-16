package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import java.util.List;

/**
 * 菜品管理
 */
public interface DishService {

    /**
     * 新增菜品和口味
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据ID查询菜品和口味
     * @param id
     * @return
     */
    DishVO getById(Long id);

    /**
     * 修改菜品状态（起售/停售）
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 编辑菜品和口味
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 删除菜品（支持批量删除，ids 用逗号分隔）
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 根据分类ID查询起售中的菜品
     * @param categoryId
     * @return
     */
    List<Dish> getByCategoryId(Long categoryId);

    /**
     * 菜品条件查询
     * @param dish
     * @return
     */
    List<Dish> list(Dish dish);
}
