package com.hzl.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageInfo {
	// 本页实际记录数
    private int count;
	// 每一页显示多少
    private int showCount;
    // 当前页数
    private int currentPage;
    // 总页数
    private int totalPage;
    // 总记录数
    private int totalResult;
    //分页信息结果集
    private List resultList; 
    
    private Map<String,Object> resultMap;
}
