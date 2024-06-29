package com.example.entity;

import lombok.Data;

/**
 * 功能：商家实体类
 * 作者：程序员子凡
 * 日期：2023/10/26 23:43
 */
@Data
public class Business extends Account {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String role;
    private String phone;
    private String info;
    private String address;
    private String license;
    private String status;
    private String timeRange;
    private String type;
}
