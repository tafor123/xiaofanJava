package com.example.springboot.dao;



import com.example.springboot.entity.User;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserDao extends Mapper<User> {

}
