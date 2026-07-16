package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜品管理
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增菜品和口味
     * @param dishDTO
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        // 1. 保存菜品基本信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);

        // 2. 获取菜品id
        Long dishId = dish.getId();

        // 3. 保存口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(flavor -> flavor.setDishId(dishId));
            dishMapper.insertFlavors(flavors);
        }
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<Dish> page = dishMapper.pageQuery(dishPageQueryDTO);

        // 组装DishVO列表
        List<DishVO> records = new ArrayList<>();
        for (Dish dish : page) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish, dishVO);
            // 查询分类名称
            if (dish.getCategoryId() != null) {
                Category category = categoryMapper.getById(dish.getCategoryId());
                if (category != null) {
                    dishVO.setCategoryName(category.getName());
                }
            }
            records.add(dishVO);
        }

        return new PageResult(page.getTotal(), records);
    }

    /**
     * 根据ID查询菜品和口味
     * @param id
     * @return
     */
    @Override
    public DishVO getById(Long id) {
        // 1. 查询菜品基本信息
        Dish dish = dishMapper.getById(id);
        if (dish == null) {
            return null;
        }

        // 2. 查询口味信息
        List<DishFlavor> flavors = dishMapper.getFlavorsByDishId(id);

        // 3. 查询分类名称
        String categoryName = null;
        if (dish.getCategoryId() != null) {
            Category category = categoryMapper.getById(dish.getCategoryId());
            if (category != null) {
                categoryName = category.getName();
            }
        }

        // 4. 组装DishVO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setCategoryName(categoryName);
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    /**
     * 修改菜品状态（起售/停售）
     * @param status
     * @param id
     */
    @Override
    @Transactional
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishMapper.update(dish);
    }

    /**
     * 编辑菜品和口味
     * @param dishDTO
     */
    @Override
    @Transactional
    public void updateWithFlavor(DishDTO dishDTO) {
        // 1. 更新菜品基本信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.update(dish);

        // 2. 删除原有口味
        dishMapper.deleteFlavorsByDishId(dishDTO.getId());

        // 3. 重新插入口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            dishMapper.insertFlavors(flavors);
        }
    }

    /**
     * 删除菜品（支持批量删除）
     * @param ids 多个ID用逗号分隔
     */
    @Override
    @Transactional
    public void deleteByIds(String ids) {
        String[] idArray = ids.split(",");
        for (String idStr : idArray) {
            Long id = Long.parseLong(idStr.trim());
            // 1. 删除口味
            dishMapper.deleteFlavorsByDishId(id);
            // 2. 删除菜品
            dishMapper.deleteById(id);
        }
    }

    /**
     * 根据分类ID查询起售中的菜品
     * @param categoryId
     * @return
     */
    @Override
    public List<Dish> getByCategoryId(Long categoryId) {
        return dishMapper.getByCategoryId(categoryId);
    }

    /**
     * 菜品条件查询
     * @param dish
     * @return
     */
    @Override
    public List<Dish> list(Dish dish) {
        return dishMapper.getByCategoryId(dish.getCategoryId());
    }
}
