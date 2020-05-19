package com.hzl.controller;

import com.hzl.entity.Category;
import com.hzl.entity.Menu;
import com.hzl.entity.PageInfo;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getCategories")
    public Result getCategories() {
        List<Category> categoryList = categoryService.getCategories();
        if (categoryList != null && categoryList.size() > 0){
            return ResultFactory.buildSuccessResult(categoryList,"获取级联分类列表成功");
        } else {
            return ResultFactory.buildFailResult("获取级联分类列表失败");
        }
    }

    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody Category category) {
        if(category.getCat_name() == null || category.getCat_name().equals("")){
            return ResultFactory.buildFailResult("分类名称不能为空");
        }
        if(category.getCat_pid() == null || category.getCat_pid().equals("")){
            return ResultFactory.buildFailResult("分类父ID不能为空");
        }
        if(category.getCat_level() == null || category.getCat_level().equals("")){
            return ResultFactory.buildFailResult("分类层级不能为空");
        }
        int isok = categoryService.addCategory(category);
        if (isok > 0){
            return ResultFactory.buildSuccessResult(null,"添加分类成功");
        } else {
            return ResultFactory.buildFailResult("添加分类失败");
        }
    }

    @GetMapping("/getCategoryById")
    public Result getCategoryById(Integer id) {
        if(id == null || id.equals("")){
            return ResultFactory.buildFailResult("分类id不能为空");
        }
        Category category = categoryService.getCategoryById(id);
        if (category != null){
            return ResultFactory.buildSuccessResult(category,"获取分类名称成功");
        } else {
            return ResultFactory.buildFailResult("获取分类名称失败");
        }
    }

    @PutMapping("/updateCategoryById")
    public Result updateCategoryById(Integer id,String cat_name) {
        if(id == null || id.equals("")){
            return ResultFactory.buildFailResult("分类id不能为空");
        }
        if(cat_name == null || cat_name.equals("")){
            return ResultFactory.buildFailResult("分配名称不能为空");
        }
        int isok = categoryService.updateCategoryById(id,cat_name);
        if (isok > 0){
            return ResultFactory.buildSuccessResult(null,"修改成功");
        } else {
            return ResultFactory.buildFailResult("修改失败");
        }
    }

    @PutMapping("/delCategoryById")
    public Result delCategoryById(Integer id) {
        if(id == null || id.equals("")){
            return ResultFactory.buildFailResult("分类id不能为空");
        }
        List<Category> categoryList = categoryService.getCategoriesById(id);
        if (categoryList.size() > 0){
            return ResultFactory.buildResult(402,true,"当前分类存在下级分类，删除失败",null);
        } else {
            int isok = categoryService.delCategoryById(id);
            if (isok > 0){
                return ResultFactory.buildSuccessResult(null,"删除成功");
            } else {
                return ResultFactory.buildFailResult("删除失败");
            }
        }
    }

    @GetMapping("/getAllCategories")
    public Result getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategories();
        if (categoryList != null && categoryList.size() > 0){
            return ResultFactory.buildSuccessResult(categoryList,"获取级联分类列表成功");
        } else {
            return ResultFactory.buildFailResult("获取级联分类列表失败");
        }
    }
}
