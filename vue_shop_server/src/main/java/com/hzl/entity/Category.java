package com.hzl.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {
    private Integer cat_id;
    private String cat_name;
    private Integer cat_pid;
    private Integer cat_level;
    private Integer cat_deleted;
    private String cat_icon;
    private Integer cat_src;
    private List<Category> children;
}
