package com.hzl.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String email;
    private String phone;
    private String sex;
    private boolean enabled;
    private String salt;
    private long create_time;
    private long update_time;
    private String role_name;
}
