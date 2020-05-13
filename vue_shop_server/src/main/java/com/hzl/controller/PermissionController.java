package com.hzl.controller;

import com.hzl.entity.*;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.MenuService;
import com.hzl.service.PermissionService;
import com.hzl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;

    @GetMapping("/rights")
    public Result rights(Integer page,Integer rows) {
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
        int total = permissionService.getRightsCount();
        //分页数据
        List<Permission> permissionList = permissionService.getRights(page,rows);
        // 计算总页数
        int count=0;
        if(total%rows==0){
            count=total/rows;
        }else{
            count=total/rows+1;
        }

        if(permissionList != null && permissionList.size() > 0){
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCount(permissionList.size()); // 本页实际记录数
            pageInfo.setCurrentPage(currentPage); // 第几页
            pageInfo.setResultList(permissionList); // 数据
            pageInfo.setTotalPage(count); // 总页数
            pageInfo.setTotalResult(total); // 总记录数
            return ResultFactory.buildSuccessResult(pageInfo,"获取权限列表成功");
        } else {
            return ResultFactory.buildFailResult("获取权限列表失败");
        }
    }

    @DeleteMapping("/rights/delPermission")
    public Result delPermission(String roleId,String rightId) {
        if (roleId == null || roleId.equals("")) {
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        if (rightId == null || rightId.equals("")) {
            return ResultFactory.buildFailResult("权限id不能为空");
        }
        Permission permission = permissionService.getRightsById(rightId);
        if (permission.getLevel() == 0) {
            int isok = permissionService.delMaxPermission(roleId);
            List<Permission> permissionList1 = permissionService.getRightsByRoleId(roleId);
            if (isok > 0) {
                return ResultFactory.buildSuccessResult(permissionList1, "删除角色权限成功");
            } else {
                return ResultFactory.buildFailResult("删除角色权限失败");
            }
        } else if (permission.getLevel() == 1) {
            List<Permission> permissionList = permissionService.getMinRights(rightId);
            String[] ids = new String[permissionList.size()];
            for (int i = 0; i < permissionList.size(); i++) {
                ids[i] = permissionList.get(i).getId();
            }
            int isok1 = permissionService.delListPermission(roleId, ids);
            int isok = permissionService.delPermission(roleId, rightId);
            List<Permission> permissionList1 = permissionService.getRightsByRoleId(roleId);
            if (isok1 > 0 && isok > 0) {
                return ResultFactory.buildSuccessResult(permissionList1, "删除角色权限成功");
            } else {
                return ResultFactory.buildFailResult("删除角色权限失败");
            }
        } else {
            int isok = permissionService.delPermission(roleId, rightId);
            List<Permission> permissionList1 = permissionService.getRightsByRoleId(roleId);
            if (isok > 0) {
                return ResultFactory.buildSuccessResult(permissionList1, "删除角色权限成功");
            } else {
                return ResultFactory.buildFailResult("删除角色权限失败");
            }
        }
    }

    @GetMapping("/rights/selectRights")
    public Result selectRights() {
        List<Permission> permissionList = permissionService.selectRights();
        if (permissionList != null && permissionList.size() != 0){
            return ResultFactory.buildSuccessResult(permissionList,"获取权限列表成功");
        } else {
            return ResultFactory.buildFailResult("获取权限列表失败");
        }
    }

    @PostMapping("/rights/addRp")
    public Result addRp(@RequestBody Rp rp) {
        if(rp.getRoleId() == null || rp.getRoleId().equals("")){
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        if(rp.getRightsId() == null || rp.getRightsId().length < 1){
            return ResultFactory.buildFailResult("权限id数组不能为空");
        }
        int isok1 =roleService.delRpById(rp.getRoleId());
        int isok = permissionService.addRp(rp.getRoleId(),rp.getRightsId());
        if (isok > 0){
            return ResultFactory.buildSuccessResult(null,"更新成功");
        } else {
            return ResultFactory.buildFailResult("更新失败");
        }
    }
}
