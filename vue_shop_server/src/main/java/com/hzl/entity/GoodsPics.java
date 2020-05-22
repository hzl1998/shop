package com.hzl.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsPics {
    private Integer pics_id;
    private Integer goods_id;
    private String pics_big;
    private String pics_mid;
    private String pics_sma;
    private String pic;
}
