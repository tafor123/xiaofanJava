package com.example.entity;

import lombok.Data;

/**
 * 功能：收藏信息
 * 作者：程序员子凡
 * 日期：2023/11/8 16:41
 */
@Data
public class Collect {

    /** ID */
    private Integer id;
    /** 商家ID */
    private Integer businessId;

    /** 用户ID */
    private Integer userId;
    /** 收藏时间 */
    private String time;

    private String businessName;
    private String userName;
}
