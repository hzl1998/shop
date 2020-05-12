package com.hzl.controller;

import com.hzl.entity.Menu;
import com.hzl.entity.Role;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.MenuService;
import com.hzl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping("/roles")
    public Result roles() {
        List<Role> menuList = roleService.getRoles();
        if (menuList != null && menuList.size() != 0){
            return ResultFactory.buildSuccessResult(menuList,"获取角色列表成功");
        } else {
            return ResultFactory.buildFailResult("获取角色列表失败");
        }
    }
}
