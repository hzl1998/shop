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

    public Category getCategoryById(Integer id) {
        return categoryMapper.getCategoryById(id);
    }

    public List<Category> getCategories() {
        return categoryMapper.getCategories();
    }

    public int addCategory(Category category) {
        return categoryMapper.addCategory(category);
    }

    public int updateCategoryById(Integer id,String cat_name) {
        return categoryMapper.updateCategoryById(id,cat_name);
    }

    public List<Category> getCategoriesById(Integer id) {
        return categoryMapper.getCategoriesById(id);
    }

    public int delCategoryById(Integer id) {
        return categoryMapper.delCategoryById(id);
    }

    public List<Category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }
}
