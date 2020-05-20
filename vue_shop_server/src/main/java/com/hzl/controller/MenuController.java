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
        try{
            List<Menu> menuList = menuService.getMenusByUserId(userId);
            return ResultFactory.buildSuccessResult(menuList,"获取菜单列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }
    }

    @GetMapping("/allMenus")
    public Result allMenus() {
        try{
            List<Menu> menuList = menuService.getAllMenus();
            return ResultFactory.buildSuccessResult(menuList,"获取菜单列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }
    }

    @GetMapping("/getMenusByRoleId")
    public Result getMenusByRoleId(String roleId) {
        if(roleId == null || roleId.equals("")){
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        try{
            List<Menu> menuList = menuService.selectMenusByRoleId(roleId);
            return ResultFactory.buildSuccessResult(menuList,"获取菜单列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
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
        try{
            int isok = menuService.delRm(rm.getRoleId());
            int isok1 = menuService.addRm(rm);
            if (isok1 > 0){
                return ResultFactory.buildSuccessResult(null,"更新成功");
            } else {
                return ResultFactory.buildFailResult("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }

    }
}
