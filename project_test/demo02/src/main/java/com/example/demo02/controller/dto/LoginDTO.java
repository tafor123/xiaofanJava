package com.example.demo02.controller.dto;

import lombok.Data;

//接收登录成功返回信息
@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String email;
    private String phone;
    private String token;
}
