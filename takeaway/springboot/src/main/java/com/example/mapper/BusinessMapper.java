package com.example.mapper;

import com.example.entity.Business;

import java.util.List;

public interface BusinessMapper {
    List<Business> selectAll(Business business);

    int insert(Business business);

    int deleteByID(Integer id);

    int updateById(Business business);


}
