package com.example.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/11/2 23:52
 */
@Data
public class Category {
    private Integer id;
    /** 名称 */
    private String name;
    /** 商家ID */
    private Integer businessId;
    //商家名称
    private String businessName;
}
