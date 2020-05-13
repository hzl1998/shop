package com.hzl.mapper;

import com.hzl.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> getRights(@Param(value="page")Integer page, @Param(value="rows")Integer rows);

    int getRightsCount();

    int delPermission(@Param(value="roleId")String roleId,@Param(value="rightId") String rightId);

    List<Permission> selectRights();

    int addRp(String roleId, String[] rightsId);

    Permission getRightsById(String id);

    List<Permission> getMinRights(String id);

    List<Permission> getNextRights(String id);

    int delListPermission(String roleId, String[] rightsId);
}
