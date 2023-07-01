package com.example.demo02.mapper;



import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.controller.request.UserPageRequest;
import com.example.demo02.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    //也可以通过mybatis官网示例
   // @Select("select * from user")
    List<User> list();

    List<User> listByCondition(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id);

    void updateById(User user);

    void deleteById(Integer id);

    User getByNo(String user_no);

}
