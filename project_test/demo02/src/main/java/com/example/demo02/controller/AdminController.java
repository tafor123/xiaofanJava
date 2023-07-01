package com.example.demo02.controller;

import com.example.demo02.common.Result;
import com.example.demo02.controller.dto.LoginDTO;
import com.example.demo02.controller.request.AdminPageRequest;
import com.example.demo02.controller.request.LoginRequest;
import com.example.demo02.controller.request.PasswordRequest;
import com.example.demo02.entity.Admin;
import com.example.demo02.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin //解决跨域问题
@RestController //标识
@RequestMapping("/admin") //一级前缀
public class AdminController {

    @Autowired
    IAdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginRequest request){
        LoginDTO login = adminService.login(request);
        return Result.success(login);
    }

    @PutMapping("/password")
    public Result password(@RequestBody PasswordRequest request){
       adminService.changePass(request);
        return Result.success();
    }

    @PostMapping("/save")
    public Result save(@RequestBody  Admin obj){
        adminService.save(obj);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update(@RequestBody  Admin obj){
        adminService.update(obj);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/{id}") //二级前缀
    public Result getById(@PathVariable Integer id){
       Admin obj =adminService.getById(id);
        return Result.success(obj);
    }

    @GetMapping("/list") //二级前缀
    public Result list(){
        List<Admin> list =adminService.list();
        return Result.success(list);
    }

    @GetMapping("/page") //二级前缀
    public Result page(AdminPageRequest pageRequest){

        return Result.success( adminService.page(pageRequest));
    }

}
