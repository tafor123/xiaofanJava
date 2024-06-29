package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Business;
import com.example.service.AdminService;
import com.example.service.BusinessService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/10/26 23:48
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;

    @Resource
    private AdminService adminService;
    /**
     * 新增商家
     */
    @PostMapping("/add")
    public Result add(@RequestBody Business business){
        //数据校验
        if(ObjectUtil.isEmpty(business.getUsername()) || ObjectUtil.isEmpty(business.getPassword())){
           return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        businessService.addBusiness(business);
        return Result.success();
    }

    /**
     * 删除商家
     */
    @DeleteMapping("/delete/{id}")
    public Result del(@PathVariable Integer id ){
        businessService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        businessService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 修改商家
     */
    @PutMapping("/update")
    public Result update(@RequestBody Business business){
        businessService.update(business);
        return Result.success();
    }

    /**
     * 查询单个商家
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        Business business = businessService.selectById(id);
        return  Result.success(business);
    }

    /**
     * 查询所有商家
     */
    @GetMapping("/selectAll")
    public Result selectAll(Business business){
        List<Business> list = businessService.selectAll(business);
        return Result.success(list);
    }

    /**
     * 分页条件查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Business business,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<Business> pageInfo = businessService.selectPage(business,pageNum,pageSize);
        return Result.success(pageInfo);
    }

}
