package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectAll(User user);

    void insert(User user);

    @Select("select * from `user` where username = #{username}")
    User selectByUsername(String username);

    void deleteById(Integer id);

    void updateById(User user);

    User selectById(Integer id);
}
