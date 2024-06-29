package com.example.cloudshareafter.entity;

import lombok.Data;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/20 16:56
 */
@Data

public class User {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String address;
    private String avatar;
    private String role;
    private String email;

    private String token;

}
