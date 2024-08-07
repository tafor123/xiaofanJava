package com.example.springboot.service;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.User;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.utils.TokenUtils;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/3 17:16
 */
@Service //放在spring容器中
public class UserService extends ServiceImpl<UserMapper ,User> {

    @Resource
    UserMapper userMapper;


    @Override
    public boolean save(User entity) {
        if (StrUtil.isBlank(entity.getName())){
            entity.setName(entity.getUsername());
        }
        if (StrUtil.isBlank(entity.getPassword())){
            entity.setPassword("123"); //默认密码
        }
        if (StrUtil.isBlank(entity.getRole())){
            entity.setRole("用户"); //默认角色为用户1
        }
        return super.save(entity);
    }

    public User selectByUsername(String username){
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("username",username); // eq => == where username = #{username}
        //根据用户名查询数据库的信息
       return getOne(queryWrapper);
    }

    //验证用户账号是否合法
    public User login(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser == null){
            //抛出自定义异常
            throw new  ServiceException("账号或密码错误");
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("账号或密码错误");
        }
        //生成token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User register(User user) {
/*        User dbUser = selectByUsername(user.getUsername());
        if(dbUser != null){
            throw new ServiceException("用户名已存在");
        }*/
        user.setName(user.getUsername());
        userMapper.insert(user);
        return user;
    }

    public void resetPassword(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if(dbUser == null){
            throw new ServiceException("用户不存在");
        }
        if(!user.getPhone().equals(dbUser.getPhone())){
            throw new ServiceException("验证错误");
        }
        dbUser.setPassword("123");
        updateById(dbUser);
    }

    public void userid(){
        User currentUser = TokenUtils.getCurrentUser();
        System.out.println(currentUser);
    }

}
