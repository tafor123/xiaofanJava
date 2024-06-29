package com.example.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/11/8 16:56
 */
@Data
public class Comment {
    /** ID */
    private Integer id;
    /** 评论 */
    private String content;
    /** 评分 */
    private Double star;
    /** 用户ID */
    private Integer userId;
    /** 商家iD */
    private Integer businessId;
    /** 商品ID */
    private Integer goodsId;
    /** 订单ID */
    private Integer orderId;

    private String time;
    /** 商家名称 */
    private String businessName;
    /** 用户名称 */
    private String userName;
    /** 订单编号 */
    private String orderNo;
}
