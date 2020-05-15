package com.hzl.controller;

import com.hzl.entity.Menu;
import com.hzl.entity.Rm;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/allMenus")
    public Result allMenus() {
        List<Menu> menuList = menuService.getAllMenus();
        if (menuList != null && menuList.size() != 0){
            return ResultFactory.buildSuccessResult(menuList,"获取菜单列表成功");
        } else {
            return ResultFactory.buildFailResult("获取菜单列表失败");
        }
    }

    @GetMapping("/getMenusByRoleId")
    public Result getMenusByRoleId(String roleId) {
        if(roleId == null || roleId.equals("")){
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        List<Menu> menuList = menuService.selectMenusByRoleId(roleId);
        if (menuList != null && menuList.size() != 0){
            return ResultFactory.buildSuccessResult(menuList,"获取菜单列表成功");
        } else {
            return ResultFactory.buildFailResult("获取菜单列表失败");
        }
    }

    @PostMapping("/addRm")
    public Result addRm(@RequestBody Rm rm) {
        if(rm.getRoleId() == null || rm.getRoleId().equals("")){
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        if(rm.getMids() == null || rm.getMids().equals("")){
            return ResultFactory.buildFailResult("菜单id数组不能为空");
        }
        int isok = menuService.delRm(rm.getRoleId());
        int isok1 = menuService.addRm(rm);
        if (isok1 > 0){
            return ResultFactory.buildSuccessResult(null,"更新成功");
        } else {
            return ResultFactory.buildFailResult("更新失败");
        }
    }
}
