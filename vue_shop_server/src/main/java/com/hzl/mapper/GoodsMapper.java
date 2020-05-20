package com.hzl.mapper;

import com.hzl.entity.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    List<Goods> getGoodsList(Integer page, Integer rows);

    int getGoodsListCount();
}
