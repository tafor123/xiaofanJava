package com.example.springboot.controller;

import com.example.springboot.commen.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 功能：提供接口返回数据
 * 作者：程序员子凡
 * 日期：2023/9/1 8:38
 */
@RestController
@RequestMapping("/web")
public class WebController {
    @GetMapping("/hello")
    public Result hello(String name){
        return Result.success(name);
    }

    @PostMapping("/post")
    public Result post(@RequestBody Obj obj){
        return Result.success(obj);
    }

    @PutMapping("/put")
    public Result put(@RequestBody Obj obj){
        return Result.success(obj);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(id);
    }
}
