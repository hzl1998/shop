package com.hzl.controller;

import com.hzl.entity.Menu;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenuController {
    @Autowired
    MenuService menuService;

    @GetMapping("/menus")
    public Result menus(String userId) {
        if(userId == null || userId.equals("")){
            return ResultFactory.buildFailResult("用户id不能为空");
        }
        List<Menu> menuList = menuService.getMenusByUserId(userId);
        if (menuList != null && menuList.size() != 0){
            return ResultFactory.buildSuccessResult(menuList,"获取菜单列表成功");
        } else {
            return ResultFactory.buildFailResult("获取菜单列表失败");
        }
    }
}
