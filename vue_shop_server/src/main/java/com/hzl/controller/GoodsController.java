package com.hzl.controller;

import com.hzl.entity.Category;
import com.hzl.entity.Goods;
import com.hzl.entity.PageInfo;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    GoodsService goodsService;

    @GetMapping("/goods")
    public Result getGoodsList(Integer page,Integer rows){
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
            int total = goodsService.getGoodsListCount();
            List<Goods> goodsList = goodsService.getGoodsList(page,rows);
            // 计算总页数
            int count=0;
            if(total%rows==0){
                count=total/rows;
            }else{
                count=total/rows+1;
            }
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCount(goodsList.size()); // 本页实际记录数
            pageInfo.setCurrentPage(currentPage); // 第几页
            pageInfo.setResultList(goodsList); // 数据
            pageInfo.setTotalPage(count); // 总页数
            pageInfo.setTotalResult(total); // 总记录数
            return ResultFactory.buildSuccessResult(pageInfo,"获取商品列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }
    }
}
