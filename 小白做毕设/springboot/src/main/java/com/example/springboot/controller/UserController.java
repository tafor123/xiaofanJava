package com.example.springboot.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.commen.AuthAccess;
import com.example.springboot.commen.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 功能：
 * 作者：程序员子凡
 * 日期：2023/9/3 17:16
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
//前端调用controller层
    @Autowired //拿数据
    UserService userService;

    /**
     * 新增用户信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody User user){
       try {
           userService.save(user);
       }catch (Exception e){
           if( e instanceof DuplicateKeyException){
               return Result.error("数据库出错");
           }else{
               return Result.error("系统错误");
           }
       }
        return Result.success();
    }

    /**
     * 修改用户信息
     */
    @PutMapping("/update")
        public Result update(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
        }


    /**
     * 删除用户信息
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        userService.removeById(id);
        return Result.success();
    }

    /**
     * 批量删除用户信息
     */
    @DeleteMapping("/delete/batch")
    public Result batchDelete(@RequestBody List<Integer> ids){
        userService.removeBatchByIds(ids);
        return Result.success();
    }

    /**
     * 查询全部用户信息
     */
    @AuthAccess
    @GetMapping("/selectAll")
    public Result selectAll(){
        List<User> users = userService.list(new QueryWrapper<User>().orderByDesc("id")); // select * from user order by id desc
        return Result.success(users);
    }




    /**
     * 根据主键查询用户信息
     * 单个查询，确定一个则用单个否则用多个
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id){
        User users = userService.getById(id);
        return Result.success(users);
    }





    /**
     * 分页多条件模糊查询用户信息
     * pageNum 当前的页码
     * pageSize 每页查询的个数
     */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam Integer pageNum ,@RequestParam Integer pageSize,
            @RequestParam String username, @RequestParam String name){
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(username),"username",username);
        queryWrapper.like(StrUtil.isNotBlank(name),"name",name);
        // select * from user where username like '%#{username}%' and name like '%#{name}%'
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);


        return Result.success(page);
    }

    /**
     * 批量导出数据
     */
    @GetMapping("/export")
    public void exportDate(@RequestParam(required = false) String username,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String ids,
                           HttpServletResponse response
    ) throws IOException {
        ExcelWriter writer = ExcelUtil.getWriter(true);

        List<User> list ;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(); //条件查询器
        if (StrUtil.isNotBlank(ids)){
            List<Integer> idsArr1 = Arrays.stream(ids.split(",")).map(Integer::valueOf).collect(Collectors.toList());
            queryWrapper.in("id",idsArr1);
        }else {
            // 第一种全部导出
            queryWrapper.like(StrUtil.isNotBlank(username),"username",username);
            queryWrapper.like(StrUtil.isNotBlank(name),"name",name);

        }
        list = userService.list(queryWrapper); //查询当前user表的所有数据

        writer.write(list,true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("用户信息表", "UTF-8") + ".xlsx");

        ServletOutputStream outputStream = response.getOutputStream();
        writer.flush(outputStream,true);

        outputStream.flush();
        outputStream.close();
        writer.close();
    }

    /**
     * 批量导入
     * @param
     * @return
     */
    @PostMapping("/import")
    public Result importData(MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<User> usersList = reader.readAll(User.class);
        //写入数据到数据库
        try {
            userService.saveBatch(usersList);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("文件批量导入数据出错");
        }
        return Result.success();

    }
}
