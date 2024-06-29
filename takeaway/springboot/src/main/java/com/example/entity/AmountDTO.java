package com.example.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/11/24 23:39
 */
@Data
public class AmountDTO {
    //总价
    private BigDecimal amount;
    //折扣
    private BigDecimal discount;
    //优惠价格
    private BigDecimal actual;
}
