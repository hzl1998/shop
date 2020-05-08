package com.hzl.entity;

import lombok.Data;

@Data
public class Permission {
    private String id;
    private String url;
    private String name;
    private String parentId;
}
