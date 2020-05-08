package com.hzl.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private char sex;
    private char enabled;
    private String salt;
    private Timestamp createTime;
    private Timestamp updateTime;
}
