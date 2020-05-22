package com.hzl.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    private Integer order_id;
    private String user_id;
    private String order_number;
    private BigDecimal order_price;
    private String order_pay;
    private String is_send;
    private String trade_no;
    private String order_fapiao_title;
    private String order_fapiao_company;
    private String order_fapiao_content;
    private String consignee_addr;
    private String pay_status;
    private Long create_time;
    private Long update_time;
}
