package com.hzl.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Goods {
    private Integer goods_id;
    private String goods_name;
    private BigDecimal goods_price;
    private Integer goods_number;
    private Integer goods_weight;
    private String cat_id;
    private String goods_introduce;
    private String goods_big_logo;
    private String goods_small_logo;
    private String is_del;
    private long add_time;
    private long upd_time;
    private long delete_time;
    private String cat_one_id;
    private String cat_two_id;
    private String cat_three_id;
    private Integer hot_mumber;
    private Integer is_promote;
    private Integer goods_state;
    private String[] pics;
    private String[] attrs;
}
