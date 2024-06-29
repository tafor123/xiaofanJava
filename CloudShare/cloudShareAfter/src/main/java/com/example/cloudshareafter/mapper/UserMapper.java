package com.example.cloudshareafter.mapper;

import com.example.cloudshareafter.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {

    @Insert("insert into `user` (username,password,name,phone,address,avatar,role,email)" +
            "values (#{username},#{password},#{name},#{phone},#{address},#{avatar},#{role},#{email})")
    void insert(User user);

    @Update("update `user` set username= #{username}, password=#{password}," +
            "name=#{name},phone=#{phone},address=#{address},avatar=#{avatar}, " +
            "email=#{email} where id=#{id}")
    void update(User user);

    @Delete("delete from `user` where id=#{id}")
    void delete(Integer id);

    @Select("select * from `user` where  id=#{id} order by id desc")
    User select(Integer id);


    @Select("select * from `user` order by id desc ")
    List<User> selectAll();

    @Select("select * from `user` where username=#{username} order by id desc")
    User selectByUsername(String user);


    @Select("select * from `user` where username like concat('%', #{username}, '%') " +
            "and name like concat('%',#{name},'%') order by id desc limit #{skipNum}, #{pageSize} ")
    List<User> selectByPage(@Param("skipNum") Integer skipNum, @Param("pageSize") Integer pageSize,
                            @Param("username") String username, @Param("name") String name);

    @Select("select count(id) from `user` where username like concat('%', #{username}, '%') " +
            "and name like concat('%',#{name},'%') order by id desc ")
    int selectCountByPage(@Param("username") String username, @Param("name") String name);
}
