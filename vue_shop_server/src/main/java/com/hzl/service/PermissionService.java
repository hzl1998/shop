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
}
