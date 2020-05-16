package com.hzl.mapper;

import com.hzl.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> getCategoryList(Integer page, Integer rows);

    int getCategoryListCount();

    Category getCategoryByid(Integer id);
}
