package com.hzl.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Permission {
    private String id;
    private String url;
    private String name;
    private Integer level;
    private String path;
    private String parentId;
    private Integer checked;
    private List<Permission> children;
}
