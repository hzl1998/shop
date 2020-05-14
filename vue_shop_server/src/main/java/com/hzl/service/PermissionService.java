package com.hzl.service;

import com.hzl.entity.Menu;
import com.hzl.entity.Permission;
import com.hzl.mapper.MenuMapper;
import com.hzl.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    public List<Permission> getRights(Integer page, Integer rows) {
        return permissionMapper.getRights(page,rows);
    }

    public int getRightsCount() {
        return permissionMapper.getRightsCount();
    }

    public int delPermission(String roleId, String rightId) {
        return permissionMapper.delPermission(roleId,rightId);
    }

    public List<Permission> selectRights() {
        return permissionMapper.selectRights();
    }

    public int addRp(String roleId, String[] rightsId) {
        return permissionMapper.addRp(roleId,rightsId);
    }

    public List<Permission> getRightsByRoleId(String roleId) {
        return permissionMapper.getRightsByRoleId(roleId);
    }

    public List<Permission> getRightsByPid(String pid) {
        return permissionMapper.getRightsByPid(pid);
    }

    public Permission getRightById(String id) {
        return permissionMapper.getRightById(id);
    }

    public int delListPermission(String roleId, String[] rightsId) {
        return permissionMapper.delListPermission(roleId,rightsId);
    }
}
