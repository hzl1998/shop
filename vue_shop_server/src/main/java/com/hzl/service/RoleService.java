package com.hzl.service;

import com.hzl.entity.Role;
import com.hzl.mapper.PermissionMapper;
import com.hzl.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;

    public List<Role> getRoles() {
        return roleMapper.getRoles();
    }
}
