package com.example.service;

import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：商品分类业务处理
 * 作者：程序员子凡
 * 日期：2023/11/2 23:54
 */

@Service
public class CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private BusinessService businessService;
    /**
     * 新增
     */
    public void add(Category category) {
        //权限校验
        businessService.checkBusinessAuth();

        Account currentUser = TokenUtils.getCurrentUser();
        if(RoleEnum.BUSINESS.name().equals(currentUser.getRole())){
            category.setBusinessId(currentUser.getId());
        }
        categoryMapper.insert(category);
    }

    /**
     * 删除
     */
    public void deleteById(Integer id) {
        //权限校验
        businessService.checkBusinessAuth();
        categoryMapper.deleteById(id);
    }

    /**
     * 批量删除
     */

    public void deleteBatch(List<Integer> ids) {
        //权限校验
        businessService.checkBusinessAuth();
        for (Integer id : ids){
            categoryMapper.deleteById(id);
        }
    }

    /**
     * 修改
     */
    public void updateById(Category category) {
        //权限校验
        businessService.checkBusinessAuth();
        categoryMapper.updateById(category);
    }


    public Category selectById(Integer id) {
        return categoryMapper.selectById(id);
    }

    public List<Category> selectAll(Category category) {
        // 拿到当前的登录用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) {  // 如果是商家的话   只能查询自己的数据
            category.setBusinessId(currentUser.getId());  // 设置商家自己的Id作为查询条件
        }
        return categoryMapper.selectAll(category);
    }

    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        // 拿到当前的登录用户信息
        Account currentUser = TokenUtils.getCurrentUser();
        String role = currentUser.getRole();
        if (RoleEnum.BUSINESS.name().equals(role)) {  // 如果是商家的话   只能查询自己的数据
            category.setBusinessId(currentUser.getId());  // 设置商家自己的Id作为查询条件
        }
        PageHelper.startPage(pageNum,pageSize);
        List<Category> list = categoryMapper.selectAll(category);
        return PageInfo.of(list);
    }
}
