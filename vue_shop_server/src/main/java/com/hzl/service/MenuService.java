package com.hzl.service;

import com.hzl.entity.Menu;
import com.hzl.entity.Rm;
import com.hzl.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    public List<Menu> getMenusByUserId(String userId) {
        return menuMapper.selectMenusByUserId(userId);
    }

    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

    public List<Menu> selectMenusByRoleId(String roleId) {
        return menuMapper.selectMenusByRoleId(roleId);
    }

    public int delRm(String roleId) {
        return menuMapper.delRm(roleId);
    }

    public int addRm(Rm rm) {
        return menuMapper.addRm(rm);
    }
}
