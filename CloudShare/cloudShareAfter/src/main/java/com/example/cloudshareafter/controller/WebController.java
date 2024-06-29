package com.example.cloudshareafter.controller;

import cn.hutool.core.util.StrUtil;
import com.example.cloudshareafter.commen.AuthAccess;
import com.example.cloudshareafter.commen.Result;
import com.example.cloudshareafter.entity.User;
import com.example.cloudshareafter.exception.ServiceException;
import com.example.cloudshareafter.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 功能：登录与注册功能
 * 作者：程序员子凡
 * 日期：2023/9/20 23:15
 */
@RestController
public class WebController {
    @Resource
    UserService userService;


    /**
     * 登录接口
     */
    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if (StrUtil.isBlank(user.getUsername())||StrUtil.isBlank(user.getPassword())){
            return Result.error("输入数据不合法");
        }
        user = userService.login(user);
        return Result.success(user);
    }

    /**
     * 注册接口
     */
    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user){
       if(StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
           throw new ServiceException("输入不合法");
       }
        user = userService.register(user);
        return Result.success(user);
    }
}
