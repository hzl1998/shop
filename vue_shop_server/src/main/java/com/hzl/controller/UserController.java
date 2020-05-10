package com.hzl.controller;

import com.hzl.entity.PageInfo;
import com.hzl.entity.UserDto;
import com.hzl.result.Result;
import com.hzl.result.ResultFactory;
import com.hzl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
