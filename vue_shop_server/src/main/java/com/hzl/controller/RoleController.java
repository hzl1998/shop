package com.hzl.controller;

import com.hzl.entity.*;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.MenuService;
import com.hzl.service.PermissionService;
import com.hzl.service.RoleService;
import com.hzl.utils.DateUtils;
import com.hzl.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('/roles')")
    public Result roles() {
        try{
            List<Role> roleList = roleService.getRoles();
            for(int i = 0;i < roleList.size();i++){
                List<Permission> permissionList = permissionService.getRightsByRoleId(roleList.get(i).getId());
                roleList.get(i).setChildren(permissionList);
            }
            return ResultFactory.buildSuccessResult(roleList,"获取角色列表成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }
    }

    @PostMapping("/roles/addRole")
    @PreAuthorize("hasAuthority('/roles/addRole')")
    public Result addRole(@RequestBody Role role) {
        role.setId(UUIDUtil.createUUID());
        role.setCreate_time(System.currentTimeMillis()/1000);
        try{
            int isok = roleService.addRole(role);
            if (isok > 0){
                return ResultFactory.buildSuccessResult(role,"添加角色成功");
            } else {
                return ResultFactory.buildFailResult("添加角色失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }

    }

    @GetMapping("/roles/getRoleById")
    @PreAuthorize("hasAuthority('/roles/getRoleById')")
    public Result getRoleById(String id){
        if (id == null || id.equals("")){
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        try{
            Role role = roleService.getRoleById(id);
            if(role != null){
                return ResultFactory.buildSuccessResult(role,"角色查询成功");
            } else {
                return ResultFactory.buildFailResult("角色查询失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }

    }

    @PutMapping("/roles/updateRoleById")
    @PreAuthorize("hasAuthority('/roles/updateRoleById')")
    public Result updateRoleById(@RequestBody Role role){
        if (role.getId() == null || role.getId().equals("")){
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        if (role.getRole_name() == null || role.getRole_name().equals("")){
            return ResultFactory.buildFailResult("角色名称不能为空");
        }
        role.setUpdate_time(System.currentTimeMillis()/1000);
        try{
            int isok = roleService.updateRole(role);
            if(isok > 0){
                return ResultFactory.buildSuccessResult(null,"角色修改成功");
            } else {
                return ResultFactory.buildFailResult("角色修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }

    }

    @DeleteMapping("/roles/delRoleById")
    @PreAuthorize("hasAuthority('/roles/delRoleById')")
    public Result delRoleById(String id){
        if(id == null || id.equals("")){
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        try{
            int isok1 = roleService.delRpById(id);
            int isok2 = roleService.delRmById(id);
            int isok = roleService.delRoleById(id);
            if(isok > 0){
                return ResultFactory.buildSuccessResult(null,"删除角色成功");
            }else {
                return ResultFactory.buildFailResult("删除角色失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }

    }

    @PostMapping("/roles/addRu")
    @PreAuthorize("hasAuthority('/rights/addRu')")
    public Result addRp(String roleId,String userId) {
        if(roleId == null || roleId.equals("")){
            return ResultFactory.buildFailResult("角色id不能为空");
        }
        if(userId == null || userId.equals("")){
            return ResultFactory.buildFailResult("用户id不能为空");
        }
        try{
            int isok1 = roleService.delRuById(userId);
            int isok = roleService.addRu(roleId,userId);
            if (isok > 0){
                return ResultFactory.buildSuccessResult(null,"更新成功");
            } else {
                return ResultFactory.buildFailResult("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }
    }

    @GetMapping("/roles/getAllRoleName")
    public Result getAllRoleName() {
        try{
            List<Role> roleList = roleService.getAllRoleName();
            return ResultFactory.buildSuccessResult(roleList,"查询成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("出现异常");
        }

    }
}
