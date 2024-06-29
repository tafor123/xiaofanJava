package com.example.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/10/28 22:47
 */
@Data
public class User extends Account{
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String role;
    private String sex;
    private String phone;
}
