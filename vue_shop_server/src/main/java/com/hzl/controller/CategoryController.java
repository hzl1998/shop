package com.hzl.controller;

import com.hzl.entity.Category;
import com.hzl.entity.Menu;
import com.hzl.entity.PageInfo;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public Result categories(Integer page,Integer rows) {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        //第几页
        Integer currentPage = page;

        page = (page - 1) * rows;
        //总条数
        int total = categoryService.getCategoryListCount();
        List<Category> categoryList = categoryService.getCategoryList(page,rows);
        // 计算总页数
        int count=0;
        if(total%rows==0){
            count=total/rows;
        }else{
            count=total/rows+1;
        }

        if (categoryList != null && categoryList.size() > 0){
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCount(categoryList.size()); // 本页实际记录数
            pageInfo.setCurrentPage(currentPage); // 第几页
            pageInfo.setResultList(categoryList); // 数据
            pageInfo.setTotalPage(count); // 总页数
            pageInfo.setTotalResult(total); // 总记录数
            return ResultFactory.buildSuccessResult(pageInfo,"获取分类列表成功");
        } else {
            return ResultFactory.buildFailResult("获取分类列表失败");
        }
    }
}
