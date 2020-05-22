package com.hzl.controller;

import com.hzl.entity.Goods;
import com.hzl.entity.Order;
import com.hzl.entity.PageInfo;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    public Result getOrderList(Integer page, Integer rows, String order_number){
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        //第几页
        Integer currentPage = page;

        page = (page - 1) * rows;
        try {
            //总条数
            int total = orderService.getOrderListCount(order_number);
            List<Order> orderList = orderService.getOrderList(page,rows,order_number);
            // 计算总页数
            int count=0;
            if(total%rows==0){
                count=total/rows;
            }else{
                count=total/rows+1;
            }
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCount(orderList.size()); // 本页实际记录数
            pageInfo.setCurrentPage(currentPage); // 第几页
            pageInfo.setResultList(orderList); // 数据
            pageInfo.setTotalPage(count); // 总页数
            pageInfo.setTotalResult(total); // 总记录数
            return ResultFactory.buildSuccessResult(pageInfo,"获取订单列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }
    }

    @PutMapping("/orders/update")
    public Result updOrder(String consignee_addr,Integer order_id){
        if(order_id == null || order_id.equals("")){
            return ResultFactory.buildFailResult("订单id不能为空");
        }
        if(consignee_addr == null || consignee_addr.equals("")){
            return ResultFactory.buildFailResult("地址不能为空");
        }
        try {
            int isok = orderService.updOrder(consignee_addr,order_id);
            if(isok > 0){
                return ResultFactory.buildSuccessResult(null,"修改成功");
            }else {
                return ResultFactory.buildFailResult("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }
    }
}
