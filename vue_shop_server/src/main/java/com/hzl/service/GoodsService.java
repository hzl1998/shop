package com.hzl.service;

import com.hzl.entity.Goods;
import com.hzl.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    public List<Goods> getGoodsList(Integer page,Integer rows,String goods_name){
        return goodsMapper.getGoodsList(page,rows,goods_name);
    }

    public int getGoodsListCount(String goods_name){
        return goodsMapper.getGoodsListCount(goods_name);
    }

    public int delGood(Integer goods_id,Long delete_time) {
        return goodsMapper.delGood(goods_id,delete_time);
    }
}
