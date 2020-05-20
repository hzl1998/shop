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

    public List<Goods> getGoodsList(Integer page,Integer rows){
        return goodsMapper.getGoodsList(page,rows);
    }

    public int getGoodsListCount(){
        return goodsMapper.getGoodsListCount();
    }
}
