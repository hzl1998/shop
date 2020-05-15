package com.hzl.service;

import com.hzl.entity.Role;
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

    public int addRole(Role role){
        return roleMapper.addRole(role);
    }

    public Role getRoleById(String id) {
        return roleMapper.getRoleById(id);
    }

    public int updateRole(Role role){
        return roleMapper.updateRole(role);
    }

    public int delRoleById(String id) {
        return roleMapper.delRoleById(id);
    }

    public int delRpById(String id) {
        return roleMapper.delRpById(id);
    }

    public int delRuById(String userId) {
        return roleMapper.delRuById(userId);
    }

    public int addRu(String roleId, String userId) {
        return roleMapper.addRu(roleId,userId);
    }

    public List<Role> getAllRoleName() {
        return roleMapper.getAllRoleName();
    }

    public int delRmById(String id) {
        return roleMapper.delRmById(id);
    }
}
