package com.hzl.service;

import com.hzl.entity.Menu;
import com.hzl.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    public List<Menu> getMenus() {
        return menuMapper.selectMenus();
    }
}
