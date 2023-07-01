package com.example.demo02.mapper;


import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.controller.request.LoginRequest;
import com.example.demo02.controller.request.PasswordRequest;
import com.example.demo02.entity.Category;
import com.example.demo02.entity.Category;
import com.example.demo02.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //也可以通过mybatis官网示例
    // @Select("select * from user")
    List<Category> list();

    List<Category> listByCondition(BaseRequest baseRequest);

    void save(Category obj);

    Category getById(Integer id);

    void updateById(Category admin);

    void deleteById(Integer id);


}
