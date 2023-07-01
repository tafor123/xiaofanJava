package com.example.demo02.mapper;


import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    //也可以通过mybatis官网示例
    // @Select("select * from user")
    List<Book> list();

    List<Book> listByCondition(BaseRequest baseRequest);

    void save(Book obj);

    Book getById(Integer id);

    void updateById(Book user);

    void deleteById(Integer id);

    Book getByBookNo(String book_no);

    void updateNumByNo(String book_no);

}
