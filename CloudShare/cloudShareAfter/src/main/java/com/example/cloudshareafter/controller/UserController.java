package com.example.cloudshareafter.controller;


import com.example.cloudshareafter.commen.Result;
import com.example.cloudshareafter.entity.Page;
import com.example.cloudshareafter.entity.User;
import com.example.cloudshareafter.exception.ServiceException;
import com.example.cloudshareafter.service.UserService;
import com.example.cloudshareafter.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/20 17:02
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;



    /**
     *新增用户信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user){
        try {
            userService.insertUser(user);
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException){
                return Result.error("插入数据失败");
            }else {
                return Result.error("系统错误");
            }
        }
        return Result.success();
    }

    /**
     *修改用户信息
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

    /**
     *删除单个用户信息
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        User currentUser = TokenUtils.getCurrentUser();
        if(currentUser != null && currentUser.getId() != null && id.equals(currentUser.getId())){
            throw new ServiceException("不能删除当前的用户");
        }
        userService.deleteById(id);
        return Result.success();
    }

    /**
     *删除多个用户信息
     */
    @DeleteMapping("/deleteMore/batch")
    public Result deleteMore(@RequestBody List<Integer> ids){
        User currentUser = TokenUtils.getCurrentUser();
        if(currentUser != null && currentUser.getId() != null && ids.contains(currentUser.getId())){
            throw new ServiceException("不能删除当前的用户");
        }
        userService.deleteBatchById(ids);
        return Result.success();
    }

    /**
     *查询单个用户信息
     */

    @GetMapping("/select/{id}")
    public Result select(@PathVariable Integer id){
      User user =  userService.selectById(id);
        return  Result.success(user);
    }

    /**
     *查询多个用户信息
     */
    @GetMapping("/selectAll")
    public Result selectAll(){
      List<User> userList = userService.selectAllByIds();
        return Result.success(userList);
    }

    /**
     * 多条件模糊查询用户信息
     * pageNum 当前的页码
     * pageSize 每页查询的个数
     */

    @GetMapping("/selectByPage")
    public Result selectByPage(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam String username,
            @RequestParam String name
    ){
     Page<User> page = userService.selectByPage(pageNum,pageSize,username,name);
     return Result.success(page);
    }


}
