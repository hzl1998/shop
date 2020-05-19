package com.hzl.mapper;

import com.hzl.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> getCategoryList(Integer page, Integer rows);

    int getCategoryListCount();

    Category getCategoryById(Integer id);

    List<Category> getCategories();

    int addCategory(Category category);

    int updateCategoryById(Integer id,String cat_name);

    List<Category> getCategoriesById(Integer id);

    int delCategoryById(Integer id);

    List<Category> getAllCategories();
}
