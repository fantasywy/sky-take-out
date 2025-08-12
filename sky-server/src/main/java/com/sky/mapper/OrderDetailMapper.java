package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderDetailMapper {
    void insertBatch(List<OrderDetail> orderDetailList);

    @Select("select id, name, image, order_id, dish_id, setmeal_id, dish_flavor, number, amount from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);
}
