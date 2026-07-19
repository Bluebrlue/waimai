package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新增菜品")
    public Result<String> save(@RequestBody DishDTO dishDTO) {

        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        //删除redis对应缓存
        String key = "dish_" + dishDTO.getCategoryId() ;
        log.info("删除redis缓存：{}" , key);
        cleanCache(key);
        return Result.success();
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据ID查询菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据ID查询菜品")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据ID查询菜品：{}", id);
        DishVO dish = dishService.getById(id);
        return Result.success(dish);
    }

    /**
     * 根据分类ID查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类ID查询菜品")
    public Result<List<Dish>> list(@RequestParam Long categoryId) {
        log.info("根据分类ID查询菜品：{}", categoryId);
        List<Dish> dishList = dishService.getByCategoryId(categoryId);
        return Result.success(dishList);
    }

    /**
     * 修改菜品状态（起售/停售）
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("修改菜品状态")
    public Result<String> startOrStop(@PathVariable Integer status, @RequestParam Long id) {
        log.info("修改菜品状态：status={}, id={}", status, id);
        dishService.startOrStop(status, id);
        //删除redis所有菜品缓存
        cleanCache("dish_*");
        log.info("删除redis缓存：{}", "dish_*");
        return Result.success();
    }

    /**
     * 编辑菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑菜品")
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        log.info("编辑菜品：{}", dishDTO);
        dishService.updateWithFlavor(dishDTO);
        //删除redis对应缓存
        String key = "dish_" + dishDTO.getCategoryId() ;
        log.info("删除redis缓存：{}", key);
        cleanCache(key);
        return Result.success();
    }

    /**
     * 批量删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result<String> deleteByIds(@RequestParam("ids") String ids) {
        log.info("批量删除菜品：{}", ids);
        dishService.deleteByIds(ids);
        //删除redis所有菜品缓存
        cleanCache("dish_*");
        log.info("删除redis缓存：{}", "dish_*");
        return Result.success();
    }

    /**
     * 清理缓存
     * @param pattern
     */
    private void cleanCache(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
