package com.example.demo02.mapper;


import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.controller.request.PasswordRequest;
import com.example.demo02.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {

    //也可以通过mybatis官网示例
   // @Select("select * from user")
    List<Admin> list();

    List<Admin> listByCondition(BaseRequest baseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void updateById(Admin admin);

    void deleteById(Integer id);

    Admin getByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    int updatePassword(PasswordRequest request);

    Admin getByUsername(String username);
}
