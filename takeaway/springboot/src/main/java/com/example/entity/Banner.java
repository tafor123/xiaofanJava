package com.example.entity;

import lombok.Data;

/**
 * 功能：广告信息
 * 作者：程序员子凡
 * 日期：2023/11/7 22:38
 */
@Data
public class Banner {
    /** ID */
    private Integer id;
    /** 图片 */
    private String img;
    /** 商家ID */
    private Integer businessId;

    private String businessName;

}
