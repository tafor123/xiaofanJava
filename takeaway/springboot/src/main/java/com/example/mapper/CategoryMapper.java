package com.example.mapper;

import com.example.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/11/2 23:55
 */
@Mapper
public interface CategoryMapper {

    int insert(Category category);

    int deleteById(Integer id);

    int updateById(Category category);


    Category selectById(Integer id);

    List<Category> selectAll(Category category);
}
