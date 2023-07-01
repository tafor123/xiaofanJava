package com.example.demo02.service;



import com.example.demo02.controller.request.BaseRequest;
import com.example.demo02.controller.request.UserPageRequest;
import com.example.demo02.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IUserService {

    List<User> list();

    PageInfo<User> page(BaseRequest baseRequest);

    void save(User user);

    User getById(Integer id);

    void update(User user);

    void deleteById(Integer id);

    void handleAccount(User user);
}
