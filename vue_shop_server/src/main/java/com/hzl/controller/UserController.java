package com.hzl.controller;

import com.hzl.entity.PageInfo;
import com.hzl.entity.UserDto;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.UserService;
import com.hzl.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
