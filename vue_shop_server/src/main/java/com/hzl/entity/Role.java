package com.hzl.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Role {
    private String id;
    private String role_name;
    private String description;
    private long create_time;
    private long update_time;
    private List<Permission> children;
}
