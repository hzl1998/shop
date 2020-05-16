package com.hzl.service;

import com.hzl.entity.Category;
import com.hzl.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    public List<Category> getCategoryList(Integer page,Integer rows){
        return categoryMapper.getCategoryList(page,rows);
    }

    public int getCategoryListCount(){
        return categoryMapper.getCategoryListCount();
    }

    public Category getCategoryByid(Integer id) {
        return categoryMapper.getCategoryByid(id);
    }
}
