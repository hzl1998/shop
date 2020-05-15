package com.hzl.mapper;

import com.hzl.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    List<Role> getRoles();

    int addRole(Role role);

    Role getRoleById(@Param(value="id")String id);

    int updateRole(Role role);

    int delRoleById(@Param(value="id")String id);

    int delRpById(@Param(value="id")String id);

    int delRuById(@Param(value="userId")String userId);

    int addRu(@Param(value="roleId")String roleId, @Param(value="userId")String userId);

    List<Role> getAllRoleName();

    int delRmById(String id);
}
