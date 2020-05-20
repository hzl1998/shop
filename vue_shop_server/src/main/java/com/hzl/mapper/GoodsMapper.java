package com.hzl.mapper;

import com.hzl.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<Goods> getGoodsList(Integer page, Integer rows,@Param(value="goods_name")String goods_name);

    int getGoodsListCount(@Param(value="goods_name")String goods_name);
}
