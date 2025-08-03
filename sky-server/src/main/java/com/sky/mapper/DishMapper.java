package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    @Select("select count(*) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    @Select("select id, name, category_id, price, image, description, status, create_time, update_time, create_user, update_user from dish where id = #{id}")
    Dish getById(Long id);

    void deleteByIds(List<Long> ids);
}
