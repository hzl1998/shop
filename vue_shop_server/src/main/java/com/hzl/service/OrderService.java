package com.hzl.service;

import com.hzl.entity.Order;
import com.hzl.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    public List<Order> getOrderList(Integer page,Integer rows,String order_number){
        return orderMapper.getOrderList(page,rows,order_number);
    }

    public int getOrderListCount(String order_number){
        return orderMapper.getOrderListCount(order_number);
    }

    public int updOrder(String consignee_addr, Integer order_id) {
        return orderMapper.updOrder(consignee_addr,order_id);
    }
}
