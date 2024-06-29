package com.example.mapper;

import com.example.entity.Banner;
import java.util.List;
/**
 * 功能：操作banner相关数据接口
 * 作者：程序员子凡
 * 日期：2023/11/7 22:41
 */
public interface BannerMapper {

    /**
     * 新增
     */
    int insert(Banner banner);

    /**
     * 删除
     */
    int deleteById(Integer id);

    /**
     * 修改
     */
    int updateById(Banner banner);

    /**
     * 根据ID查询
     */
    Banner selectById(Integer id);

    /**
     * 查询所有
     */
    List<Banner> selectAll(Banner banner);

}