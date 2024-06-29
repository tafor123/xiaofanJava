package com.example.springboot.service;

import com.example.springboot.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/10/23 22:05
 */
@Service
public class UserService {

    @Resource
    UserDao userDao;

}
