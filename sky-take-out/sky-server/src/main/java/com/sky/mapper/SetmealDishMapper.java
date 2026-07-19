package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询关联的套餐数量
     * @param dishId
     * @return
     */
    @Select("select count(*) from setmeal_dish where dish_id = #{dishId}")
    Integer countByDishId(Long dishId);

    /**
     * 根据套餐id查询关联的菜品列表
     * @param setmealId
     * @return
     */
    @Select("select * from setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);

    /**
     * 根据菜品id删除套餐菜品关联
     * @param dishId
     */
    @Delete("delete from setmeal_dish where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 根据菜品id列表查询关联的套餐id列表
     * @param dishIds
     * @return
     */
    @Select("<script>select setmeal_id from setmeal_dish where dish_id in <foreach collection='dishIds' item='id' open='(' separator=',' close=')'>#{id}</foreach></script>")
    List<Long> getSetmealIdByDishIds(List<Long> dishIds);
}
