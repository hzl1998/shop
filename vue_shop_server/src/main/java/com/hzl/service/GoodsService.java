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

    public int addGood(Goods goods) {
        return goodsMapper.addGood(goods);
    }

    public int addGoodPics(Object[] pics, Integer goods_id) {
        return goodsMapper.addGoodPics(pics,goods_id);
    }

    public int addGoodAttr(Object[] attrs, Integer goods_id) {
        return goodsMapper.addGoodAttr(attrs,goods_id);
    }
}
