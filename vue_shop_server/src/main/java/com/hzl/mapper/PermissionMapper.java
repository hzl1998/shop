package com.hzl.mapper;

import com.hzl.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> getRights(@Param(value="page")Integer page, @Param(value="rows")Integer rows);

    int getRightsCount();
}
