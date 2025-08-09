package com.sky.controller.user;

import com.alibaba.fastjson2.JSON;
import com.sky.constant.StatusConstant;
import com.sky.entity.Dish;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Slf4j
@Tag(name = "C端-菜品浏览接口")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/list")
    @Operation(summary = "根据分类id查询菜品")
    public Result<List<DishVO>> list(Long categoryId){
        log.info("根据分类id查询菜品：{}", categoryId);

        String key = "dish_" + categoryId;
        String json = redisTemplate.opsForValue().get(key);
        List<DishVO> list = JSON.parseArray(json, DishVO.class);
        if(list != null && !list.isEmpty()){
            return Result.success(list);
        }

        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        list = dishService.listWithFlavor(dish);
        redisTemplate.opsForValue().set(key, JSON.toJSONString(list));
        return Result.success(list);
    }
}
