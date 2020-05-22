package com.hzl.service;

import com.hzl.entity.Goods;
import com.hzl.entity.GoodsPics;
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

    public Goods getGoodById(Integer goods_id) {
        return goodsMapper.getGoodById(goods_id);
    }

    public List<GoodsPics> getGoodPicsById(Integer goods_id) {
        return goodsMapper.getGoodPicsById(goods_id);
    }

    public int updateGood(Goods goods) {
        return goodsMapper.updateGood(goods);
    }

    public int delGoodPics(Integer goods_id) {
        return goodsMapper.delGoodPics(goods_id);
    }

    public int delGoodAttr(Integer goods_id) {
        return goodsMapper.delGoodAttr(goods_id);
    }
}
