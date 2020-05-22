package com.hzl.mapper;

import com.hzl.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<Order> getOrderList(Integer page, Integer rows, @Param(value="order_number")String order_number);

    int getOrderListCount(String order_number);

    int updOrder(@Param(value="consignee_addr")String consignee_addr, Integer order_id);
}
