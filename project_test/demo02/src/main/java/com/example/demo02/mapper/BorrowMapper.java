package com.example.demo02.mapper;


import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.entity.Book;
import com.example.demo02.entity.Borrow;
import com.example.demo02.entity.Retur;
import com.example.demo02.mapper.po.BorrowReturCountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BorrowMapper {

    //也可以通过mybatis官网示例
    // @Select("select * from user")
    List<Borrow> list();

    List<Borrow> listByCondition(BaseRequest baseRequest);

    List<Retur> listReturByCondition(BaseRequest baseRequest);

    void save(Borrow obj);

    void saveRetur(Retur obj);

    Borrow getById(Integer id);

    void updateById(Borrow user);

    void deleteById(Integer id);

    void deleteReturById(Integer id);

    void updateStatus(String status, Integer id);

    List<BorrowReturCountPO> getCountByTimeRange(@Param("timeRange") String timeRange, @Param("type") int type);  // 1 borrow  2 return

}
