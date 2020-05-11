package com.hzl.service;

import com.hzl.entity.Permission;
import com.hzl.entity.UserDto;
import com.hzl.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user = this.userMapper.getUserByUserName(username);
        if (user == null) {
            return null;
        } else {
            List<Permission> permissionList = this.userMapper.findPermissionByUserId(user.getId());
            List<String> permissions = new ArrayList();
            permissionList.forEach((c) -> {
                permissions.add(c.getUrl());
            });
            String[] permissionArray = new String[permissions.size()];
            permissions.toArray(permissionArray);
            UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).authorities(permissionArray).build();
            return userDetails;
        }
    }

    public List<UserDto> getUsers(String username,Integer page,Integer rows){
        return userMapper.getUsers(username,page,rows);
    }

    public int getUsersCount(String username){
        return userMapper.getUsersCount(username);
    }

    public int updateUserEnabled(String userId,String enabled,long update_time){
        return userMapper.updateUserEnabled(userId,enabled,update_time);
    }
}
