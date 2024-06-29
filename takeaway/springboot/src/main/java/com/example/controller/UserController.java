package com.example.controller;

import com.example.common.Result;
import com.example.entity.User;
import com.example.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/10/28 22:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        userService.add(user);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id){
        userService.deleteById(id);
        return Result.success();
    }


    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
       userService.deleteBatch(ids);
        return Result.success();
    }


    /**
     * 修改
     */
    @PutMapping("/update")
    public Result updateById(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }

    /**
     * 查询所有用户
     */
    @GetMapping("/selectAll")
    public Result selectAll(@RequestBody User user){
        List<User> list = userService.selectAll(user);
        return Result.success(list);
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public  Result selectById(@PathVariable Integer id){
        User list = userService.selectById(id);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public  Result selectByPage(User user,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize
                                ){
        PageInfo<User> pageInfo = userService.selectByPage(user,pageNum,pageSize);
        return Result.success(pageInfo);
    }

}
