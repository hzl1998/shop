package com.hzl.mapper;

import com.hzl.entity.Permission;
import com.hzl.entity.UserDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDto getUserByUserName(String username);

    List<Permission> findPermissionByUserId(String userId);

    List<UserDto> getUsers(String username,Integer page,Integer rows);

    int getUsersCount(String username);
}
