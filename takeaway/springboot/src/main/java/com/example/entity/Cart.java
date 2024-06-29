package com.example.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/11/20 16:42
 */
@Data
public class Cart {
    /** ID */
    private Integer id;
    /** 商品ID */
    private Integer goodsId;
    /** 数量 */
    private Integer num;
    /** 用户ID */
    private Integer userId;
    private Integer businessId;

    private  Goods goods;

}
