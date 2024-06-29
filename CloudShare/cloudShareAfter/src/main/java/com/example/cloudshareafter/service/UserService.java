package com.example.cloudshareafter.service;

import com.example.cloudshareafter.entity.Page;
import com.example.cloudshareafter.entity.User;
import com.example.cloudshareafter.exception.ServiceException;
import com.example.cloudshareafter.mapper.UserMapper;
import com.example.cloudshareafter.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/20 17:37
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void insertUser(User user) {
        User dbUser=userMapper.selectByUsername(user.getUsername());
        //判断输入账户是否存在
        if(dbUser != null){
            throw  new ServiceException("该用户名存在");
        }
        userMapper.insert(user);
    }

    public void updateUser(User user) {

        try {
            userMapper.update(user);
        } catch (DuplicateKeyException e) {
            throw new ServiceException("用户名已存在");
        }
    }

    public void deleteById(Integer id) {
        userMapper.delete(id);
    }

    public void deleteBatchById(List<Integer> ids) {
        for (Integer id : ids) {
            userMapper.delete(id);
        }
    }

    public User selectById(Integer id) {
      return  userMapper.select(id);
    }


    public List<User> selectAllByIds() {
        return userMapper.selectAll();
    }


    public User login(User user) {
        User dbUser=userMapper.selectByUsername(user.getUsername());
        //判断输入账户是否存在
        if(dbUser == null){
            throw  new ServiceException("该用户不存在");
        }
        if(!user.getPassword().equals(dbUser.getPassword())){
            throw new ServiceException("用户名或密码错误");
        }
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User register(User user) {
        User dbuser = userMapper.selectByUsername(user.getUsername());
        if(dbuser != null){
            throw new ServiceException("用户已存在");
        }
        user.setName(user.getUsername());
        userMapper.insert(user);
        return user;
    }

    public Page<User> selectByPage(Integer pageNum, Integer pageSize, String username, String name) {
        Integer skipNum =(pageNum-1) * pageSize; // 计算出来  1 -> 0,5    2 -> 5,5   3 -> 10,5
        Page<User> page = new Page<>();
        List<User> users = userMapper.selectByPage(skipNum,pageSize,username,name);
        Integer total = userMapper.selectCountByPage(username,name);
        page.setTotal(total);
        page.setList(users);
        return page;
    }
}
