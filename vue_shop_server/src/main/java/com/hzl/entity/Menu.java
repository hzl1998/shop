package com.hzl.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Menu {
    private String id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String icon;
    private String parentId;
    private String enabled;
    private Integer checked;
    private List<Menu> children;
}
