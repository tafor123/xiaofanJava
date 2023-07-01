package com.example.demo02.service;



import com.example.demo02.controller.dto.LoginDTO;
import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.controller.request.LoginRequest;
import com.example.demo02.controller.request.PasswordRequest;
import com.example.demo02.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IAdminService {

    List<Admin> list();

    PageInfo<Admin> page(BaseRequest baseRequest);

    void save(Admin obj);

    Admin getById(Integer id);

    void update(Admin obj);

    void deleteById(Integer id);

    LoginDTO login(LoginRequest request);

    void changePass(PasswordRequest request);

}
