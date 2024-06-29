package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.commen.AuthAccess;
import com.example.springboot.commen.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：提供接口返回数据
 * 作者：程序员子凡
 * 日期：2023/9/1 8:38
 */
@RestController

public class WebController {
    @Resource
    UserService userService;

    @AuthAccess
    @GetMapping("/")
    public Result hello(String name){
        return Result.success("success");
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername())|| StrUtil.isBlank(user.getPassword())){
            return Result.error("输入不合法");
        }
        user = userService.login(user);
        return Result.success(user);
    }

    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if(StrUtil.isBlank(user.getUsername())|| StrUtil.isBlank(user.getPassword()) || StrUtil.isBlank(user.getRole())){
            return Result.error("输入不合法");
        }
        if(user.getUsername().length()>10 || user.getPassword().length()>20){
            return Result.error("用户名或密码过长");
        }
        if(user.getUsername().length()<2 || user.getPassword().length()<2){
            return Result.error("用户名或密码过短");
        }
        //从数据库查询是否存在该用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",user.getUsername());
        User dbUser = userService.getOne(queryWrapper);
        if (dbUser != null){
            return Result.error("用户已存在");
        }
        user = userService.register(user);
        return Result.success(user);
    }

    /**
     *  重置密码
     */
    @AuthAccess
    @PutMapping("/password")
    public Result password(@RequestBody User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPhone())) {
            return Result.error("数据输入不合法");
        }
        userService.resetPassword(user);
        return Result.success();
    }
}
