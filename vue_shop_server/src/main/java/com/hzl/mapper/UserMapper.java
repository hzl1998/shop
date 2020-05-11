package com.hzl.mapper;

import com.hzl.entity.Permission;
import com.hzl.entity.UserDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserDto getUserByUserName(@Param(value="username")String username);

    List<Permission> findPermissionByUserId(@Param(value="userId")String userId);

    List<UserDto> getUsers(@Param(value="username")String username,
                           @Param(value="page")Integer page,
                           @Param(value="rows")Integer rows);

    int getUsersCount(@Param(value="username")String username);

    int updateUserEnabled(@Param(value="userId")String userId,
                          @Param(value="enabled")String enabled,
                          @Param(value="update_time")long update_time);

    int addUser(UserDto userDto);

    UserDto getUserById(@Param(value="id")String id);

    int updateUserById(UserDto userDto);
}
