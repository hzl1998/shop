package com.hzl.controller;

import com.hzl.entity.PageInfo;
import com.hzl.entity.UserDto;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.UserService;
import com.hzl.utils.DateUtils;
import com.hzl.utils.MD5;
import com.hzl.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public Result users(String username,Integer page,Integer rows){
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 10;
        }
        //第几页
        Integer currentPage = page;

        page = (page - 1) * rows;
        //总条数
        int total = userService.getUsersCount(username);
        //分页数据
        List<UserDto> userList = userService.getUsers(username,page,rows);
        // 计算总页数
        int count=0;
        if(total%rows==0){
            count=total/rows;
        }else{
            count=total/rows+1;
        }

        if(userList != null && userList.size() > 0){
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCount(userList.size()); // 本页实际记录数
            pageInfo.setCurrentPage(currentPage); // 第几页
            pageInfo.setResultList(userList); // 数据
            pageInfo.setTotalPage(count); // 总页数
            pageInfo.setTotalResult(total); // 总记录数
            return ResultFactory.buildSuccessResult(pageInfo,"获取用户列表成功");
        } else {
            return ResultFactory.buildFailResult("获取用户列表失败");
        }
    }

    @PutMapping("/users/status")
    public Result usersStatus(String userId,String enabled){
        if(userId == null || userId.equals("")){
            return ResultFactory.buildFailResult("用户id不能为空");
        }
        if(enabled == null || enabled.equals("")){
            return ResultFactory.buildFailResult("用户状态不能为空");
        }
        // UTC时间
        long time = DateUtils.getUTCTime();
        int isok = userService.updateUserEnabled(userId,enabled,time);
        if(isok > 0){
            return ResultFactory.buildSuccessResult(null,"修改状态成功");
        }else {
            return ResultFactory.buildFailResult("修改用户状态失败");
        }
    }

    @PostMapping("/users/addUser")
    public Result addUser(@RequestBody UserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(userDto.getUsername() == null || userDto.getUsername().equals("")){
            return ResultFactory.buildFailResult("用户名不能为空");
        }
        if(userDto.getPassword() == null || userDto.getPassword().equals("")){
            return ResultFactory.buildFailResult("密码不能为空");
        }
        if(userDto.getEmail() == null || userDto.getEmail().equals("")){
            return ResultFactory.buildFailResult("邮箱地址不能为空");
        }
        if(userDto.getPhone() == null || userDto.getPhone().equals("")){
            return ResultFactory.buildFailResult("手机号码不能为空");
        }
        //UUID
        userDto.setId(UUIDUtil.createUUID());
        // UTC时间
        userDto.setCreate_time(DateUtils.getUTCTime());
        //加密盐
        String salt = UUIDUtil.createUUID();
        userDto.setSalt(salt);
        //密码
        String pwd = MD5.getEncryptedPwd(userDto.getPassword(),salt);
        userDto.setPassword(pwd);
        int isok = userService.addUser(userDto);
        if(isok > 0){
            return ResultFactory.buildSuccessResult(null,"添加用户成功");
        }else {
            return ResultFactory.buildFailResult("添加用户失败");
        }
    }

    @GetMapping("/users/getUserById")
    public Result getUserById(String id){
        if (id == null || id.equals("")){
            return ResultFactory.buildFailResult("用户id不能为空");
        }
        UserDto userDto = userService.getUserById(id);
        if(userDto != null){
            return ResultFactory.buildSuccessResult(userDto,"用户查询成功");
        } else {
            return ResultFactory.buildFailResult("用户查询失败");
        }
    }

    @PutMapping("/users/updateUserById")
    public Result updateUserById(@RequestBody UserDto userDto){
        if(userDto.getId() == null || userDto.getId().equals("")){
            return ResultFactory.buildFailResult("用户id不能为空");
        }
        if(userDto.getEmail() == null || userDto.getEmail().equals("")){
            return ResultFactory.buildFailResult("邮箱地址不能为空");
        }
        if(userDto.getPhone() == null || userDto.getPhone().equals("")){
            return ResultFactory.buildFailResult("手机号码不能为空");
        }
        userDto.setUpdate_time(DateUtils.getUTCTime());
        int isok = userService.updateUserById(userDto);
        if(isok > 0){
            return ResultFactory.buildSuccessResult(null,"修改用户信息成功");
        }else {
            return ResultFactory.buildFailResult("修改用户信息失败");
        }
    }

    @DeleteMapping("/users/delUserById")
    public Result delUserById(String id){
        if(id == null || id.equals("")){
            return ResultFactory.buildFailResult("用户id不能为空");
        }
        int isok = userService.delUserById(id);
        if(isok > 0){
            return ResultFactory.buildSuccessResult(null,"删除用户成功");
        }else {
            return ResultFactory.buildFailResult("删除用户成功");
        }
    }
}
