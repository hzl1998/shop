package com.hzl.mapper;

import com.hzl.entity.Menu;
import com.hzl.entity.Rm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    List<Menu> selectMenusByUserId(@Param(value="userId")String userId);

    List<Menu> getAllMenus();

    List<Menu> selectMenusByRoleId(@Param(value="roleId")String roleId);

    int delRm(String roleId);

    int addRm(Rm rm);
}
